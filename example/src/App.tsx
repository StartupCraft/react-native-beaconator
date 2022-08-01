import React, { useEffect } from 'react';
import { DeviceEventEmitter } from 'react-native';
import { startRangingBeacons, stopRangingBeacons } from 'react-native-beaconator';

const region = {
  identifier: 'Beaconator Region',
};

export default function App() {
  useEffect(() => {
    startRangingBeacons(region);

    return () => {
      stopRangingBeacons(region);
    };
  }, []);

  useEffect(() => {
    const listener = DeviceEventEmitter.addListener('beaconsDidRange', (data) => console.log('didRange', data));

    return () => {
      listener.remove();
    };
  }, []);

  return null;
}
