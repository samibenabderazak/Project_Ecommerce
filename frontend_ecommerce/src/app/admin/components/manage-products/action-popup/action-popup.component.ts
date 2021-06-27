import { ManageProductService } from './../../../services/manage-product.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Component, OnInit, Optional, Inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-product-action-popup',
  templateUrl: './action-popup.component.html',
  styleUrls: ['./action-popup.component.scss']
})
export class ActionProductPopupComponent implements OnInit {

  action:string;
  product: any;
  categories:any;
  public productForm:FormGroup;
  description:any;
  image: File = null;

  constructor(
    public dialogRef: MatDialogRef<ActionProductPopupComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private manageProductService: ManageProductService,
    private toast: ToastrService,
  ) { 
    this.action = data.action;
    this.product = data;
    this.categories = data.categories;
  }

  ngOnInit() {
    this.loadData();
  }

  loadData(): void {
    this.productForm = this.fb.group({
      categoryId: ['', Validators.required],
      name: ['', Validators.required],
      price: ['', Validators.required],
      description: ['', Validators.required],
      image: ['', Validators.required],
      createDate: ['']
    })

   
    if(this.action === 'edit'){
      for(let controlName in this.productForm.controls){
        this.productForm.controls[controlName].setValue(this.product[controlName]);
      }
    }
    this.description = this.product.description;
  }

  changeCategory(event){
    this.productForm.controls.categoryId.setValue(event.value);
  }

  update(){
    
    for(let controlName in this.productForm.controls){
      this.product[controlName] = this.productForm.controls[controlName].value;
    }
  
    this.dialogRef.close({event: this.action, data: this.product});
  }

  closeDialog(){
    this.dialogRef.close({event: 'cancel'});
  }

  onChangeFile(event) {
    this.image = <File>event.target.files[0];
  }

  uploadImage(){
    this.manageProductService.uploadImage(this.image).subscribe(data => {
      if(!data){
        this.toast.error("Error uploading pictures");
        this.image = null;
      }else if(data.httpStatus == 'BAD_REQUEST'){
        this.image = null;
        this.toast.error(data.msg);
      }else if(data.httpStatus == 'OK'){
        this.productForm.controls['image'].setValue(data.data);
        this.toast.success(data.msg);
      }
      console.log(data)
    })
  }

  showRemind(){
    this.toast.error('Please select the image first!');
  }

}
