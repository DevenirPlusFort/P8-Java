package org.codebetter.adv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;

/***
 *
 * @File
 * @Desc
 * @Author Happy
 * @Create 2022/10/06 22:40
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *
 */
@Component
public class RedisLua {
     public static final String RS_LUA_NS = "rlilf:";

      public final static String LUA_SCRIPTS =
            "local num = redis.call('incr', KEYS[1])\n" +
            "if tonumber(num) == 1 then\n" +
            "\tredis.call('expire', KEYS[1], ARGV[1])\n" +
            "\treturn 1\n" +
            "elseif tonumber(num) > tonumber(ARGV[2]) then\n" +
            "\treturn 0\n" +
            "else \n" +
            "\treturn 1\n" +
            "end";
      @Autowired
      private JedisPool jedisPool;

      public String loadScript() {
          Jedis jedis = null;
          try {
             jedis = jedisPool.getResource();
             String sha = jedis.scriptLoad(LUA_SCRIPTS);
             return sha;
          } catch (Exception e) {
             throw new RuntimeException("加载脚本失败!", e);
          } finally {
              jedis.close();
          }
      }

      public String ipLimitFlow(String ip) {
         Jedis jedis = null;
         try {
           jedis = jedisPool.getResource();
           String result = jedis.evalsha("9ac7623ae2435baf9ebf3ef4d21cde13de60e85c", Arrays.asList(RS_LUA_NS+ip), Arrays.asList("60","2")).toString();
           return result;
         } catch (Exception e) {
            throw new RuntimeException("执行脚本失败!", e);
         } finally {
            jedis.close();
         }
      }

}
