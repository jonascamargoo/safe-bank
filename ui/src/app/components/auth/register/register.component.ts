import { Component } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms'; // Import ReactiveFormsModule
import { CommonModule } from '@angular/common'; // Import CommonModule

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule], // Add ReactiveFormsModule here
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    cpf: new FormControl('', [Validators.required /* Add CPF validation */]),
    phoneNumber: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)])
  });

  onSubmit() {
    if (this.registerForm.valid) {
      // TODO: Call your authentication service to register the user
      console.log('Register form submitted:', this.registerForm.value);
    } else {
      // Mark fields as touched to show errors
      this.registerForm.markAllAsTouched();
    }
  }
}