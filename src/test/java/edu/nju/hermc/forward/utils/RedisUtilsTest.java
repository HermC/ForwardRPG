package edu.nju.hermc.forward.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilsTest {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void test() {
        redisUtils.set("test", new Date());
        Date d = (Date) redisUtils.get("test");
        System.out.println(d);
        System.out.println("here");
    }

}
