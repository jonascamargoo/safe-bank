import { Routes } from '@angular/router';
import { RegisterComponent } from './components/auth/register/register.component';
import { LoginComponent } from './components/auth/login/login.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },

];



// import { Routes } from '@angular/router';


// export const routes: Routes = [
//   { path: 'login', component: LoginComponent },
//   { path: 'register', component: RegisterComponent },
  // { path: '', redirectTo: '/login', pathMatch: 'full' }, // Default route
  // TODO: Add routes for other components (dashboard, accounts, etc.)
  // TODO: Add guards for authenticated routes
// ];