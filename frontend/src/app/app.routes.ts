import { Routes } from '@angular/router';
import { authGuard } from './core/auth/auth.guard';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./modules/auth/auth.routes').then(m => m.AUTH_ROUTES)
  },
  {
    path: 'personas',
    canActivate: [authGuard],
    loadChildren: () => import('./modules/personas/personas.routes').then(m => m.PERSONAS_ROUTES)
  },
  {
    path: 'servicios',
    loadChildren: () => import('./modules/servicios/servicios.routes').then(m => m.SERVICIOS_ROUTES)
  },
  {
    path: 'ordenes',
    canActivate: [authGuard],
    loadChildren: () => import('./modules/ordenes/ordenes.routes').then(m => m.ORDENES_ROUTES)
  },
  {
    path: 'comentarios',
    canActivate: [authGuard],
    loadChildren: () => import('./modules/comentarios/comentarios.routes').then(m => m.COMENTARIOS_ROUTES)
  },
  {
    path: '**',
    redirectTo: ''
  }
];
