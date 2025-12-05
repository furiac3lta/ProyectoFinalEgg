import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiciosService } from './servicios.service';

@Component({
  standalone: true,
  selector: 'app-servicio-form',
  imports: [CommonModule, ReactiveFormsModule, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  template: `
    <mat-card>
      <h2>{{ isEdit ? 'Editar Servicio' : 'Nuevo Servicio' }}</h2>
      <form [formGroup]="form" (ngSubmit)="save()" class="form-grid">
        <mat-form-field appearance="outline">
          <mat-label>Tipo</mat-label>
          <input matInput formControlName="tipo" required>
        </mat-form-field>
        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Detalle</mat-label>
          <textarea matInput rows="4" formControlName="detalle"></textarea>
        </mat-form-field>
        <button mat-raised-button color="primary" [disabled]="form.invalid">{{ isEdit ? 'Actualizar' : 'Guardar' }}</button>
      </form>
    </mat-card>
  `,
  styles: [`.form-grid { display: grid; gap: 12px; }`]
})
export class ServicioFormComponent implements OnInit {
  form = this.fb.group({
    tipo: ['', Validators.required],
    detalle: ['', Validators.required]
  });

  isEdit = false;
  currentId?: number;

  constructor(private fb: FormBuilder, private service: ServiciosService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = idParam ? Number(idParam) : undefined;
    if (id) {
      this.isEdit = true;
      this.currentId = id;
      this.service.getById(id).subscribe(servicio => this.form.patchValue(servicio));
    }
  }

  save(): void {
    if (this.form.valid) {
      if (this.isEdit && this.currentId) {
        this.service.update(this.currentId, this.form.value as any).subscribe(() => this.router.navigate(['/servicios']));
      } else {
        this.service.create(this.form.value as any).subscribe(() => this.router.navigate(['/servicios']));
      }
    }
  }
}
