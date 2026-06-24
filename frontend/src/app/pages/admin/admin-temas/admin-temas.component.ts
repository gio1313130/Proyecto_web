import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../../services/api.service';

@Component({
  selector: 'app-admin-temas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-temas.component.html',
  styleUrl: './admin-temas.component.css'
})
export class AdminTemasComponent implements OnInit {
  materias: any[] = [];
  temas: any[] = [];
  materiaSeleccionada: number | null = null;
  cargando: boolean = false;
  
  // Para el formulario
  mostrarModal: boolean = false;
  editando: boolean = false;
  temaActual: any = {
    nombreTema: '',
    descripcionTema: ''
  };

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.cargarMaterias();
  }

  cargarMaterias() {
    this.apiService.getMaterias().subscribe({
      next: (data) => {
        this.materias = data;
        if (this.materias.length > 0) {
          this.materiaSeleccionada = this.materias[0].idMateria;
          this.cargarTemas();
        }
      },
      error: (err) => console.error('Error al cargar materias', err)
    });
  }

  onMateriaChange() {
    this.cargarTemas();
  }

  cargarTemas() {
    if (!this.materiaSeleccionada) return;
    this.cargando = true;
    this.apiService.getTemasPorMateria(this.materiaSeleccionada).subscribe({
      next: (data) => {
        this.temas = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar temas', err);
        this.cargando = false;
      }
    });
  }

  abrirModalNuevo() {
    this.editando = false;
    this.temaActual = { nombreTema: '', descripcionTema: '' };
    this.mostrarModal = true;
  }

  abrirModalEditar(tema: any) {
    this.editando = true;
    this.temaActual = { ...tema };
    this.mostrarModal = true;
  }

  cerrarModal() {
    this.mostrarModal = false;
  }

  guardarTema() {
    if (!this.materiaSeleccionada) return;

    if (this.editando) {
      // Editar
      // Aseguramos que la estructura corresponda a lo esperado por el backend
      const payload = {
        nombreTema: this.temaActual.nombreTema,
        descripcionTema: this.temaActual.descripcionTema,
        materia: { idMateria: this.materiaSeleccionada }
      };

      this.apiService.actualizarTema(this.temaActual.idTema, payload).subscribe({
        next: () => {
          this.cargarTemas();
          this.cerrarModal();
        },
        error: (err) => alert('Error al actualizar tema')
      });
    } else {
      // Nuevo
      const payload = {
        nombreTema: this.temaActual.nombreTema,
        descripcionTema: this.temaActual.descripcionTema,
        materia: { idMateria: this.materiaSeleccionada }
      };

      this.apiService.crearTema(payload).subscribe({
        next: () => {
          this.cargarTemas();
          this.cerrarModal();
        },
        error: (err) => alert('Error al crear tema')
      });
    }
  }

  eliminarTema(idTema: number) {
    if (confirm('¿Estás seguro de que deseas eliminar este tema? Esto podría afectar a los recursos y cuestionarios asociados.')) {
      this.apiService.eliminarTema(idTema).subscribe({
        next: () => this.cargarTemas(),
        error: (err) => alert('Error al eliminar el tema. Asegúrate de que no tenga recursos o cuestionarios asociados.')
      });
    }
  }
}
