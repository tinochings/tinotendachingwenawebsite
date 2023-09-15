package com.tinotendachingwena.website;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class RateLimitConfig {
    @Autowired
    public ProxyManager buckets;

    /**
     * @param key ipAddress of client
     * */
    public Bucket resolveBucket(String key) {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser(key);

        return buckets.builder().build(key, configSupplier);
    }

    /**
     * Limit five requests per users ipAddress within a minute
     */
    private Supplier<BucketConfiguration> getConfigSupplierForUser(String key) {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));

        Bandwidth limit = Bandwidth.classic(5, refill);

        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }
}