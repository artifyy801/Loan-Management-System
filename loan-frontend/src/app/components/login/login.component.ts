import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule,CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials = { email: '', password: '' };
  errorMessage = '';

  constructor(private auth: AuthService, private router: Router){}
  login() {
    this.auth.login(this.credentials).subscribe({
      next: (res) => {
        // 1. Save the token (The "Key")
        localStorage.setItem('token', res.token);
        localStorage.setItem('userEmail', this.credentials.email);
        // 2. Redirect to Dashboard
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.errorMessage = 'Invalid Email or Password';
        console.error(err);
      }
    });
  }
}