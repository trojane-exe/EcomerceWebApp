package org.example.cartentity.Repositories;

import org.example.cartentity.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Query("select c from Cart c where c.userId =:id")
    Cart findByUserId(@Param("id")Integer id);




}
