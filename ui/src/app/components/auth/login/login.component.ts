import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { User } from '../../../domains/User';
import { Router } from '@angular/router';
import { AuthService, LoginRequestDTO, LoginResponseDTO } from '../../../services/auth.service';
import { Auth } from '../../../domains/Auth';
import { ToastModule } from 'primeng/toast';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ButtonModule, FormsModule, ReactiveFormsModule, ToastModule, CommonModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  form: FormGroup;
  user: User;

  constructor(private fBuilder: FormBuilder, private router: Router, private authService: AuthService) {
    this.user = new User();
    this.form = this.fBuilder.group({
      'login': [this.user.cpf, Validators.compose([
        Validators.required])],
      'password': [this.user.password, Validators.compose([
        Validators.required])
      ]
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const loginData: LoginRequestDTO = {
      cpf: this.form.get('login')?.value,
      password: this.form.get('password')?.value
    };

    this.authService.login(loginData).subscribe({
      next: (response: LoginResponseDTO) => {
        sessionStorage.setItem('token', response.token);
        
        this.router.navigate(['/']);
      },
      error: (error) => {
       
      }
    });
  }

  goHome() {
    this.router.navigate(['/']);
  }
}