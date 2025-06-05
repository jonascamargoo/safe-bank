import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // For *ngFor, *ngIf, number pipe
import { RouterLink, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

interface AccountSelection {
  accountNumber: string;
}

interface Transaction {
  date: string; // Or Date object, then pipe for formatting
  amount: number;
  type: 'Credit' | 'Debit' | string; // Be more specific if possible
  description?: string;
}

@Component({
  selector: 'app-statement',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    ReactiveFormsModule
  ],
  templateUrl: './statement.component.html',
  // styleUrls: ['./statement.component.css']
})
export class StatementComponent implements OnInit {
  customerName: string = "Maria"; // Example
  accounts: AccountSelection[] = [];
  transactions: Transaction[] = [];
  statementForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router
    ) {
    this.statementForm = this.fb.group({
      selectedAccount: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.fetchUserAccounts();
    // Listen for changes in selected account to auto-fetch transactions
    this.statementForm.get('selectedAccount')?.valueChanges.subscribe(accountNumber => {
      if (accountNumber) {
        this.fetchTransactions(accountNumber);
      } else {
        this.transactions = []; // Clear transactions if no account is selected
      }
    });
  }

  fetchUserAccounts(): void {
    // Replace with actual service call
    this.accounts = [
      { accountNumber: 'MA-120214' },
      { accountNumber: 'MA-120215' },
    ];
  }

  fetchTransactions(accountNumber: string): void {
    if (!accountNumber) {
      this.transactions = [];
      return;
    }
    console.log('Fetching transactions for account:', accountNumber);
    // Replace with actual service call based on accountNumber
    // Example data:
    if (accountNumber === 'MA-120214') {
      this.transactions = [
        { date: '05/04/2025', amount: 4000.00, type: 'Credit', description: 'Salary' },
        { date: '06/04/2025', amount: 50.00, type: 'Debit', description: 'Coffee Shop' },
      ];
    } else if (accountNumber === 'MA-120215') {
      this.transactions = [
        { date: '03/04/2025', amount: 1200.00, type: 'Credit', description: 'Freelance Payment' },
        { date: '07/04/2025', amount: 150.00, type: 'Debit', description: 'Online Shopping' },
      ];
    } else {
        this.transactions = [];
    }
  }

  // This method can be called by a button if you don't want automatic fetching on change
  onViewStatement(): void {
    if (this.statementForm.valid) {
      const selectedAccount = this.statementForm.get('selectedAccount')?.value;
      this.fetchTransactions(selectedAccount);
    } else {
        console.log("Please select an account.");
        this.transactions = [];
    }
  }
}