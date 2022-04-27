package com.geekbrains.spring.web.cart.services;

import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.cart.integrations.ProductsServiceIntegration;
import com.geekbrains.spring.web.cart.models.AnalyticsCart;
import com.geekbrains.spring.web.cart.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductsServiceIntegration productsServiceIntegration;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    @Value("${utils.analytic.prefix}")
    private String analyticPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String getAnalyticsUuidFromSuffix(String suffix) {
        return analyticPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    public AnalyticsCart getAnalyticsByDate(String analyticsKey) {
        if (!redisTemplate.hasKey(analyticsKey)) {
            redisTemplate.opsForValue().set(analyticsKey, new AnalyticsCart());
        }
        return (AnalyticsCart) redisTemplate.opsForValue().get(analyticsKey);
    }

    public void addToCart(String cartKey, Long productId) {

        // GET from Redis
        // UPDATE OBJECT
        // SET to Redis
        ProductDto productDto = productsServiceIntegration.findById(productId);
        execute(cartKey, c -> {
            c.add(productDto);
        });
        String analyticKey = getAnalyticsUuidFromSuffix(String.valueOf(LocalDate.now()));
        executeAnalytics(analyticKey, ac -> {
            ac.add(productDto);
        });
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }

    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }

    //склейка корзин из гостевой в аутентифицированную
    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey); // GET
        action.accept(cart); // UPDATE
        redisTemplate.opsForValue().set(cartKey, cart); // SET
    }

    private void executeAnalytics(String analyticsKey, Consumer<AnalyticsCart> action) {
        AnalyticsCart analyticsCart = getAnalyticsByDate(analyticsKey); // GET
        action.accept(analyticsCart); // UPDATE добавляет в корзину аналитики продукт
        redisTemplate.opsForValue().set(analyticsKey, analyticsCart); // SET добавляет корзину аналитики в базу
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}