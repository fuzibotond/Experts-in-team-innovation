import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { MatIcon } from '@angular/material/icon';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatTable } from '@angular/material/table';
import { SensorData } from '../../data/SensorData';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, 
    MatIcon, 
    MatProgressSpinner, 
    MatCard, 
    MatCardContent, 
    MatTable
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
}
