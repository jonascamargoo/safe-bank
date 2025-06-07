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
    // Validadores removidos da construção do formulário
    this.registerForm = this.fb.group({
      nome: [''],
      cpf: [''],
      email: [''],
      senha: ['']
    });
  }

  /**
   * Método onSubmit modificado para submeter o formulário diretamente,
   * ignorando qualquer validação do lado do cliente.
   */
  onSubmit() {
    const registerData: RegisterRequestDTO = this.registerForm.value;

    this.authService.register(registerData).subscribe({
      next: () => {
        console.log('Registro bem-sucedido!');
        // Redireciona para a página de login após o sucesso
        this.router.navigate(['/login'], { queryParams: { registered: 'true' } });
      },
      error: (error) => {
        console.error('Falha no registro', error);
        // Aqui você pode adicionar uma notificação de erro para o usuário
      }
    });
  }
}