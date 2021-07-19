package kc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig1 {
    /**
     * 配置分布式锁
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        long defaultExpireTime = 10000L;
        return new RedisLockRegistry(redisConnectionFactory, "redis-kc.lock", defaultExpireTime);

    }
}
