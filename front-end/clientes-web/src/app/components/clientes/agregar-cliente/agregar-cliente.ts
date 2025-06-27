import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClientesService } from '../../../services/clientes-service';
import { ClienteModel } from '../../../models/cliente-model';
import { Router } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-agregar-cliente',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    CommonModule
  ],
  templateUrl: './agregar-cliente.html',
  styleUrl: './agregar-cliente.css'
})
export class AgregarCliente {
  clienteForm: FormGroup;
  cliente: ClienteModel = {
    nickname: '',
    nombre: '',
    apellido: '',
    email: '',
    telefono: ''
  };

  constructor(
    private clienteService: ClientesService,
    private router: Router,
    private fr: FormBuilder
   ){
    this.clienteForm = this.fr.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
      nickname: ['', Validators.required]
    });
  }

  agregar(){
    if (this.clienteForm.invalid) {
    this.clienteForm.markAllAsTouched(); // marca errores
    return;
  }
    this.clienteService.crearCliente(this.clienteForm.value).subscribe({
      next: (res) => {
        alert(`El cliente ${res.nombre} ha sido añadido a la base de datos.`);
        this.router.navigate(['home/clientes']);
      },
      error: (err) => {
        if(err === '409'){
          alert(`No se ha podido insertar al cliente ${this.cliente.nombre}`);
        }else{
          alert(`Se ha producido un error al insertar el cliente ${err} ${this.clienteForm.value.nombre}`);
        }
      }
    })
  }
}
