import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class EdgeDeviceHttpService {

  private apiUrl = '/api/edge-device';

  constructor(private http: HttpService) {}

  // Load all available topics
  loadAvailableTopics(): Observable<any> {
    return this.http.get(`${this.apiUrl}/active/list`);
  }

  // Register a new topic
  registerTopic(registrationData:any): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/add`, {
      topic: registrationData.topic,
      username: registrationData.username,
      password: registrationData.password
    });
  }
  // Subscribe to all topics
  subscribeToAllTopics(): Observable<any> {
    return this.http.post<void>(`${this.apiUrl}/subscribe-all`, {})
  }
   // Delete topics by id
   deleteTopicById(id: string): Observable<void> {
    return this.http.delete(`${this.apiUrl}/remove?id=${id}`);
  }

  getAllEdgeDevice(): Observable<any> {
    return this.http.get(`${this.apiUrl}/all`)
  }

}
