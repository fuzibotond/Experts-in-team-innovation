import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  private apiUrl = 'http://localhost:8085';


  constructor(private http:HttpClient) { }

  get<T>(url: string): any {
    return this.http.get<T>(this.apiUrl + url,
      {headers: new HttpHeaders({"Authorization" : "Bearer " + localStorage.getItem('authToken')})}
    );
  }

  post<T>(url: string, body:any): any {
    return this.http.post<T>(
      this.apiUrl + url,
      body,
      {headers: new HttpHeaders({"Authorization" : "Bearer " +localStorage.getItem('authToken')})}
    );
  }

  delete(url: string): any {
    return this.http.delete(
      this.apiUrl + url,
      {headers: new HttpHeaders({"Authorization" : "Bearer " + localStorage.getItem('authToken')})}
    );
  }
}
