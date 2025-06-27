import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientesService } from '../../../services/clientes-service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule} from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { Observable } from 'rxjs';


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
  eliminar(id: number){
    this.clienteService.eliminarCliente(id).subscribe({
      next: (res: boolean) => {
        //Si el cliente ha sido eliminado correctamente de la base de datos eliminamos el cliente de la lista 
        //previamente guardada
        this.clientes = this.clientes.filter(cli => cli.id !== id);
        if(res){
          alert(`El cliente con id: ${id} ha sido eliminado correctemante`);
        }else{
          alert('No ha sido eliminado correctamente');
        }
      },
      error: (err: any) => {
        alert('Ha habido un error al eliminar el usuario'+err);
      }
    });
  }

}
