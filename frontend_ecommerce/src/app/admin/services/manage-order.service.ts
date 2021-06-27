import { catchError } from 'rxjs/operators';
import { throwError, Observable } from 'rxjs';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ManageOrderService {
  private httpOptions = {
    headers: new HttpHeaders({ 
      'Content-Type': 'application/json',
    })
  }
  private REST_API_SERVER = 'http://localhost:8080';
  constructor(private http: HttpClient) { }

  getOrderByStatus(status:number, page:number, pageSize:number): Observable<any>  {
    const url= `${this.REST_API_SERVER}/admin/order?status=${status}&page=${page}&limit=${pageSize}`;
    return this.http.get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  countOrderByStatus(status:number): Observable<any>  {
    const url= `${this.REST_API_SERVER}/admin/order-count?status=${status}`;
    return this.http.get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  changeStatus(id:number): Observable<any>  {
    const url= `${this.REST_API_SERVER}/admin/order-confirm/${id}`;
    return this.http.put<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  updateOrder(order: any){
    const url= `${this.REST_API_SERVER}/admin/order`;
    return this.http.put<any>(url, order, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  deleteOrder(id: number){
    const url= `${this.REST_API_SERVER}/admin/order?id=${id}`;
    return this.http.delete<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  getRevenue(year: number){
    const url= `${this.REST_API_SERVER}/admin/revenue?year=${year}`;
    return this.http.get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  getOderById(id:number){
    const url= `${this.REST_API_SERVER}/admin/order-detail?id=${id}`;
    return this.http.get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

   //Handles error when send data to server
  private handleError(error: HttpErrorResponse){
    if(error.error instanceof ErrorEvent){
      console.log('An error :', error.error.message);
    }else {
      console.log(`Backend return code ${error.status},`+`body was: ${error.error} `);
    }
    return throwError(error);
  }
}
