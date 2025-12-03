import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule, MatCardModule, MatButtonModule, MatInputModule],
  template: `
    <div class="auth-wrapper">
      <mat-card>
        <h2>Ingresar</h2>
        <form [formGroup]="form" (ngSubmit)="onSubmit()">
          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Email</mat-label>
            <input matInput formControlName="email" type="email" required>
          </mat-form-field>
          <mat-form-field appearance="outline" class="full-width">
            <mat-label>Contraseña</mat-label>
            <input matInput formControlName="password" type="password" required>
          </mat-form-field>
          <button mat-raised-button color="primary" class="full-width" [disabled]="form.invalid">Entrar</button>
        </form>
      </mat-card>
    </div>
  `,
  styles: [
    `.auth-wrapper { display: flex; justify-content: center; padding: 48px 16px; }
     mat-card { width: 380px; padding: 24px; }
     form { display: flex; flex-direction: column; gap: 12px; }`
  ]
})
export class LoginComponent {
  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {}

  onSubmit(): void {
    if (this.form.valid) {
      this.auth.login(this.form.value as any).subscribe(() => this.router.navigate(['/servicios']));
    }
  }
}
