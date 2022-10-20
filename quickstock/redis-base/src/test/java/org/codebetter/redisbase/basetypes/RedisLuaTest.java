package org.codebetter.redisbase.basetypes;

import org.codebetter.adv.RedisLua;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/***
 *
 * @File
 * @Desc
 * @Author XZH
 * @Create 2022/10/19 21:21
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *
 */
@SpringBootTest
public class RedisLuaTest {


   @Autowired
   private RedisLua redisLua;

   @Test
   public void testLoadScript() {
       String loadScriptRes = redisLua.loadScript();
       System.out.println(loadScriptRes);
   }
}
