package org.codebetter.redisbase.redismq;


import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.StreamEntryID;

import java.util.Map;

/***
 * 
 * @File  StreamVer.java
 * @Desc  实现消费组消费，不考虑消费者模式
 * @Author codebetter
 * @Create 2022/10/20 22:09 
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *                 
 */
public class StreamVer {
   public static final String RS_STRING_MQ_NS = "rsm:";

   @Autowired
   private JedisPool jedisPool;

   /*
     * 
     * @Description
     * @return: redis.clients.jedis.StreamEntryID
     *
     * @author: codebetter
     * @create: 2022/10/20 22:11
     * @ChangeList
     * --------------------------------------------------------------------------
     * Date                Editor                     ChangeReasons
     *
   */
   public StreamEntryID produce(String key, Map<String, String> message) {
       Jedis jedis = null;
       try {
         jedis = jedisPool.getResource();
         StreamEntryID id = jedis.xadd(RS_STRING_MQ_NS+key, StreamEntryID.NEW_ENTRY, message);
         System.out.println("发布消息到"+RS_STRING_MQ_NS+key+" 返回消息id="+id.toString());
         return id;
       } catch (Exception e) {
          throw new RuntimeException("创建消费群组失败", e);
       } finally {
          jedis.close();
       }
   }

   /*
     * 
     * @Description  创建消费群组,消费群组不可重复创建
     * @Param key: 
     * @Param groupName: 
     * @Param lastDeliveredId: 
     * @return: void
     *
     * @author: codebetter
     * @create: 2022/10/20 22:32
     * @ChangeList
     * --------------------------------------------------------------------------
     * Date                Editor                     ChangeReasons
     *
   */
   public void createCustomGroup(String key, String groupName, String lastDeliveredId) {
        Jedis jedis = null;
        try {
            StreamEntryID id = null;
            if (lastDeliveredId==null){
                lastDeliveredId = "0-0";
            }
            id = new StreamEntryID(lastDeliveredId);
            jedis = jedisPool.getResource();
            /*makeStream表示没有时是否自动创建stream，但是如果有，再自动创建会异常*/
            jedis.xgroupCreate(RS_STRING_MQ_NS+key,groupName,id,false);
            System.out.println("创建消费群组成功："+groupName);
        } catch (Exception e) {
            throw new RuntimeException("创建消费群组失败！",e);
        } finally {
            jedis.close();
        }
   }
}
