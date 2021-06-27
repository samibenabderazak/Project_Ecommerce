import { catchError } from 'rxjs/operators';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { timeout, delay } from 'q';
import { throwError } from 'rxjs';
import { Observable, of } from 'rxjs';
import { LoadingService } from './loading.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private httpOptions ={
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    }),
  }

  private REST_API_SERVER = 'http://localhost:8080';

  constructor(private loadingService: LoadingService, private httpClient: HttpClient) { }


  //Get products on a page
  getAllProducts(page: number, pageSize: number): Observable<any> {
    const url= `${this.REST_API_SERVER}/product-page?page=${page}&limit=${pageSize}`;
    return this.httpClient
      .get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  //Find product by id category
  findProductByCategory(id): Observable<any> {
    const url= `${this.REST_API_SERVER}/product-page/category/${id}`;
    return this.httpClient
      .get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  //Find product by price
  findByPrice(price: number, page: number, pageSize: number): Observable<any> {
    const url= `${this.REST_API_SERVER}/product-page/search?price=${price}&page=${page}&limit=${pageSize}`;
    return this.httpClient
      .get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  //Find product by id
  findById(id: number): Observable<any>{
    const url= `${this.REST_API_SERVER}/product-page/${id}`;
    return this.httpClient
      .get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  //Get total product
  totalProduct(): Observable<any>{
    const url= `${this.REST_API_SERVER}/product-page/count`;
    return this.httpClient
      .get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  getSimillarProducts(id: number): Observable<any> {
    const url= `${this.REST_API_SERVER}/single-product/relate/${id}`;
    return this.httpClient
      .get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  addProductToCart(idUser: number, idProduct: number): Observable<any> {
    const url= `${this.REST_API_SERVER}/product-page/cart?iduser=${idUser}&idproduct=${idProduct}`;
    return this.httpClient
      .get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }


  getHomePage(): Observable<any> {
    const url= `${this.REST_API_SERVER}/home`;
    return this.httpClient
      .get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  //Handles error when send data to server
  private handleError(error: HttpErrorResponse){
    if(error.error instanceof ErrorEvent){
      console.error('An error :', error.error.message);
    }else {
      console.error(`Backend return code ${error.status},`+`body was: ${error.error} `);
    }
    return throwError('Something bad happened; please try again later.')
  }
}
