import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { routes } from './app/app.routes';
import { LayoutComponent } from './app/layout/layout.component';
import { jwtInterceptor } from './app/core/auth/jwt.interceptor';
import { provideClientHydration } from '@angular/platform-browser';

bootstrapApplication(LayoutComponent, {
  providers: [
    provideRouter(routes),
    provideAnimations(),
    provideHttpClient(withInterceptors([jwtInterceptor])),
    provideClientHydration()
  ]
}).catch(err => console.error(err));
