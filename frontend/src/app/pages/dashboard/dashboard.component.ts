import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  materias: any[] = [];
  usuario: any = null;

  constructor(
    private apiService: ApiService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const userStr = localStorage.getItem('usuario');
    if (userStr) {
      this.usuario = JSON.parse(userStr);
    }
    
    this.cargarMaterias();
  }

  isAdmin(): boolean {
    if (this.usuario) {
      return this.usuario.rol === 'ADMIN' || this.usuario.rol === 'admin';
    }
    return false;
  }

  cargarMaterias() {
    this.apiService.getMaterias().subscribe({
      next: (data) => {
        this.materias = data;
      },
      error: (err) => {
        console.error('Error al cargar materias', err);
      }
    });
  }

  goToProfile() {
    this.router.navigate(['/perfil']);
  }

  goToAdmin() {
    this.router.navigate(['/admin']);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  explorarMateria(idMateria: number) {
    this.router.navigate(['/materias', idMateria, 'temas']);
  }
}
