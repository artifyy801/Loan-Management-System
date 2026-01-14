import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // <--- 1. Import this
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule], // <--- 2. Add it here!
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  
  loans: any[] = [];
  userEmail: string = ''; // We will get this from the token later

  constructor(private http: HttpClient) {}

  ngOnInit() {
    // For now, let's hardcode an email to test. 
    // Later we will extract it from the JWT token.
    this.userEmail = 'trt@example.com'; 
    this.fetchLoans();
  }

  fetchLoans() {
    this.http.get<any[]>(`http://localhost:8080/api/loans/my-loans?email=${this.userEmail}`)
      .subscribe({
        next: (data) => {
          this.loans = data;
        },
        error: (err) => {
          console.error("Error fetching loans:", err);
        }
      });
  }
}