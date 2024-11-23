package org.example.ecomercewebapp.Controler.UserAuth;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.ecomercewebapp.JWT.JwtService;
import org.example.ecomercewebapp.Model.RoleEnum;
import org.example.ecomercewebapp.Model.Utilisateur;
import org.example.ecomercewebapp.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Data
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setRole(RoleEnum.User);
        utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(utilisateur);
        String jwtToken = jwtService.generateToken(null,new org.springframework.security.core.userdetails.User(
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name()))
        ));
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setRole(RoleEnum.Admin);
        utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(utilisateur);
        String jwtToken = jwtService.generateToken(null,new org.springframework.security.core.userdetails.User(
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name()))
        ));
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Utilisateur utilisateur = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("Invalid email or password"));
        String jwtToken = jwtService.generateToken(null,new org.springframework.security.core.userdetails.User(
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name()))
                ));
        return new AuthenticationResponse(jwtToken);

    }


}

