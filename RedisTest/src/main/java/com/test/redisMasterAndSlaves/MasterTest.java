/**
 * 
 */
package com.test.redisMasterAndSlaves;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** 
* @ClassName: MasterTest 
* @Description: 
* @author LUCKY
* @date 2016年6月5日 下午3:40:28 
*  
*/
public class MasterTest {
    private static JedisPool masterPool = null;

    private static void CreateMasterPool() {
        if (masterPool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            /*
             * #redis服务器ip #   
            redis.ip=172.30.5.117 
            
            #redis服务器端口号# 
            redis.port=6379 
            
            ###jedis##pool##config### 
            #jedis的最大分配对象# 
            jedis.pool.maxActive=1024 
            
            #jedis最大保存idel状态对象数 # 
            jedis.pool.maxIdle=200 
            
            #jedis池没有对象返回时，最大等待时间 # 
            jedis.pool.maxWait=1000 
            
            #jedis调用borrowObject方法时，是否进行有效检查# 
            jedis.pool.testOnBorrow=true 
            
            #jedis调用returnObject方法时，是否进行有效检查 # 
            jedis.pool.testOnReturn=true
             */
            try {
                config.setMaxTotal(1024);
                config.setMaxIdle(200);
                config.setMaxWaitMillis(1000);
                config.setTestOnBorrow(true);
                masterPool = new JedisPool(config, "100.66.162.82", 8888);
            } catch (Exception e) {

            }
        }
    }

    public static Jedis getJedis() {
        CreateMasterPool();
        Jedis jedis = null;
        try {
            jedis = masterPool.getResource();
        } catch (Exception e) {
            //释放redis对象
            /*
             * 在instance出错时，必须调用returnBrokenResource返还给pool，否则下次通过getResource得到的instance的缓冲区可能还存在数据，出现问题
             */
            if (jedis == null) {
                masterPool.returnBrokenResource(jedis);
            }
        }

        return jedis;
    }

    public static void returnJedis(Jedis jedis) {
        if (jedis != null) {
            masterPool.returnResource(jedis);
        }
    }

    public static void main(String[] args) {
        Jedis jedis = getJedis();
        System.out.println(jedis.get("zhou"));
    }
}
