import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ManagerComponent } from './list/manager.component';
import { ManagerDetailComponent } from './detail/manager-detail.component';
import { ManagerUpdateComponent } from './update/manager-update.component';
import ManagerResolve from './route/manager-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const managerRoute: Routes = [
  {
    path: '',
    component: ManagerComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ManagerDetailComponent,
    resolve: {
      manager: ManagerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ManagerUpdateComponent,
    resolve: {
      manager: ManagerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ManagerUpdateComponent,
    resolve: {
      manager: ManagerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default managerRoute;
