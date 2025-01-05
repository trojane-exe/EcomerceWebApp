package org.example.cartentity.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "cart_Id", nullable = false)
    private Cart cart;
    private Integer productId;
    private Float prix;
    private Integer quantity;
    @Lob
    private String imgUrl;

    private Float total;


}
