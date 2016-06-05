package com.fengzhuang.redisMasterSlaves;

import com.fengzhuang.ConstantsRedis;
import com.fengzhuang.redisFactory.BaseRedisPool;

public class RedisSlaves extends BaseRedisPool {

    public void init() {

        if (JedisIsnull()) {
            setRedis_type(ConstantsRedis.REDIS_TYPE_SLAVES);
            super.setJedis(RedisSlavesPool.getJedis());
        }

    }

    @Override
    public void releaseJedis() {
        // TODO Auto-generated method stub
        if (!JedisIsnull()) {
            RedisSlavesPool.returnJedis(super.getJedis());
            super.setJedis(null);
        }
    }

    public static void main(String[] argv) throws Exception {

        for (int i = 0; i < 10; i++) {

            RedisSlaves re = new RedisSlaves();
            //  re.jedisHset("1234","4444","77777");
            String value = re.jedisHget("1234", "4444");
            System.out.println(value);
            System.out.println("22222fffffhahaha");

        }

    }

}
