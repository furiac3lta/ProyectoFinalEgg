import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { ComentariosService, Comentario } from './comentarios.service';

@Component({
  standalone: true,
  selector: 'app-comentario-list',
  imports: [CommonModule, MatListModule, MatButtonModule, RouterModule],
  template: `
    <h2>Comentarios</h2>
    <mat-list>
      <mat-list-item *ngFor="let c of comentarios">
        <div matListItemTitle>{{ c.opinion }}</div>
        <div matListItemLine>{{ c.experiencia }}</div>
        <a mat-button color="accent" [routerLink]="['/comentarios', c.id, 'editar']" *ngIf="c.id">Editar</a>
      </mat-list-item>
    </mat-list>
    <a mat-raised-button color="primary" routerLink="/comentarios/nuevo">Agregar comentario</a>
  `
})
export class ComentarioListComponent implements OnInit {
  comentarios: Comentario[] = [];
  constructor(private service: ComentariosService) {}
  ngOnInit(): void { this.service.getAll().subscribe(res => this.comentarios = res); }
}
