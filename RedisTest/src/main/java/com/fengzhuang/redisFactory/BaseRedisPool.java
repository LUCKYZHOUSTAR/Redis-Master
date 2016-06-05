package com.fengzhuang.redisFactory;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public abstract class BaseRedisPool extends BaseRedis {

	
	private  Jedis jedis=null;
	public BaseRedisPool() {
		init();
	}
	
	public abstract  void init();
	
	public boolean JedisIsnull() {
		
		return jedis ==null;
	}
 
	public Jedis getJedis() {
		
		if(JedisIsnull()){
			init();
		}
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	
	@Override
	public Map<String, String> JedisHgetAll(String key) {
		return getJedis().hgetAll(key);
	}

	@Override
	public long jedisHdel(String key, String field) {
		return getJedis().hdel(key,field);	
	}

	@Override
	public boolean jedisHexists(String key, String field) {
		return getJedis().hexists(key,field);	
	}

	@Override
	public String jedisHget(String key, String field) {
		if( getJedis().hexists(key,field ) ) {
			return getJedis().hget(key,field);		
		}
		return null;
	}
	
	@Override
	public int jedisset(String key, String value) {
		if(null == getJedis().set(key, value) ) {
			return -1;		
		}
		return 0;
	}

	@Override
	public String jedisget(String key) {
		return getJedis().get(key);
	}


	@Override
	public long jedisHset(String key, String field, String Value) {
		// TODO Auto-generated method stub
		return getJedis().hset(key,field,Value);	
	}

	@Override
	public long jedisdel(String key) {
		// TODO Auto-generated method stub
		return getJedis().del(key);	
	}

	@Override
	public  void releaseJedis() {
		JedisPoolUtils.returnJedis(getJedis());
	}

	@Override
	public long jedisLlen(String key) {
		return getJedis().llen(key);
	}

	@Override
	public String jedisLpop(String key) {
		return getJedis().lpop(key);
	}

	@Override
	public long jedisRpush(String key, String value) {
		return getJedis().rpush(key, value);
	}

	@Override
	public List jedisLrange(String key, long start, long end) {
		// TODO Auto-generated method stub
		return getJedis().lrange(key, start, end);
	}

	@Override
	public long jedisLpush(String key, String... strs) {
		// TODO Auto-generated method stub
		return getJedis().lpush(key, strs);
	}

	@Override
	public boolean jedisExists(String key) {
		// TODO Auto-generated method stub
		return getJedis().exists(key);
	}
	
	
}
