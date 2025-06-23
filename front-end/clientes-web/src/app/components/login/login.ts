import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Auth } from '../../services/auth';
import { UserModel } from '../../models/user-model';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    CommonModule,
    Auth
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  user: UserModel = {
    id: 0,
    nombre: '',
    apellido: '',
    nickname: '',
    email: '',
    telefono: ''
  };

  constructor(private auth: Auth) {}

  
}
