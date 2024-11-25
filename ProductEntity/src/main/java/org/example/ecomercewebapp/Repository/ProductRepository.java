package org.example.ecomercewebapp.Repository;

import org.example.ecomercewebapp.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p  WHERE p.nom like :nom% or p.nom like %:nom% or p.nom like :nom%")
    List<Product> findByName(@Param("nom") String nom);


    @Query("SELECT p FROM Product p WHERE p.categorie like :cat% or p.categorie like %:cat% or p.categorie like :cat%")
    List<Product> findByCategorie(@Param("cat") String cat);



}
