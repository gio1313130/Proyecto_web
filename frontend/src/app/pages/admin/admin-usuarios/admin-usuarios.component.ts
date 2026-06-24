import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../../services/api.service';

@Component({
  selector: 'app-admin-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-usuarios.component.html',
  styleUrl: './admin-usuarios.component.css'
})
export class AdminUsuariosComponent implements OnInit {
  usuarios: any[] = [];
  cargando: boolean = true;
  
  // Modal state
  mostrarModal: boolean = false;
  usuarioActual: any = {
    nombreUsuario: '',
    correo: '',
    rol: 'ALUMNO'
  };

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.cargando = true;
    this.apiService.getUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar usuarios', err);
        this.cargando = false;
      }
    });
  }

  abrirModalEditar(usuario: any) {
    this.usuarioActual = { ...usuario };
    this.mostrarModal = true;
  }

  cerrarModal() {
    this.mostrarModal = false;
  }

  guardarUsuario() {
    this.apiService.actualizarUsuario(this.usuarioActual.idUsuario, this.usuarioActual).subscribe({
      next: () => {
        this.cargarUsuarios();
        this.cerrarModal();
      },
      error: (err) => alert('Error al actualizar usuario')
    });
  }

  eliminarUsuario(idUsuario: number) {
    if (confirm('¿Estás seguro de que deseas eliminar a este usuario permanentemente?')) {
      this.apiService.eliminarUsuario(idUsuario).subscribe({
        next: () => this.cargarUsuarios(),
        error: (err) => alert('Error al eliminar usuario')
      });
    }
  }
}
