import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ServiciosService } from './servicios.service';

@Component({
  standalone: true,
  selector: 'app-servicio-form',
  imports: [CommonModule, ReactiveFormsModule, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  template: `
    <mat-card>
      <h2>Nuevo Servicio</h2>
      <form [formGroup]="form" (ngSubmit)="save()" class="form-grid">
        <mat-form-field appearance="outline">
          <mat-label>Tipo</mat-label>
          <input matInput formControlName="tipo" required>
        </mat-form-field>
        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Detalle</mat-label>
          <textarea matInput rows="4" formControlName="detalle"></textarea>
        </mat-form-field>
        <button mat-raised-button color="primary" [disabled]="form.invalid">Guardar</button>
      </form>
    </mat-card>
  `,
  styles: [`.form-grid { display: grid; gap: 12px; }`]
})
export class ServicioFormComponent {
  form = this.fb.group({
    tipo: ['', Validators.required],
    detalle: ['', Validators.required]
  });

  constructor(private fb: FormBuilder, private service: ServiciosService) {}

  save(): void {
    if (this.form.valid) {
      this.service.create(this.form.value as any).subscribe();
    }
  }
}
