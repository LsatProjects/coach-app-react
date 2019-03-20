import { IMovementSet } from 'app/shared/model//movement-set.model';

export interface IPhase {
  id?: number;
  name?: string;
  description?: string;
  movementSets?: IMovementSet[];
  trainingId?: number;
}

export const defaultValue: Readonly<IPhase> = {};
