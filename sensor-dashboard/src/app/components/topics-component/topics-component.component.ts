import {Component, OnInit} from '@angular/core';
import {CommonModule, NgClass, NgIf} from '@angular/common';
import {MatIcon} from '@angular/material/icon';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {MatCard, MatCardContent} from '@angular/material/card';
import {MatTable} from '@angular/material/table';
import {TopicData} from '../../data/TopicData';
import {MatButton} from '@angular/material/button';
import {EdgeDeviceHttpService} from '../../services/edge-device-http.service';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';

@Component({
  selector: 'app-topics-component',
  standalone: true,
  imports: [CommonModule,
    MatIcon,
    MatProgressSpinner,
    MatCard,
    MatCardContent,
    MatTable,
    MatButton,
    MatSnackBarModule,
    NgClass
  ],
  templateUrl: './topics-component.component.html',
  styleUrl: './topics-component.component.css'
})
export class TopicsComponentComponent {
  topicData: any[] = [];
  availableTopics: any[] = [];
  errorMessage: string = '';
  isLoading: boolean = false; // Track loading state
  isSubscribed: boolean = false;

  constructor(
    private edgeDeviceHttpService: EdgeDeviceHttpService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadLatestData();
    this.loadAvailableTopics();
  }

  // Load all topics
  loadAvailableTopics(): void {
    this.edgeDeviceHttpService.loadAvailableTopics().subscribe(
      (topics: any) => {
        console.log("Topics: " + topics)
        this.availableTopics = topics.data;
      },
      (error) => {
        this.snackBar.open('Failed to load topics', 'Close', {duration: 3000});
      }
    );
  }

  loadLatestData(): void {
    this.isLoading = true; // Set loading to true

    this.edgeDeviceHttpService.getAllEdgeDevice().subscribe({
      next: (data) => {
        console.log("Topic device data: " + data)
        this.topicData = data.data.map((item: any) => {
          return item;
        });
        this.topicData = data.data.sort((a: any, b: any) => new Date(b.lastUsed).getTime() - new Date(a.lastUsed).getTime());
        ;
        this.isLoading = false; // Set loading to false after data is loaded
      },
      error: (err) => {
        console.error('Failed to load data:', err);
        this.isLoading = false; // Set loading to false on error
      }
    });
  }

  subscribeAll(): void {
    this.edgeDeviceHttpService.subscribeToAllTopics().subscribe({
      next: (data) => {
        console.log("Active topics data: " + data)
        this.snackBar.open('Subscription done: ' + data.message, 'Close', {
          duration: 3000,
        });
        this.loadLatestData();
      },
      error: (err) => {
        console.error('Failed to load data:', err);

      }
    })
  }

  deleteTopicData(id: string): void {
    if (confirm('Are you sure you want to delete this sensor data?')) {
      this.edgeDeviceHttpService.deleteTopicById(id)
        .subscribe({
          next: () => {
            // Remove the deleted item from the local array
            this.topicData = this.topicData.filter(data => data.id !== id);
            console.log(`Sensor data with ID ${id} deleted successfully`);
          },
          error: (error) => {
            this.errorMessage = 'Failed to delete sensor data';
            console.error(error);
          }
        });
    }
  }

  isActiveTopic(name: string): boolean | undefined {
    console.log("Logs: " + this.availableTopics)
    return this.availableTopics.findIndex((it: any) => {
      return it.name === name
    }) !== -1;
  }
}
