import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmpComponent } from './list/emp.component';
import { EmpDetailComponent } from './detail/emp-detail.component';
import { EmpUpdateComponent } from './update/emp-update.component';
import EmpResolve from './route/emp-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const empRoute: Routes = [
  {
    path: '',
    component: EmpComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmpDetailComponent,
    resolve: {
      emp: EmpResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmpUpdateComponent,
    resolve: {
      emp: EmpResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmpUpdateComponent,
    resolve: {
      emp: EmpResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default empRoute;
