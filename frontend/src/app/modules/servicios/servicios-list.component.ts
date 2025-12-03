import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { ServiciosService, Servicio } from './servicios.service';

@Component({
  standalone: true,
  selector: 'app-servicios-list',
  imports: [CommonModule, MatCardModule, MatButtonModule, MatFormFieldModule, MatInputModule, RouterModule],
  template: `
    <div class="card-grid">
      <mat-card *ngFor="let servicio of filtered">
        <mat-card-title>{{ servicio.tipo }}</mat-card-title>
        <mat-card-content>{{ servicio.detalle }}</mat-card-content>
        <mat-card-actions>
          <a mat-button color="primary" [routerLink]="['/servicios', servicio.id]">Ver</a>
        </mat-card-actions>
      </mat-card>
    </div>
    <mat-card class="filters">
      <mat-form-field appearance="outline" class="full-width">
        <mat-label>Buscar</mat-label>
        <input matInput (keyup)="filter($event.target?.value || '')" placeholder="Palabra clave">
      </mat-form-field>
      <a mat-raised-button color="primary" routerLink="/servicios/nuevo">Agregar servicio</a>
    </mat-card>
  `,
  styles: [`.filters { margin-top: 16px; display: flex; gap: 12px; align-items: center; }
            @media(max-width: 600px){ .filters{ flex-direction: column; align-items: stretch; } }`]
})
export class ServiciosListComponent implements OnInit {
  data: Servicio[] = [];
  filtered: Servicio[] = [];
  constructor(private service: ServiciosService) {}
  ngOnInit(): void { this.service.getAll().subscribe(res => { this.data = res; this.filtered = res; }); }
  filter(term: string): void {
    const value = term.toLowerCase();
    this.filtered = this.data.filter(s => s.tipo.toLowerCase().includes(value) || s.detalle.toLowerCase().includes(value));
  }
}
