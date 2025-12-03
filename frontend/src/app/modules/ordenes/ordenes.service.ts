import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Orden {
  id?: number;
  detalle: string;
  email: string;
  prestador_id: number;
  finished_at?: string;
  activo?: boolean;
}

@Injectable({ providedIn: 'root' })
export class OrdenesService {
  private api = '/api/ordenes';
  constructor(private http: HttpClient) {}

  getAll(): Observable<Orden[]> { return this.http.get<Orden[]>(this.api); }
  getById(id: number): Observable<Orden> { return this.http.get<Orden>(`${this.api}/${id}`); }
  search(term: string): Observable<Orden[]> { return this.http.get<Orden[]>(`${this.api}?q=${term}`); }
  create(orden: Orden): Observable<Orden> { return this.http.post<Orden>(this.api, orden); }
  update(id: number, orden: Orden): Observable<Orden> { return this.http.put<Orden>(`${this.api}/${id}`, orden); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.api}/${id}`); }
}
