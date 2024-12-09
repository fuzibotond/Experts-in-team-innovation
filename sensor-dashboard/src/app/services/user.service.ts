import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { Observable, BehaviorSubject } from 'rxjs';
import { UserInfo } from '../data/user-info';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpService) { }

  private userSubject = new BehaviorSubject<any | null>(null); // Store user name/email

  user$ = this.userSubject.asObservable(); // Expose user data as observable

  setUser(user: any | null): void {
    this.userSubject.next(user); // Update user information
  }

  getUser(): string | null {
    return this.userSubject.value; // Access current user information
  }

  clearUser(): void {
    this.setUser(null); // Clear user information
  }

  getUserInfo(): Observable<any> {
    return this.http.get("/api/user/user-info");
  }
}
