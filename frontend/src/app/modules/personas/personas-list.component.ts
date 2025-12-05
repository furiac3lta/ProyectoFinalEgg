import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { RouterModule } from '@angular/router';
import { PersonasService, Persona } from './personas.service';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  standalone: true,
  selector: 'app-personas-list',
  imports: [CommonModule, MatTableModule, MatInputModule, MatButtonModule, MatIconModule, MatCardModule, RouterModule],
  template: `
    <mat-card>
      <div class="header">
        <h2>Personas</h2>
        <div class="actions">
          <mat-form-field appearance="outline">
            <mat-label>Buscar</mat-label>
            <input matInput (keyup)="filter((($event.target as HTMLInputElement)?.value) || '')" placeholder="Nombre o email">
          </mat-form-field>
          <a mat-raised-button color="primary" routerLink="/personas/nuevo">Nuevo</a>
        </div>
      </div>
      <table mat-table [dataSource]="filtered" class="mat-elevation-z2 full-width">
        <ng-container matColumnDef="nombre">
          <th mat-header-cell *matHeaderCellDef>Nombre</th>
          <td mat-cell *matCellDef="let row">{{ row.nombre }} {{ row.apellido }}</td>
        </ng-container>
        <ng-container matColumnDef="email">
          <th mat-header-cell *matHeaderCellDef>Email</th>
          <td mat-cell *matCellDef="let row">{{ row.email }}</td>
        </ng-container>
        <ng-container matColumnDef="rol">
          <th mat-header-cell *matHeaderCellDef>Rol</th>
          <td mat-cell *matCellDef="let row">{{ row.rol }}</td>
        </ng-container>
        <ng-container matColumnDef="acciones">
          <th mat-header-cell *matHeaderCellDef></th>
          <td mat-cell *matCellDef="let row">
            <a mat-button color="primary" [routerLink]="['/personas', row.id]">Ver</a>
            <a mat-button color="accent" [routerLink]="['/personas', row.id, 'editar']">Editar</a>
            <button mat-button color="warn" (click)="delete(row.id)" *ngIf="isAdmin">Eliminar</button>
          </td>
        </ng-container>
        <tr mat-header-row *matHeaderRowDef="cols"></tr>
        <tr mat-row *matRowDef="let row; columns: cols"></tr>
      </table>
    </mat-card>
  `,
  styles: [`.header { display: flex; justify-content: space-between; align-items: center; gap: 16px; flex-wrap: wrap; }
            .actions { display: flex; gap: 12px; align-items: center; }
            table { margin-top: 12px; }`]
})
export class PersonasListComponent implements OnInit {
  data: Persona[] = [];
  filtered: Persona[] = [];
  cols = ['nombre', 'email', 'rol', 'acciones'];

  constructor(private service: PersonasService, private auth: AuthService) {}

  ngOnInit(): void {
    this.service.getAll().subscribe(res => { this.data = res; this.filtered = res; });
  }

  filter(term: string): void {
    const value = term.toLowerCase();
    this.filtered = this.data.filter(p => p.nombre.toLowerCase().includes(value) || p.email.toLowerCase().includes(value));
  }

  delete(id?: number): void {
    if (!this.isAdmin || id === undefined) { return; }
    this.service.delete(id).subscribe(() => {
      this.data = this.data.filter(p => p.id !== id);
      this.filtered = this.filtered.filter(p => p.id !== id);
    });
  }

  get isAdmin(): boolean {
    return this.auth.role === 'ADMIN';
  }
}
