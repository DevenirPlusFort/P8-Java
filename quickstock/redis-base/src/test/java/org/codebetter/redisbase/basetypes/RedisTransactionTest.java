package org.codebetter.redisbase.basetypes;

import org.codebetter.adv.RedisLua;
import org.codebetter.adv.RedisTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/***
 *
 * @File
 * @Desc
 * @Author codebetter
 * @Create 2022/10/20 10:50
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *
 */
@SpringBootTest
public class RedisTransactionTest {
   @Autowired
   private RedisTransaction redisTransaction;

   @Test
   public void testTrasaction() {
       redisTransaction.transaction();
   }
}
