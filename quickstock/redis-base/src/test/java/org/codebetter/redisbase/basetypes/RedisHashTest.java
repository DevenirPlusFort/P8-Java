package org.codebetter.redisbase.basetypes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/***
 *
 * @File
 * @Desc
 * @Author Happy
 * @Create 2022/09/20 13:01
 *
 * @ChangeList
 * ------------------------------------------------------------------------
 * Date					Editor				   ChangeReason			
 *
 */
@SpringBootTest
public class RedisHashTest {
    @Autowired
    private RedisHash redisHash;

    @Test
    void testHGet() {
        System.out.println();
    }

    @Test
    void testHSet() {

    }

}
