import { BeaconatorModule } from './module';

export type RegionBase = {
  identifier: string;
  major?: number;
  minor?: number;
};

export type RegionAndroid = RegionBase & { uuid?: string };

export type RegionIos = RegionBase & { uuid: string };

export type RegionType = RegionAndroid | RegionIos;

export const PARSER_IBEACON_LAYOUT = BeaconatorModule.PARSER_IBEACON_LAYOUT as string;
export const PARSER_ESTIMOTE_LAYOUT = BeaconatorModule.PARSER_ESTIMOTE_LAYOUT as string;
export const PARSER_ALTBEACON_LAYOUT = BeaconatorModule.PARSER_ALTBEACON_LAYOUT as string;
export const PARSER_EDDYSTONE_TLM_LAYOUT = BeaconatorModule.PARSER_EDDYSTONE_TLM_LAYOUT as string;
export const PARSER_EDDYSTONE_UID_LAYOUT = BeaconatorModule.PARSER_EDDYSTONE_UID_LAYOUT as string;
export const PARSER_EDDYSTONE_URL_LAYOUT = BeaconatorModule.PARSER_EDDYSTONE_URL_LAYOUT as string;
