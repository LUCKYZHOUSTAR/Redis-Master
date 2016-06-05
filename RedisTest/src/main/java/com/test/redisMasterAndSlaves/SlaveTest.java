/**
 * 
 */
package com.test.redisMasterAndSlaves;

import java.util.ArrayList;
import java.util.List;

import com.fengzhuang.redisMasterSlaves.RedisSlavesInfo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** 
* @ClassName: SlaveTest 
* @Description: 
* @author LUCKY
* @date 2016年6月5日 下午3:52:45 
*  
*/
public class SlaveTest {

    private static JedisPool             slavePool = null;
    private static int                   slavesNum = 0;
    private static List<RedisSlavesInfo> Slavelist = new ArrayList<RedisSlavesInfo>();

    private static void initSlavesInfo() {
        RedisSlavesInfo tmp = new RedisSlavesInfo();
        tmp.setRedis_Host("100.66.162.82");
        tmp.setRedis_maxactive("1024");
        tmp.setRedis_maxidle("200");
        tmp.setRedis_maxwait("1000");
        tmp.setRedis_password("");
        tmp.setRedis_port("8887");
        tmp.setRedis_version("");
        tmp.setUsed(0);
        Slavelist.add(tmp);
    }

    private static void CreateOnePool(int reflash) {
        initSlavesInfo();
        JedisPoolConfig config = new JedisPoolConfig();
        RedisSlavesInfo tmp = Slavelist.get(0);
        String MaxTotal = tmp.getRedis_maxactive();
        config.setMaxTotal(Integer.parseInt(MaxTotal));
        config.setMaxIdle(Integer.parseInt(tmp.getRedis_maxidle()));
        config.setMaxWaitMillis(Integer.parseInt(tmp.getRedis_maxwait()));
        config.setTestOnBorrow(true);
        slavePool = new JedisPool(config, tmp.getRedis_Host(),
            Integer.parseInt(tmp.getRedis_port()));
    }

    public static Jedis getJedis() {
        CreateOnePool(1);
        Jedis jedis = null;
        try {
            jedis = slavePool.getResource();
        } catch (Exception e) {
            //释放redis对象
            if (null != jedis) {
                slavePool.returnBrokenResource(jedis);
            }
        }

        return jedis;
    }

    public static void returnJedis(Jedis pm) {
        slavePool.returnResource(pm);

    }

    public static void main(String[] args) {
        Jedis jedis = getJedis();
        System.out.println(jedis.get("zhou"));
    }
}
