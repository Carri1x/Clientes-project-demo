import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserModel } from '../models/user-model';

@Injectable({
  providedIn: 'root'
})
export class Auth {
  private apiUrl = 'http://localhost:5432/api/auth';

  constructor(private http: HttpClient) { }

  login(user: UserModel): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, user);
  }

  logout(): Observable<any> {
    return this.http.post(`${this.apiUrl}/logout`, {});
  }

  register(user: UserModel): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user);
  }
  getUserProfile(): Observable<UserModel> {
    return this.http.get<UserModel>(`${this.apiUrl}/profile`);
  }
}
