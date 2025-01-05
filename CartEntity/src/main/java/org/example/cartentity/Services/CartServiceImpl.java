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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
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
    public String addToCart(Integer userId, Integer productId, int qte) {
        ProductDTO product = productFeignClient.getProductById(productId);
        Cart cart = cartRepository.findByUserId(userId);

        if (product != null) {
            if (product.getStock() <= 0 || product.getStock() < qte) {
                return "error stock";
            } else {
                CartItems cartItems = new CartItems();
                cartItems.setCart(cart);
                cartItems.setProductId(productId);
                cartItems.setQuantity(qte);

                cartItems.setPrix(product.getPrix());
                cartItems.setImgUrl(this.getProductImg(productId));
                cartItemsRepository.save(cartItems);
                cart.setUpdatedAt(LocalDateTime.now());
                cart.setStatus(StatusEnum.Active);
                if (cart.getCartItems() == null) {
                    cart.setCartItems(new ArrayList<>());
                }
                cart.getCartItems().add(cartItems);
                cartItems.setTotal(product.getPrix()*qte);

                // Save the updated Cart entity
                cartRepository.save(cart);

                //decrease the added prodict's stock

                productFeignClient.decreaseStock(productId, qte);
                //productFeignClient.updateStock();

                return "ok";

            }
        } else {
            return "error";
        }
    }


    @Override
    public String createCart(Integer userId) {
        Cart cart1 = cartRepository.findByUserId(userId);
        if (cart1 == null) {
            cart1 = new Cart();
            cart1.setUserId(userId);
            cart1.setCreatedAt(LocalDateTime.now());
            cart1.setStatus(StatusEnum.Empty);
            cartRepository.save(cart1);
            return "created";
        } else {
            return "creation error";
        }
    }

    @Override
    public String updateQte(Integer userId, Integer cartItemId, int qte) {

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            return "cart null";
        }
        CartItems cartItems = cartItemsRepository.findById(cartItemId)
                .filter(item -> item.getCart().getCartId()
                        .equals(cart.getCartId())).orElse(null);
        if (cartItems == null) {
            return "cartItems null";
        }
        Integer productId = cartItems.getProductId();
        ProductDTO productDTO = productFeignClient.getProductById(productId);
        if (productDTO == null) {
            return "product null";
        }
        int realStock = productDTO.getStock() + cartItems.getQuantity();
        if (realStock < qte) {
            return "error stock";
        } else {
            int newStock = realStock - qte;
            cartItems.setQuantity(qte);
            cartItems.setTotal(productDTO.getPrix()*qte);
            cartItemsRepository.save(cartItems);
            productFeignClient.updateStock(productId, newStock);
            return "ok";
        }

    }

    @Override
    public String removeProductFromCart(Integer userId, Integer cartItemId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            return "cart null";
        }
        CartItems cartItems = cartItemsRepository.findById(cartItemId).filter(item -> item.getCart().getCartId().equals(cart.getCartId())).orElse(null);
        if (cartItems == null) {
            return "item null";
        } else {
            ProductDTO productDTO = productFeignClient.getProductById(cartItems.getProductId());
            if (productDTO != null) {
                int newStock = productDTO.getStock() + cartItems.getQuantity();
                productFeignClient.updateStock(productDTO.getProductId(), newStock);
            }
            cartItemsRepository.deleteById(cartItemId);
            List<CartItems> cartItems1 = cartItemsRepository.findAllByCartId(cart.getCartId());
            if(cartItems1==null){
                cart.setStatus(StatusEnum.Empty);
                cartRepository.save(cart);
            }
            return "ok";
        }
    }

    @Override
    public String cancelCart(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            return "cart null";
        } else {
            List<CartItems> items = cartItemsRepository.findAllByCartId(cart.getCartId());
            if (items == null) {
                return "items null";
            } else {
                for (CartItems items1 : items) {
                    ProductDTO productDTO = productFeignClient.getProductById(items1.getProductId());
                    if (productDTO != null) {
                        int newStock = productDTO.getStock() + items1.getQuantity();
                        productFeignClient.updateStock(productDTO.getProductId(), newStock);
                    }
                    cartItemsRepository.deleteById(items1.getId());
                    cart.setStatus(StatusEnum.Empty);
                    cartRepository.save(cart);
                }
                return "ok";
            }
        }
    }

    @Override
    public String deleteCart(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if(cart!=null){
            cartRepository.deleteById(cart.getCartId());
            return "deleted";
        }
        else {
            return "error";
        }
    }

    @Override
    public List<CartItems> getUserCartItems(Integer userId) {
        return cartItemsRepository.findAllByUserId(userId);
    }

    @Override
    public String getProductImg(Integer productId) {
        if(productFeignClient.getImage(productId)!=null){
        return productFeignClient.getImage(productId);}
        else return null;



    }

    @Override
    public Float totalToPay(Integer id) {
        return cartItemsRepository.totalToPay(id);
    }

    @Override
    public Integer getCartId(Integer id) {
        return cartRepository.findCartIdOfUser(id);
    }

}
