import { ToastrService } from 'ngx-toastr';
import { Component, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material';
import { LoginComponent } from '../../login/login.component';
import { LoginService } from '../../services/login.service';
import { User } from '../../interfaces/Ilogin';
import { EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { LoadingService } from 'src/app/shop/services/loading.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  loadingEnable: boolean;
  sidenavEnable = false;
  isLogin: boolean;
  private userCurrent: any;
  admin:any;
  isAdmin: boolean;

  @Output()
  sidenav = new EventEmitter();

  toggelSidenav() {
    this.sidenav.emit('toggel');
  }

  constructor(
    public dialog: MatDialog, 
    private router: Router, 
    public loginService: LoginService,
    public loadingService: LoadingService,
    private toastr: ToastrService,) { 
  
    }


  ngOnInit() {
    this.loginService.ckeckHaslogin$.asObservable().subscribe(data =>{
      this.isLogin = data;
      this.userCurrent = this.loginService.getUserCurrent();
      console.log('user current',this.userCurrent)
      this.checkAdmin();
      console.log(this.isAdmin);
    })
  }

  checkAdmin() {
    if(this.userCurrent){
      this.userCurrent.roles.forEach(role =>{
        if(role === 'ADMIN'){
          this.isAdmin = true;
        }
      })
    }
  }

  enableSidenav() {
    this.sidenavEnable = !this.sidenavEnable;
  }
  openLoginDialog(): void {
    const dialogRef = this.dialog.open(LoginComponent, {

    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  
  logout() {
    this.toastr.info('Signed out')
    localStorage.removeItem('token');
    localStorage.removeItem('userCurrent');
    this.isAdmin = false;
    this.userCurrent = null;
    this.loginService.ckeckHaslogin$.next(false);
    this.router.navigate(['home']);
    // window.location.reload();
  }

 
  openOrder(){
    this.router.navigate(['/shopping-cart/'+this.userCurrent.id]);
  }
  
  
  openProfile(){
    this.router.navigate(['/myprofile']);
  }
}
