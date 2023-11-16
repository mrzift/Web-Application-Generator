export interface IManager {
  id: number;
  manId?: number | null;
  manName?: string | null;
  manEmail?: string | null;
}

export type NewManager = Omit<IManager, 'id'> & { id: null };
