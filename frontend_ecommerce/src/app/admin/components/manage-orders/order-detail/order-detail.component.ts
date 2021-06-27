import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Component, OnInit, Optional, Inject } from '@angular/core';
import { ManageOrderService } from 'src/app/admin/services/manage-order.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.scss']
})
export class OrderDetailComponent implements OnInit {
  order: any;
  productOrders:any[];
  calculateOrder;
  shipFee: any = 0;

  constructor(private manageOrderService: ManageOrderService,
    public dialogRef: MatDialogRef<OrderDetailComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private router: Router) {
      this.order = data;
     }

  ngOnInit() {
    this.loadPage();
  }

  public loadPage() {
    //Load data 
    this.manageOrderService.getOderById(this.order.id).subscribe((data) =>{
      console.log(data);
      this.productOrders = data;
    })
  }

  getPathImage(image:string):string {
    return '../../../../../assets/images/'+image;
  }

  goToProductDetails(id:number){
    this.router.navigate(['product/'+id]);
    this.dialogRef.close({event: 'cancel'});
  }
}
