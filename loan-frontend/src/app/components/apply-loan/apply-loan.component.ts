import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoanService } from '../../services/loan.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-apply-loan',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './apply-loan.component.html',
  styleUrls: ['./apply-loan.component.css']
})
export class ApplyLoanComponent {
  loan = { amount: 0, interestRate: 5, reason: '' };
  // For now, we hardcode the email (or get it from login later)
  email = "neeru@test.com"; 

  constructor(private loanService: LoanService, private router: Router) {}

  submitApplication() {
    this.loanService.applyLoan(this.loan, this.email).subscribe({
        next: (res) => {
            alert("Loan Applied Successfully!");
            this.router.navigate(['/dashboard']);
        },
        error: (err) => {
            alert("Something went wrong");
            console.error(err);
        }
    });
  }
}