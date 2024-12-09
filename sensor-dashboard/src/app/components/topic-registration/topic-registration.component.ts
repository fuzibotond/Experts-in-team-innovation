import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { EdgeDeviceHttpService } from '../../services/edge-device-http.service';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-topic-registration',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatSelectModule,
    MatSnackBarModule,
  ],
  templateUrl: './topic-registration.component.html',
  styleUrl: './topic-registration.component.css'
})
export class TopicRegistrationComponent implements OnInit {
  registerForm: FormGroup;
  associateForm: FormGroup;
  availableTopics: string[] = [];

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private topicApiService: EdgeDeviceHttpService,
    private snackBar: MatSnackBar
  ) {
    this.registerForm = this.fb.group({
      topic: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.associateForm = this.fb.group({
      topic: ['', Validators.required],
      deviceId: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadAvailableTopics();
  }

  // Load all topics
  loadAvailableTopics(): void {
    this.topicApiService.loadAvailableTopics().subscribe(
      (topics:any) => {
        console.log("Topics: " + topics)
        this.availableTopics = topics.data;
      },
      (error) => {
        this.snackBar.open('Failed to load topics', 'Close', { duration: 3000 });
      }
    );
  }

  // Register a new topic
  registerTopic(): void {
    this.topicApiService.registerTopic({...this.registerForm.value}).subscribe(
      () => {
        this.snackBar.open('Topic registered successfully', 'Close', {
          duration: 3000,
        });
        this.loadAvailableTopics(); // Refresh topics list
        this.registerForm.reset();
      },
      (error) => {
        this.snackBar.open('Failed to register topic', 'Close', {
          duration: 3000,
        });
      }
    );
  }
}
