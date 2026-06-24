import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent implements OnInit {
  usuario: any = null;
  intentos: any[] = [];
  cargando: boolean = true;
  errorMensaje: string = '';

  constructor(private apiService: ApiService, private router: Router) {}

  ngOnInit(): void {
    this.cargarPerfil();
  }

  cargarPerfil() {
    this.cargando = true;
    this.apiService.getAuthMe().subscribe({
      next: (userData) => {
        this.usuario = userData;
        this.cargarIntentos(userData.idUsuario);
      },
      error: (err) => {
        console.error('Error al cargar perfil', err);
        this.errorMensaje = 'No se pudo cargar la información del perfil.';
        this.cargando = false;
        
        // Si no hay sesión válida, lo mandamos al login
        if (err.status === 401 || err.status === 403) {
          this.router.navigate(['/login']);
        }
      }
    });
  }

  cargarIntentos(idUsuario: number) {
    this.apiService.getMisIntentos(idUsuario).subscribe({
      next: (data) => {
        this.intentos = data.sort((a, b) => {
          const dateA = new Date(a.fechaRealizacion || a.fecha_realizacion || 0).getTime();
          const dateB = new Date(b.fechaRealizacion || b.fecha_realizacion || 0).getTime();
          return dateB - dateA;
        });
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar historial', err);
        this.errorMensaje = 'No se pudo cargar el historial de calificaciones.';
        this.cargando = false;
      }
    });
  }

  getTitulo(intento: any): string {
    if (intento.cuestionario) {
      return intento.cuestionario.titulo || intento.cuestionario.tituloCuestionario || intento.cuestionario.titulo_cuestionario || 'Cuestionario';
    }
    return `Cuestionario #${intento.idCuestionario || intento.id_cuestionario}`;
  }

  getPorcentaje(intento: any): number {
    // Si el backend nos manda las preguntas totales
    if (intento.totalPreguntas && intento.totalPreguntas > 0) {
      return Math.round((intento.puntaje) / intento.totalPreguntas * 100);
    }
    // Si no tenemos el total, asumimos que cada acierto vale un 10% (como si fueran 10 preguntas)
    // O si prefieres, solo retornamos el puntaje crudo
    return Math.min(intento.puntaje * 10, 100); 
  }

  descargandoPDF = false;

  async descargarPDF() {
    this.descargandoPDF = true;
    try {
      // Importamos las librerías dinámicamente
      const { jsPDF } = await import('jspdf');
      const autoTable = (await import('jspdf-autotable')).default;

      // Creamos el documento PDF
      const pdf = new jsPDF({
        orientation: 'portrait',
        unit: 'mm',
        format: 'a4'
      });

      // Agregamos un título bonito
      pdf.setFontSize(20);
      pdf.setTextColor(15, 23, 42); // slate-900
      pdf.text('Reporte Académico', 14, 20);
      
      pdf.setFontSize(12);
      pdf.setTextColor(100, 116, 139); // slate-500
      pdf.text(`Estudiante: ${this.usuario?.nombreUsuario || 'Usuario'}`, 14, 30);
      pdf.text(`Correo: ${this.usuario?.correo || ''}`, 14, 36);
      pdf.text(`Fecha de emisión: ${new Date().toLocaleDateString()}`, 14, 42);

      // Preparamos los datos de la tabla
      const tableHeaders = [['Cuestionario', 'Fecha de Realización', 'Aciertos', 'Calificación']];
      
      const tableData = this.intentos.map(intento => {
        const titulo = this.getTitulo(intento);
        const fecha = (intento.fechaRealizacion || intento.fecha_realizacion) 
          ? new Date(intento.fechaRealizacion || intento.fecha_realizacion).toLocaleString() 
          : 'Sin fecha';
        const aciertos = `${intento.puntaje} aciertos`;
        const calificacion = `${this.getPorcentaje(intento)}%`;
        
        return [titulo, fecha, aciertos, calificacion];
      });

      // Dibujamos la tabla
      autoTable(pdf, {
        head: tableHeaders,
        body: tableData,
        startY: 50, // Empezar debajo del texto
        theme: 'striped',
        headStyles: {
          fillColor: [15, 23, 42], // Azul oscuro del header
          textColor: [255, 255, 255],
          fontStyle: 'bold'
        },
        styles: {
          font: 'helvetica',
          fontSize: 10,
          cellPadding: 5
        },
        alternateRowStyles: {
          fillColor: [248, 250, 252] // Gris muy claro para filas pares
        }
      });

      // Descargamos el archivo
      pdf.save(`Historial_${this.usuario?.nombreUsuario || 'Regulariza'}.pdf`);
    } catch (err) {
      console.error('Error al generar el PDF', err);
      alert('Hubo un problema al generar el documento PDF.');
    } finally {
      this.descargandoPDF = false;
    }
  }
}
