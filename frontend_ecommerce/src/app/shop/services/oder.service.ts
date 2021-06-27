import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OderService {

  private httpOptions ={
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    }),
  }

  private REST_API_SERVER = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }


  public getOder(): Observable<any> {
    const url= `${this.REST_API_SERVER}/order/`;
    return this.httpClient
      .get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }


  public payment(id: number, data: any): Observable<any>{
    const url = `${this.REST_API_SERVER}/order/payment/${id}`;
    return this.httpClient
      .post<any>(url,data, this.httpOptions)
      .pipe(catchError(this.handleError));
  }


  public removeProductOrder(idProductOrder: number): Observable<any> {
    const url = `${this.REST_API_SERVER}/order/remove?id-product-order=${idProductOrder}`;
    return this.httpClient
      .delete<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  getHistoryOrders(id:number): Observable<any>{
    const url = `${this.REST_API_SERVER}/order/history?id-user=${id}`;
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
