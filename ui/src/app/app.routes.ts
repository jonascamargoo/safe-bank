import { Routes } from '@angular/router';

import { authGuard } from './guards/auth.guard';

import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { AccessMenuComponent } from './components/access-menu/access-menu.component';
import { AccountListComponent } from './components/account-list/account-list.component';
import { DepositComponent } from './components/deposit/deposit.component';
import { WithdrawComponent } from './components/withdraw/withdraw.component';
import { StatementComponent } from './components/statement/statement.component';

export const routes: Routes = [
  { path: 'logar', component: LoginComponent },
  { path: 'registrar', component: RegisterComponent },

  { path: '', redirectTo: '/logar', pathMatch: 'full' },

  {
    path: 'menu',
    component: AccessMenuComponent,
    canActivate: [authGuard],
    children: [
      { path: 'contas', component: AccountListComponent },
      { path: 'deposito', component: DepositComponent },
      { path: 'saque', component: WithdrawComponent },
      { path: 'extrato', component: StatementComponent },
      { path: '', redirectTo: 'contas', pathMatch: 'full' },
    ]
  },

  { path: '**', redirectTo: '/logar' }
];