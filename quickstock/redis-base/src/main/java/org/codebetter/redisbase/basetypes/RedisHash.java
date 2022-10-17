package org.codebetter.redisbase.basetypes;

import org.omg.SendingContext.RunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/***
 *
 * @File
 * @Desc
 * @Author XZH
 * @Create 2022/09/20 12:04
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *
 */
@Component
public class RedisHash {
   public final static String RS_HASH_NS = "rh:";

   @Autowired
   private JedisPool jedisPool;


   public String get(String key, String field) {
       Jedis jedis = null;
       String value;
       try {
           jedis = jedisPool.getResource();
           value = jedis.hget(key, field);
       } catch (Exception e) {
           throw new RuntimeException("获取Redis值失败！");
       } finally {
           jedis.close();
       }
      return value;
   }

   public Long setKey(String key, String field, String value) {
       Jedis jedis = null;
       try {
           jedis = jedisPool.getResource();
           return jedis.hset(RS_HASH_NS+key, field, value);
       } catch (Exception e) {
           throw new RuntimeException("向Redis中存值失败！");
       } finally {
           jedis.close();
       }
   }


}
