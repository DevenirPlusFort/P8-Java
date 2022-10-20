package org.codebetter.redisbase.advtypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/***
 *
 * @File
 * @Desc
 * @Author codebetter
 * @Create 2022/10/20 21:37
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *
 */
@Component
public class RedisHLL {
   public static final String RS_HLL_NS = "rhll:";

   @Autowired
   private JedisPool jedisPool;

   /*
     *
     * @Description   统计键个数
     * @return: void
     *
     * @author: codebetter
     * @create: 2022/10/20 21:44
     * @ChangeList
     * --------------------------------------------------------------------------
     * Date                Editor                     ChangeReasons
     *
   */
   public void count() {
       Jedis jedis = null;
       try {
          jedis = jedisPool.getResource();
          for (int i = 0; i < 1000; i++) {
              jedis.pfadd(RS_HLL_NS+"countest","user"+i);
          }
          long total = jedis.pfcount(RS_HLL_NS+"countest");
          System.out.println("实际次数:"+1000+",统计次数:"+total);
       } catch (Exception e) {
          e.printStackTrace();
       } finally {
           jedis.close();
       }
   }
}
