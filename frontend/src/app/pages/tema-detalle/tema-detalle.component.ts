import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tema-detalle',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tema-detalle.component.html',
  styleUrl: './tema-detalle.component.css'
})
export class TemaDetalleComponent implements OnInit {
  recursos: any[] = [];
  cuestionarios: any[] = [];
  idTema: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.idTema = +id;
        this.cargarDatosTema(this.idTema);
      }
    });
  }

  cargarDatosTema(id: number) {
    this.apiService.getRecursosPorTema(id).subscribe({
      next: (data) => {
        this.recursos = data;
      },
      error: (err) => console.error('Error al cargar recursos', err)
    });

    this.apiService.getCuestionariosPorTema(id).subscribe({
      next: (data) => {
        this.cuestionarios = data;
      },
      error: (err) => console.error('Error al cargar cuestionarios', err)
    });
  }

  abrirRecurso(url: string) {
    if (url) {
      window.open(url, '_blank');
    }
  }

  iniciarCuestionario(idCuestionario: number) {
    if (idCuestionario) {
      this.router.navigate(['/cuestionarios', idCuestionario]);
    }
  }

  volver() {
    window.history.back();
  }
}
