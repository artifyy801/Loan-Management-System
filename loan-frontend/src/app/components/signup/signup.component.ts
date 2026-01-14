import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  user={
    name: '',
    email: '',
    password: '',
    phone: '',
    salary: ''
  };
  constructor(private auth: AuthService, private router: Router){}
  register(){
    this.auth.signup(this.user).subscribe({
      next: (res) => {
        alert("Registration Successful!");
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error("Full Error:", err);
        if (err.error && err.error.message) {
          alert(err.error.message);
        } else {
          alert("Registration failed! Please try again.");
        }
      }
    })
  }
}
