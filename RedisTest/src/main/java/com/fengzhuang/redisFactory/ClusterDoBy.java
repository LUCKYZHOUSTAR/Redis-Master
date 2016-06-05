package com.fengzhuang.redisFactory;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.JedisCluster;

import com.fengzhuang.ConstantsRedis;


public class ClusterDoBy extends BaseRedis {

	JedisCluster cluster=null;
	public void init(){
		
		if(cluster == null) {
			setRedis_type(ConstantsRedis.REDIS_TYPE_CLUST);
			cluster = JedisClusterPoolUtils.getCluster();
		}
			
	}
		
	public void releaseJedis(){
	}
	
	@Override
	public Map<String, String> JedisHgetAll(String key) {
		init();
		return cluster.hgetAll(key);
	}

	@Override
	public long jedisHset(String key, String field, String value) {
		init();
		return cluster.hset(key, field, value);
	}

	@Override
	public String jedisHget(String key, String field) {
		init();
		return cluster.hget(key, field);
	}

	@Override
	public boolean jedisHexists(String key, String field) {
		init();
		return cluster.hexists(key, field);
	}

	@Override
	public long jedisHdel(String key, String field) {
		init();
		return cluster.hdel(key, field);
	}

	@Override
	public long jedisdel(String key) {
		init();
		return cluster.del(key);
	}

	@Override
	public String jedisget(String key) {
		init();
		return cluster.get(key);
	}

	@Override
	public int jedisset(String key, String value) {
		init();
		if(null == cluster.set(key, value) ) {
			return -1;		
		}
		return 0;
	}

	@Override
	public long jedisLlen(String key) {
		init();
		return cluster.llen(key);
	}

	@Override
	public String jedisLpop(String key) {
		init();
		return cluster.lpop(key);
	}

	@Override
	public long jedisRpush(String key, String value) {
		init();
		return cluster.rpush(key, value);
	}

	@Override
	public List jedisLrange(String key, long start, long end) {
		// TODO Auto-generated method stub
		return cluster.lrange(key, start, end);
	}

	@Override
	public long jedisLpush(String key, String... strs) {
		// TODO Auto-generated method stub
		return cluster.lpush(key, strs);
	}

	@Override
	public boolean jedisExists(String key) {
		// TODO Auto-generated method stub
		return cluster.exists(key);
	}


	
	
}
