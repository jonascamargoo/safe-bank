import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccountDTO } from '../dtos/AccountDTO';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:8080/api/contas';

  constructor(private http: HttpClient) { }

  getAccounts(): Observable<AccountDTO[]> {
    return this.http.get<AccountDTO[]>(this.apiUrl);
  }

  createAccount(): Observable<AccountDTO> {
    return this.http.post<AccountDTO>(this.apiUrl, {});
  }

  updateCreditLimit(accountNumber: string, newCreditLimit: number): Observable<void> {
    const url = `${this.apiUrl}/${accountNumber}/limite`;
    const body = { newCreditLimit };
    return this.http.patch<void>(url, body);
  }
}