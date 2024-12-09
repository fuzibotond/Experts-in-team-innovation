import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css'
})
export class LoginFormComponent {
  url: string = "";
  btnLabel: string = "Server is down"; 

  constructor(private http: AuthService) {

  }

  ngOnInit(): void {
    this.http.get("auth/url").subscribe((data:any) => {
      console.log("Login data: " + data.url)
      this.url = data.url;
      this.btnLabel = "Sign in with Google" 
    })
  }
}
