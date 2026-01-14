import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms'; // Needed for the form

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule], // Import modules here
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  // --- VARIABLES ---
  loans: any[] = [];
  userEmail: string = '';
  
  // 1. Variable to toggle the form
  showForm: boolean = false;

  // 2. Variable to store form data
  newLoan = {
    reason: 'Personal',
    amount: null
  };

  constructor(private http: HttpClient) {}

  ngOnInit() {
    const savedEmail = localStorage.getItem('userEmail');
    if (savedEmail) {
      this.userEmail = savedEmail;
    } else {
      alert("Please log in first!");
    }

    this.fetchLoans();
  }

  // --- FUNCTIONS ---

  toggleForm() {
    this.showForm = !this.showForm;
  }

  fetchLoans() {
    this.http.get<any[]>(`/api/loans/my-loans?email=${this.userEmail}`)
      .subscribe({
        next: (data) => {
          this.loans = data;
        },
        error: (err) => {
          console.error("Error fetching loans:", err);
        }
      });
  }

  applyLoan() {
    // This URL matches your Java Controller: @RequestMapping("/loans") + @PostMapping("/apply")
    const url = `/api/loans/apply?email=${this.userEmail}`;
    
    this.http.post(url, this.newLoan).subscribe({
      next: (res: any) => {
        alert("Loan Application Submitted Successfully!");
        
        this.showForm = false; // Hide the form
        this.newLoan = { reason: 'Personal', amount: null }; // Reset form
        this.fetchLoans(); // Refresh the table immediately
      },
      error: (err) => {
        console.error("Full Error:", err);
        alert("Error applying for loan. Check console.");
      }
    });
  }
}