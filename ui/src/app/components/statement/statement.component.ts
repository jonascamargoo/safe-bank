import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AccountDTO } from '../../dtos/AccountDTO';
import { TransactionDTO } from '../../dtos/TransactionDTO';
import { AccountService } from '../../services/account.service';
import { TransactionService } from '../../services/transaction.service';
import { TransactionType } from '../../domains/TransactionType'; // 1. Importe o Enum


@Component({
  selector: 'app-statement',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, DatePipe],
  templateUrl: './statement.component.html',
})
export class StatementComponent implements OnInit {
  public TransactionTypeEnum = TransactionType;

  accounts: AccountDTO[] = [];
  transactions: TransactionDTO[] = [];
  statementForm: FormGroup;
  isLoading = false;
  hasSearched = false;

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private transactionService: TransactionService
  ) {
    this.statementForm = this.fb.group({
      selectedAccount: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadUserAccounts();
  }

  loadUserAccounts(): void {
    this.accountService.getAccounts().subscribe(data => {
      this.accounts = data;
    });
  }

  onViewStatement(): void {
    if (this.statementForm.invalid) {
      return;
    }
    this.hasSearched = true;
    this.isLoading = true;
    this.transactions = [];
    const accountNumber = this.statementForm.get('selectedAccount')?.value;

    this.transactionService.getStatement(accountNumber).subscribe({
      next: (data) => {
        this.transactions = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error(err);
        this.isLoading = false;
      }
    });
  }
}