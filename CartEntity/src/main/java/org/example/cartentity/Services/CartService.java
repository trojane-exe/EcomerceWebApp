package org.example.cartentity.Services;


import org.example.cartentity.Model.CartItems;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {

    public String addToCart(Integer userId,Integer productId,int qte);
    public String createCart(Integer userId);
    public String updateQte(Integer userId,Integer cartItemId,int qte);
    public String removeProductFromCart(Integer cartId,Integer cartItemId);
    public String cancelCart(Integer userId);
    public String deleteCart(Integer userId);
    public List<CartItems> getUserCartItems(Integer userId);
    public String getProductImg(Integer productId);
    public Float totalToPay(Integer id);
    public Integer getCartId(Integer id);
}
