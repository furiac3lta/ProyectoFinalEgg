import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <aside class="sidebar" [class.closed]="!open">
      <nav>
        <a routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }">Inicio</a>
        <a routerLink="/servicios" routerLinkActive="active">Servicios</a>
        <a routerLink="/personas" routerLinkActive="active">Personas</a>
        <a routerLink="/ordenes" routerLinkActive="active">Órdenes</a>
        <a routerLink="/comentarios" routerLinkActive="active">Comentarios</a>
        <a routerLink="/dashboard" routerLinkActive="active">Dashboard</a>
      </nav>
    </aside>
  `,
  styles: [
    `
    .sidebar { background: white; min-height: calc(100vh - 64px); padding: 16px; border-right: 1px solid #e5e7eb; }
    .sidebar.closed { display: none; }
    nav { display: flex; flex-direction: column; gap: 12px; }
    a { color: #374151; text-decoration: none; font-weight: 600; }
    .active { color: #1565c0; }
    @media (max-width: 960px) { .sidebar { min-height: auto; } }
    `
  ]
})
export class SidebarComponent {
  @Input() open = true;
}
