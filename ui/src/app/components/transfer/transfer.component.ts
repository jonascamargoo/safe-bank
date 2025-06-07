import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AccountDTO } from '../../dtos/AccountDTO';
import { AccountService } from '../../services/account.service';
import { TransactionService } from '../../services/transaction.service';
import { TransferRequestDTO } from '../../dtos/TransferRequestDTO';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './transfer.component.html',
})
export class TransferComponent implements OnInit {
  transferForm: FormGroup;
  accounts: AccountDTO[] = [];
  message: string | null = null;
  messageType: 'success' | 'error' = 'success';
  customerName: string | null = null;

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private transactionService: TransactionService,
    private router: Router,
    private authService: AuthService
  ) {
    this.transferForm = this.fb.group({
      sourceAccountNumber: ['', Validators.required],
      destinationAccountNumber: ['', [Validators.required, Validators.pattern(/^[A-Z]{2}-\d{6}$/)]],
      value: ['', [Validators.required, Validators.min(0.01)]],
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
    if (this.transferForm.invalid) {
      this.showMessage('Por favor, preencha todos os campos corretamente.', 'error');
      return;
    }

    const transferData: TransferRequestDTO = this.transferForm.value;

    if (transferData.sourceAccountNumber === transferData.destinationAccountNumber) {
        this.showMessage('A conta de origem e destino não podem ser as mesmas.', 'error');
        return;
    }

    this.transactionService.transfer(transferData).subscribe({
      next: () => {
        this.showMessage('Transferência realizada com sucesso!', 'success');
        setTimeout(() => this.router.navigate(['/menu']), 2000);
      },
      error: (err) => {
        this.showMessage(err.error?.message || 'Erro ao realizar a transferência.', 'error');
        console.error(err);
      },
    });
  }

  private showMessage(msg: string, type: 'success' | 'error', duration: number = 3000): void {
    this.message = msg;
    this.messageType = type;
    setTimeout(() => (this.message = null), duration);
  }
}