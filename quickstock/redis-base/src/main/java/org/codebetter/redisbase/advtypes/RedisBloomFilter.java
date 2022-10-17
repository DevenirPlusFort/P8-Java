package org.codebetter.redisbase.advtypes;

import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.nio.charset.Charset;

/***
 *
 * @File
 * @Desc 仿Google的布隆过滤器实现，基于Redis支持分布式
 * @Author Happy
 * @Create 2022/10/02 21:58
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *
 */
public class RedisBloomFilter {

   private final static String RS_BF_NS = "rbf:";
   private int numApproxElements;//预估元素数量
   private double fpp;//可接受的最大误差
   private int numHashFunctions;//自动计算的hash函数个数
   private int bitmaplength;//自动计算的最优Bitmap长度

   @Autowired
   private JedisPool jedisPool;

   /**
     * 构造布隆过滤器
     * @param numApproxElements 预估元素数量
     * @param fpp 可接受的最大误差
     * @return
     */
    public RedisBloomFilter init(int numApproxElements,double fpp){
        this.numApproxElements = numApproxElements;
        this.fpp = fpp;
        /*位数组的长度*/
        this.bitmaplength = (int) (-numApproxElements*Math.log(fpp)/(Math.log(2)*Math.log(2)));
        /*算hash函数个数*/   /**  数组长度/ 元素个数*2  **/
        this.numHashFunctions = Math.max(1, (int) Math.round((double) bitmaplength / numApproxElements * Math.log(2)));
        return  this;
    }

    /**
     * 计算一个元素值哈希后映射到Bitmap的哪些bit上
     * @param element
     * @return
     */
    private long[] getBitIndices(String element) {
       long[] indics = new long[numHashFunctions];
       byte[] bytes = Hashing.murmur3_128().hashObject(element, Funnels.stringFunnel(Charset.forName("UTF-8"))).asBytes();
       long hash1 = Longs.fromBytes(bytes[7],bytes[6],bytes[5],bytes[4],bytes[3],bytes[2],bytes[1],bytes[0]);
       long hash2 = Longs.fromBytes(bytes[15],bytes[14],bytes[13],bytes[12],bytes[11],bytes[10],bytes[9],bytes[8]);

       long combindedHash = hash1;
       for (int i = 0; i < numHashFunctions; i++) {
          indics[i] = (combindedHash&Long.MAX_VALUE) % bitmaplength;
          combindedHash = combindedHash + hash2;
       }
       System.out.println(element+"数组下标");
       for (long index :indics) {
           System.out.println(index+",");
       }
       System.out.println(" ");
       return indics;
    }

    /**
     *  插入元素
     * @param key 原始Redis键
     * @param element 元素值
     * @param expireSec 过期时间(秒)
     */
    public void insert(String key, String element, int expireSec) {
        if (key == null || element == null) {
            throw new RuntimeException("键值均不为空");
        }
        String actualKey = RS_BF_NS.concat(key);
        try (Jedis jedis = jedisPool.getResource()){
           try (Pipeline pieline = jedis.pipelined()) {
              for (long index :getBitIndices(element)) {
                 pieline.setbit(actualKey, index, true);
              }
              pieline.syncAndReturnAll();
           }catch (Exception ex) {
           ex.printStackTrace();
          }
          jedis.expire(actualKey, expireSec);
        }
    }

    /**
     * 检查元素在集合中是否(可能)存在
     * @param key
     * @param element
     * @return
     */
    public boolean mayExist(String key, String element) {
       if (key == null || element == null) {
          throw new RuntimeException("键值均不能为空");
       }
       String actualKey = RS_BF_NS.concat(key);
       boolean result = false;
       try (Jedis jedis = jedisPool.getResource()) {
          try (Pipeline pipeline = jedis.pipelined()) {
                for (long index : getBitIndices(element)) {
                    pipeline.getbit(actualKey, index);
                }
                result = !pipeline.syncAndReturnAll().contains(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
       }
       return result;
    }












}
