import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar.component';
import { SidebarComponent } from './sidebar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, NavbarComponent, SidebarComponent],
  template: `
    <div class="app-shell">
      <app-navbar (toggleSidebar)="sidebarOpen = !sidebarOpen"></app-navbar>
      <div class="content-wrapper">
        <app-sidebar [open]="sidebarOpen"></app-sidebar>
        <main class="main-content">
          <router-outlet></router-outlet>
        </main>
      </div>
    </div>
  `,
  styles: [
    `
    .app-shell { min-height: 100vh; background: #f7f9fc; }
    .content-wrapper { display: grid; grid-template-columns: 260px 1fr; }
    .main-content { padding: 24px; }
    @media (max-width: 960px) {
      .content-wrapper { grid-template-columns: 1fr; }
    }
    `
  ]
})
export class LayoutComponent {
  sidebarOpen = true;
}
