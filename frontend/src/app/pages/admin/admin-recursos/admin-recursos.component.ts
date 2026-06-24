import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../../services/api.service';

@Component({
  selector: 'app-admin-recursos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-recursos.component.html',
  styleUrl: './admin-recursos.component.css'
})
export class AdminRecursosComponent implements OnInit {
  materias: any[] = [];
  temas: any[] = [];
  recursos: any[] = [];
  
  materiaSeleccionada: number | null = null;
  temaSeleccionado: number | null = null;
  cargando: boolean = false;
  
  // Modal state
  mostrarModal: boolean = false;
  nuevoRecurso: any = {
    tituloRecurso: '',
    tipoRecurso: 'VIDEO',
    url: '',
    autor: ''
  };
  guardando: boolean = false;
  archivoSeleccionado: File | null = null;

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
    this.temaSeleccionado = null;
    this.temas = [];
    this.recursos = [];
    this.cargarTemas();
  }

  cargarTemas() {
    if (!this.materiaSeleccionada) return;
    this.apiService.getTemasPorMateria(this.materiaSeleccionada).subscribe({
      next: (data) => {
        this.temas = data;
        if (this.temas.length > 0) {
          this.temaSeleccionado = this.temas[0].idTema;
          this.cargarRecursos();
        }
      },
      error: (err) => console.error('Error al cargar temas', err)
    });
  }

  onTemaChange() {
    this.cargarRecursos();
  }

  cargarRecursos() {
    if (!this.temaSeleccionado) return;
    this.cargando = true;
    this.apiService.getRecursosPorTema(this.temaSeleccionado).subscribe({
      next: (data) => {
        this.recursos = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar recursos', err);
        this.cargando = false;
      }
    });
  }

  abrirModalNuevo() {
    this.nuevoRecurso = { tituloRecurso: '', tipoRecurso: 'VIDEO', url: '', autor: '' };
    this.archivoSeleccionado = null;
    this.mostrarModal = true;
  }

  cerrarModal() {
    this.mostrarModal = false;
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.archivoSeleccionado = file;
    }
  }

  guardarRecurso() {
    if (!this.temaSeleccionado) return;
    this.guardando = true;

    // Si es un archivo físico usamos FormData
    if (this.nuevoRecurso.tipoRecurso === 'PDF' && this.archivoSeleccionado) {
      const formData = new FormData();
      formData.append('file', this.archivoSeleccionado);
      formData.append('tituloRecurso', this.nuevoRecurso.tituloRecurso);
      formData.append('autor', this.nuevoRecurso.autor || 'Admin');
      formData.append('idTema', this.temaSeleccionado.toString());

      this.apiService.uploadRecurso(formData).subscribe({
        next: () => {
          this.cargarRecursos();
          this.cerrarModal();
          this.guardando = false;
        },
        error: (err) => {
          alert('Error al subir el archivo. Es posible que el backend requiera una configuración especial.');
          this.guardando = false;
        }
      });
    } else {
      // Si es un link / JSON
      // Modifica esta lógica si tu backend espera algo distinto
      const payload = {
        tituloRecurso: this.nuevoRecurso.tituloRecurso,
        tipoRecurso: this.nuevoRecurso.tipoRecurso,
        url: this.nuevoRecurso.url,
        autor: this.nuevoRecurso.autor || 'Admin',
        tema: { idTema: this.temaSeleccionado }
      };

      alert('Se enviará como URL. Asegúrate de que el backend soporte la inserción de recursos sin archivo físico.');
      this.guardando = false;
      this.cerrarModal();
    }
  }

  eliminarRecurso(idRecurso: number) {
    if (confirm('¿Estás seguro de que deseas eliminar este recurso?')) {
      this.apiService.eliminarRecurso(idRecurso).subscribe({
        next: () => this.cargarRecursos(),
        error: (err) => alert('Error al eliminar recurso')
      });
    }
  }
}
