package com.example.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsynConfiguration {

    @Value("${executor.corePoolSize: 10}")
    private Integer CORE_POOL_SIZE;

    @Value("${executor.maxPoolSize: 100}")
    private Integer MAX_POOL_SIZE;

    @Value("${executor.queueCapacity: 500}")
    private Integer QUEUE_CAPACITY;

    @Bean("threadPoolTaskExecutor")
    public Executor getAsyncExecutor(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(CORE_POOL_SIZE); // Arranca con 1 hilo
        executor.setMaxPoolSize(MAX_POOL_SIZE); // max cantidad de hilos ejecutandose
        executor.setQueueCapacity(QUEUE_CAPACITY); // capacidad de la cola

        executor.initialize();

        return executor;
    }
}
