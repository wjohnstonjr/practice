package com.practice.application;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

import io.r2dbc.spi.ConnectionFactory;

@Configuration
@ComponentScan({
	"com.practice.controller"
})
@EntityScan({
	"com.practice.data"
})
@EnableR2dbcRepositories({
	"com.practice.data"
})
@EnableWebFlux
public class PracticeConfiguration extends WebFluxConfigurerComposite {
    @Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all paths
                .allowedOriginPatterns("http://localhost:*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}


