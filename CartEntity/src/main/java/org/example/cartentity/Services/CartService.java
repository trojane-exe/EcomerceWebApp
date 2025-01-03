package org.example.cartentity.Services;


import org.example.cartentity.Model.Cart;

public interface CartService {

    public String addToCart(Integer userId,Integer productId,int qte);
    public String createCart(Integer userId);
    public String updateQte(Integer userId,Integer cartItemId,int qte);
    public String removeProductFromCart(Integer cartId,Integer cartItemId);
    public String cancelCart(Integer userId);
}
