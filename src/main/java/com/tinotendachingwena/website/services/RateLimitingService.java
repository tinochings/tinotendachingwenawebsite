package com.tinotendachingwena.website.services;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class RateLimitingService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

        public Bucket resolveBucket(String ipKey) {
            return cache.computeIfAbsent(ipKey, this::newBucket);
        }

        private Bucket newBucket(String ipKey) {
            Refill refill = Refill.intervally(5, Duration.ofMinutes(1));

            Bandwidth limit = Bandwidth.classic(5, refill);
            return Bucket.builder()
                    .addLimit(limit)
                    .build();
        }

}
