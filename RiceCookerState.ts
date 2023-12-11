export enum RiceCookerState {
  IDLE = 'idle',
  COOKING = 'cooking',
  WARM = 'warm'
}

export enum Commands {
  COOK = 1,
  WARM = 2,
  CANCEL = 3,
  ADD_WATER = 4,
  PLUG_IN = 5,
  UNPLUG = 6
}
export enum ErrorCodes {
  NO_WATER = 101,
  NOT_PLUGGED_IN = 102,
  BUSY = 103
}
