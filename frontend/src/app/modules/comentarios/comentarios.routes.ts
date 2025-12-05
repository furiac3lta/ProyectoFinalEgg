import { Routes } from '@angular/router';
import { ComentarioListComponent } from './comentario-list.component';
import { ComentarioFormComponent } from './comentario-form.component';

export const COMENTARIOS_ROUTES: Routes = [
  { path: '', component: ComentarioListComponent },
  { path: 'nuevo', component: ComentarioFormComponent },
  { path: ':id/editar', component: ComentarioFormComponent }
];
