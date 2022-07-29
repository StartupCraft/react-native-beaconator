package io.startupcraft.beaconator;

public class Constants {
  public static final String MODULE_NAME = "BeaconatorModule";
  public static final String TAG = "Beaconator";

  public static final String PARSER_IBEACON_LAYOUT = "m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24";
  public static final String PARSER_ESTIMOTE_LAYOUT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";
  public static final String PARSER_ALTBEACON_LAYOUT = "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25";
  public static final String PARSER_EDDYSTONE_TLM_LAYOUT = "x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15";
  public static final String PARSER_EDDYSTONE_UID_LAYOUT = "s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19";
  public static final String PARSER_EDDYSTONE_URL_LAYOUT = "s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v";
}
