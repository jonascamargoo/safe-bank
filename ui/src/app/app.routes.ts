import { Routes } from '@angular/router';

// Guards
import { authGuard } from './guards/auth.guard';

// Componentes
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { AccessMenuComponent } from './components/access-menu/access-menu.component';
import { AccountListComponent } from './components/account-list/account-list.component';
import { DepositComponent } from './components/deposit/deposit.component';
import { WithdrawComponent } from './components/withdraw/withdraw.component';
import { StatementComponent } from './components/statement/statement.component';

export const routes: Routes = [
  // Rotas públicas com os novos nomes
  { path: 'logar', component: LoginComponent },
  { path: 'registrar', component: RegisterComponent },

  // Rota padrão atualizada para /logar
  { path: '', redirectTo: '/logar', pathMatch: 'full' },

  // Rotas privadas (protegidas pelo AuthGuard)
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

  // Rota de fallback para qualquer caminho não encontrado
  { path: '**', redirectTo: '/logar' }
];