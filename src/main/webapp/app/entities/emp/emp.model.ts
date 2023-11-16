import { IManager } from 'app/entities/manager/manager.model';

export interface IEmp {
  id: number;
  empId?: number | null;
  empName?: string | null;
  empJob?: string | null;
  empAddress?: string | null;
  empSalary?: number | null;
  manager?: Pick<IManager, 'id'> | null;
}

export type NewEmp = Omit<IEmp, 'id'> & { id: null };
