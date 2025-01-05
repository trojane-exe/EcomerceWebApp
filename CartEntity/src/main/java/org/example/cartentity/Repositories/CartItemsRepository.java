package org.example.cartentity.Repositories;

import org.example.cartentity.Model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemsRepository extends JpaRepository<CartItems,Integer> {

    @Query("delete from CartItems c where c.cart.cartId =:id")
    void deleteitems(@Param("id")Integer id);

    @Query("select i from CartItems i where i.cart.cartId =:id")
    List<CartItems>findAllByCartId(@Param("id")Integer id);

    @Query("select i from CartItems i where i.cart.userId =:id")
    List<CartItems>findAllByUserId(@Param("id")Integer id);

    @Query("select sum(c.total) from CartItems c where c.cart.userId =:userId")
    Float totalToPay(@Param("userId")Integer id);




}
