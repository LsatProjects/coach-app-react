import { IMovementSet } from 'app/shared/model//movement-set.model';
import { ITraining } from 'app/shared/model//training.model';

export interface IPhase {
  id?: number;
  name?: string;
  description?: string;
  movementSets?: IMovementSet[];
  training?: ITraining;
}

export const defaultValue: Readonly<IPhase> = {};
