import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'emp',
        data: { pageTitle: 'timothyApp.emp.home.title' },
        loadChildren: () => import('./emp/emp.routes'),
      },
      {
        path: 'manager',
        data: { pageTitle: 'timothyApp.manager.home.title' },
        loadChildren: () => import('./manager/manager.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
