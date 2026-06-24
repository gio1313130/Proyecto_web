import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

export interface LoginResponse {
  token: string;
  idUsuario: number;
  nombreUsuario: string;
  correo: string;
  rol: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl + '/api/auth';

  constructor(private http: HttpClient) { }

  login(correo: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, { correo, password })
      .pipe(
        tap(response => {
          if (response.token) {
            localStorage.setItem('token', response.token);
            localStorage.setItem('usuario', JSON.stringify({
              idUsuario: response.idUsuario,
              nombreUsuario: response.nombreUsuario,
              correo: response.correo,
              rol: response.rol
            }));
          }
        })
      );
  }

  register(nombreUsuario: string, correo: string, passwordUsuario: string, rol: string): Observable<any> {
    // Forzamos la limpieza del token local para evitar que el interceptor envíe basura por accidente
    localStorage.clear();
    
    return this.http.post(`${environment.apiUrl}/api/usuarios`, {
      nombreUsuario,
      correo,
      passwordUsuario,
      rol
    });
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('usuario');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
