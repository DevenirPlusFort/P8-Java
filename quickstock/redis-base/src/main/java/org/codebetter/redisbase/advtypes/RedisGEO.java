package org.codebetter.redisbase.advtypes;

import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/***
 *
 * @File
 * @Desc
 * @Author XZH
 * @Create 2022/10/03 23:13
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *
 */
@Component
public class RedisGEO {
   public final static String RS_GEO_NS = "rg:";

   @Autowired
   private JedisPool jedisPool;


    /**
     *
     * @param key
     * @param longitude 经度
     * @param latiude 维度
     * @param member 成员名
     * @return
     */
   public Long addLocation(String key, double longitude, double latiude, String member) {
       Jedis jedis = null;
       try {
          jedis = jedisPool.getResource();
          return jedis.geoadd(RS_GEO_NS+key, longitude, latiude, member);
       } catch (Exception e) {
          return null;
       } finally {
          jedis.close();
       }
   }
}
