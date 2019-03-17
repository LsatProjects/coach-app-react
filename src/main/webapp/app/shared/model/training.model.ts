import { IPhase } from 'app/shared/model//phase.model';

export interface ITraining {
  id?: number;
  name?: string;
  description?: string;
  phases?: IPhase[];
}

export const defaultValue: Readonly<ITraining> = {};
