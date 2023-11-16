import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IManager } from '../manager.model';
import { ManagerService } from '../service/manager.service';

export const managerResolve = (route: ActivatedRouteSnapshot): Observable<null | IManager> => {
  const id = route.params['id'];
  if (id) {
    return inject(ManagerService)
      .find(id)
      .pipe(
        mergeMap((manager: HttpResponse<IManager>) => {
          if (manager.body) {
            return of(manager.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default managerResolve;
