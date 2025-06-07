import { Routes } from '@angular/router';

import { authGuard } from './guards/auth.guard';



import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { AccessMenuComponent } from './components/access-menu/access-menu.component';
import { DepositComponent } from './components/deposit/deposit.component';
import { WithdrawComponent } from './components/withdraw/withdraw.component';
import { StatementComponent } from './components/statement/statement.component';
import { TransferComponent } from './components/transfer/transfer.component'; // Importe o novo componente

export const routes: Routes = [
  { path: 'logar', component: LoginComponent },
  { path: 'registrar', component: RegisterComponent },
  { path: '', redirectTo: '/logar', pathMatch: 'full' },
  
  { path: 'menu', component: AccessMenuComponent, canActivate: [authGuard] },
  { path: 'deposito', component: DepositComponent, canActivate: [authGuard] },
  { path: 'saque', component: WithdrawComponent, canActivate: [authGuard] },
  { path: 'extrato', component: StatementComponent, canActivate: [authGuard] },
  { path: 'transferencia', component: TransferComponent, canActivate: [authGuard] }, // Nova rota

  { path: '**', redirectTo: '/logar' }
];