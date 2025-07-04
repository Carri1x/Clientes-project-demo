import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClienteModel } from '../models/cliente-model';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {
  private apiUrl = 'http://localhost:8080/home';

  constructor(private http: HttpClient) { }

  getAll(): Observable<ClienteModel[]>{
    return this.http.get<ClienteModel[]>(`${this.apiUrl}/clientes`);
  }

  crearCliente(cliente: ClienteModel): Observable<ClienteModel>{
    return this.http.post<ClienteModel>(`${this.apiUrl}/add`,cliente);
  }

  eliminarCliente(id: number): Observable<boolean>{
    return this.http.delete<boolean>(`${this.apiUrl}/delete/${id}`);  
  }
}
