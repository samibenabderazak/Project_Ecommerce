import { LoginService } from 'src/app/shop/services/login.service';
import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-information',
  templateUrl: './profile-information.component.html',
  styleUrls: ['./profile-information.component.scss']
})
export class ProfileInformationComponent implements OnInit {

  private id;
  private user: any;
  public profileForm = new FormGroup({
    fullname: new FormControl(''),
    createDate: new FormControl(''),
    phoneNumber: new FormControl(''),
    address: new FormControl(''),
  });

  constructor(
    private loginService: LoginService,
    private router: Router,
    private toastr: ToastrService,
  ) {  
  }

  ngOnInit() {
    this.loadPage();
  }

  
  public loadPage() {
    this.user = this.loginService.getUserCurrent();
    if(!this.user){
      this.router.navigate(['/home']);
      console.log('logout')
    }else {
      for(let controlName in this.profileForm.controls){
        if(controlName){
          this.profileForm.controls[controlName].setValue(this.user[controlName]);
        }
      }
    }
  }

 
  saveUser(user){
    this.refreshUser();
    console.log('updated user: ');
    console.log(user);
    this.loginService.updateUser(user).subscribe((data) =>{
      if(data){
        window.location.reload();
        let currentUser = JSON.stringify(data.userDTO); 
        
        localStorage.setItem('token', data.token);
        localStorage.setItem('userCurrent', currentUser);
        this.toastr.success('Profile update successful', 'Notification');

      }
    })
  }

 
  refreshUser(){
    for(let controlName in this.profileForm.controls){
      if(controlName){
        this.user[controlName] = this.profileForm.controls[controlName].value;
      }
    }
  }
}
