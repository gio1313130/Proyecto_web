import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-temas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './temas.component.html',
  styleUrl: './temas.component.css'
})
export class TemasComponent implements OnInit {
  temas: any[] = [];
  idMateria: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.idMateria = +id;
        this.cargarTemas(this.idMateria);
      }
    });
  }

  cargarTemas(id: number) {
    this.apiService.getTemasPorMateria(id).subscribe({
      next: (data) => {
        this.temas = data;
      },
      error: (err) => {
        console.error('Error al cargar temas', err);
      }
    });
  }

  explorarTema(idTema: number) {
    this.router.navigate(['/temas', idTema]);
  }

  volver() {
    this.router.navigate(['/dashboard']);
  }
}
