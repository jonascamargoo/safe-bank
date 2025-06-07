import { Component } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { RegisterRequestDTO } from '../../../dtos/authentication/RegisterRequestDTO';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  registerForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    cpf: new FormControl('', [Validators.required]),
    phoneNumber: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)])
  });

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    if (this.registerForm.valid) {
      const registerData: RegisterRequestDTO = this.registerForm.value as RegisterRequestDTO;

      this.authService.register(registerData).subscribe({
        next: () => {
          console.log('Registration successful');
          // Adicionar feedback de sucesso e redirecionar
          this.router.navigate(['/login']);
        },
        error: (err) => {
          console.error('Registration failed', err);
          // Implementar feedback de erro para o usuário
        }
      });
    }
  }
}