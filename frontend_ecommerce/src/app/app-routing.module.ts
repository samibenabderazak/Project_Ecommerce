import { DashboardComponent } from './admin/components/dashboard/dashboard.component';
import { ManageOrdersComponent } from './admin/components/manage-orders/manage-orders.component';
import { ManageUsersComponent } from './admin/components/manage-users/manage-users.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './shop/home/home.component';
import { ProductsComponent } from './shop/products/products.component';
import { SingleProductComponent } from './shop/single-product/single-product.component';
import { ShoppingCartComponent } from './shop/myprofile/shopping-cart/shopping-cart.component';
import { MyprofileComponent } from './shop/myprofile/myprofile.component';
import { ProfileInformationComponent } from './shop/myprofile/profile-information/profile-information.component';
import { ManageAddressComponent } from './shop/myprofile/manage-address/manage-address.component';
import { ReviewsRatingComponent } from './shop/myprofile/reviews-rating/reviews-rating.component';
import { SavedCardsComponent } from './shop/myprofile/saved-cards/saved-cards.component';
import { WishlistComponent } from './shop/myprofile/wishlist/wishlist.component';
import { MyRewardsComponent } from './shop/myprofile/my-rewards/my-rewards.component';
import { NotificationsComponent } from './shop/myprofile/notifications/notifications.component';
import {AuthService } from './shop/services/auth-service';
import { ManageProductsComponent } from './admin/components/manage-products/manage-products.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'products',
    component: ProductsComponent
  },
  {
    path: 'product/:id',
    component: SingleProductComponent
  },
  {
    path: 'shopping-cart/:id',
    component: ShoppingCartComponent
  },
  {
    path: 'myprofile',
    component: MyprofileComponent,
    canActivate: [AuthService],
    children: [
      {
        path: 'profile',
        component: ProfileInformationComponent
      },
      {
        path: 'address',
        component: ManageAddressComponent
      },
      {
        path: 'notifications',
        component: NotificationsComponent
      },
      {
        path: 'reviews',
        component: ReviewsRatingComponent
      },
      {
        path: 'carddetails',
        component: SavedCardsComponent
      },
      {
        path: 'wishlist',
        component: WishlistComponent
      },
      {
        path: 'rewards',
        component: MyRewardsComponent
      },
      {
        path: '',
        redirectTo: 'profile',
        pathMatch: 'full'
      }
    ]

  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '*', redirectTo: 'home', pathMatch: 'full' },

  //admin
 
  {
    path: 'admin/user',
    component: ManageUsersComponent
  },
  
  {
    path: 'admin/product',
    component: ManageProductsComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
