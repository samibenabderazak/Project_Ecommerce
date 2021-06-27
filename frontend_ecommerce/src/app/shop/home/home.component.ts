import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import {MatIconRegistry} from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { ProductService } from '../services/product.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  carouselOptions = 
  {
    items: 1, 
    dots: false, 
    navigation: false, 
    loop:true,
    margin:10,
    autoplay:true,
    animateOut: 'fadeOut',
    autoHeight: true,
    autoHeightClass: 'owl-height',
  }

  private idUser;
  private listNewProduct:any[] = [];
  private listBestSeller:any[] = [];

  constructor(
    iconRegistry: MatIconRegistry, 
    sanitizer: DomSanitizer,
    private router: Router, 
    private productService: ProductService,
    private loginService: LoginService,
    private toastr: ToastrService) {
      this.loadData();
  
  }

  ngOnInit() {
  }

  loadData() {
    this.productService.getHomePage().subscribe(data => {
      console.log(data);
      this.listNewProduct = data.listNewProduct;
      this.listBestSeller = data.listBestSeller;
    })
  }

 
  singleProduct(id: number) {
    this.router.navigate(['product/'+ id]);
  }

  
  addToCart(idProduct:number) {
    let userCurrent: any = JSON.parse(localStorage.getItem('userCurrent'));
    console.log(userCurrent)

    if(userCurrent ===null){
      this.toastr.warning('Please login to add to cart!', 'Notification');
      return;
    }

    this.idUser = userCurrent.id;
   
    
    this.productService.addProductToCart(this.idUser, idProduct).subscribe((data) => {
      
      console.log(data);
      this.toastr.success('Added to cart successfully', 'Notification');
    })
  }



  getPathImage(image:string) : string{
    return '../../../assets/images/'+ image;
  }

}
