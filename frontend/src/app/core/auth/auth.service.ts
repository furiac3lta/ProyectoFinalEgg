import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';

export interface LoginPayload { email: string; password: string; }
export interface AuthResponse { token: string; email: string; rol: string; }

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly tokenKey = 'jwt_token';
  private readonly emailKey = 'user_email';
  private readonly roleKey = 'user_role';
  private loggedIn$ = new BehaviorSubject<boolean>(!!localStorage.getItem(this.tokenKey));

  isLoggedIn$ = this.loggedIn$.asObservable();

  constructor(private http: HttpClient) {}

  login(payload: LoginPayload): Observable<AuthResponse> {
    return this.http.post<AuthResponse>('/api/auth/login', payload).pipe(
      tap(res => this.persist(res))
    );
  }

  register(data: any): Observable<any> {
    return this.http.post('/api/auth/register', data);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.emailKey);
    localStorage.removeItem(this.roleKey);
    this.loggedIn$.next(false);
  }

  persist(res: AuthResponse): void {
    localStorage.setItem(this.tokenKey, res.token);
    localStorage.setItem(this.emailKey, res.email);
    localStorage.setItem(this.roleKey, res.rol);
    this.loggedIn$.next(true);
  }

  get token(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  get userEmail(): string | null {
    return localStorage.getItem(this.emailKey);
  }

  get role(): string | null {
    return localStorage.getItem(this.roleKey);
  }
}
