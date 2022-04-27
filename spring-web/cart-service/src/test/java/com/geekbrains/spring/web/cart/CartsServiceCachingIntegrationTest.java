package com.geekbrains.spring.web.cart;

import com.geekbrains.spring.web.cart.configs.CacheConfigs;
import com.geekbrains.spring.web.cart.integrations.ProductsServiceIntegration;
import com.geekbrains.spring.web.cart.models.AnalyticsCart;

import com.geekbrains.spring.web.cart.services.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import({CacheConfigs.class, CartService.class})
@ExtendWith(SpringExtension.class)
@EnableCaching
@ImportAutoConfiguration(classes = {
        CacheAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class CartsServiceCachingIntegrationTest {
    @MockBean
    private RedisTemplate<String, Object> mockRedisTemplate;

    @Autowired
    private CartService cartService;

    @Autowired
    private CacheManager cacheManager;
    @MockBean
    private ProductsServiceIntegration productsServiceIntegration;


    @Test
    void givenRedisCaching_whenFindCartByAnalyticsKey_thenCartReturnedFromCache(){
        AnalyticsCart analyticsCart = new AnalyticsCart();
      //  mockRedisTemplate.opsForValue().set("${utils.analytic.prefix}", new AnalyticsCart());
        given(mockRedisTemplate.opsForValue().get("${utils.analytic.prefix}"))
        .willReturn(Optional.of(analyticsCart));

        AnalyticsCart analyticsCartCacheMiss = cartService.getAnalyticsByDate("${utils.analytic.prefix}");
        AnalyticsCart analyticsCartCacheHit = cartService.getAnalyticsByDate("${utils.analytic.prefix}");

        assertThat(analyticsCartCacheMiss).isEqualTo(analyticsCart);
        assertThat(analyticsCartCacheHit).isEqualTo(analyticsCart);

        verify(mockRedisTemplate, times(1)).opsForValue().get("${utils.analytic.prefix}");
        assertThat(itemFromCache()).isEqualTo(analyticsCart);

    }

    private Object itemFromCache() {
        return Objects.requireNonNull(Objects.requireNonNull(cacheManager.getCache("cartCache")).get("${utils.analytic.prefix}")).get();
    }

}
