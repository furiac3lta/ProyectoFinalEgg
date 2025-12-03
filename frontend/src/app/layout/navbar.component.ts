import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { AuthService } from '../core/auth/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, MatToolbarModule, MatButtonModule, RouterModule],
  template: `
    <mat-toolbar color="primary" class="navbar">
      <button mat-button (click)="toggleSidebar.emit()">☰</button>
      <span class="brand" routerLink="/">ServicioExpress</span>
      <span class="spacer"></span>
      <ng-container *ngIf="!(auth.isLoggedIn$ | async); else logged">
        <a mat-button routerLink="/login">Login</a>
        <a mat-raised-button color="accent" routerLink="/register">Registrarse</a>
      </ng-container>
      <ng-template #logged>
        <span class="email">{{ auth.userEmail }}</span>
        <button mat-button (click)="auth.logout()">Salir</button>
      </ng-template>
    </mat-toolbar>
  `,
  styles: [
    `.navbar { position: sticky; top: 0; z-index: 10; }
     .brand { font-weight: 700; }
     .spacer { flex: 1; }
     .email { margin-right: 12px; font-weight: 600; }`
  ]
})
export class NavbarComponent {
  @Output() toggleSidebar = new EventEmitter<void>();
  constructor(public auth: AuthService) {}
}
