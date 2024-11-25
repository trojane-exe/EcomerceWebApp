package org.example.ecomercewebapp.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;

import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String nom;
    private String description;
    private Float prix;
    private String categorie;
    private Integer stock;
    @Lob
    private byte[] image;




}
