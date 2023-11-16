import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ManagerDetailComponent } from './manager-detail.component';

describe('Manager Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManagerDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ManagerDetailComponent,
              resolve: { manager: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(ManagerDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load manager on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ManagerDetailComponent);

      // THEN
      expect(instance.manager).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
