import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ComentariosService } from './comentarios.service';

@Component({
  standalone: true,
  selector: 'app-comentario-form',
  imports: [CommonModule, ReactiveFormsModule, MatCardModule, MatInputModule, MatButtonModule],
  template: `
    <mat-card>
      <h2>Nuevo Comentario</h2>
      <form [formGroup]="form" (ngSubmit)="save()" class="form-grid">
        <mat-form-field appearance="outline">
          <mat-label>Opinión</mat-label>
          <input matInput formControlName="opinion" required>
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>Experiencia</mat-label>
          <textarea matInput rows="3" formControlName="experiencia"></textarea>
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>ID Persona</mat-label>
          <input matInput type="number" formControlName="persona_id" required>
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>ID Orden</mat-label>
          <input matInput type="number" formControlName="orden_id" required>
        </mat-form-field>
        <button mat-raised-button color="primary" [disabled]="form.invalid">Guardar</button>
      </form>
    </mat-card>
  `,
  styles: [`.form-grid { display: grid; gap: 12px; }`]
})
export class ComentarioFormComponent {
  form = this.fb.group({
    opinion: ['', Validators.required],
    experiencia: ['', Validators.required],
    persona_id: [null as any, Validators.required],
    orden_id: [null as any, Validators.required]
  });
  constructor(private fb: FormBuilder, private service: ComentariosService) {}
  save(): void { if (this.form.valid) { this.service.create(this.form.value as any).subscribe(); } }
}
