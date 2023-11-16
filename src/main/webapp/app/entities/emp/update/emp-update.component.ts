import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { EmpFormService, EmpFormGroup } from './emp-form.service';
import { IEmp } from '../emp.model';
import { EmpService } from '../service/emp.service';
import { IManager } from 'app/entities/manager/manager.model';
import { ManagerService } from 'app/entities/manager/service/manager.service';

@Component({
  standalone: true,
  selector: 'jhi-emp-update',
  templateUrl: './emp-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EmpUpdateComponent implements OnInit {
  isSaving = false;
  emp: IEmp | null = null;

  managersSharedCollection: IManager[] = [];

  editForm: EmpFormGroup = this.empFormService.createEmpFormGroup();

  constructor(
    protected empService: EmpService,
    protected empFormService: EmpFormService,
    protected managerService: ManagerService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareManager = (o1: IManager | null, o2: IManager | null): boolean => this.managerService.compareManager(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emp }) => {
      this.emp = emp;
      if (emp) {
        this.updateForm(emp);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emp = this.empFormService.getEmp(this.editForm);
    if (emp.id !== null) {
      this.subscribeToSaveResponse(this.empService.update(emp));
    } else {
      this.subscribeToSaveResponse(this.empService.create(emp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmp>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(emp: IEmp): void {
    this.emp = emp;
    this.empFormService.resetForm(this.editForm, emp);

    this.managersSharedCollection = this.managerService.addManagerToCollectionIfMissing<IManager>(
      this.managersSharedCollection,
      emp.manager
    );
  }

  protected loadRelationshipsOptions(): void {
    this.managerService
      .query()
      .pipe(map((res: HttpResponse<IManager[]>) => res.body ?? []))
      .pipe(map((managers: IManager[]) => this.managerService.addManagerToCollectionIfMissing<IManager>(managers, this.emp?.manager)))
      .subscribe((managers: IManager[]) => (this.managersSharedCollection = managers));
  }
}
