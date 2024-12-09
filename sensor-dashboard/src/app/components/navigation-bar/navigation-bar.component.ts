import { Component } from '@angular/core';
import { MatToolbarModule} from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink, RouterLinkActive, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import {MatMenuModule} from '@angular/material/menu';
import { MatIcon } from '@angular/material/icon'
import { Router } from '@angular/router';
@Component({
  selector: 'app-navigation-bar',
  standalone: true,
  imports: [
    MatIcon,
    MatMenuModule, 
    MatToolbarModule,
    MatButtonModule,
    RouterLink,
    RouterLinkActive,
    RouterModule
  ],
  templateUrl: './navigation-bar.component.html',
  styleUrl: './navigation-bar.component.css'
})
export class NavigationBarComponent {

  user: any; // Replace 'any' with the UserInfo model if available
  constructor(private userService: UserService, private router: Router, private authService: AuthService) {

  }
  
  ngOnInit(): void {
    this.userService.user$.subscribe((user) => {
      if(user) {
        this.user = user
        localStorage.setItem('user', user)
      } else{
        this.user = localStorage.getItem('user')
      }
    });    
  }

  logout(): void {
    // Clear user data and token
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
    this.user = null

    // Redirect to the login page
    this.router.navigate(['/']);
  }
}
