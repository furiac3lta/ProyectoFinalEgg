import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { PersonasService } from './personas.service';

@Component({
  standalone: true,
  selector: 'app-persona-form',
  imports: [CommonModule, ReactiveFormsModule, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  template: `
    <mat-card>
      <h2>Nueva Persona</h2>
      <form [formGroup]="form" (ngSubmit)="save()" class="form-grid">
        <mat-form-field appearance="outline">
          <mat-label>Nombre</mat-label>
          <input matInput formControlName="nombre" required>
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>Apellido</mat-label>
          <input matInput formControlName="apellido" required>
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>Email</mat-label>
          <input matInput formControlName="email" type="email" required>
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>Rol</mat-label>
          <input matInput formControlName="rol" placeholder="ADMIN/USER/GUEST" required>
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>Teléfono</mat-label>
          <input matInput formControlName="telefono">
        </mat-form-field>
        <mat-form-field appearance="outline">
          <mat-label>Contraseña</mat-label>
          <input matInput type="password" formControlName="password">
        </mat-form-field>
        <div class="actions">
          <button mat-raised-button color="primary" [disabled]="form.invalid">Guardar</button>
        </div>
      </form>
    </mat-card>
  `,
  styles: [`.form-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(240px, 1fr)); gap: 16px; }
            .actions { grid-column: 1 / -1; display: flex; justify-content: flex-end; }`]
})
export class PersonaFormComponent {
  form = this.fb.group({
    nombre: ['', Validators.required],
    apellido: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    rol: ['USER', Validators.required],
    telefono: [''],
    password: ['']
  });

  constructor(private fb: FormBuilder, private service: PersonasService) {}

  save(): void {
    if (this.form.valid) {
      this.service.create(this.form.value as any).subscribe();
    }
  }
}
