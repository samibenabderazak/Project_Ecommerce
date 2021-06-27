import { LoginService } from 'src/app/shop/services/login.service';
import { ToastrService } from 'ngx-toastr';
import { Component, OnInit, NgModule, HostListener } from '@angular/core';
import { ProductService } from '../services/product.service';
import { Route } from '@angular/compiler/src/core';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';

@Component({
  selector: 'app-single-product',
  templateUrl: './single-product.component.html',
  styleUrls: ['./single-product.component.scss']
})
export class SingleProductComponent implements OnInit {
  public id;
  private idUser;number;
  simillarProducts = [];
  product: any;
  constructor(
    private productService: ProductService, 
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService, 
    private toastr: ToastrService,
  ) {
    // this.productService.getSingleProduct(Number(this.route.snapshot.params.id)).subscribe(res => {
    //   this.product = res;
    // });
  }

  ngOnInit() {
    this.id = +this.route.snapshot.paramMap.get('id')!;
    this.loadData(this.id);
  }

  loadData(id : number) {
    this.productService.findById(id).subscribe((data) => {
      console.log(data);
      this.product = data;
    })
    this.productService.getSimillarProducts(id).subscribe((data) => {
      this.simillarProducts = data;
      console.log(data);
    });
  }

 
  productDetail(id: number){
    console.log(id);
    this.loadData(id);
    this.id = id;
    this.router.navigate(['product/'+id]);
  }

 
  addToCart(id: number){
   
    let userCurrent: any = JSON.parse(localStorage.getItem('userCurrent'));
    console.log(userCurrent)

    if(userCurrent ===null){
      this.toastr.warning('Please login to add to cart!', 'Notification');
      return;
    }
    this.idUser = userCurrent.id;
    console.log('Entering time: '+ id);
   
    this.productService.addProductToCart(this.idUser, id).subscribe((data) => {
      console.log('Get data when click add to cart');
      console.log(data);
      this.toastr.success('Add to cart successfully');
    });
  }

  
  buyNow(){
    let userCurrent: any = JSON.parse(localStorage.getItem('userCurrent'));
    console.log(userCurrent)

    if(userCurrent ===null){
      this.toastr.warning('Please login to add to cart!', 'Notification');
      return;
    }
    this.idUser = userCurrent.id;
    console.log('Entering time: '+ this.id);
   
    this.productService.addProductToCart(this.idUser, this.id).subscribe((data) => {
      console.log('Get data when you click buy now');
      console.log(data);
     
      this.router.navigate(['shopping-cart/'+this.idUser]);
    });
  }

  getPathImage(image:string) : string{
    return '../../../assets/images/'+ image;
  }

 

}
