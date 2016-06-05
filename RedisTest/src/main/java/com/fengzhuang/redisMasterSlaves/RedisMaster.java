package com.fengzhuang.redisMasterSlaves;
import com.fengzhuang.ConstantsRedis;
import com.fengzhuang.redisFactory.BaseRedisPool;


public class RedisMaster extends BaseRedisPool {
	
	
	public void init(){
		
		if(JedisIsnull()) {
			setRedis_type(ConstantsRedis.REDIS_TYPE_MASTER);
			super.setJedis(RedisMasterPool.getJedis());
			
		}
			
	}
	
	@Override
	public void releaseJedis() {
		// TODO Auto-generated method stub
		if(!JedisIsnull()) {
			RedisMasterPool.returnJedis(super.getJedis());
			super.setJedis(null);
		}
	}



}
