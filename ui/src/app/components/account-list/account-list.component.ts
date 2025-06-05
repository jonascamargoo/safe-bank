import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // For *ngFor, *ngIf, number pipe
import { Router, RouterLink } from '@angular/router'; // For navigation

// Define an interface for Account for better type safety
interface Account {
  id?: string; // Optional, if you have a unique ID
  accountNumber: string;
  balance: number;
}

@Component({
  selector: 'app-account-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink    // For the "Back to Menu" link
  ],
  templateUrl: './account-list.component.html',
  // styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {
  customerName: string = "Maria"; // Example
  accounts: Account[] = []; // Initialize with empty array

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.fetchAccounts();
  }

  fetchAccounts(): void {
    // Replace with actual service call
    this.accounts = [
      { accountNumber: 'MA-120214', balance: 5500.75 },
      { accountNumber: 'MA-120215', balance: 10320.00 },
    ];
  }

  createNewAccount(): void {
    // Navigate to a form/page for creating a new account
    // For example, if you have a route like '/accounts/new'
    this.router.navigate(['/accounts/new']);
    console.log('Navigate to create new account');
  }
}