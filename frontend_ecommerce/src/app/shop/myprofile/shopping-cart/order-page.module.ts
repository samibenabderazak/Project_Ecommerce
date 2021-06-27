import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { OrderCurrentComponent } from './order-current/order-current.component';
import { HistoryOrderComponent } from './history-order/history-order.component';
import { OrderPageRouting } from './order.routing';
import {NgModule} from '@angular/core';
import { MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';

@NgModule({
  declarations: [
    HistoryOrderComponent,
    OrderCurrentComponent
  ],
  imports: [
    OrderPageRouting,
    MatIconModule,
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  exports: [
    HistoryOrderComponent,
    OrderCurrentComponent
  ],
  entryComponents: []
})
export class OrderPageModule {
}
