import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmpFormService } from './emp-form.service';
import { EmpService } from '../service/emp.service';
import { IEmp } from '../emp.model';
import { IManager } from 'app/entities/manager/manager.model';
import { ManagerService } from 'app/entities/manager/service/manager.service';

import { EmpUpdateComponent } from './emp-update.component';

describe('Emp Management Update Component', () => {
  let comp: EmpUpdateComponent;
  let fixture: ComponentFixture<EmpUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let empFormService: EmpFormService;
  let empService: EmpService;
  let managerService: ManagerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EmpUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EmpUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmpUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    empFormService = TestBed.inject(EmpFormService);
    empService = TestBed.inject(EmpService);
    managerService = TestBed.inject(ManagerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Manager query and add missing value', () => {
      const emp: IEmp = { id: 456 };
      const manager: IManager = { id: 91227 };
      emp.manager = manager;

      const managerCollection: IManager[] = [{ id: 95523 }];
      jest.spyOn(managerService, 'query').mockReturnValue(of(new HttpResponse({ body: managerCollection })));
      const additionalManagers = [manager];
      const expectedCollection: IManager[] = [...additionalManagers, ...managerCollection];
      jest.spyOn(managerService, 'addManagerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ emp });
      comp.ngOnInit();

      expect(managerService.query).toHaveBeenCalled();
      expect(managerService.addManagerToCollectionIfMissing).toHaveBeenCalledWith(
        managerCollection,
        ...additionalManagers.map(expect.objectContaining)
      );
      expect(comp.managersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const emp: IEmp = { id: 456 };
      const manager: IManager = { id: 44119 };
      emp.manager = manager;

      activatedRoute.data = of({ emp });
      comp.ngOnInit();

      expect(comp.managersSharedCollection).toContain(manager);
      expect(comp.emp).toEqual(emp);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmp>>();
      const emp = { id: 123 };
      jest.spyOn(empFormService, 'getEmp').mockReturnValue(emp);
      jest.spyOn(empService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emp }));
      saveSubject.complete();

      // THEN
      expect(empFormService.getEmp).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(empService.update).toHaveBeenCalledWith(expect.objectContaining(emp));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmp>>();
      const emp = { id: 123 };
      jest.spyOn(empFormService, 'getEmp').mockReturnValue({ id: null });
      jest.spyOn(empService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emp: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emp }));
      saveSubject.complete();

      // THEN
      expect(empFormService.getEmp).toHaveBeenCalled();
      expect(empService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmp>>();
      const emp = { id: 123 };
      jest.spyOn(empService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(empService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareManager', () => {
      it('Should forward to managerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(managerService, 'compareManager');
        comp.compareManager(entity, entity2);
        expect(managerService.compareManager).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
