import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = '/api/auth';

  constructor(private http: HttpClient) { }

  // LOGIN
  login(credentials: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials).pipe(
      tap((response: any) => {
        if (response.token) {
          localStorage.setItem('token', response.token);
        }
      })
    );
  }

  // SIGNUP
  signup(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, user);
  }
  
  // LOGOUT
  logout() {
    localStorage.removeItem('token');
  }

  getUserEmail() : string|null{
    const token = localStorage.getItem('token');
    if(!token){
      return null;
    }

    try{
      const decoded: any = jwtDecode(token);
      return decoded.sub;
    }catch(err){
      return null;
    }
  }
}