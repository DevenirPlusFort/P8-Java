package org.codebetter.adv;

import javafx.scene.chart.PieChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;

/***
 * 
 * @File  RedisPipeline.java
 * @Desc  
 * @Author codebetter
 * @Create 2022/10/19 23:03 
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *                 
 */
@Component
public class RedisPipeline {
    @Autowired
    private JedisPool jedisPool;

    /**
      * 
      * @Description   查询技术文件列表
      * @Param keys: 键集合
      * @return: java.util.List<java.lang.Object>
      *
      * @author: codebetter
      * @create: 2022/10/19 23:00
      * @ChangeList
      * --------------------------------------------------------------------------
      * Date                Editor                     ChangeReasons
      *
    */
    public List<Object> plGet(List<String> keys) {
        Jedis jedis = null;
        try {
           jedis = jedisPool.getResource();
           Pipeline pipelined = jedis.pipelined();
           for (String key :keys) {
              pipelined.get(key);
           }
           return pipelined.syncAndReturnAll();
        } catch (Exception e) {
            throw new RuntimeException("执行Pipeline获取失败!", e);
        } finally {
            jedis.close();
        }
    }

    /*
      * 
      * @Description   查询技术文件列表
      * @Param keys: 
  * @Param values:
      * @return: void
      *
      * @author: codebetter
      * @create: 2022/10/19 23:09
      * @ChangeList
      * --------------------------------------------------------------------------
      * Date                Editor                     ChangeReasons
      *
    */
    public void plSet(List<String> keys, List<String> values) {
        if (keys.size()!=values.size()) {
           throw new RuntimeException("key和value个数不匹配");
        }
        Jedis jedis = null;
        try {
             jedis = jedisPool.getResource();
             Pipeline pipelined = jedis.pipelined();
             for (int i = 0; i < keys.size(); i++) {
               pipelined.set(keys.get(i), values.get(i));
             }
             pipelined.sync();
        } catch (Exception e) {
            throw new RuntimeException("执行Pipeline设置失败", e);
        } finally {
            jedis.close();
        }
    }
}
