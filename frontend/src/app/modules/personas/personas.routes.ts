import { Routes } from '@angular/router';
import { PersonasListComponent } from './personas-list.component';
import { PersonaFormComponent } from './persona-form.component';
import { PersonaDetailComponent } from './persona-detail.component';

export const PERSONAS_ROUTES: Routes = [
  { path: '', component: PersonasListComponent },
  { path: 'nuevo', component: PersonaFormComponent },
  { path: ':id', component: PersonaDetailComponent }
];
