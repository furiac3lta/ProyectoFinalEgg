import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { OrdenesService, Orden } from './ordenes.service';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  standalone: true,
  selector: 'app-orden-list',
  imports: [CommonModule, MatTableModule, MatButtonModule, MatFormFieldModule, MatInputModule, RouterModule],
  template: `
    <div class="header">
      <h2>Órdenes</h2>
      <div class="actions">
        <mat-form-field appearance="outline">
          <mat-label>Buscar</mat-label>
          <input matInput (keyup)="filter((($event.target as HTMLInputElement)?.value) || '')" placeholder="detalle o email">
        </mat-form-field>
        <a mat-raised-button color="primary" routerLink="/ordenes/nueva">Nueva orden</a>
      </div>
    </div>
    <table mat-table [dataSource]="filtered" class="mat-elevation-z2 full-width">
      <ng-container matColumnDef="detalle">
        <th mat-header-cell *matHeaderCellDef>Detalle</th>
        <td mat-cell *matCellDef="let row">{{ row.detalle }}</td>
      </ng-container>
      <ng-container matColumnDef="email">
        <th mat-header-cell *matHeaderCellDef>Email</th>
        <td mat-cell *matCellDef="let row">{{ row.email }}</td>
      </ng-container>
        <ng-container matColumnDef="acciones">
          <th mat-header-cell *matHeaderCellDef></th>
          <td mat-cell *matCellDef="let row">
            <a mat-button [routerLink]="['/ordenes', row.id]">Ver</a>
            <a mat-button color="accent" [routerLink]="['/ordenes', row.id, 'editar']">Editar</a>
            <button mat-button color="warn" (click)="delete(row.id)" *ngIf="isAdmin">Eliminar</button>
          </td>
        </ng-container>
      <tr mat-header-row *matHeaderRowDef="cols"></tr>
      <tr mat-row *matRowDef="let row; columns: cols"></tr>
    </table>
  `,
  styles: [`.header { display: flex; justify-content: space-between; align-items: center; gap: 12px; flex-wrap: wrap; }
            .actions { display: flex; gap: 12px; align-items: center; }
            table { margin-top: 12px; }`]
})
export class OrdenListComponent implements OnInit {
  data: Orden[] = [];
  filtered: Orden[] = [];
  cols = ['detalle', 'email', 'acciones'];
  constructor(private service: OrdenesService, private auth: AuthService) {}
  ngOnInit(): void { this.service.getAll().subscribe(res => { this.data = res; this.filtered = res; }); }
  filter(term: string): void {
    const value = term.toLowerCase();
    this.filtered = this.data.filter(o => o.detalle.toLowerCase().includes(value) || o.email.toLowerCase().includes(value));
  }

  delete(id?: number): void {
    if (!this.isAdmin || id === undefined) { return; }
    this.service.delete(id).subscribe(() => {
      this.data = this.data.filter(o => o.id !== id);
      this.filtered = this.filtered.filter(o => o.id !== id);
    });
  }

  get isAdmin(): boolean {
    return this.auth.role === 'ADMIN';
  }
}
