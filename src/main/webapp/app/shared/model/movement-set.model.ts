import { IPhase } from 'app/shared/model//phase.model';
import { IMovement } from 'app/shared/model//movement.model';

export const enum Unit {
  REP = 'REP',
  CAL = 'CAL',
  METER = 'METER'
}

export const enum Level {
  RX = 'RX',
  SCALE = 'SCALE',
  BEGINNER = 'BEGINNER',
  INTERMEDIATE = 'INTERMEDIATE'
}

export interface IMovementSet {
  id?: number;
  unit?: Unit;
  round?: number;
  weight?: number;
  level?: Level;
  phase?: IPhase;
  movements?: IMovement[];
}

export const defaultValue: Readonly<IMovementSet> = {};
