import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ManagerFormService, ManagerFormGroup } from './manager-form.service';
import { IManager } from '../manager.model';
import { ManagerService } from '../service/manager.service';

@Component({
  standalone: true,
  selector: 'jhi-manager-update',
  templateUrl: './manager-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ManagerUpdateComponent implements OnInit {
  isSaving = false;
  manager: IManager | null = null;

  editForm: ManagerFormGroup = this.managerFormService.createManagerFormGroup();

  constructor(
    protected managerService: ManagerService,
    protected managerFormService: ManagerFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ manager }) => {
      this.manager = manager;
      if (manager) {
        this.updateForm(manager);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const manager = this.managerFormService.getManager(this.editForm);
    if (manager.id !== null) {
      this.subscribeToSaveResponse(this.managerService.update(manager));
    } else {
      this.subscribeToSaveResponse(this.managerService.create(manager));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IManager>>): void {
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

  protected updateForm(manager: IManager): void {
    this.manager = manager;
    this.managerFormService.resetForm(this.editForm, manager);
  }
}
