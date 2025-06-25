import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { UserModel } from '../../models/user-model';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})

export class Login {
  user: UserModel = {
    nombre: '',
    apellido: '',
    email: '',
    password: '',
  };

  constructor(private auth: Auth /**,private router: Router */) {}
  
  onRegister() {
    console.log(this.user);
    this.auth.register(this.user).subscribe({
      next: (res) => {
        //En este apartado en vez de sacar el alert
        //querré direccionar al usuario a otra página de la aplicacion DESCOMENTANDO LO QUE HAY COMENTANDO
        
        //this.router.navigate(['ruta-que-queramos-llevar']); ESTA RUTA TIENE QUE ESTAR DEFINIDA EN = app.routes.ts
        alert(`Usuario ${this.user.nombre} registrado`);
      },
      error: (err) => {
        if (err.status === 409) {
          alert('El email ya está registrado');
        } else {
          alert('Error al registrar usuario');
        }
      },
    });
  }
}
