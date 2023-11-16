import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Emp e2e test', () => {
  const empPageUrl = '/emp';
  const empPageUrlPattern = new RegExp('/emp(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const empSample = { empId: 67643, empName: 'Comoros yum', empJob: 'male' };

  let emp;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/emps+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/emps').as('postEntityRequest');
    cy.intercept('DELETE', '/api/emps/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (emp) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/emps/${emp.id}`,
      }).then(() => {
        emp = undefined;
      });
    }
  });

  it('Emps menu should load Emps page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('emp');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Emp').should('exist');
    cy.url().should('match', empPageUrlPattern);
  });

  describe('Emp page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(empPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Emp page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/emp/new$'));
        cy.getEntityCreateUpdateHeading('Emp');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', empPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/emps',
          body: empSample,
        }).then(({ body }) => {
          emp = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/emps+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/emps?page=0&size=20>; rel="last",<http://localhost/api/emps?page=0&size=20>; rel="first"',
              },
              body: [emp],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(empPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Emp page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('emp');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', empPageUrlPattern);
      });

      it('edit button click should load edit Emp page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Emp');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', empPageUrlPattern);
      });

      it('edit button click should load edit Emp page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Emp');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', empPageUrlPattern);
      });

      it('last delete button click should delete instance of Emp', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('emp').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', empPageUrlPattern);

        emp = undefined;
      });
    });
  });

  describe('new Emp page', () => {
    beforeEach(() => {
      cy.visit(`${empPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Emp');
    });

    it('should create an instance of Emp', () => {
      cy.get(`[data-cy="empId"]`).type('10517');
      cy.get(`[data-cy="empId"]`).should('have.value', '10517');

      cy.get(`[data-cy="empName"]`).type('siemens East East');
      cy.get(`[data-cy="empName"]`).should('have.value', 'siemens East East');

      cy.get(`[data-cy="empJob"]`).type('Home North');
      cy.get(`[data-cy="empJob"]`).should('have.value', 'Home North');

      cy.get(`[data-cy="empAddress"]`).type('pushy');
      cy.get(`[data-cy="empAddress"]`).should('have.value', 'pushy');

      cy.get(`[data-cy="empSalary"]`).type('80784');
      cy.get(`[data-cy="empSalary"]`).should('have.value', '80784');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        emp = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', empPageUrlPattern);
    });
  });
});
