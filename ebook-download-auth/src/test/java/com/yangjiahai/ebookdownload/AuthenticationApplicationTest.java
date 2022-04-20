package com.yangjiahai.ebookdownload;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/20 10:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthenticationApplication.class)
public class AuthenticationApplicationTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test(){
//        redisTemplate.opsForHash().put("userinfo2","user",1);
        redisTemplate.delete("userinfo2");
    }
}
