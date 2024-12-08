package org.example.ecomercewebapp.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.ecomercewebapp.FeignClient.CartFeignClient;
import org.example.ecomercewebapp.JWT.JwtService;
import org.example.ecomercewebapp.Model.RoleEnum;
import org.example.ecomercewebapp.Model.Utilisateur;
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
public class UserServiceAdminImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CartFeignClient feignClient;

    @Autowired
    public UserServiceAdminImpl(PasswordEncoder ps, UserRepository ur, JwtService jwt, CartFeignClient feignClient){
        this.passwordEncoder = ps;
        this.userRepository = ur;
        this.jwtService = jwt;
        this.feignClient = feignClient;
    }
    @Override
    public String addUSer(Utilisateur user) {

        if(user.getNom()==null|| user.getPrenom()==null||user.getEmail()==null||user.getPassword()==null){
            return "error";
        }
        else{
            if(user.getRole()==null){
                user.setRole(RoleEnum.User);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                jwtService.generateToken(null,new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + RoleEnum.User.name()))
                ));
                userRepository.save(user);
                feignClient.createCart(user.getUserId()).getBody();

                return "ok";
            } else if (user.getRole().equals(RoleEnum.User)) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                jwtService.generateToken(null,new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + RoleEnum.User.name()))
                ));
                userRepository.save(user);
                feignClient.createCart(user.getUserId()).getBody();
                return "user";

            } else{
                user.setRole(RoleEnum.Admin);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                jwtService.generateToken(null, new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                ));
                userRepository.save(user);
                feignClient.createCart(user.getUserId()).getBody();
                return "admin";

            }
        }

    }

    @Override
    public String updateUser(Integer id,Utilisateur user) {
        Utilisateur oldUser = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found"));
        if (oldUser != null) {
            if(user.getNom() != null) {
                oldUser.setNom(user.getNom());
            }
            if(user.getPrenom() != null) {
                oldUser.setPrenom(user.getPrenom());
            }
            if(user.getEmail()!=null){
                oldUser.setEmail(user.getEmail());
            }
            if(user.getPassword()!=null && !user.getPassword().equals(oldUser.getPassword())){
                oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            else{
                oldUser.setPassword(oldUser.getPassword());
            }
            if(user.getRole()==(RoleEnum.User) || user.getRole()==(RoleEnum.Admin )){
            oldUser.setRole(user.getRole());}
            if(user.getRole()==null){
                oldUser.setRole(oldUser.getRole());
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
        if(user.isEmpty()){
            return "error";
        }
        else {
            userRepository.deleteById(id);
            return "ok";
        }


    }

    @Override
    public List<Utilisateur> listOfUsers() {
        return userRepository.findAll();
    }

    @Override
    public Utilisateur findUser(Integer id) {
        Optional<Utilisateur> utilisateur = userRepository.findById(id);
        assert utilisateur.isPresent();
        return utilisateur.get();
    }
}
