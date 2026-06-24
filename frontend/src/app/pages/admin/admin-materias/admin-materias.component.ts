import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../../services/api.service';

@Component({
  selector: 'app-admin-materias',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-materias.component.html',
  styleUrl: './admin-materias.component.css'
})
export class AdminMateriasComponent implements OnInit {
  materias: any[] = [];
  cargando = true;
  error: string | null = null;

  // Modal logic
  mostrarModal = false;
  editando = false;
  guardando = false;
  materiaActual: any = {
    nombreMateria: '',
    descripcionMateria: '',
    semestre: 1
  };

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.cargarMaterias();
  }

  cargarMaterias() {
    this.cargando = true;
    this.error = null;
    this.apiService.getMaterias().subscribe({
      next: (data) => {
        this.materias = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar materias:', err);
        this.error = 'No se pudieron cargar las materias. Intenta nuevamente.';
        this.cargando = false;
      }
    });
  }

  abrirModal(materia?: any) {
    if (materia) {
      this.editando = true;
      this.materiaActual = {
        idMateria: materia.id_materia || materia.idMateria,
        nombreMateria: materia.nombre_materia || materia.nombreMateria,
        descripcionMateria: materia.descripcion_materia || materia.descripcionMateria,
        semestre: materia.semestre
      };
    } else {
      this.editando = false;
      this.materiaActual = {
        nombreMateria: '',
        descripcionMateria: '',
        semestre: 1
      };
    }
    this.mostrarModal = true;
  }

  cerrarModal(event?: Event) {
    if (event) {
      event.preventDefault();
    }
    this.mostrarModal = false;
  }

  guardarMateria() {
    this.guardando = true;
    // Adapt payload to match backend DTO if necessary, assuming camelCase mapped by Spring automatically
    const payload = { ...this.materiaActual };
    
    if (this.editando && this.materiaActual.idMateria) {
      this.apiService.actualizarMateria(this.materiaActual.idMateria, payload).subscribe({
        next: () => {
          this.cargarMaterias();
          this.cerrarModal();
          this.guardando = false;
        },
        error: (err) => {
          console.error('Error al actualizar materia', err);
          alert('Hubo un error al actualizar la materia.');
          this.guardando = false;
        }
      });
    } else {
      this.apiService.crearMateria(payload).subscribe({
        next: () => {
          this.cargarMaterias();
          this.cerrarModal();
          this.guardando = false;
        },
        error: (err) => {
          console.error('Error al crear materia', err);
          alert('Hubo un error al crear la materia.');
          this.guardando = false;
        }
      });
    }
  }

  eliminarMateria(idMateria: number) {
    if (confirm('¿Estás seguro de que deseas eliminar esta materia? Esto podría eliminar los temas y recursos asociados.')) {
      this.apiService.eliminarMateria(idMateria).subscribe({
        next: () => {
          this.cargarMaterias();
        },
        error: (err) => {
          console.error('Error al eliminar materia', err);
          alert('No se pudo eliminar la materia. Puede que tenga temas asociados.');
        }
      });
    }
  }
}
