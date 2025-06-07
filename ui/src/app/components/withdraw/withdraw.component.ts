import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AccountDTO } from '../../dtos/AccountDTO';
import { AccountService } from '../../services/account.service';
import { TransactionService } from '../../services/transaction.service';
import { TransactionRequestDTO } from '../../dtos/TransactionRequestDTO';
import { AuthService } from '../../services/auth.service'; // 1. Importar o AuthService

@Component({
  selector: 'app-withdraw',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './withdraw.component.html',
})
export class WithdrawComponent implements OnInit {
  accounts: AccountDTO[] = [];
  withdrawForm: FormGroup;
  message: string | null = null;
  messageType: 'success' | 'error' = 'success';
  customerName: string | null = null; // 2. Declarar a propriedade

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private accountService: AccountService,
    private transactionService: TransactionService,
    private authService: AuthService // 3. Injetar o AuthService
  ) {
    this.withdrawForm = this.fb.group({
      accountNumber: ['', Validators.required],
      value: ['', [Validators.required, Validators.min(0.01)]]
    });
  }

  ngOnInit(): void {
    this.customerName = this.authService.getCustomerName(); // 4. Inicializar a propriedade
    this.loadUserAccounts();
  }

  loadUserAccounts(): void {
    this.accountService.getAccounts().subscribe(data => {
      this.accounts = data;
    });
  }

  onSubmit(): void {
    if (this.withdrawForm.invalid) {
      this.showMessage('Formulário inválido.', 'error');
      return;
    }
    
    const withdrawData: TransactionRequestDTO = this.withdrawForm.value;

    this.transactionService.withdraw(withdrawData).subscribe({
      next: () => {
        this.showMessage('Saque realizado com sucesso!', 'success');
        setTimeout(() => this.router.navigate(['/menu']), 2000);
      },
      error: (err) => {
        this.showMessage(err.error?.message || 'Erro ao realizar o saque.', 'error');
        console.error(err);
      }
    });
  }

  private showMessage(msg: string, type: 'success' | 'error', duration: number = 3000): void {
    this.message = msg;
    this.messageType = type;
    setTimeout(() => this.message = null, duration);
  }
}