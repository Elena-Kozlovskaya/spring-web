package com.geekbrains.spring.web.core.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class CoreConfig {

    @Value("${integrations.cart-service.url}")
    private String cartServiceUrl;

    //асинхронный клиент (ответ обрабатывается по мере поступления, ответ не ждем, не блокируемся)
    @Bean
    public WebClient cartServiceWebClient() {
        //устанавливается соединение с MS + параметры
        //посылаем запрос если за 10 сек ответ не получили, получили excp либо повторно послали запрос либо ответ клиенту, что не можем обработать
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(10000, TimeUnit.MILLISECONDS)); // время ожидания чтения
                    connection.addHandlerLast(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS)); // время ожидания записи
                });

        // сборка веб клиента
        return WebClient
                .builder()
                .baseUrl(cartServiceUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}