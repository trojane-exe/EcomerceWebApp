package org.example.cartentity.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {



    private Integer productId;
    private String nom;
    private Float prix;
    private String imageUrl;
    private Integer stock;



}
