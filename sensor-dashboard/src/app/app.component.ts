import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { NavigationBarComponent } from './components/navigation-bar/navigation-bar.component';
import { CommonModule } from '@angular/common';
import { AuthService } from './services/auth.service';
import { UserService } from './services/user.service';
import { BehaviorSubject } from 'rxjs';
import { LocalStorageUtil } from './utils/local-storage.util';
import { NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet, 
    HttpClientModule,
    NavigationBarComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'sensor-dashboard';
  constructor(private http: AuthService, private route: ActivatedRoute, private router: Router, private userService: UserService) {}
  ngOnInit(): void {
    this.router.events.subscribe((event:any) => {
      if (event instanceof NavigationEnd) {
        if(this.http.isAuthenticated()){
          this.userService.getUserInfo().subscribe((userInfo:any) => {
            console.log("User data fetched: " + userInfo.email)
            this.userService.setUser(userInfo.name || 'Guest');
          })
        }
      }
    });
    this.route.queryParams.subscribe(params => {
      if (params["code"] !== undefined){
        this.http.getToken(params["code"]).subscribe(result => {
          if(result === true) {
            this.router.navigate(['/dashboard']);
          }
        })
      }
    })

    
    
  }


}
