import { catchError } from 'rxjs/operators';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private httpOptions ={
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  }

  ckeckHaslogin$ = new BehaviorSubject(false);
  
  private REST_API_SERVER = 'http://localhost:8080';

  private user: any;

  constructor(private httpClient: HttpClient) {
  }


  //Lấy user hiện tại
  getUserCurrent(): any{
    this.user = JSON.parse(localStorage.getItem('userCurrent'));
    return this.user;
  }

  //Logout
  logout(): void{
    localStorage.removeItem('token');
    localStorage.removeItem('userCurrent');
    this.user = null;
  }

  //Kiểm tra đăng nhập
  public checkLogin(data: any): Observable<any> {
    const url= `${this.REST_API_SERVER}/login`;
    return this.httpClient
      .post<any>(url, data, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  //Update user
  public updateUser(user: any): Observable<any> {
    const url= `${this.REST_API_SERVER}/user`;
    return this.httpClient
      .put<any>(url, user, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  //Đăng ký user
  public addUser(user: any): Observable<any> {
    const url= `${this.REST_API_SERVER}/user`;
    return this.httpClient
      .post<any>(url, user, this.httpOptions)
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