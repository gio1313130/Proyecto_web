import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';

export const adminGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const userStr = localStorage.getItem('usuario');
  
  if (userStr) {
    try {
      const user = JSON.parse(userStr);
      if (user.rol === 'ADMIN' || user.rol === 'admin') {
        return true;
      }
    } catch (e) {
      console.error('Error parsing user data in adminGuard');
    }
  }
  
  // Si no es admin o no está logueado, lo mandamos al dashboard
  router.navigate(['/dashboard']);
  return false;
};
