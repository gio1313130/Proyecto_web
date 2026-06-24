import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cuestionario',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cuestionario.component.html',
  styleUrl: './cuestionario.component.css'
})
export class CuestionarioComponent implements OnInit {
  idCuestionario!: number;
  cuestionario: any = null;
  loading: boolean = true;
  error: string | null = null;
  
  // Respuestas del usuario: map de idPregunta -> idOpcion
  respuestas: { [key: number]: number } = {};
  
  // Resultado
  resultado: any = null;
  enviando: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.idCuestionario = +id;
        this.cargarCuestionario();
      }
    });
  }

  cargarCuestionario() {
    this.loading = true;
    this.apiService.getCuestionarioResolver(this.idCuestionario).subscribe({
      next: (data) => {
        this.cuestionario = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar cuestionario', err);
        this.error = 'No se pudo cargar el cuestionario. Verifica tu conexión.';
        this.loading = false;
      }
    });
  }

  seleccionarOpcion(idPregunta: number, idOpcion: number) {
    if (this.resultado) return; // Si ya terminó, no deja cambiar
    this.respuestas[idPregunta] = idOpcion;
  }

  enviarCuestionario() {
    if (!this.cuestionario || !this.cuestionario.preguntas) return;

    // Validar que respondió todo
    if (Object.keys(this.respuestas).length < this.cuestionario.preguntas.length) {
      alert('Por favor responde todas las preguntas antes de enviar.');
      return;
    }

    this.enviando = true;

    // Obtener idUsuario del localStorage
    const userStr = localStorage.getItem('usuario');
    let idUsuario = 1; // Fallback para pruebas locales
    if (userStr) {
      try {
        const user = JSON.parse(userStr);
        if (user && user.idUsuario) {
          idUsuario = user.idUsuario;
        }
      } catch(e) {}
    }

    const payload = {
      idUsuario: idUsuario,
      idCuestionario: this.idCuestionario,
      respuestas: Object.keys(this.respuestas).map(key => ({
        idPregunta: +key,
        idOpcion: this.respuestas[+key]
      }))
    };

    this.apiService.resolverCuestionario(payload).subscribe({
      next: (res) => {
        this.resultado = res;
        this.enviando = false;
        window.scrollTo({ top: 0, behavior: 'smooth' });
      },
      error: (err) => {
        console.error('Error enviando respuestas', err);
        alert('Ocurrió un error al enviar el cuestionario. Intenta de nuevo.');
        this.enviando = false;
      }
    });
  }

  volver() {
    window.history.back();
  }
}
