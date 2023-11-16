import { IManager, NewManager } from './manager.model';

export const sampleWithRequiredData: IManager = {
  id: 1101,
  manId: 59609,
  manName: 'Baby Southwest',
  manEmail: 'moratorium',
};

export const sampleWithPartialData: IManager = {
  id: 66782,
  manId: 73469,
  manName: 'Metrics',
  manEmail: 'against Towels',
};

export const sampleWithFullData: IManager = {
  id: 82380,
  manId: 66662,
  manName: 'upbeat',
  manEmail: 'copying Northwest',
};

export const sampleWithNewData: NewManager = {
  manId: 73596,
  manName: 'Iridium',
  manEmail: 'when Connecticut',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
