package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository, UserService userService){
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public Product handleSaveProduct(Product product){
        return this.productRepository.save(product);
    }

    public Product getProductById(long id){
        return this.productRepository.findById(id).get();
    }

    public List<Product> fetchProducts(){
        return this.productRepository.findAll();
    }

    public void handleDeleteProductById(long id){
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session){
        User user = this.userService.getUserByEmail(email);

        if (user != null){
            //check if user already has cart, otherwise create a new one
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null){
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);

                cart = this.cartRepository.save(newCart);
            }

            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()){
                Product product = productOptional.get();

                // check if product has already been added to cart
                CartDetail oldCartDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);

                if (oldCartDetail == null) {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(product);
                    cartDetail.setPrice(product.getPrice());
                    cartDetail.setQuantity(1);
                    this.cartDetailRepository.save(cartDetail);

                    //update cart (sum)
                    cart.setSum(cart.getSum() + 1);
                    cart = this.cartRepository.save(cart);
                    session.setAttribute("sum", cart.getSum());
                } else {
                    oldCartDetail.setQuantity(oldCartDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldCartDetail);
                }
            }

        }
    }

    public Cart fetchCartByUser(User user){
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session){
        Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()){
            //delete cartDetail -> delete cart
            Cart cart = cartDetailOptional.get().getCart();
            this.cartDetailRepository.deleteById(cartDetailId);

            //update cart
            if (cart.getSum() > 1){
                int s = cart.getSum() - 1;
                cart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(cart);
            } else if (cart.getSum() == 1){
                this.cartRepository.delete(cart);
                session.setAttribute("sum", 0);
            }

        }
    }
}
