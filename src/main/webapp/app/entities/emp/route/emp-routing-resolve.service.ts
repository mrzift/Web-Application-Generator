import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmp } from '../emp.model';
import { EmpService } from '../service/emp.service';

export const empResolve = (route: ActivatedRouteSnapshot): Observable<null | IEmp> => {
  const id = route.params['id'];
  if (id) {
    return inject(EmpService)
      .find(id)
      .pipe(
        mergeMap((emp: HttpResponse<IEmp>) => {
          if (emp.body) {
            return of(emp.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default empResolve;
