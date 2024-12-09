import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpService} from './http.service';

@Injectable({
  providedIn: 'root',
})
export class ApiService {

  constructor(private http: HttpService) {
  }

  // Get all sensor data
  getAllSensorData(): Observable<any> {
    return this.http.get("/api/data/all");
  }

  // Get sensor data by type
  getSensorDataByType(sensorType: string): Observable<any> {
    return this.http.get(`/api/data/${sensorType}`);
  }

  deleteSensorDataById(id: string): Observable<any> {
    return this.http.delete(`/api/data/remove?id=${id}`);
  }

  deleteAllSensorData(): Observable<any> {
    return this.http.delete(`/api/data/remove/all`);
  }
}
