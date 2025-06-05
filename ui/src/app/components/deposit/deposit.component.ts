import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

interface AccountSelection {
  accountNumber: string;
}

@Component({
  selector: 'app-deposit',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    ReactiveFormsModule
  ],
  templateUrl: './deposit.component.html',
  // styleUrls: ['./deposit.component.css']
})
export class DepositComponent implements OnInit {
  customerName: string = "Maria"; // Example
  accounts: AccountSelection[] = [];
  depositForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router
  ) {
    this.depositForm = this.fb.group({
      selectedAccount: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(0.01)]]
    });
  }

  ngOnInit(): void {
    this.fetchUserAccounts();
  }

  fetchUserAccounts(): void {
    // Replace with actual service call
    this.accounts = [
      { accountNumber: 'MA-120214' },
      { accountNumber: 'MA-120215' },
    ];
  }

  onSubmit(): void {
    if (this.depositForm.valid) {
      console.log('Deposit Submitted:', this.depositForm.value);
      // Call your transaction service here
      // this.router.navigate(['/menu']); // Example navigation
    } else {
      console.error('Deposit form is invalid.');
      this.depositForm.markAllAsTouched();
    }
  }
}