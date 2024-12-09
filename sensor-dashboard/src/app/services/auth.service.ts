import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8085/';

  token: string = "";

  constructor(private http: HttpClient, private userService: UserService) {}

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);


  get isAuthenticated$(): Observable<boolean> {
    return this.isAuthenticatedSubject.asObservable();
  }

  setAuthenticated(status: boolean): void {
    this.isAuthenticatedSubject.next(status);
  }

  get(url: string): any {
    return this.http.get(this.apiUrl + url);
  }

  getToken(code: string): Observable<boolean> {
    return this.http
      .get(this.apiUrl + 'auth/callback?code=' + code, { observe: 'response' })
      .pipe(
        map((response: HttpResponse<any>) => {
          if (response.status === 200 && response.body !== null) {
            this.token = response.body.token;
            localStorage.setItem('authToken', this.token)
            // Set user data (e.g., email from the response)

            return true;
          } else {
            return false;
          }
        })
      );
  }

  isAuthenticated(): boolean {
    if (!this.token) {
      this.token = localStorage.getItem('authToken') || '';
    }

    console.log("User is authenticated? " + this.token !== null && this.token !== '')
    return this.token !== null && this.token !== '';
  }

}
