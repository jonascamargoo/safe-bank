import { Component } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService, RegisterResponseDTO } from '../../../services/auth.service';
import { RegisterRequestDTO } from '../../../dtos/authentication/RegisterRequestDTO';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  registerForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    cpf: new FormControl('', [Validators.required]),
    phoneNumber: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(3)])
  });

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit() {
    if (this.registerForm.valid) {
      const registerData: RegisterRequestDTO = this.registerForm.value as RegisterRequestDTO;

      this.authService.register(registerData).subscribe({
        next: (response: RegisterResponseDTO) => {
          this.router.navigate(['/logar']);
        },
        error: (err) => {
        }
      });
    }
  }
}