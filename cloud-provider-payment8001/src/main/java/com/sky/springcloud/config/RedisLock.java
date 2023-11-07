package com.sky.springcloud.config;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 基于Redis实现分布式锁
 *
 * @author dlf
 * @date 2023/2/17 16:34
 */
public class RedisLock implements AutoCloseable {

    private RedisTemplate redisTemplate;
    private String key;
    private String value;
    private int expireTime;

    public RedisLock(RedisTemplate redisTemplate, String key, int expireTime) {
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.value = UUID.randomUUID().toString();
        this.expireTime = expireTime;
    }

    /**
     * 获取分布式锁
     */
    public boolean getLock() {
        RedisCallback<Boolean> redisCallback = redisConnection -> {
            //设置NX
            RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.ifAbsent();
            //设置过期时间
            Expiration expiration = Expiration.seconds(expireTime);
            //序列化key
            byte[] redisKey = redisTemplate.getHashKeySerializer().serialize(key);
            //序列化value
            byte[] redisValue = redisTemplate.getValueSerializer().serialize(value);
            //执行setNx操作
            Boolean result = redisConnection.set(redisKey, redisValue, expiration, setOption);
            return result;
        };
        //获取分布式锁
        return (Boolean) redisTemplate.execute(redisCallback);

    }

    /**
     * 解锁
     */
    public boolean unLock() {
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        RedisScript<Boolean> redisScript = RedisScript.of(script, Boolean.class);
        List<String> keys = Arrays.asList(key);
        Boolean result = (Boolean) redisTemplate.execute(redisScript, keys, value);
        return result;

    }


    /**
     * 自动关闭
     */
    @Override
    public void close() throws Exception {

    }
}
