import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-myprofile',
  templateUrl: './myprofile.component.html',
  styleUrls: ['./myprofile.component.scss']
})
export class MyprofileComponent implements OnInit {

  private user:any = {};
  constructor( 
    private loginService: LoginService,
    private router: Router,
    private toastr: ToastrService) {
   }

  ngOnInit() {
    this.loadData();
  }

  //Load Data
  loadData() {
    this.user = this.loginService.getUserCurrent();
    if(this.user){
     
      console.log(this.user)
    }else{
      this.router.navigate(['/home']);
      console.log('logout')
    }
  }

  logout() {
    this.loginService.logout();
    window.location.reload();
 
    this.toastr.info('Signed out');
  }
}
