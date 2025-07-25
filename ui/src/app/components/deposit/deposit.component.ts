import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AccountDTO } from '../../dtos/AccountDTO';
import { AccountService } from '../../services/account.service';
import { TransactionService } from '../../services/transaction.service';
import { TransactionRequestDTO } from '../../dtos/TransactionRequestDTO';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-deposit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './deposit.component.html',
})
export class DepositComponent implements OnInit {
  accounts: AccountDTO[] = [];
  depositForm: FormGroup;
  message: string | null = null;
  messageType: 'success' | 'error' = 'success';
  customerName: string | null = null;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private accountService: AccountService,
    private transactionService: TransactionService,
    private authService: AuthService
    
  ) {
    this.depositForm = this.fb.group({
      accountNumber: ['', Validators.required],
      value: ['', [Validators.required, Validators.min(0.01)]]
    });
  }

  ngOnInit(): void {
    this.customerName = this.authService.getCustomerName();
    this.loadUserAccounts();
  }

  loadUserAccounts(): void {
    this.accountService.getAccounts().subscribe(data => {
      this.accounts = data;
    });
  }

  onSubmit(): void {
    if (this.depositForm.invalid) {
      this.showMessage('Formulário inválido.', 'error');
      return;
    }
    
    const depositData: TransactionRequestDTO = this.depositForm.value;

    this.transactionService.deposit(depositData).subscribe({
      next: () => {
        this.showMessage('Depósito realizado com sucesso!', 'success');
        setTimeout(() => this.router.navigate(['/menu']), 2000);
      },
      error: (err) => {
        this.showMessage(err.error?.message || 'Erro ao realizar o depósito.', 'error');
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