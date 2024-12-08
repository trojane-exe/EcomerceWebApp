package org.example.cartentity.Repositories;

import org.example.cartentity.Model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepository extends JpaRepository<CartItems,Integer> {
}
