import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEmp, NewEmp } from '../emp.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmp for edit and NewEmpFormGroupInput for create.
 */
type EmpFormGroupInput = IEmp | PartialWithRequiredKeyOf<NewEmp>;

type EmpFormDefaults = Pick<NewEmp, 'id'>;

type EmpFormGroupContent = {
  id: FormControl<IEmp['id'] | NewEmp['id']>;
  empId: FormControl<IEmp['empId']>;
  empName: FormControl<IEmp['empName']>;
  empJob: FormControl<IEmp['empJob']>;
  empAddress: FormControl<IEmp['empAddress']>;
  empSalary: FormControl<IEmp['empSalary']>;
  manager: FormControl<IEmp['manager']>;
};

export type EmpFormGroup = FormGroup<EmpFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmpFormService {
  createEmpFormGroup(emp: EmpFormGroupInput = { id: null }): EmpFormGroup {
    const empRawValue = {
      ...this.getFormDefaults(),
      ...emp,
    };
    return new FormGroup<EmpFormGroupContent>({
      id: new FormControl(
        { value: empRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      empId: new FormControl(empRawValue.empId, {
        validators: [Validators.required],
      }),
      empName: new FormControl(empRawValue.empName, {
        validators: [Validators.required],
      }),
      empJob: new FormControl(empRawValue.empJob, {
        validators: [Validators.required],
      }),
      empAddress: new FormControl(empRawValue.empAddress),
      empSalary: new FormControl(empRawValue.empSalary),
      manager: new FormControl(empRawValue.manager),
    });
  }

  getEmp(form: EmpFormGroup): IEmp | NewEmp {
    return form.getRawValue() as IEmp | NewEmp;
  }

  resetForm(form: EmpFormGroup, emp: EmpFormGroupInput): void {
    const empRawValue = { ...this.getFormDefaults(), ...emp };
    form.reset(
      {
        ...empRawValue,
        id: { value: empRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EmpFormDefaults {
    return {
      id: null,
    };
  }
}
