import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Component, OnInit, Optional, Inject } from '@angular/core';

@Component({
  selector: 'app-delete-popup',
  templateUrl: './delete-popup.component.html',
  styleUrls: ['./delete-popup.component.scss']
})
export class DeleteProductPopupComponent implements OnInit {
  action: string;
  local_data:any;

  constructor(
    public dialogRef: MatDialogRef<DeleteProductPopupComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.local_data = data;
    this.action = data.action;
  }

  ngOnInit() {
  }

 
  doAction(){
    this.dialogRef.close({event:this.action, data:this.local_data});
  }

 
  closeDialog(){
    this.dialogRef.close({event:'Cancel'});
  }

}
