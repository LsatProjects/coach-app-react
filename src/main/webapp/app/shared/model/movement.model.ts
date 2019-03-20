export interface IMovement {
  id?: number;
  name?: string;
  abreviation?: string;
  description?: string;
  url?: string;
  movementCategoryId?: number;
  movementSetId?: number;
}

export const defaultValue: Readonly<IMovement> = {};
