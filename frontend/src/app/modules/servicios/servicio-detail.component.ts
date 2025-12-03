import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { ServiciosService, Servicio } from './servicios.service';

@Component({
  standalone: true,
  selector: 'app-servicio-detail',
  imports: [CommonModule, MatCardModule],
  template: `
    <mat-card *ngIf="servicio">
      <mat-card-title>{{ servicio.tipo }}</mat-card-title>
      <mat-card-content>
        <p>{{ servicio.detalle }}</p>
        <p>Activo: {{ servicio.activo ? 'Sí' : 'No' }}</p>
      </mat-card-content>
    </mat-card>
  `
})
export class ServicioDetailComponent implements OnInit {
  servicio?: Servicio;
  constructor(private route: ActivatedRoute, private service: ServiciosService) {}
  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.service.getById(id).subscribe(s => this.servicio = s);
    }
  }
}
