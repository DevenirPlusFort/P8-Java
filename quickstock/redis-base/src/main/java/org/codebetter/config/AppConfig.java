package org.codebetter.config;

import org.codebetter.redisbase.advtypes.RedisBloomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 *
 * @File
 * @Desc
 * @Author Happy
 * @Create 2022/10/03 22:46
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *
 */
@Configuration
public class AppConfig {
    private static final int NUM_APPROX_ELEMENTS = 3000;
    private static final double FPP = 0.03;

    @Bean
    public RedisBloomFilter getRedisBloomFilter() {
         RedisBloomFilter redisBloomFilter  = new RedisBloomFilter();
         redisBloomFilter.init(NUM_APPROX_ELEMENTS, FPP);
         return redisBloomFilter;
    }
}
