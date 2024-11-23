package org.example.ecomercewebapp.Controler.UserAuth;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RegisterRequest {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String role;
}
