import { IEmp, NewEmp } from './emp.model';

export const sampleWithRequiredData: IEmp = {
  id: 14722,
  empId: 92473,
  empName: 'Branding South',
  empJob: 'Movies Pants Latin',
};

export const sampleWithPartialData: IEmp = {
  id: 62535,
  empId: 93655,
  empName: 'invoice',
  empJob: 'Newark plum bandwidth',
  empAddress: 'redundant',
};

export const sampleWithFullData: IEmp = {
  id: 41893,
  empId: 78056,
  empName: 'Account nervously',
  empJob: 'Southeast katal Mike',
  empAddress: 'female Configuration SDD',
  empSalary: 35924,
};

export const sampleWithNewData: NewEmp = {
  empId: 68779,
  empName: 'West Hybrid',
  empJob: 'Northwest',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
