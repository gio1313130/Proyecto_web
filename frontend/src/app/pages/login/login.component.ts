import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  correo: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    if (this.correo && this.password) {
      this.authService.login(this.correo, this.password).subscribe({
        next: (res) => {
          console.log('Login exitoso', res);
          // Redirigir al inicio o dashboard
          this.router.navigate(['/dashboard']);
        },
        error: (err) => {
          console.error('Error de login', err);
          alert('Credenciales incorrectas');
        }
      });
    } else {
      alert('Por favor, ingresa tu correo y contraseña');
    }
  }
}
