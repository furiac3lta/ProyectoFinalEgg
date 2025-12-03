import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Comentario {
  id?: number;
  opinion: string;
  experiencia: string;
  imagen?: string;
  persona_id: number;
  orden_id: number;
  activo?: boolean;
}

@Injectable({ providedIn: 'root' })
export class ComentariosService {
  private api = '/api/comentarios';
  constructor(private http: HttpClient) {}

  getAll(): Observable<Comentario[]> { return this.http.get<Comentario[]>(this.api); }
  getById(id: number): Observable<Comentario> { return this.http.get<Comentario>(`${this.api}/${id}`); }
  search(term: string): Observable<Comentario[]> { return this.http.get<Comentario[]>(`${this.api}?q=${term}`); }
  create(comentario: Comentario): Observable<Comentario> { return this.http.post<Comentario>(this.api, comentario); }
  update(id: number, comentario: Comentario): Observable<Comentario> { return this.http.put<Comentario>(`${this.api}/${id}`, comentario); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.api}/${id}`); }
  uploadImage(id: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.api}/${id}/imagen`, formData);
  }
}
