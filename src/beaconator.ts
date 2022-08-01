import { Platform } from 'react-native';
import { BeaconatorModule } from './module';
import type { RegionAndroid, RegionIos, RegionType } from './values';

/**
 * Starts ranging in the specified region
 *
 * @param {RegionType} region region to range
 * @returns {Promise<void>} promise resolves to void or error
 */
export function startRangingBeacons(region: RegionIos): Promise<void>;
/**
 * Starts ranging in the specified region (with optional beaconUUID).
 *
 * @remarks
 *
 * This method is supported only on Android
 *
 * @param {RegionAndroid} region region to range
 * @returns {Promise<void>} promise resolves to void or error
 */
export function startRangingBeacons(region: RegionAndroid): Promise<void>;
/**
 * Starts ranging in the specified region based on regionIdentifier (with optional beaconUUID).
 *
 * @remarks
 *
 * This method is supported only on Android
 *
 * @param {String} regionIdentifier identifier of a region to range
 * @param {String} [beaconUUID] optional UUID
 * @returns {Promise<any>} promise resolves to void or error
 */
export function startRangingBeacons(regionIdentifier: string, beaconUUID?: string): Promise<void>;

export function startRangingBeacons(region: string | RegionType, beaconUUID?: string) {
  if (typeof region === 'string') {
    if (Platform.OS !== 'android') {
      throw new Error('You can pass region as a string only on Android');
    }

    return BeaconatorModule.startRanging(region, beaconUUID);
  }

  if (!('uuid' in region) && Platform.OS !== 'android') {
    throw new Error('You can pass region without "uuid" field only on Android');
  }

  return BeaconatorModule.startRanging(region.identifier, region.uuid);
}

/**
 * Stops ranging in the specified region
 *
 * @param {RegionType} region region to range
 * @returns {Promise<void>} promise resolves to void or error
 */
export function stopRangingBeacons(region: RegionIos): Promise<void>;
/**
 * Stops ranging in the specified region (with optional beaconUUID).
 *
 * @remarks
 *
 * This method is supported only on Android
 *
 * @param {RegionAndroid} region region to range
 * @returns {Promise<void>} promise resolves to void or error
 */
export function stopRangingBeacons(region: RegionAndroid): Promise<void>;
/**
 * Starts ranging in the specified region based on regionIdentifier (with optional beaconUUID).
 *
 * @remarks
 *
 * This method is supported only on Android
 *
 * @param {String} regionIdentifier identifier of a region to range
 * @param {String} [beaconUUID] optional UUID
 * @returns {Promise<any>} promise resolves to void or error
 */
export function stopRangingBeacons(regionIdentifier: string, beaconUUID?: string): Promise<void>;

export function stopRangingBeacons(region: string | RegionType, beaconUUID?: string) {
  if (typeof region === 'string') {
    if (Platform.OS !== 'android') {
      throw new Error('You can pass region as a string only on Android');
    }

    return BeaconatorModule.stopRanging(region, beaconUUID);
  }

  if (!('uuid' in region) && Platform.OS !== 'android') {
    throw new Error('You can pass region without "uuid" field only on Android');
  }

  return BeaconatorModule.stopRanging(region.identifier, region.uuid);
}
