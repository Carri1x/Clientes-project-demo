import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Login } from './components/login/login';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    Login
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'clientes-web';
}
