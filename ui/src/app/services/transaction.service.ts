import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TransactionDTO } from '../dtos/TransactionDTO';
import { TransactionRequestDTO } from '../dtos/TransactionRequestDTO';
import { TransferRequestDTO } from '../dtos/TransferRequestDTO';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private readonly baseUrl = 'http://localhost:8080/api/transacoes';

  constructor(private http: HttpClient) { }

  deposit(data: TransactionRequestDTO): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/deposito`, data);
  }

  withdraw(data: TransactionRequestDTO): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/saque`, data);
  }

  transfer(data: TransferRequestDTO): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/transferencia`, data);
  }

  getStatement(accountNumber: string): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/extrato/${accountNumber}`);
  }
}