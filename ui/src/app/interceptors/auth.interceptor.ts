// // src/app/interceptors/auth.interceptor.ts
// import { HttpInterceptorFn } from '@angular/common/http';

// export const AuthInterceptor: HttpInterceptorFn = (req, next) => {
//   const authToken = localStorage.getItem('authToken'); // Obtenha o token do localStorage

//   // Se o token existir, clone a requisição e adicione o cabeçalho Authorization
//   if (authToken) {
//     req = req.clone({
//       setHeaders: {
//         Authorization: `Bearer ${authToken}`
//       }
//     });
//   }

//   // Passe a requisição (clonada ou original) para o próximo handler
//   return next(req);
// };