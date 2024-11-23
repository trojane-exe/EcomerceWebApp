package org.example.ecomercewebapp.Repository;

import org.example.ecomercewebapp.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Utilisateur,Integer> {


    Optional<Utilisateur> findByEmail(String email);
}
