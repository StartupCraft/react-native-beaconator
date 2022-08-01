package io.startupcraft.beaconator;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@ReactModule(name = Constants.MODULE_NAME)
public class BeaconatorModule extends ReactContextBaseJavaModule implements RangeNotifier {
  private static ReactApplicationContext reactAppContext;
  private Context applicationContext;
  private BeaconManager beaconManager;

  public BeaconatorModule(ReactApplicationContext reactAppContext) {
    super(reactAppContext);
    this.reactAppContext = reactAppContext;
  }

  @Override
  public void initialize() {
    this.applicationContext = this.reactAppContext.getApplicationContext();
    this.beaconManager = BeaconManager.getInstanceForApplication(applicationContext);

    beaconManager.getBeaconParsers().clear();
    beaconManager.getBeaconParsers().add(
      new BeaconParser().setBeaconLayout(Constants.PARSER_IBEACON_LAYOUT)
    );

    beaconManager.addRangeNotifier(this);
    beaconManager.setEnableScheduledScanJobs(false);
    beaconManager.setRegionStatePersistenceEnabled(false);
  }

  @Override
  @NonNull
  public String getName() {
    return Constants.MODULE_NAME;
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put("PARSER_IBEACON_LAYOUT", Constants.PARSER_IBEACON_LAYOUT);
    constants.put("PARSER_ESTIMOTE_LAYOUT", Constants.PARSER_ESTIMOTE_LAYOUT);
    constants.put("PARSER_ALTBEACON_LAYOUT", Constants.PARSER_ALTBEACON_LAYOUT);
    constants.put("PARSER_EDDYSTONE_TLM_LAYOUT", Constants.PARSER_EDDYSTONE_TLM_LAYOUT);
    constants.put("PARSER_EDDYSTONE_UID_LAYOUT", Constants.PARSER_EDDYSTONE_UID_LAYOUT);
    constants.put("PARSER_EDDYSTONE_URL_LAYOUT", Constants.PARSER_EDDYSTONE_URL_LAYOUT);
    return constants;
  }

  /***********************************************************************************************
   * Ranging
   **********************************************************************************************/
  @ReactMethod
  public void startRanging(String regionId, String beaconUUID, Promise promise) {
    Region region = createRegion(regionId, beaconUUID);

    try {
      beaconManager.startRangingBeacons(region);
      promise.resolve(null);
    } catch (Exception e) {
      Log.e(Constants.TAG, "Unsuccessful start ranging, error: ", e);
      promise.reject(e);
    }
  }

  @ReactMethod
  public void stopRanging(String regionId, String beaconUUID, Promise promise) {
    Region region = createRegion(regionId, beaconUUID);

    try {
      beaconManager.stopRangingBeacons(region);
      promise.resolve(null);
    } catch (Exception e) {
      Log.e(Constants.TAG, "Unsuccessful stop ranging, error: ", e);
      promise.reject(e);
    }
  }

  @ReactMethod
  public void requestStateForRegion(String regionId, String beaconUUID, int minor, int major) {
    Region region = createRegion(
      regionId,
      beaconUUID,
      String.valueOf(minor).equals("-1") ? "" : String.valueOf(minor),
      String.valueOf(major).equals("-1") ? "" : String.valueOf(major)
    );
    beaconManager.requestStateForRegion(region);
  }

  @Override
  public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
    sendEvent("beaconsDidRange", createRangingResponse(beacons, region));
  }

  private WritableMap createRangingResponse(Collection<Beacon> beacons, Region region) {
    WritableMap map = new WritableNativeMap();
    map.putString("identifier", region.getUniqueId());
    map.putString("uuid", region.getId1() != null ? region.getId1().toString() : "");
    WritableArray a = new WritableNativeArray();
    for (Beacon beacon : beacons) {
      WritableMap b = new WritableNativeMap();
      b.putString("uuid", beacon.getId1().toString());
      if (beacon.getIdentifiers().size() > 2) {
        b.putInt("major", beacon.getId2().toInt());
        b.putInt("minor", beacon.getId3().toInt());
      }
      b.putInt("rssi", beacon.getRssi());
      if (beacon.getDistance() == Double.POSITIVE_INFINITY
        || Double.isNaN(beacon.getDistance())
        || beacon.getDistance() == Double.NEGATIVE_INFINITY) {
        b.putDouble("distance", 999.0);
        b.putString("proximity", "far");
      } else {
        b.putDouble("distance", beacon.getDistance());
        b.putString("proximity", getProximity(beacon.getDistance()));
      }
      a.pushMap(b);
    }
    map.putArray("beacons", a);
    return map;
  }

  private String getProximity(double distance) {
    if (distance == -1.0) {
      return "unknown";
    } else if (distance < 1) {
      return "immediate";
    } else if (distance < 3) {
      return "near";
    } else {
      return "far";
    }
  }

  /***********************************************************************************************
   * Utils
   **********************************************************************************************/
  private void sendEvent(String eventName,
                         @Nullable WritableMap params) {
    reactAppContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(eventName, params);
  }

  private Region createRegion(String regionId, String beaconUUID) {
    Identifier id1 = (beaconUUID == null) ? null : Identifier.parse(beaconUUID);
    return new Region(regionId, id1, null, null);
  }

  private Region createRegion(String regionId, String beaconUUID, String minor, String major) {
    Identifier id1 = (beaconUUID == null) ? null : Identifier.parse(beaconUUID);
    return new Region(
      regionId,
      id1,
      major.length() > 0 ? Identifier.parse(major) : null,
      minor.length() > 0 ? Identifier.parse(minor) : null
    );
  }
}
