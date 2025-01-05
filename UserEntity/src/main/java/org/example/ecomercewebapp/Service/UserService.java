package org.example.ecomercewebapp.Service;

import org.example.ecomercewebapp.Model.Utilisateur;

import java.util.List;

public interface UserService {

    public String addUSer(Utilisateur user);
    public String updateUser(Integer id,Utilisateur user);
    public String deleteUser(Integer id);
    public List<Utilisateur> listOfUsers();
    public Utilisateur findUser(Integer id);
    public Integer findIdByEmail(String email);

}
