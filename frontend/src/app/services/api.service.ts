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

  // --- Temas ---
  getTemas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/temas`);
  }
  getTema(idTema: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/temas/${idTema}`);
  }
  crearTema(tema: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/temas`, tema);
  }
  actualizarTema(idTema: number, tema: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/temas/${idTema}`, tema);
  }
  eliminarTema(idTema: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/temas/${idTema}`);
  }

  // --- Recursos ---
  getRecursos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/recursos`);
  }
  getRecurso(idRecurso: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/recursos/${idRecurso}`);
  }
  actualizarRecurso(idRecurso: number, recurso: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/recursos/${idRecurso}`, recurso);
  }
  eliminarRecurso(idRecurso: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/recursos/${idRecurso}`);
  }
  uploadRecurso(formData: FormData): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/recursos/upload`, formData);
  }

  // --- Cuestionarios ---
  getCuestionarios(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/cuestionarios`);
  }
  getCuestionario(idCuestionario: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/cuestionarios/${idCuestionario}`);
  }
  crearCuestionario(cuestionario: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/cuestionarios`, cuestionario);
  }
  actualizarCuestionario(idCuestionario: number, cuestionario: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/cuestionarios/${idCuestionario}`, cuestionario);
  }
  eliminarCuestionario(idCuestionario: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/cuestionarios/${idCuestionario}`);
  }

  // --- Preguntas ---
  getPreguntasPorCuestionario(idCuestionario: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/cuestionarios/${idCuestionario}/preguntas`);
  }
  getPreguntas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/preguntas`);
  }
  getPregunta(idPregunta: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/preguntas/${idPregunta}`);
  }
  crearPregunta(pregunta: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/preguntas`, pregunta);
  }
  actualizarPregunta(idPregunta: number, pregunta: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/preguntas/${idPregunta}`, pregunta);
  }
  eliminarPregunta(idPregunta: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/preguntas/${idPregunta}`);
  }

  // --- Opciones ---
  getOpcionesPorPregunta(idPregunta: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/preguntas/${idPregunta}/opciones`);
  }
  getOpciones(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/opciones`);
  }
  getOpcion(idOpcion: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/opciones/${idOpcion}`);
  }
  crearOpcion(opcion: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/opciones`, opcion);
  }
  actualizarOpcion(idOpcion: number, opcion: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/opciones/${idOpcion}`, opcion);
  }
  eliminarOpcion(idOpcion: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/opciones/${idOpcion}`);
  }

  // --- Usuarios ---
  getUsuarios(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/usuarios`);
  }
  getUsuario(idUsuario: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/usuarios/${idUsuario}`);
  }
  crearUsuario(usuario: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/usuarios`, usuario);
  }
  actualizarUsuario(idUsuario: number, usuario: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/usuarios/${idUsuario}`, usuario);
  }
  eliminarUsuario(idUsuario: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/usuarios/${idUsuario}`);
  }

  // --- Existentes ---
  getAuthMe(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/auth/me`);
  }

  getMisIntentos(idUsuario: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/usuarios/${idUsuario}/intentos`);
  }
}
