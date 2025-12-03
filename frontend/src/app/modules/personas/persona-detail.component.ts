import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { PersonasService, Persona } from './personas.service';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  standalone: true,
  selector: 'app-persona-detail',
  imports: [CommonModule, MatCardModule, MatChipsModule],
  template: `
    <mat-card *ngIf="persona">
      <div class="header">
        <img [src]="persona.foto || 'https://via.placeholder.com/96'" alt="foto" />
        <div>
          <h2>{{ persona.nombre }} {{ persona.apellido }}</h2>
          <p>{{ persona.email }}</p>
          <mat-chip-row>{{ persona.rol }}</mat-chip-row>
        </div>
      </div>
      <p *ngIf="persona.telefono">Teléfono: {{ persona.telefono }}</p>
      <p>Activo: {{ persona.activo ? 'Sí' : 'No' }}</p>
    </mat-card>
  `,
  styles: [`.header { display: flex; gap: 16px; align-items: center; }
            img { width: 96px; height: 96px; border-radius: 50%; object-fit: cover; }`]
})
export class PersonaDetailComponent implements OnInit {
  persona?: Persona;
  constructor(private route: ActivatedRoute, private service: PersonasService) {}
  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.service.getById(id).subscribe(p => this.persona = p);
    }
  }
}
