package com.fengzhuang.redisFactory;

import com.fengzhuang.ConstantsRedis;


public class redisDoByPool extends BaseRedisPool {

	
	public void init(){
		
		if(JedisIsnull()) {
			setRedis_type(ConstantsRedis.REDIS_TYPE_COMMON);
			super.setJedis(JedisPoolUtils.getJedis());
		}
			
	}
		
	public void releaseJedis(){
		
		if( !JedisIsnull()) {
			 JedisPoolUtils.returnJedis(super.getJedis());
			 super.setJedis(null);
		}
			
	}




	
	
}
