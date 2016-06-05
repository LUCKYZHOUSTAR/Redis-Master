package com.fengzhuang;

public class ConstantsRedis {
	//1 common_redis ;2 master_redis; 3 slaves_redis; 
	//4 shared_redis ;5 shared_master_redis; 6 shared_slave_redis	
	
	public static final int REDIS_TYPE_COMMON = 1; 
	public static final int REDIS_TYPE_MASTER = 2;
	public static final int REDIS_TYPE_SLAVES = 3;
	public static final int REDIS_TYPE_SHARED = 4;
	public static final int REDIS_TYPE_SHARED_MASTER = 5;
	public static final int REDIS_TYPE_SHARED_SLAVES = 6;
	public static  int REDIS_KEY_EXPIRE_TIME = 60*60;  // 秒 , 默认一个小时
	
	public static final int REDIS_TYPE_CLUST = 7;
	
	public static final String JEDISPOOL_IS_NULL="JEDISPOOL_IS_NULL";
}
