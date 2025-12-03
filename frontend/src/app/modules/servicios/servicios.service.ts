import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Servicio {
  id?: number;
  tipo: string;
  detalle: string;
  activo?: boolean;
}

@Injectable({ providedIn: 'root' })
export class ServiciosService {
  private api = '/api/servicios';
  constructor(private http: HttpClient) {}

  getAll(): Observable<Servicio[]> { return this.http.get<Servicio[]>(this.api); }
  getById(id: number): Observable<Servicio> { return this.http.get<Servicio>(`${this.api}/${id}`); }
  search(term: string): Observable<Servicio[]> { return this.http.get<Servicio[]>(`${this.api}?q=${term}`); }
  create(servicio: Servicio): Observable<Servicio> { return this.http.post<Servicio>(this.api, servicio); }
  update(id: number, servicio: Servicio): Observable<Servicio> { return this.http.put<Servicio>(`${this.api}/${id}`, servicio); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.api}/${id}`); }
}
