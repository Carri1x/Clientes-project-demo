import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { MatToolbar } from '@angular/material/toolbar';
import { MatButton } from '@angular/material/button';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone:true,
  imports: [
    MatIconModule,
    CommonModule,
    MatToolbar,
    MatButton,
    RouterLink
  ],
  templateUrl: './header.html',
  styleUrl: './header.css'
})
export class Header {
  title: String = 'App de clientes - productos';
}
