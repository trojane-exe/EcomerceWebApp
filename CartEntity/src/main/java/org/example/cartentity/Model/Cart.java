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

    @Column(unique = true)
    private Integer userId;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<CartItems> cartItems ;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt;



}
