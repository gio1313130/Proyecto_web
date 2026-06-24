import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = environment.apiUrl + '/api';

  constructor(private http: HttpClient) { }

  getMaterias(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/materias`);
  }

  crearMateria(materia: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/materias`, materia);
  }

  actualizarMateria(idMateria: number, materia: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/materias/${idMateria}`, materia);
  }

  eliminarMateria(idMateria: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/materias/${idMateria}`);
  }

  getTemasPorMateria(idMateria: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/materias/${idMateria}/temas`);
  }

  getRecursosPorTema(idTema: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/temas/${idTema}/recursos`);
  }

  getCuestionariosPorTema(idTema: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/temas/${idTema}/cuestionarios`);
  }

  getCuestionarioResolver(idCuestionario: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/cuestionarios/${idCuestionario}/resolver`);
  }

  resolverCuestionario(payload: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/intentos/resolver`, payload);
  }

  getAuthMe(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/auth/me`);
  }

  getMisIntentos(idUsuario: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/usuarios/${idUsuario}/intentos`);
  }
}
