import { Component, OnInit, ViewChild } from '@angular/core';
import {
  ApexNonAxisChartSeries,
  ApexPlotOptions,
  ApexChart,
  ApexFill,
  ChartComponent,
  ApexStroke
} from "ng-apexcharts";
import { AuthenticationService } from 'src/app/Services/authenticationService/authentication.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User.model';
import { UserserviceService } from 'src/app/Services/AdminServices/UserService/userservice.service';
import { ProductService } from 'src/app/Services/AdminServices/ProductService/product.service';
import { Product } from 'src/app/models/Product.model';

export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  labels: string[];
  plotOptions: ApexPlotOptions;
  fill: ApexFill;
  stroke: ApexStroke;
};

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  @ViewChild("chart") chart: ChartComponent | undefined;
  public chartOptions: ChartOptions;

  user: User = new User();
  userId!:number ;

  outOfStockProducts : Product[]=[];
  

  constructor( private authService:AuthenticationService,private toast :ToastrService,private route:Router,
    private userService : UserserviceService,private productService:ProductService
  ) {
    this.chartOptions = {
      series: [this.outOfStockProducts.length],
      chart: {
        height: 170,
        type: "radialBar",
        toolbar: {
          show: true
        }
      },
      plotOptions: {
        radialBar: {
          startAngle: -135,
          endAngle: 225,
          hollow: {
            margin: 0,
            size: "70%",
            background: "#fff",
            image: undefined,
            position: "front",
            dropShadow: {
              enabled: true,
              top: 3,
              left: 0,
              blur: 4,
              opacity: 0.24
            }
          },
          track: {
            background: "#fff",
            strokeWidth: "67%",
            margin: 0, // margin is in pixels
            dropShadow: {
              enabled: true,
              top: -3,
              left: 0,
              blur: 4,
              opacity: 0.35
            }
          },

          dataLabels: {
            show: true,
            name: {
              offsetY: -10,
              show: true,
              color: "#888",
              fontSize: "17px"
            },
            value: {
              formatter: function (val) {
                return parseInt(val.toString(), 10).toString();
              },
              color: "#111",
              fontSize: "36px",
              show: true
            }
          }
        }
      },
      fill: {
        type: "gradient",
        gradient: {
          shade: "dark",
          type: "horizontal",
          shadeIntensity: 0.5,
          gradientToColors: ["#ABE5A1"],
          inverseColors: true,
          opacityFrom: 1,
          opacityTo: 1,
          stops: [0, 100]
        }
      },
      stroke: {
        lineCap: "round"
      },
      labels: ["Products"]
    };
  }

  loadProducts(){
    this.productService.getOutOfStockProducts().subscribe(
      (response : Product[])=>{
        this.outOfStockProducts = response
        console.log(this.outOfStockProducts.length)
        this.chartOptions.series = [this.outOfStockProducts.length]; 
      }
    )    
  }

  
  
  loadUserInfo(id:number):void{
    this.userService.getSingleUser(id).subscribe({
    next:(data:User)=>{
      this.user = data
      console.log("user nom " +this.user.nom);
    }
      })
  
    }
  

  



  ngOnInit(): void {

    
    if(this.authService.getRole()==='User'){
      this.toast.info("Regular users are not allowed to access this page")
      this.authService.logout();
      this.route.navigate([''])
    }

    if(this.authService.isConnected()===false){
      this.authService.logout();
      this.route.navigate(['']);
      this.toast.error("Please log in using you credentials to proceed","",{
        toastClass:'false-login'
      })
    }
    const id =  localStorage.getItem('userId');
    if(id){this.userId = parseInt(id,10)}
    this.loadUserInfo(this.userId);
    
    if(this.authService.isConnected()===false){
      this.route.navigate(['']);
      this.toast.error("Please log in using you credentials to proceed","",{
        toastClass:'false-login'
      })
    }
    this.loadProducts()
  }
}
