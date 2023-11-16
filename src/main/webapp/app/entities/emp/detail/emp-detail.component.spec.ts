import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EmpDetailComponent } from './emp-detail.component';

describe('Emp Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmpDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EmpDetailComponent,
              resolve: { emp: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(EmpDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load emp on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EmpDetailComponent);

      // THEN
      expect(instance.emp).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
