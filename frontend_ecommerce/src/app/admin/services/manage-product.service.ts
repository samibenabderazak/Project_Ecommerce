import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ManageProductService {

  private httpOptions = {
    headers: new HttpHeaders({ 
      'Content-Type': 'application/json',
    })
  }
  private httpFileUpload = {
    headers: new HttpHeaders({ 
      'Content-Type': 'multipart/form-data',
    })
  }
  private REST_API_SERVER = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getAllProducts(page: number, pageSize: number): Observable<any> {
    const url= `${this.REST_API_SERVER}/admin/product?page=${page}&limit=${pageSize}`;
    return this.http.get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }


  getAllCategory(page: number, pageSize: number): Observable<any> {
    const url= `${this.REST_API_SERVER}/admin/category?page=${page}&limit=${pageSize}`;
    return this.http.get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

 
  getProductsByCategory(id: number, page: number, pageSize: number): Observable<any> {
    const url= `${this.REST_API_SERVER}/admin/product/${id}?&page=${page}&limit=${pageSize}`;
    return this.http.get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }
  
  getCount(): Observable<any>  {
    const url= `${this.REST_API_SERVER}/admin/product-count`;
    return this.http.get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  getCountCategory(): Observable<any>  {
    const url= `${this.REST_API_SERVER}/admin/category-count`;
    return this.http.get<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  addProduct(product:any): Observable<any>  {
    const url= `${this.REST_API_SERVER}/admin/product`;
    return this.http.post<any>(url, product, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  updateProduct(product:any): Observable<any>  {
    const url= `${this.REST_API_SERVER}/admin/product`;
    return this.http.put<any>(url, product, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  deleteProduct(id:any): Observable<any>  {
    const url= `${this.REST_API_SERVER}/admin/product/${id}`;
    return this.http.delete<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  addCategory(category:any): Observable<any>  {
    const url= `${this.REST_API_SERVER}/admin/category`;
    return this.http.post<any>(url, category, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  updateCategory(category:any): Observable<any>  {
    const url= `${this.REST_API_SERVER}/admin/category`;
    return this.http.put<any>(url, category, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  deleteCategory(id:any): Observable<any>  {
    const url= `${this.REST_API_SERVER}/admin/category/${id}`;
    return this.http.delete<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  uploadImage(image: File): Observable<any>  {
    const formData: FormData = new FormData();
    formData.append('image', image, image.name);
    const url= `${this.REST_API_SERVER}/admin/upload-img`;
    return this.http.post(url, formData);
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
