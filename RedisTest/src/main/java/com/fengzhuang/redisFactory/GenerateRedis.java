package com.fengzhuang.redisFactory;

import java.util.Properties;

import com.fengzhuang.ConstantsRedis;
import com.fengzhuang.redisConfig;
import com.fengzhuang.redisMasterSlaves.RedisMaster;
import com.fengzhuang.redisMasterSlaves.RedisSlaves;
import com.fengzhuang.shardredis.ShardRedisDo;

public class GenerateRedis {

	private static int redistype=-1;
	
	private static void LoadRedisFlag(){
		
		if(-1==redistype ){
			
			Properties prty= redisConfig.getRedisConfig();
			if(prty.containsKey("REDIS_TYPE") ){
				redistype= Integer.parseInt(prty.getProperty("REDIS_TYPE")) ;	
			}else
			{
				redistype=1;
			}
				
		}
	
	}
	
	
	public static BaseRedis GenRedis(int redis_type){
		
		BaseRedis ret =null;
		if(ConstantsRedis.REDIS_TYPE_COMMON==redis_type )
		{
			ret=  new redisDoByPool();
		}else if(ConstantsRedis.REDIS_TYPE_MASTER==redis_type ){
			
			ret=  new RedisMaster();
			
		}else if(ConstantsRedis.REDIS_TYPE_SLAVES==redis_type ){
			
			ret=  new RedisSlaves();
			
		}else if(ConstantsRedis.REDIS_TYPE_SHARED==redis_type ){
			
			ret=  new ShardRedisDo();
			
		}else if(ConstantsRedis.REDIS_TYPE_SHARED_MASTER==redis_type ){
			
			
		}else if(ConstantsRedis.REDIS_TYPE_SHARED_SLAVES==redis_type ){
			
		} else if (ConstantsRedis.REDIS_TYPE_CLUST == redis_type) {
			ret = new ClusterDoBy();
		}
		return ret;
	}
	
	
	public static BaseRedis GenRedis(){
		
		LoadRedisFlag();
		//1 common_redis ;2 master_redis; 3 slaves_redis; 
		//4 shared_redis ;5 shared_master_redis; 6 shared_slave_redis		
		return   GenRedis(redistype);
	}
	
}
