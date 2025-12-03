import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { OrdenesService, Orden } from './ordenes.service';

@Component({
  standalone: true,
  selector: 'app-orden-detail',
  imports: [CommonModule, MatCardModule],
  template: `
    <mat-card *ngIf="orden">
      <mat-card-title>Orden #{{ orden.id }}</mat-card-title>
      <mat-card-content>
        <p>{{ orden.detalle }}</p>
        <p>Cliente: {{ orden.email }}</p>
        <p>Prestador: {{ orden.prestador_id }}</p>
        <p>Finalizada: {{ orden.finished_at || 'Pendiente' }}</p>
      </mat-card-content>
    </mat-card>
  `
})
export class OrdenDetailComponent implements OnInit {
  orden?: Orden;
  constructor(private route: ActivatedRoute, private service: OrdenesService) {}
  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) { this.service.getById(id).subscribe(o => this.orden = o); }
  }
}
