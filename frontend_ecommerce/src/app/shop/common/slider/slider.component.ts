import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.scss']
})
export class SliderComponent implements OnInit {
  carouselOptions = 
    {
      items: 1, 
      dots: true, 
      navigation: false, 
      loop:true,
      margin:10,
      autoplay:true,
      animateOut: 'fadeOut',
      autoHeight: true,
      autoHeightClass: 'owl-height',
      
  }
  
 
  images = [
    
    {
      text: "Festive Deer",
      image: "https://ak.picdn.net/shutterstock/videos/2867827/thumb/1.jpg"
    },
    {
      text: "Festive Deer",
      image: "https://store.hp.com/app/assets/images/uploads/prod/ecommerce-vs-online-marketplace1603983748724190.jpg"
    }
  ];
 
  constructor() { }

  ngOnInit() {
  }


}
