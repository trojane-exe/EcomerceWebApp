package org.example.payemententity.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Payement {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "factureId")
    private Integer factureId ;

    private Integer userId;

    private Integer cartId;

    private Float total;

    @Enumerated(EnumType.STRING)
    private EtatEnum status;

}
