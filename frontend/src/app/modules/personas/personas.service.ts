import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Persona {
  id?: number;
  nombre: string;
  apellido: string;
  email: string;
  rol: 'ADMIN' | 'USER' | 'GUEST';
  telefono?: string;
  foto?: string;
  servicio_id?: number;
  activo?: boolean;
}

@Injectable({ providedIn: 'root' })
export class PersonasService {
  private api = '/api/personas';
  constructor(private http: HttpClient) {}

  getAll(): Observable<Persona[]> { return this.http.get<Persona[]>(this.api); }
  getById(id: number): Observable<Persona> { return this.http.get<Persona>(`${this.api}/${id}`); }
  search(term: string): Observable<Persona[]> { return this.http.get<Persona[]>(`${this.api}?q=${term}`); }
  create(persona: Persona): Observable<Persona> { return this.http.post<Persona>(this.api, persona); }
  update(id: number, persona: Persona): Observable<Persona> { return this.http.put<Persona>(`${this.api}/${id}`, persona); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.api}/${id}`); }
  uploadImage(id: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.api}/${id}/foto`, formData);
  }
}
