import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterRequestDTO } from '../dtos/authentication/RegisterRequestDTO';
import { LoginRequestDTO } from '../dtos/authentication/LoginRequestDTO';
import { LoginResponseDTO } from '../dtos/authentication/LoginResponseDTO';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  register(data: RegisterRequestDTO): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/registrar`, data);
  }

  login(data: LoginRequestDTO): Observable<LoginResponseDTO> {
    return this.http.post<LoginResponseDTO>(`${this.apiUrl}/logar`, data);
  }
}