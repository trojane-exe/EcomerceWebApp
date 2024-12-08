package org.example.cartentity.Services;


import jakarta.transaction.Transactional;
import lombok.Data;
import org.example.cartentity.FeignClient.ProductFeignClient;
import org.example.cartentity.Model.Cart;
import org.example.cartentity.Model.CartItems;
import org.example.cartentity.Model.ProductDTO;
import org.example.cartentity.Model.StatusEnum;
import org.example.cartentity.Repositories.CartItemsRepository;
import org.example.cartentity.Repositories.CartRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ProductFeignClient productFeignClient;

    public CartServiceImpl(CartRepository cartRepository, CartItemsRepository cartItemsRepository, ProductFeignClient productFeignClient) {
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.productFeignClient = productFeignClient;
    }

    @Override
    public String addToCart(Integer userId,Integer productId,int qte) {
        ProductDTO product = productFeignClient.getProductById(productId);
        Cart cart = cartRepository.findByUserId(userId);

        if(product!=null) {
            if (product.getStock() <= 0 || product.getStock() < qte) {
                return "error stock";
            } else {
                CartItems cartItems = new CartItems();
                cartItems.setCart(cart);
                cartItems.setProductId(productId);
                cartItems.setQuantity(qte);
                cartItemsRepository.save(cartItems);
                cart.setUpdatedAt(LocalDateTime.now());
                cart.setStatus(StatusEnum.Active);
                if (cart.getCartItems() == null) {
                    cart.setCartItems(new ArrayList<>());
                }
                cart.getCartItems().add(cartItems);

                // Save the updated Cart entity
                cartRepository.save(cart);

                //decrease the added prodict's stock

                productFeignClient.decreaseStock(productId,qte);
                //productFeignClient.updateStock();

                return "ok";

            }
        }
        else {
            return "error";
        }
    }



    @Override
    public String createCart(Integer userId) {
        Cart cart1 = cartRepository.findByUserId(userId);
        if(cart1 == null) {
            cart1= new Cart();
            cart1.setUserId(userId);
            cart1.setCreatedAt(LocalDateTime.now());
            cart1.setStatus(StatusEnum.Empty);
            cartRepository.save(cart1);
            return "created";
        }
        else{
            return "creation error";
        }
    }

    @Override
    public String updateQte(Integer cartId,Integer cartItemId,int qte) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(()->new IllegalArgumentException("Cart not found"));
        assert cart!=null;
        CartItems cartItems = cartItemsRepository.findById(cartItemId).filter(item -> item.getCart().getCartId().equals(cartId)).orElse(null);
        assert cartItems != null;
        Integer productId = cartItems.getProductId();
        ProductDTO productDTO = productFeignClient.getProductById(productId);
        assert productDTO!=null;
        if(productDTO.getStock()<=0 || productDTO.getStock()<qte){
            return "error stock";
        }
        else{
            cartItems.setQuantity(qte);
            cartItemsRepository.save(cartItems);
        }


        return null;
    }

    @Override
    public String removeProductFromCart(Integer cartId,Integer cartItemId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        assert cart!=null;
        CartItems cartItems = cartItemsRepository.findById(cartItemId).filter(item ->item.getCart().getCartId().equals(cartId)).orElse(null);
        assert cartItems!=null;
        cartItemsRepository.deleteById(cartItemId);


        return null;
    }
}
