import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../emp.test-samples';

import { EmpFormService } from './emp-form.service';

describe('Emp Form Service', () => {
  let service: EmpFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpFormService);
  });

  describe('Service methods', () => {
    describe('createEmpFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmpFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            empId: expect.any(Object),
            empName: expect.any(Object),
            empJob: expect.any(Object),
            empAddress: expect.any(Object),
            empSalary: expect.any(Object),
            manager: expect.any(Object),
          })
        );
      });

      it('passing IEmp should create a new form with FormGroup', () => {
        const formGroup = service.createEmpFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            empId: expect.any(Object),
            empName: expect.any(Object),
            empJob: expect.any(Object),
            empAddress: expect.any(Object),
            empSalary: expect.any(Object),
            manager: expect.any(Object),
          })
        );
      });
    });

    describe('getEmp', () => {
      it('should return NewEmp for default Emp initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEmpFormGroup(sampleWithNewData);

        const emp = service.getEmp(formGroup) as any;

        expect(emp).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmp for empty Emp initial value', () => {
        const formGroup = service.createEmpFormGroup();

        const emp = service.getEmp(formGroup) as any;

        expect(emp).toMatchObject({});
      });

      it('should return IEmp', () => {
        const formGroup = service.createEmpFormGroup(sampleWithRequiredData);

        const emp = service.getEmp(formGroup) as any;

        expect(emp).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmp should not enable id FormControl', () => {
        const formGroup = service.createEmpFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmp should disable id FormControl', () => {
        const formGroup = service.createEmpFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
