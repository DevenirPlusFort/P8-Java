package org.codebetter.redisbase.basetypes;

import org.codebetter.redisbase.advtypes.RedisBloomFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/***
 *
 * @File
 * @Desc
 * @Author Happy
 * @Create 2022/10/03 22:50
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *
 */
@SpringBootTest
public class TestRedisBloomFilter {
   private static final int DAY_SEC = 60 * 60 * 24;
   @Autowired
   private RedisBloomFilter redisBloomFilter;

   @Test
   public void testInsert() {
       System.out.println(redisBloomFilter);
       redisBloomFilter.insert("topic_read:8839540:20210810", "76930242", DAY_SEC);
   }

   @Test
   public void testMayInsert() {
       System.out.println(redisBloomFilter.mayExist("topic_read:8839549:20210810", "76930242"));
   }
}
