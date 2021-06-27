import { Component, ViewChild, ElementRef } from '@angular/core';
import { MatSidenav } from '@angular/material';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  @ViewChild('drawer', {static: false}) drawer: MatSidenav;
  title = 'Projet_spring';
  
  toggelNavbar () {
    this.drawer.toggle();
  }

   sideNavMenu = [
     {
       title: 'home',
       link: '/home'
     },
     {
      title: 'products',
      link: '/products'
    },
    
    
   ];
}

