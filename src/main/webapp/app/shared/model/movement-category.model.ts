export interface IMovementCategory {
  id?: number;
  name?: string;
  description?: string;
  sportId?: number;
}

export const defaultValue: Readonly<IMovementCategory> = {};
