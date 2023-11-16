import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IManager, NewManager } from '../manager.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IManager for edit and NewManagerFormGroupInput for create.
 */
type ManagerFormGroupInput = IManager | PartialWithRequiredKeyOf<NewManager>;

type ManagerFormDefaults = Pick<NewManager, 'id'>;

type ManagerFormGroupContent = {
  id: FormControl<IManager['id'] | NewManager['id']>;
  manId: FormControl<IManager['manId']>;
  manName: FormControl<IManager['manName']>;
  manEmail: FormControl<IManager['manEmail']>;
};

export type ManagerFormGroup = FormGroup<ManagerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ManagerFormService {
  createManagerFormGroup(manager: ManagerFormGroupInput = { id: null }): ManagerFormGroup {
    const managerRawValue = {
      ...this.getFormDefaults(),
      ...manager,
    };
    return new FormGroup<ManagerFormGroupContent>({
      id: new FormControl(
        { value: managerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      manId: new FormControl(managerRawValue.manId, {
        validators: [Validators.required],
      }),
      manName: new FormControl(managerRawValue.manName, {
        validators: [Validators.required],
      }),
      manEmail: new FormControl(managerRawValue.manEmail, {
        validators: [Validators.required],
      }),
    });
  }

  getManager(form: ManagerFormGroup): IManager | NewManager {
    return form.getRawValue() as IManager | NewManager;
  }

  resetForm(form: ManagerFormGroup, manager: ManagerFormGroupInput): void {
    const managerRawValue = { ...this.getFormDefaults(), ...manager };
    form.reset(
      {
        ...managerRawValue,
        id: { value: managerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ManagerFormDefaults {
    return {
      id: null,
    };
  }
}
