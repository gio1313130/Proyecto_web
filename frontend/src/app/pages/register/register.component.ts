import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  nombreUsuario: string = '';
  correo: string = '';
  passwordUsuario: string = '';
  rol: string = 'ALUMNO'; // Valor por defecto

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    if (this.passwordUsuario.length < 6) {
      alert('La contraseña debe tener al menos 6 caracteres.');
      return;
    }

    if (this.nombreUsuario && this.correo && this.passwordUsuario) {
      this.authService.register(this.nombreUsuario, this.correo, this.passwordUsuario, this.rol).subscribe({
        next: (res) => {
          console.log('Registro exitoso', res);
          alert('Registro exitoso. Revisa tu correo y luego inicia sesión.');
          this.router.navigate(['/login']);
        },
        error: (err) => {
          console.error('Error detallado:', err);
          let mensajeError = `Error HTTP: ${err.status} ${err.statusText}\n`;
          
          if (err.error) {
            if (typeof err.error === 'string') {
              mensajeError += `Detalle: ${err.error}`;
            } else {
              mensajeError += `Detalle JSON: ${JSON.stringify(err.error, null, 2)}`;
            }
          } else {
            mensajeError += `No hay cuerpo en el error. Probablemente bloqueado por CORS o Security.`;
          }
          
          alert(mensajeError);
        }
      });
    } else {
      alert('Por favor, llena todos los campos');
    }
  }
}
