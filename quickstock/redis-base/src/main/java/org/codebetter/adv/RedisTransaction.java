package org.codebetter.adv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;

/***
 *
 * @File  RedisTransaction.java
 * @Desc  Redis事务控制
 * @Author codebetter
 * @Create 2022/10/20 10:59
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason
 *
 */
@Component
public class RedisTransaction {
    public final static String RS_TRANS_NS = "rts:";

    @Autowired
    private JedisPool jedisPool;

    public List<Object> transaction(String... watchKeys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (watchKeys.length > 0) {
               String watchResult = jedis.watch(watchKeys);
               if (!"OK".equals(watchResult)) {
                   throw new RuntimeException("执行watch失败:"+watchResult);
               }
            }
            Transaction multi = jedis.multi();
            multi.set(RS_TRANS_NS+"testa1", "a1");
            multi.set(RS_TRANS_NS+"testa2", "a2");
            multi.set(RS_TRANS_NS+"testa3", "a3");
            List<Object> execResult = multi.exec();
            if (execResult == null) {
               throw new RuntimeException("事务无法执行, 监视KEY被修改");
            }
            return execResult;
        }catch (Exception e) {
             throw new RuntimeException("执行Redis事务失败!", e);
        } finally {
            if (watchKeys.length > 0) {
                jedis.unwatch();
            }
            jedis.close();
        }
    }
}
