package kc.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@CacheConfig(cacheNames = "DoSomeThing")
public class DoSomeThing {

    public void test(String body) {
        System.out.println("处理消息" + body);
    }

    @Cacheable(key = "'putCache::' + #p0", unless = "#result.size() == 0", cacheManager = "twoMinCacheManager")
    public List<String> putCache(String s1){
        return Stream.of("张三","李四","王五").collect(Collectors.toList());
    }
}
