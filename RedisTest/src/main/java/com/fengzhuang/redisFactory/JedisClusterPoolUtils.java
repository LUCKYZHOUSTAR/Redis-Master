package com.fengzhuang.redisFactory;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import com.fengzhuang.ConstantsRedis;
import com.fengzhuang.RedisPoolException;
import com.fengzhuang.redisConfig;

public class JedisClusterPoolUtils {

	private static JedisCluster cluster = null;

	private static void createJedisCluster() {

		if (cluster == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			try {
				Properties prty = redisConfig.getRedisConfig();

				config.setMaxTotal(Integer.parseInt(prty
						.getProperty("POOL_REDIS_MAXACTIVE")));
				config.setMaxIdle(Integer.parseInt(prty
						.getProperty("POOL_REDIS_MAXIDLE")));
				config.setMaxWaitMillis(Integer.parseInt(prty
						.getProperty("POOL_REDIS_MAXWAIT")));
				//config.setTestOnBorrow(true);

				// init REDIS_KEY_EXPIRE_TIME
				if (null != prty.getProperty("REDIS_KEY_EXPIRE_TIME")) {
					ConstantsRedis.REDIS_KEY_EXPIRE_TIME = Integer
							.parseInt(prty.getProperty("REDIS_KEY_EXPIRE_TIME"));
				}

				Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
				jedisClusterNodes.add(new HostAndPort(prty
						.getProperty("POOL_REDIS_HOST"), Integer.parseInt(prty
						.getProperty("POOL_REDIS_PORT"))));
				
				//jedisClusterNodes.add(new HostAndPort("10.1.39.57", 8000));
				//jedisClusterNodes.add(new HostAndPort("10.1.39.57", 8001));
				//jedisClusterNodes.add(new HostAndPort("10.1.39.57", 8005));
				
				System.err.println(prty.getProperty("POOL_REDIS_HOST"));
				System.err.println(prty.getProperty("POOL_REDIS_PORT"));

				cluster = new JedisCluster(jedisClusterNodes, 5000,10);
			} catch (Exception e1) {
				System.err.println("ddddddddddddd");
				e1.printStackTrace();
			}

		}

	}

	public static JedisCluster getCluster() {
		createJedisCluster();
		if (cluster == null)
			throw new RedisPoolException("jedis is null");
		return cluster;
	}

}
