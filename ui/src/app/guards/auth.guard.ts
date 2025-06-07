import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('authToken'); // Pega o token do localStorage

  if (token) {
    // Se o token existe, permite o acesso
    return true;
  } else {
    // Se não há token, redireciona para a página de login
    router.navigate(['/login']);
    return false;
  }
};