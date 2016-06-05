package com.fengzhuang.redisMasterSlaves;

import java.util.Properties;

import com.fengzhuang.redisConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisMasterPool {
    private static JedisPool masterpool = null;

    private static void CreateMasterPool() {

        if (masterpool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            try {
                Properties prty = redisConfig.getRedisConfig();

                config
                    .setMaxTotal(Integer.parseInt(prty.getProperty("POOL_MASTER_REDIS_MAXACTIVE")));
                config.setMaxIdle(Integer.parseInt(prty.getProperty("POOL_MASTER_REDIS_MAXIDLE")));
                config.setMaxWaitMillis(Integer.parseInt(prty
                    .getProperty("POOL_MASTER_REDIS_MAXWAIT")));
                config.setTestOnBorrow(true);
                masterpool = new JedisPool(config, prty.getProperty("POOL_MASTER_REDIS_HOST"),
                    Integer.parseInt(prty.getProperty("POOL_MASTER_REDIS_PORT")));

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
    }

    public static Jedis getJedis() {

        CreateMasterPool();
        Jedis jds = null;
        try {
            jds = masterpool.getResource();
        } catch (Exception e) {
            //释放redis对象
            if (jds == null) {
                masterpool.returnBrokenResource(jds);
            }
        }

        return jds;
    }

    public static void returnJedis(Jedis pm) {

        masterpool.returnResource(pm);

    }

}
