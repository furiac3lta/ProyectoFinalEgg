import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { OrdenesService } from './ordenes.service';

@Component({
  standalone: true,
  selector: 'app-orden-form',
  imports: [CommonModule, ReactiveFormsModule, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  template: `
    <mat-card>
      <h2>Nueva Orden</h2>
      <form [formGroup]="form" (ngSubmit)="save()" class="form-grid">
        <mat-form-field appearance="outline">
          <mat-label>Detalle</mat-label>
          <textarea matInput rows="3" formControlName="detalle"></textarea>
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>Email del cliente</mat-label>
          <input matInput formControlName="email" type="email" required>
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>ID Prestador</mat-label>
          <input matInput formControlName="prestador_id" type="number" required>
        </mat-form-field>
        <button mat-raised-button color="primary" [disabled]="form.invalid">Guardar</button>
      </form>
    </mat-card>
  `,
  styles: [`.form-grid { display: grid; gap: 12px; }`]
})
export class OrdenFormComponent {
  form = this.fb.group({
    detalle: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    prestador_id: [null as any, Validators.required]
  });
  constructor(private fb: FormBuilder, private service: OrdenesService) {}
  save(): void { if (this.form.valid) { this.service.create(this.form.value as any).subscribe(); } }
}
