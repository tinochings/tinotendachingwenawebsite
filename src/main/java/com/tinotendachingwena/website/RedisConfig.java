package com.tinotendachingwena.website;

import com.giffing.bucket4j.spring.boot.starter.config.cache.SyncCacheResolver;
import com.giffing.bucket4j.spring.boot.starter.config.cache.jcache.JCacheCacheResolver;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.cache.CacheManager;
import javax.cache.Caching;

@Configuration
public class RedisConfig {
    @Bean
    public Config config() {
        Config config = new Config();
//        config.useSingleServer()
        config.useSingleServer().setPassword("p6f2f323da02ad7f3c0f0ffad78c507c2c571df99480cd18bac353a9a2b3bb0d0").setAddress("rediss://:p6f2f323da02ad7f3c0f0ffad78c507c2c571df99480cd18bac353a9a2b3bb0d" +
                "0@ec2-63-33-139-127.eu-west-1.compute.amazonaws.com:16710");
        return config;
    }

    @Bean
    public CacheManager cacheManager(Config config) {
        CacheManager manager = Caching.getCachingProvider().getCacheManager();
        manager.createCache("cache", RedissonConfiguration.fromConfig(config));
        return manager;
    }

    @Bean
    ProxyManager<String> proxyManager(CacheManager cacheManager) {
        return new JCacheProxyManager<>(cacheManager.getCache("cache"));
    }

    @Bean
    @Primary
    public SyncCacheResolver bucket4jCacheResolver(CacheManager cacheManager) {
        return new JCacheCacheResolver(cacheManager);
    }

}