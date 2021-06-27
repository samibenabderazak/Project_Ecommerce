import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Optional, Inject } from '@angular/core';

@Component({
  selector: 'app-category-action-popup',
  templateUrl: './category-action-popup.component.html',
  styleUrls: ['./category-action-popup.component.scss']
})
export class CategoryActionPopupComponent implements OnInit {
  action:string;
  category: any;
  public categoryForm:FormGroup;

  constructor(
    public dialogRef: MatDialogRef<CategoryActionPopupComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,  
  ) { 
    this.action = data.action;
    this.category = data;
  }
  ngOnInit() {
    this.categoryForm = this.fb.group({
      name: ['', Validators.required],
    })
    if(this.action === 'edit'){
      for(let controlName in this.categoryForm.controls){
        this.categoryForm.controls[controlName].setValue(this.category[controlName]);
      }
    }
  }

  update(){
   
    for(let controlName in this.categoryForm.controls){
      this.category[controlName] = this.categoryForm.controls[controlName].value;
    }
    this.dialogRef.close({event: this.action, data: this.category});
  }

  closeDialog(){
    this.dialogRef.close({event:'cancel'})
  }

}
