import { LoginService } from 'src/app/shop/services/login.service';
import { FormControl, FormGroup } from '@angular/forms';
import { OderService } from '../../../services/oder.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-order-current',
  templateUrl: './order-current.component.html',
  styleUrls: ['./order-current.component.scss'],
})
export class OrderCurrentComponent implements OnInit {
  private id: number = 0;
  
  private orders;
  private calculateOrder;
  private shipFee: number = 0;
  status:number;
  orderId:number;

  public notifyForm = new FormGroup({
    nameUser: new FormControl(''),
    phoneNumber: new FormControl(''),
    address: new FormControl(''),
    orderDate: new FormControl('')
  });

  constructor( 
    public loginService: LoginService,
    private orderService: OderService,
    private router: Router,
    private toastr: ToastrService) 
    {
    }

  ngOnInit() {
    this.loadPage();
  }


  public loadPage() {
    let currentUser = JSON.parse(localStorage.getItem('userCurrent'));

    if(!currentUser){
      this.router.navigate(['/home']);
      this.toastr.warning('Please log in!');
      return;
    }
    
    this.id = currentUser.id;
    //Load data 
    this.orderService.getOder().subscribe((data) =>{

      if(!data){
        this.router.navigate(['/home']);
        this.toastr.warning('Please log in!');
        return;
      }

      console.log('Your current order has the user id:  '+ this.id)
      console.log(data);
      if(data.ordersDTO){
        this.status = data.ordersDTO.status;
        this.orderId = data.ordersDTO.id;
        this.calculateOrder = data.ordersDTO;
      }
      if(data.orderProducts){
        this.orders = data.orderProducts;
      }
     
      if(data.ordersDTO){
        for(let controlName in this.notifyForm.controls){
          if(controlName){
            this.notifyForm.controls[controlName].setValue(data.ordersDTO[controlName]);
          }
        }
      }
      console.log('status order: ', this.status);
      console.log('id order: ', this.orderId);
    })
  }


  payment(){
    console.log("Pay: ")
    this.orderService.payment(this.id, this.orderCurrent()).subscribe((data)=>{
      
      this.toastr.info("bill added", 'Notification');
      this.router.navigate(['/home']);
    })
  }

  orderCurrent(): any{
    const newOrder: any ={};
    newOrder['id'] = this.calculateOrder.id;
    for(let controlName in this.notifyForm.controls){
      if(controlName){
        newOrder[controlName] = this.notifyForm.controls[controlName].value;
      }
    }
    const totalprice = "totalprice";
    this.status = 1;
    newOrder['status']= this.status;
    newOrder[totalprice] = this.calculateOrder.totalprice + this.shipFee;
    return newOrder;
  }


  removeProductOrder(id: number){
    console.log("Delete productOrder");
    console.log(id);
    this.orderService.removeProductOrder(id).subscribe((data)=>{
      console.log('Delete order product successfully')
      console.log(data);
      this.orders = data.orderProducts;
      this.calculateOrder = data.ordersDTO;
      this.toastr.success('This product has been removed!', 'Notification');
    });
  }


  goToProductDetails(id:number){
    this.router.navigate(['product/'+id]);
  }

  getPathImage(image:string) : string{
    return '../../../assets/images/'+ image;
  }
}
