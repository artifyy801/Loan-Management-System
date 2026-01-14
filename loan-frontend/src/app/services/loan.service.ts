import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  private apiUrl = 'http://localhost:8080/loans';

  constructor(private http: HttpClient) {}

  applyLoan(loanData: any, email: string): Observable<any> {
    // We send the email as a Query Parameter (?email=...)
    return this.http.post(`${this.apiUrl}/apply?email=${email}`, loanData);
  }

  getUserEmail():string | null{
    const token = localStorage.getItem('token');
    if(!token){
      return null;
    }
    try{
      const decoded: any = jwtDecode(token);
      return decoded.sub;
    }catch(error){
      return null;
    }
  }

  getMyLoans(email: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/my-loans?email=${email}`);
  }
}