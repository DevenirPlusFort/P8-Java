package org.codebetter.redisbase.redismq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Component
public class ListVer {
  public static final String RS_LIST_MQ_NS= "rlm:";

  @Autowired
  private JedisPool jedisPool;
  
  /*
    * 
    * @Description 消费者接收消息
    * @Param key: 
    * @return: java.util.List<java.lang.String>
    *
    * @author: codebetter
    * @create: 2022/10/20 21:53
    * @ChangeList
    * --------------------------------------------------------------------------
    * Date                Editor                     ChangeReasons
    *
  */
  public List<String> get(String key) {
      Jedis jedis = null;
      try {
         jedis = jedisPool.getResource();
         return jedis.brpop(0, RS_LIST_MQ_NS+key);
      } catch (Exception e) {
         throw new RuntimeException("消费消息失败!");
      } finally {
          jedis.close();
      }
  }
  
  /*
    * 
    * @Description  
    * @Param key: 
    * @Param message: 
    * @return: void
    *
    * @author: codebetter
    * @create: 2022/10/20 22:00
    * @ChangeList
    * --------------------------------------------------------------------------
    * Date                Editor                     ChangeReasons
    *
  */
  public void put(String key, String message) {
     Jedis jedis = null;
     try {
        jedis = jedisPool.getResource();
        jedis.lpush(RS_LIST_MQ_NS+key, message);
     } catch (Exception e) {
        throw new RuntimeException("生产消息失败");
     }
  }


}
