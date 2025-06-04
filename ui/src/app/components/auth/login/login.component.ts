import { Component } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms'; // Import ReactiveFormsModule
import { CommonModule } from '@angular/common'; // Import CommonModule

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule], // Add ReactiveFormsModule here
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm = new FormGroup({
    cpf: new FormControl('', [Validators.required /* Add CPF validation */]),
    password: new FormControl('', [Validators.required])
  });

  onSubmit() {
    if (this.loginForm.valid) {
      // TODO: Call your authentication service to log in the user
      console.log('Login form submitted:', this.loginForm.value);
      // On successful login, navigate to the main menu/dashboard
    } else {
      this.loginForm.markAllAsTouched();
    }
  }
}