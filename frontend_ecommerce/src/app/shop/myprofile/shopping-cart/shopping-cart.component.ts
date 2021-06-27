import { LoginService } from 'src/app/shop/services/login.service';
import { FormControl, FormGroup } from '@angular/forms';
import { OderService } from '../../services/oder.service';
import { Router } from '@angular/router';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ShoppingCartComponent implements OnInit {

  constructor( ) {}

  ngOnInit() {
  }

 
}
