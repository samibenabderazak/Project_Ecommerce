import { OderService } from './../../../services/oder.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-history-order',
  templateUrl: './history-order.component.html',
  styleUrls: ['./history-order.component.scss']
})
export class HistoryOrderComponent implements OnInit {
  id:number;
  orders:any[];

  status: any= {
    0: 'Ordering',
    1: 'Waiting for confirmation',
    2: 'Delivery in progress',
    3: 'Delivered'
  };

  constructor(
    private orderService: OderService, 
    private router: Router) { }

  ngOnInit() {
    let currentUser = JSON.parse(localStorage.getItem('userCurrent'));

    if(!currentUser){
      this.router.navigate(['/home']);
      return;
    }
    
    this.id = currentUser.id;
    this.getHistoryOrders();
  }


  getHistoryOrders(){
    this.orderService.getHistoryOrders(this.id).subscribe(data =>{
      console.log('history order: ',data);
      this.orders = data;
    })
  }
}
