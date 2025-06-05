import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms'; // For reactive forms

interface AccountSelection {
  accountNumber: string;
  // other properties if needed for display or logic
}

@Component({
  selector: 'app-withdraw',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    ReactiveFormsModule // Import for formGroup, formControlName
  ],
  templateUrl: './withdraw.component.html',
  // styleUrls: ['./withdraw.component.css']
})
export class WithdrawComponent implements OnInit {
  customerName: string = "Maria"; // Example
  accounts: AccountSelection[] = [];
  withdrawForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router
  ) {
    this.withdrawForm = this.fb.group({
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
    if (this.withdrawForm.valid) {
      console.log('Withdrawal Submitted:', this.withdrawForm.value);
      // Call your transaction service here
      // Potentially navigate or show success/error message
      // this.router.navigate(['/menu']); // Example navigation after success
    } else {
      console.error('Withdrawal form is invalid.');
      // Mark fields as touched to show validation errors
      this.withdrawForm.markAllAsTouched();
    }
  }
}