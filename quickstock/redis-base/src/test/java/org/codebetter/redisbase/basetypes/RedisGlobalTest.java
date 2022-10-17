package org.codebetter.redisbase.basetypes;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

/***
 *
 * @File
 * @Desc
 * @Author codebetter
 * @Create 2022/9/2011:46 
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *
 */
@SpringBootTest
public class RedisGlobalTest {
   @Autowired
   private RedisGlobal redisGlobal;

   @Test
   void testKeys() {
       Set<String> set = redisGlobal.keys("*");
       for (String str :set) {
           System.out.println(str);
       }
   }

   @Test
   void testDBsize() {
       Long dbSize = redisGlobal.dbsize();
       System.out.println(dbSize);
   }


}
