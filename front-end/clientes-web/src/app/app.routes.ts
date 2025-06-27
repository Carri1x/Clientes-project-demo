import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { ClientesList } from './components/clientes/clientes-list/clientes-list';
import { MainLayout } from './layout/main-layout/main-layout';
import { AgregarCliente } from './components/clientes/agregar-cliente/agregar-cliente';

export const routes: Routes = [
  { path: '', redirectTo: 'home/clientes', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'home' , component: MainLayout,
    children: [
      {path: 'clientes', component: ClientesList},
      {path: 'agregar-cliente', component: AgregarCliente}
    ]
  }
];
