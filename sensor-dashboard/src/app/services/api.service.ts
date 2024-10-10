import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private apiUrl = 'http://localhost:8085/api/data';


  constructor(private http: HttpClient) {}

  // Get all sensor data
  getAllSensorData(): Observable<any> {
    return this.http.get(`${this.apiUrl}`);
  }

  // Get sensor data by type
  getSensorDataByType(sensorType: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${sensorType}`);
  }
}
