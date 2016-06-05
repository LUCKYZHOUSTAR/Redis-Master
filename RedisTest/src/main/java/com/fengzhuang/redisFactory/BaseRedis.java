package com.fengzhuang.redisFactory;

import java.util.List;
import java.util.Map;

public abstract class BaseRedis {
   
	//1 common_redis ;2 master_redis; 3 slaves_redis; 
	//4 shared_redis ;5 shared_master_redis; 6 shared_slave_redis	
	private  int redis_type=1;
	
	public abstract  Map<String,String> JedisHgetAll(String key);
	
	public abstract long jedisHset( String key,String field, String Value);
	
	public abstract String jedisHget( String key,String field);
	
	public abstract boolean jedisHexists( String key,String field);
	
	public abstract long jedisHdel( String key,String field);
	
	public abstract long jedisdel( String key);
	public abstract String jedisget( String key);
	public abstract int jedisset( String key,String value);
	
	public abstract long jedisLlen( String key);
	public abstract String jedisLpop( String key);
	public abstract long jedisRpush( String key,String value);
	public abstract List jedisLrange(String key,long start,long end);
	public abstract void releaseJedis();
	public abstract long jedisLpush(String key,final String... strs);
	public abstract boolean jedisExists(String key);

	public int getRedis_type() {
		return redis_type;
	}

	public void setRedis_type(int redis_type) {
		this.redis_type = redis_type;
	}
	
}

