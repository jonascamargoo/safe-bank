import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms'; // Removido Validators
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { RegisterRequestDTO } from '../../../dtos/authentication/RegisterRequestDTO';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './register.component.html',
})
export class RegisterComponent {

  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {
    this.registerForm = this.fb.group({
      nome: [''],
      cpf: [''],
      email: [''],
      senha: ['']
    });
  }

  onSubmit() {
    const registerData: RegisterRequestDTO = this.registerForm.value;

    this.authService.register(registerData).subscribe({
      next: () => {
        console.log('Registro bem-sucedido!');
        this.router.navigate(['/login'], { queryParams: { registered: 'true' } });
      },
      error: (error) => {
        console.error('Falha no registro', error);
      }
    });
  }
}