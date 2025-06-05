import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // For *ngFor, *ngIf, etc.
import { Router, RouterLink } from '@angular/router'; // Router for navigation, RouterLink for <a routerLink="">

// Define an interface for menu options for better type safety
interface MenuOption {
  title: string;
  icon: string;
  route: string;
}

@Component({
  selector: 'app-access-menu',
  standalone: true,
  imports: [
    CommonModule, // Provides *ngFor, *ngIf, etc.
    RouterLink    // For any <a routerLink="..."> elements if you add them
  ],
  templateUrl: './access-menu.component.html', // Assuming HTML is in a separate file
  // styleUrls: ['./access-menu.component.css'] // If you have specific styles
})
export class AccessMenuComponent implements OnInit {
  customerName: string = "Maria"; // Example, fetch from a service
  menuOptions: MenuOption[] = [
    { title: 'Accounts', icon: 'assets/icons/accounts.svg', route: '/accounts' },
    { title: 'Withdraw', icon: 'assets/icons/withdraw.svg', route: '/withdraw' },
    { title: 'Deposit', icon: 'assets/icons/deposit.svg', route: '/deposit' },
    { title: 'Statement', icon: 'assets/icons/statement.svg', route: '/statement' },
    // Add other options like 'Transfers' if needed
  ];

  constructor(private router: Router) {} // Inject Router for navigation

  ngOnInit(): void {
    // Fetch customerName or other initial data if needed
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }

  logout(): void {
    // Implement logout logic (e.g., clear session, navigate to login)
    this.router.navigate(['/login']);
    console.log('Logged out');
  }
}