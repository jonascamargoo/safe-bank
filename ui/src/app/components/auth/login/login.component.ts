import { Component } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { LoginRequestDTO } from '../../../dtos/authentication/LoginRequestDTO';
import { LoginResponseDTO } from '../../../dtos/authentication/LoginResponseDTO';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  loginForm = new FormGroup({
    cpf: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  });

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    if (this.loginForm.valid) {
      // O TypeScript garante que o objeto tem o formato correto de LoginRequestDTO
      const loginData: LoginRequestDTO = this.loginForm.value as LoginRequestDTO;

      this.authService.login(loginData).subscribe({
        next: (response: LoginResponseDTO) => {
          // O autocompletar e o compilador garantem o acesso correto a `response.token`
          localStorage.setItem('authToken', response.token);
          this.router.navigate(['/menu']);
        },
        error: (err) => {
          console.error('Login failed', err);
          // Implementar feedback de erro para o usuário
        }
      });
    }
  }
}