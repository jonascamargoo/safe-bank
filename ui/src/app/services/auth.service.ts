import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { jwtDecode } from "jwt-decode";

// Interfaces para os DTOs
export interface RegisterRequestDTO {
  name: string;
  cpf: string;
  phoneNumber: string;
  password: string;
}

export interface LoginRequestDTO {
  cpf: string;
  password: string;
}

export interface LoginResponseDTO {
  token: string;
}

export interface RegisterResponseDTO {
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = 'http://localhost:8080/api/auth';

  constructor(private httpClient: HttpClient) { }

  register(registerData: RegisterRequestDTO): Observable<RegisterResponseDTO> {
    return this.httpClient.post<RegisterResponseDTO>(
      `${this.baseUrl}/registrar`,
      registerData
    );
  }

  login(loginData: LoginRequestDTO): Observable<LoginResponseDTO> {
    console.log(JSON.stringify(loginData))
    return this.httpClient.post<LoginResponseDTO>(
      `${this.baseUrl}/logar`,
      // JSON.stringify(loginData)
      loginData
    );
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  decodeToken(): any {
    const token = this.getToken();
    return token ? jwtDecode(token) : null;
  }

  isAuthenticated(): boolean {
    return this.getToken() !== null;
  }

  logout(): void {
    sessionStorage.removeItem('token');
  }
}