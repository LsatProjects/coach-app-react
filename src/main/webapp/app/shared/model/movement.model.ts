import { IMovementCategory } from 'app/shared/model//movement-category.model';
import { IMovementSet } from 'app/shared/model//movement-set.model';

export interface IMovement {
  id?: number;
  name?: string;
  abreviation?: string;
  description?: string;
  url?: string;
  movementCategory?: IMovementCategory;
  movementSet?: IMovementSet;
}

export const defaultValue: Readonly<IMovement> = {};
