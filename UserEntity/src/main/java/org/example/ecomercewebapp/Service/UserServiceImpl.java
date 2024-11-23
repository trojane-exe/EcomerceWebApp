package org.example.ecomercewebapp.Service;


import io.jsonwebtoken.Jwt;
import jakarta.persistence.EntityNotFoundException;
import org.example.ecomercewebapp.JWT.JwtService;
import org.example.ecomercewebapp.Model.RoleEnum;
import org.example.ecomercewebapp.Model.Utilisateur;
import jakarta.transaction.Transactional;
import org.example.ecomercewebapp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserServiceImpl(PasswordEncoder ps, UserRepository ur, JwtService jwt){
        this.passwordEncoder = ps;
        this.userRepository = ur;
        this.jwtService = jwt;
    }

    @Override
    public String addUSer(Utilisateur user) {
        if (user.getNom()==null || user.getPrenom()==null || user.getEmail()==null|| user.getPassword()==null) {
            return "error";
        }
        else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(RoleEnum.User);
            jwtService.generateToken(null,new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + RoleEnum.User.name()))
            ));
            userRepository.save(user);
            return null;
        }
    }

    @Override
    public String updateUser(Integer id,Utilisateur user) {
        Utilisateur oldUser = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found"));

        if(user.getRole()!=null || user.getEmail()!=null){
            return "errorInfos";
        }

        else if(oldUser!=null){
            if(user.getNom()!=null){
                oldUser.setNom(user.getNom());
            }
            if(user.getPrenom()!=null){
                oldUser.setPrenom(user.getPrenom());
            }
            if(user.getPassword()!=null && !user.getPassword().equals(oldUser.getPassword())){
                oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            else{
                oldUser.setPassword(oldUser.getPassword());
            }
            userRepository.save(oldUser);
            return "ok";

        }
        else{
            return "error";
        }

    }

    @Override
    public String deleteUser(Integer id) {
        Optional<Utilisateur> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            return "ok";
        }
        else{
            return "error";
        }

    }

    @Override
    public List<Utilisateur> listOfUsers() {
        return null;

    }

    @Override
    public Utilisateur findUser(Integer id) {
        Optional<Utilisateur> utilisateur = userRepository.findById(id);
        assert utilisateur.isPresent();
        return utilisateur.get();

    }
}
