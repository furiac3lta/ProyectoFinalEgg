import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  standalone: true,
  selector: 'app-register',
  imports: [CommonModule, ReactiveFormsModule, MatCardModule, MatInputModule, MatButtonModule],
  template: `
    <div class="auth-wrapper">
      <mat-card>
        <h2>Crear cuenta</h2>
        <form [formGroup]="form" (ngSubmit)="onSubmit()">
          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Nombre</mat-label>
            <input matInput formControlName="nombre" required>
          </mat-form-field>
          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Apellido</mat-label>
            <input matInput formControlName="apellido" required>
          </mat-form-field>
          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Email</mat-label>
            <input matInput formControlName="email" type="email" required>
          </mat-form-field>
          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Contraseña</mat-label>
            <input matInput formControlName="password" type="password" required>
          </mat-form-field>
          <button mat-raised-button color="primary" class="full-width" [disabled]="form.invalid">Registrar</button>
        </form>
      </mat-card>
    </div>
  `,
  styles: [`.auth-wrapper { display: flex; justify-content: center; padding: 48px 16px; }
            mat-card { width: 420px; padding: 24px; }
            form { display: flex; flex-direction: column; gap: 12px; }`]
})
export class RegisterComponent {
  form = this.fb.group({
    nombre: ['', Validators.required],
    apellido: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {}

  onSubmit(): void {
    if (this.form.valid) {
      this.auth.register(this.form.value).subscribe(() => this.router.navigate(['/login']));
    }
  }
}
