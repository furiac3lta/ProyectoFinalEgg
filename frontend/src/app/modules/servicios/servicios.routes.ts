import { Routes } from '@angular/router';
import { ServiciosListComponent } from './servicios-list.component';
import { ServicioFormComponent } from './servicio-form.component';
import { ServicioDetailComponent } from './servicio-detail.component';

export const SERVICIOS_ROUTES: Routes = [
  { path: '', component: ServiciosListComponent },
  { path: 'nuevo', component: ServicioFormComponent },
  { path: ':id/editar', component: ServicioFormComponent },
  { path: ':id', component: ServicioDetailComponent }
];
