
// ui/src/app/components/access-menu/access-menu.component.ts
import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterModule } from '@angular/router';
import { AccountService } from '../../services/account.service';
import { AccountDTO } from '../../dtos/AccountDTO';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-access-menu',
  standalone: true,
  imports: [RouterLink, CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './access-menu.component.html',
})
export class AccessMenuComponent implements OnInit {
  accounts: AccountDTO[] = [];
  limitsForm: FormGroup;
  message: string | null = null;

  constructor(
    private accountService: AccountService,
    private fb: FormBuilder
  ) {
    this.limitsForm = this.fb.group({
      accountLimits: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.loadAccounts();
  }

  get accountLimits(): FormArray {
    return this.limitsForm.get('accountLimits') as FormArray;
  }

  loadAccounts(): void {
    this.accountService.getAccounts().subscribe(data => {
      this.accounts = data;
      this.createFormControls();
    });
  }

  createFormControls(): void {
    this.accountLimits.clear(); // Limpa controles antigos antes de recriar
    this.accounts.forEach(account => {
      this.accountLimits.push(this.fb.group({
        limit: [account.creditLimit, [Validators.required, Validators.min(0)]]
      }));
    });
  }
  
  onCreateAccount(): void {
    this.accountService.createAccount().subscribe({
      next: () => {
        this.message = 'Conta criada com sucesso!';
        this.loadAccounts(); // Recarrega a lista de contas
      },
      error: (err) => {
        this.message = 'Erro ao criar conta.';
        console.error(err);
      }
    });
  }

  onUpdateLimit(index: number): void {
    const formGroup = this.accountLimits.at(index);
    if (formGroup.invalid) {
      this.message = 'O valor do limite é inválido.';
      return;
    }
    const accountNumber = this.accounts[index].accountNumber;
    const newLimit = formGroup.value.limit;

    this.accountService.updateCreditLimit(accountNumber, newLimit).subscribe({
      next: () => {
        this.accounts[index].creditLimit = newLimit; // Atualiza o valor localmente
        this.message = `Limite da conta ${accountNumber} atualizado com sucesso!`;
      },
      error: (err) => {
        this.message = 'Falha ao atualizar o limite.';
        console.error(err);
      }
    });
  }
}