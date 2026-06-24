import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../../services/api.service';

@Component({
  selector: 'app-admin-cuestionarios',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-cuestionarios.component.html',
  styleUrl: './admin-cuestionarios.component.css'
})
export class AdminCuestionariosComponent implements OnInit {
  materias: any[] = [];
  temas: any[] = [];
  cuestionarios: any[] = [];
  
  materiaSeleccionada: number | null = null;
  temaSeleccionado: number | null = null;
  cargando: boolean = false;
  
  // Builder state
  mostrarBuilder: boolean = false;
  guardando: boolean = false;
  nuevoCuestionario: any = {
    tituloCuestionario: '',
    preguntas: []
  };

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.cargarMaterias();
  }

  cargarMaterias() {
    this.apiService.getMaterias().subscribe(data => {
      this.materias = data;
      if (data.length > 0) {
        this.materiaSeleccionada = data[0].idMateria;
        this.cargarTemas();
      }
    });
  }

  onMateriaChange() {
    this.temaSeleccionado = null;
    this.temas = [];
    this.cuestionarios = [];
    this.cargarTemas();
  }

  cargarTemas() {
    if (!this.materiaSeleccionada) return;
    this.apiService.getTemasPorMateria(this.materiaSeleccionada).subscribe(data => {
      this.temas = data;
      if (data.length > 0) {
        this.temaSeleccionado = data[0].idTema;
        this.cargarCuestionarios();
      }
    });
  }

  onTemaChange() {
    this.cargarCuestionarios();
  }

  cargarCuestionarios() {
    if (!this.temaSeleccionado) return;
    this.cargando = true;
    this.apiService.getCuestionariosPorTema(this.temaSeleccionado).subscribe({
      next: (data) => {
        this.cuestionarios = data;
        this.cargando = false;
      },
      error: () => this.cargando = false
    });
  }

  iniciarBuilder() {
    this.nuevoCuestionario = {
      tituloCuestionario: '',
      preguntas: []
    };
    this.generarPreguntaVacia();
    this.mostrarBuilder = true;
  }

  cancelarBuilder() {
    this.mostrarBuilder = false;
  }

  generarPreguntaVacia() {
    this.nuevoCuestionario.preguntas.push({
      enunciado: '',
      opciones: [
        { textoOpcion: '', esCorrecta: true },
        { textoOpcion: '', esCorrecta: false },
        { textoOpcion: '', esCorrecta: false },
        { textoOpcion: '', esCorrecta: false }
      ]
    });
  }

  agregarPregunta() {
    this.generarPreguntaVacia();
  }

  eliminarPregunta(index: number) {
    this.nuevoCuestionario.preguntas.splice(index, 1);
  }

  setCorrecta(pregunta: any, indexCorrecta: number) {
    pregunta.opciones.forEach((opc: any, i: number) => {
      opc.esCorrecta = (i === indexCorrecta);
    });
  }

  async guardarTodo() {
    if (!this.temaSeleccionado || !this.nuevoCuestionario.tituloCuestionario) return;
    
    this.guardando = true;
    
    try {
      // 1. Crear Cuestionario
      const cuestionarioPayload = {
        tituloCuestionario: this.nuevoCuestionario.tituloCuestionario,
        dificultad: 'Intermedio',
        tema: { idTema: this.temaSeleccionado }
      };
      
      const cuestionarioCreado = await this.apiService.crearCuestionario(cuestionarioPayload).toPromise();
      const idCuestionarioGuardado = cuestionarioCreado.idCuestionario;

      // 2. Iterar Preguntas
      for (const p of this.nuevoCuestionario.preguntas) {
        if (!p.enunciado) continue;
        
        const preguntaPayload = {
          enunciado: p.enunciado,
          cuestionario: { idCuestionario: idCuestionarioGuardado }
        };
        
        const preguntaCreada = await this.apiService.crearPregunta(preguntaPayload).toPromise();
        const idPreguntaGuardada = preguntaCreada.idPregunta;

        // 3. Iterar Opciones
        for (const o of p.opciones) {
          if (!o.textoOpcion) continue;
          
          const opcionPayload = {
            textoOpcion: o.textoOpcion,
            esCorrecta: o.esCorrecta,
            pregunta: { idPregunta: idPreguntaGuardada }
          };
          
          await this.apiService.crearOpcion(opcionPayload).toPromise();
        }
      }

      alert('¡Cuestionario creado exitosamente con todas sus preguntas!');
      this.mostrarBuilder = false;
      this.cargarCuestionarios();

    } catch (error) {
      console.error('Error durante el guardado en cascada', error);
      alert('Hubo un error al guardar el cuestionario completo.');
    } finally {
      this.guardando = false;
    }
  }

  eliminarCuestionario(idCuestionario: number) {
    if (confirm('¿Eliminar este cuestionario y TODAS sus preguntas?')) {
      this.apiService.eliminarCuestionario(idCuestionario).subscribe({
        next: () => this.cargarCuestionarios(),
        error: () => alert('Error al eliminar')
      });
    }
  }
}
