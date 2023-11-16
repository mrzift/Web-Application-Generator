import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmp, NewEmp } from '../emp.model';

export type PartialUpdateEmp = Partial<IEmp> & Pick<IEmp, 'id'>;

export type EntityResponseType = HttpResponse<IEmp>;
export type EntityArrayResponseType = HttpResponse<IEmp[]>;

@Injectable({ providedIn: 'root' })
export class EmpService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/emps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(emp: NewEmp): Observable<EntityResponseType> {
    return this.http.post<IEmp>(this.resourceUrl, emp, { observe: 'response' });
  }

  update(emp: IEmp): Observable<EntityResponseType> {
    return this.http.put<IEmp>(`${this.resourceUrl}/${this.getEmpIdentifier(emp)}`, emp, { observe: 'response' });
  }

  partialUpdate(emp: PartialUpdateEmp): Observable<EntityResponseType> {
    return this.http.patch<IEmp>(`${this.resourceUrl}/${this.getEmpIdentifier(emp)}`, emp, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmp[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmpIdentifier(emp: Pick<IEmp, 'id'>): number {
    return emp.id;
  }

  compareEmp(o1: Pick<IEmp, 'id'> | null, o2: Pick<IEmp, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmpIdentifier(o1) === this.getEmpIdentifier(o2) : o1 === o2;
  }

  addEmpToCollectionIfMissing<Type extends Pick<IEmp, 'id'>>(empCollection: Type[], ...empsToCheck: (Type | null | undefined)[]): Type[] {
    const emps: Type[] = empsToCheck.filter(isPresent);
    if (emps.length > 0) {
      const empCollectionIdentifiers = empCollection.map(empItem => this.getEmpIdentifier(empItem)!);
      const empsToAdd = emps.filter(empItem => {
        const empIdentifier = this.getEmpIdentifier(empItem);
        if (empCollectionIdentifiers.includes(empIdentifier)) {
          return false;
        }
        empCollectionIdentifiers.push(empIdentifier);
        return true;
      });
      return [...empsToAdd, ...empCollection];
    }
    return empCollection;
  }
}
