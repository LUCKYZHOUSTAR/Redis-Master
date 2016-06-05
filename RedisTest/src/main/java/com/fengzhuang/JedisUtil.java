package com.fengzhuang;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** 
* @ClassName: JedisUtil 
* @Description: 
* @author LUCKY
* @date 2016年6月5日 下午2:58:34 
*  
*/
public class JedisUtil {
    protected Logger                      log            = LoggerFactory.getLogger(JedisUtil.class);

    private static int                    MAX_ACTIVE     = 1024;

    private static int                    MAX_IDLE       = 10;

    private static int                    MIN_IDLE       = 1;

    private static int                    MAX_WAIT       = 10000;

    private static int                    TIME_OUT       = 3000;

    private static int                    RETRY_NUM      = 3;

    private static boolean                TEST_ON_BORROW = true;

    private static boolean                TEST_ON_RETURN = true;

    private static Map<String, JedisPool> maps           = new ConcurrentHashMap();

    private static JedisPool getPool(String ip, String port) {
        String key = ip + ":" + port;
        JedisPool pool = null;
        if (!maps.containsKey(key)) {
            JedisPoolConfig config = new JedisPoolConfig();
//            config.setMaxActive(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMinIdle(MIN_IDLE);
//            config.setMaxWait(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            config.setTestOnReturn(TEST_ON_RETURN);
            try {
                pool = new JedisPool(config, ip, Integer.parseInt(port), TIME_OUT);
                maps.put(key, pool);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            pool = (JedisPool) maps.get(key);
        }
        return pool;
    }

    public Jedis getJedis(String ip, String port) {
        Jedis jedis = null;
        int count = 0;
        do {
            try {
                jedis = (Jedis) getPool(ip, port).getResource();
            } catch (Exception e) {
                this.log.error("get redis master1 failed!", e);

                getPool(ip, port).returnBrokenResource(jedis);
            }
            count++;
        } while ((jedis == null) && (count < RETRY_NUM));
        return jedis;
    }

    public void closeJedis(Jedis jedis, String ip, String port) {
        if (jedis != null)
            getPool(ip, port).returnResource(jedis);
    }
}