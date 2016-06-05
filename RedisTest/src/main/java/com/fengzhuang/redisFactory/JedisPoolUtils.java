package com.fengzhuang.redisFactory;

import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.fengzhuang.ConstantsRedis;
import com.fengzhuang.RedisPoolException;
import com.fengzhuang.redisConfig;

public class JedisPoolUtils {

    private static JedisPool pool = null;

    private static void createJedisPool() {

        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            try {
                Properties prty = redisConfig.getRedisConfig();

                config.setMaxTotal(Integer.parseInt(prty.getProperty("POOL_REDIS_MAXACTIVE")));
                config.setMaxIdle(Integer.parseInt(prty.getProperty("POOL_REDIS_MAXIDLE")));
                config.setMaxWaitMillis(Integer.parseInt(prty.getProperty("POOL_REDIS_MAXWAIT")));
                config.setTestOnBorrow(true);
                pool = new JedisPool(config, prty.getProperty("POOL_REDIS_HOST"),
                    Integer.parseInt(prty.getProperty("POOL_REDIS_PORT")));
                // init REDIS_KEY_EXPIRE_TIME
                if (null != prty.getProperty("REDIS_KEY_EXPIRE_TIME")) {
                    ConstantsRedis.REDIS_KEY_EXPIRE_TIME = Integer.parseInt(prty
                        .getProperty("REDIS_KEY_EXPIRE_TIME"));
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }

    }

    public static Jedis getJedis() {

        createJedisPool();
        Jedis jds = null;
        try {
            jds = pool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
            if (null != jds) {
                pool.returnBrokenResource(jds);
            }
            //			throw new RedisPoolException("jedis is null");
        }
        return jds;
    }

    public static void returnJedis(Jedis pm) {

        pool.returnResource(pm);

    }

    public static void main(String[] args) {
        JedisPoolUtils.getJedis();
    }
}
