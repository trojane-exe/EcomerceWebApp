import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserserviceService } from 'src/app/Services/AdminServices/UserService/userservice.service';
import { AuthenticationService } from 'src/app/Services/authenticationService/authentication.service';
import { Product } from 'src/app/models/Product.model';
import { ProductService } from 'src/app/Services/AdminServices/ProductService/product.service';
import { NgForm } from '@angular/forms';


@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit{
  product:Product = new Product();
  selectedFile: File | null = null;
  img: File | null = null;

  


    constructor(private router:Router, private toast :ToastrService,private userService : UserserviceService,private authService:AuthenticationService,
      private productService:ProductService
    ){
  
    }

    onFileChange(event: any): void {
      if (event.target.files && event.target.files.length) {
        this.selectedFile = event.target.files[0];
      } else {
        this.selectedFile = null;
      }
    }




  //   addProduct(productForm: NgForm, fileInput: HTMLInputElement): void {

      
  //     const product = productForm.value;
  //     const file = fileInput.files?.[0] || null; // Get the selected file or null
  
  //     this.productService.addProduct(product, file).subscribe({
  //         next: (response) => {
  //             console.log('Product added successfully', response);
  //         },
  //         error: (error) => {
  //             console.error('Failed to add product', error);
  //         },
  //     });
  // }





  //   addProduct(form:NgForm){
  //     const formData = new FormData();
  //     formData.append('nom', form.value.nom);
  //     formData.append('description', form.value.description);
  //     formData.append('stock', form.value.stck);
  //     formData.append('prix', form.value.confirm_password);
  //     formData.append('img', this.product.image);
  //     console.log(formData)

  //   this.productService.addProduct(formData).subscribe(
  //     (response) => {
  //       console.log('Product added successfully:', response);
  //       alert('Product added successfully!');
  //     },
  //     (error) => {
  //       console.error('Error adding product:', error);
  //       alert('Failed to add product. Please try again.');
  //     }
  //   );
  // }
    
      

  



    // validateFile(event: any) {
    //   const file = event.target.files[0];
    //   if (file) {
    //     const validTypes = ['image/png', 'image/jpg'];
    //     if (!validTypes.includes(file.type)) {
    //       this.toast.warning('Please upload a valid image file (PNG or JPG)');
    //       event.target.value = ''; // Clear the input if the file is invalid
    //     }
    //   }
    // }
    

    validateFile(event: any) {
      const file = event.target.files[0];
      if (file) {
          const validTypes = ['image/png', 'image/jpg', 'image/jpeg', 'image/JPG'];
          if (!validTypes.includes(file.type)) {
              this.toast.warning('Please upload a valid image file (PNG, JPG, or JPEG)');
              event.target.value = ''; // Clear the input if the file is invalid
          } else {
              this.img = file;
              console.log('File selected:', this.img); // Debugging line
          }
      }
  }
  
  
  
    addProduct(form: any) {
      if(!form.valid){
        this.toast.warning("Please fill all the required fiels","",{toastClass:'fields-warning'})
      }
      else if (form.valid ) {
        const defaultImgPath = 'assets/defaultProduct.png'; // Path to the default image
        let imgFile: File | null = this.img;
  
        if (!this.img) {
          // Fetch the default image file
          fetch(defaultImgPath)
            .then(response => response.blob())
            .then(blob => {
              imgFile = new File([blob], 'defaultProduct.jpg', { type: blob.type });
              this.productService.addProduct(this.product, imgFile).subscribe(
                response => {
                  this.toast.success("Product added successfully");
                  this.router.navigate(['/products']);
                },
                error => {
                  this.toast.error('Error adding product', error);
                }
              );
            })
            .catch(error => {
              this.toast.error('Error loading default image', error);
            });
        } else {
          this.productService.addProduct(this.product, this.img).subscribe(
            response => {
              this.toast.success("Product added successfully");
              this.router.navigate(['/products']);
            },
            error => {
              this.toast.error('Error adding product', error);
            }
          );
        }
      } else {
        console.error('Please ensure all fields are valid');
        this.toast.warning("Please fill all the required fields");
      }
    }
    


















  ngOnInit(): void {

    if(this.authService.getRole()==='User'){
      this.toast.info("Regular users are not allowed to access this page")
      this.authService.logout();
      this.router.navigate([''])
    }

    if(this.authService.isConnected()===false){
      this.authService.logout();
      this.router.navigate(['']);
      this.toast.error("Please log in using you credentials to proceed","",{
        toastClass:'false-login'
      })
    }
    this.toast.info("the red * marks a required fields","",{
      toastClass:'fields-warning'
    })
  }


}
