import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { MatIcon } from '@angular/material/icon';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatTable } from '@angular/material/table';
import { SensorData } from '../../data/SensorData';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, 
    MatIcon, 
    MatProgressSpinner, 
    MatCard, 
    MatCardContent, 
    MatTable,
    MatButton
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  sensorData: any[] = [];
  errorMessage: string = '';
  isLoading: boolean = false; // Track loading state


  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadLatestData();
  }

  loadLatestData(): void {
    this.isLoading = true; // Set loading to true

    this.apiService.getAllSensorData().subscribe({
      next: (data) => {
        this.sensorData = data.map((item:SensorData) => {
          return item;
        });
        this.sensorData = data.sort((a:any, b:any) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime());;
        this.isLoading = false; // Set loading to false after data is loaded
      },
      error: (err) => {
        console.error('Failed to load data:', err);
        this.isLoading = false; // Set loading to false on error
      }
    });
  }

  deleteSensorData(id: string): void {
    if (confirm('Are you sure you want to delete this sensor data?')) {
      this.apiService.deleteSensorDataById(id)
        .subscribe({
          next: () => {
            // Remove the deleted item from the local array
            this.sensorData = this.sensorData.filter(data => data.id !== id);
            console.log(`Sensor data with ID ${id} deleted successfully`);
          },
          error: (error) => {
            this.errorMessage = 'Failed to delete sensor data';
            console.error(error);
          }
        });
    }
  }

  deleteAllSensorData(): void {
    if (confirm('Are you sure you want to delete all sensor data?')) {
      this.apiService.deleteAllSensorData()
        .subscribe({
          next: () => {
            // Remove the deleted item from the local array
            this.sensorData = [];
            console.log(`Sensor data deleted successfully`);
          },
          error: (error) => {
            this.errorMessage = 'Failed to delete sensor data';
            console.error(error);
          }
        });
    }
  }

  isActiveTopic(id:string): boolean | undefined {
    return this.sensorData.findIndex((it:any) => {
      return it.id === id
    }) == -1;
  }
}
