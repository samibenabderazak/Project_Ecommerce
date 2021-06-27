
import { ToastrService } from 'ngx-toastr';
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { LoginService } from '../services/login.service';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../interfaces/Ilogin';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  private checkSignUp: boolean;
  loginForm:FormGroup;
  user = {} as User;
  public signUpForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    fullname: new FormControl(''),
    phoneNumber: new FormControl(''),
    address: new FormControl(''),
  });

  constructor(
    public dialogRef: MatDialogRef<LoginComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, 
    private loginService: LoginService, 
    private fb: FormBuilder,
    private router: Router,
    private toastr: ToastrService,
    
  ) {}

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: [''],
      password: ['']
    })
  }


  public createData() {
    const newUser: any = {};
    for(let controlName in this.loginForm.controls){
      if(controlName){
        newUser[controlName] = this.loginForm.controls[controlName].value;
      }
    }
    return newUser;
  }


  login(){
    this.loginService.checkLogin(this.createData()).subscribe((data) =>{
      this.user = data.userDTO;
      if(this.user != null){
        let currentUser = JSON.stringify(data.userDTO); 
      
        localStorage.setItem('token', data.token);
        localStorage.setItem('userCurrent', currentUser);
        this.user.username = this.loginForm.value.username;
        this.loginService.ckeckHaslogin$.next(true);
        for(let i of data.userDTO.roles){
          if(i === "ADMIN"){
            this.toastr.success("Success");
            this.onNoClick();
            
            this.router.navigate(['admin/dashboard']);
            return;
          }
        }
        this.onNoClick();
        this.toastr.success("Success");
      }else{
        this.toastr.error('Wrong username or password');
      }
    })
  }

  
  public getNewUser() {
    const newUser: any = {};
    for(let controlName in this.signUpForm.controls){
      if(controlName){
        newUser[controlName] = this.signUpForm.controls[controlName].value;
      }
    }
    return newUser;
  }

  
  signUp(){
    this.loginService.addUser(this.getNewUser()).subscribe(data =>{
     
      console.log(data);
      this.signUpForm.reset();
      this.toastr.success('Sign Up Success!');

    })
    this.checkSignUp = false;
  }
  
  onNoClick(): void {
    this.dialogRef.close();
  }

  
  goToSignUp(){
    this.checkSignUp = true;
  }

  
  goToLogin(){
    this.checkSignUp = false;
  }
}
