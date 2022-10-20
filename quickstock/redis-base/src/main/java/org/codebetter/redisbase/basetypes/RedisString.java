package org.codebetter.redisbase.basetypes;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/***
 *
 * @File  RedisString.java
 * @Desc  Redis的String数据类型
 * @Author codebetter
 * @Create 2022/10/20 21:47
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason
 *
 */
@Component
public class RedisString {
    public static final String RS_STR_NS = "rs:";

    @Autowired
    private JedisPool jedisPool;

    /**
     * 向Redis中存值，永久有效
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
       Jedis jedis = null;
       try {
         jedis = jedisPool.getResource();
         return jedis.set(RS_STR_NS+key, value);
       } catch (Exception e) {
         throw new RuntimeException("向Redis中存值失败");
       } finally {
           jedis.close();
       }
    }

    /**
     * 批量向Redis中存值，永久有效
     */
    public String msetRaw(String... keysvalues) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            /*自己去拆解，加前缀，再组合...*/
            return jedis.mset(keysvalues);
        } catch (Exception e) {
            throw new RuntimeException("批量向Redis中存值失败！");
        } finally {
            jedis.close();
        }
    }

    /**
     * 根据传入Key获取指定Value
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return  jedis.get(RS_STR_NS+key);
        } catch (Exception e) {
            throw new RuntimeException("获取Redis值失败！");
        } finally {
            jedis.close();
        }
    }
}
