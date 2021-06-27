import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Optional, Inject } from '@angular/core';

@Component({
  selector: 'app-action-popup',
  templateUrl: './action-popup.component.html',
  styleUrls: ['./action-popup.component.scss']
})
export class ActionPopupComponent implements OnInit {

  public profileForm: FormGroup;

  private roles: any[] = [
    {
      checkbox: false,
      name: "ADMIN"
    },
    { 
      checkbox: false, 
      name: "USER"
    }
  ];
  private roleUpdate: string[] =[];
  private action: string ;
  private user: any = null;

  constructor(
    public dialogRef: MatDialogRef<ActionPopupComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder
  ) {
    if(data.action === 'edit'){
      this.roleUpdate = data.roles;
    }
    this.action = data.action;
    this.user = data;
  }

  ngOnInit() {
    this.profileForm = this.fb.group({
      username: ['', Validators.required],
      password: [{value: '', disabled: true}],
      fullname: ['', Validators.required],
      phoneNumber: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
      address: ['', Validators.required],
    })
    this.loadData();
  }

  
  loadData() {
    //Set form control
    if(this.action === 'edit'){
      for(let controlName in this.profileForm.controls){
        this.profileForm.controls[controlName].setValue(this.user[controlName]);
      }
    }
    //Set options list
    if(this.roleUpdate){
      this.roles.forEach(item => {
        this.roleUpdate.forEach(role => {
          if(item.name === role){
            item.checkbox = true;
          }
        })
      })
    }
  }


  onCheckboxClick(event, list) {
 
    this.roleUpdate = [];

    list.selectedOptions.selected.forEach(item => {
      this.roleUpdate.push(item.value);
    })
  }

 
  update(){
   
    this.user.roles = this.roleUpdate;
   
    for(let controlName in this.profileForm.controls){
      this.user[controlName] = this.profileForm.controls[controlName].value;
    }
    console.log('user sau khi sua/ them: ', this.user);
    this.dialogRef.close({event: this.action, data: this.user});
  }

  closeDialog(){
    this.dialogRef.close({event:'cancel'});
  }
}
