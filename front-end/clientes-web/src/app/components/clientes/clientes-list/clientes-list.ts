import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientesService } from '../../../services/clientes-service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule} from '@angular/material/icon';
import { RouterLink } from '@angular/router';



@Component({
  selector: 'app-clientes-list',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    RouterLink
  ],
  templateUrl: './clientes-list.html',
  styleUrl: './clientes-list.css'
})
export class ClientesList implements OnInit {
  clientes: any[] = [];

  constructor(private clienteService: ClientesService){}
  
  
  ngOnInit(): void {
    this.clienteService.getAll().subscribe({
      next: (res) => {
        this.clientes = res;
      },
      error: (err) => {
        alert('Error al cargar los clientes'+err)
      }
    });
  } 
}
