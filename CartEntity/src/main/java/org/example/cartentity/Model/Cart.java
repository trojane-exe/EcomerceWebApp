package org.example.cartentity.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    private Integer userId;

    @ElementCollection
    private List<Integer> productId = new ArrayList<>();

    @Transient
    private List<ProductDTO> cartItems ;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
