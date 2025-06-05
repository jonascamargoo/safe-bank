import { Routes } from '@angular/router';
import { AccessMenuComponent } from './components/access-menu/access-menu.component';
import { AccountListComponent } from './components/account-list/account-list.component';
import { WithdrawComponent } from './components/withdraw/withdraw.component';
import { DepositComponent } from './components/deposit/deposit.component';
import { StatementComponent } from './components/statement/statement.component';

export const routes: Routes = [
  // { path: '', redirectTo: '/login', pathMatch: 'full' },
  // { path: 'login', component: LoginComponent },
  // { path: 'register', component: RegisterComponent },
  { path: 'menu', component: AccessMenuComponent /*, canActivate: [AuthGuard] */ },
  { path: 'accounts', component: AccountListComponent /*, canActivate: [AuthGuard] */ },
  { path: 'withdraw', component: WithdrawComponent /*, canActivate: [AuthGuard] */ },
  { path: 'deposit', component: DepositComponent /*, canActivate: [AuthGuard] */ },
  { path: 'statement', component: StatementComponent /*, canActivate: [AuthGuard] */ },
  // ... other routes
];