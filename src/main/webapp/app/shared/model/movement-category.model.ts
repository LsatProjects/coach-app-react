import { ISport } from 'app/shared/model//sport.model';

export interface IMovementCategory {
  id?: number;
  name?: string;
  description?: string;
  sport?: ISport;
}

export const defaultValue: Readonly<IMovementCategory> = {};
