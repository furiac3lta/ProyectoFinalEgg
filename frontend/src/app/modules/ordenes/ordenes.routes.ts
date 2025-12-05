import { Routes } from '@angular/router';
import { OrdenListComponent } from './orden-list.component';
import { OrdenFormComponent } from './orden-form.component';
import { OrdenDetailComponent } from './orden-detail.component';

export const ORDENES_ROUTES: Routes = [
  { path: '', component: OrdenListComponent },
  { path: 'nueva', component: OrdenFormComponent },
  { path: ':id/editar', component: OrdenFormComponent },
  { path: ':id', component: OrdenDetailComponent }
];
