import { LoginService } from 'src/app/shop/services/login.service';
import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { FlatTreeControl } from '@angular/cdk/tree';
import { MatTreeFlattener, MatTreeFlatDataSource } from '@angular/material';
import { Router } from '@angular/router';
import { ProductService } from '../services/product.service';



@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {
  private idUser: number;

  page:number = 1;
  totalProduct: number;
  pageSize: number= 6;
  priceValue: number = 10000;
  
  paginationIsDisplay: boolean = true;

  paginationCase:number = 1;
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
  products = [];
  category = [];
  
  constructor(
    private router: Router, 
    private productService: ProductService,
    private loginService: LoginService, 
    private toastr: ToastrService,
    ) 
  {
  }

  ngOnInit() {
    this.getAllProductsOnPage();
  }

  getAllProductsOnPage(){
    if(this.paginationCase == 2){
      this.page = 1;
    }
    this.paginationCase = 1;
    this.paginationIsDisplay = true;
   
    this.productService.getAllProducts(this.page, this.pageSize).subscribe((data) => {
      this.products=data.productDTOList;
      this.category = data.categoryDTOList;
      console.log(this.category);
    });
    
    this.productService.totalProduct().subscribe((data) => {
      this.totalProduct = data;
      console.log('totalProduct: '+this.totalProduct);
    })
  }

 
  findProductByCategory(id, event) {
    this.paginationIsDisplay = false;
    this.productService.findProductByCategory(id).subscribe((data) => {
      this.products = data;
    })
  }

  
  updatePrice(event, page){
    if(this.paginationCase ==1){
      this.page = 1;
    }
    this.paginationCase = 2;
    console.log(event.value);
    if(typeof event.value != 'undefined'){
      this.priceValue = event.value;
    }
    console.log("priceValue= "+ this.priceValue);
    this.productService.findByPrice(this.priceValue, this.page, this.pageSize).subscribe((data) => {
      console.log(data);
      this.products = data.productDTOList;
      this.totalProduct = data.totalProduct;
      
      if(this.totalProduct > 0){
        this.paginationIsDisplay = true;
      }else{
        this.paginationIsDisplay = false;
      }
    });
  }

 
  handlePageChange(event:any) {
    this.page = event;
    console.log('page: '+this.page);
    if(this.paginationCase == 1){
      this.productService.getAllProducts(this.page, this.pageSize).subscribe((data) => {
        this.products=data.productDTOList;
      });
    }else if(this.paginationCase ==2){
      this.updatePrice(this.priceValue, this.page);
    }
  }

  
  productHome(id) {
    this.router.navigate(['product/'+id]);
  }

 
  addToCart(idProduct:number) {
    
    let userCurrent: any = JSON.parse(localStorage.getItem('userCurrent'));
    console.log(userCurrent)

    if(userCurrent ===null){
      this.toastr.warning('Please login to add to cart!', 'Notification');
      return;
    }
    this.idUser = userCurrent.id;
    console.log('Add to cart: '+ idProduct);
    
    this.productService.addProductToCart(this.idUser, idProduct).subscribe((data) => {
      console.log('Get data when click');
      console.log(data);
      this.toastr.success('Added to cart successfully');
    });
  }


  formatLabel(value: number) {
    if (value >= 10000 && value <1000000) {
      return Math.round(value / 1000) + 'k';
    }else if(value >=1000000){
      return Math.round(value / 1000000) + 'M';
    }

    return value;
  }

  getPathImage(image:string) : string{
    return '../../../assets/images/'+ image;
  }
}

