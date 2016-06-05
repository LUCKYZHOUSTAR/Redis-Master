package com.fengzhuang.shardredis;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.fengzhuang.redisConfig;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

public class RedisShardPool {

	private static ShardedJedisPool shardpool=null;
	private static void createShardedJedis() {
		
		if(null == shardpool){
			 JedisPoolConfig config = new JedisPoolConfig();
			 
			   try {
				   Properties prty= redisConfig.getRedisConfig();
				   config.setMaxTotal(Integer.parseInt(prty.getProperty("POOL_REDIS_MAXACTIVE")));
				   config.setMaxIdle( Integer.parseInt(prty.getProperty("POOL_REDIS_MAXIDLE")  ));
				   config.setMaxWaitMillis( Integer.parseInt(prty.getProperty("POOL_REDIS_MAXWAIT")  ));
				   config.setTestOnBorrow(true);
				   config.setTestWhileIdle(false);
				   
				   List<JedisShardInfo> jdsInfoList =new ArrayList<JedisShardInfo>();
				   String num_str= prty.getProperty("SHARDPOOL_REDIS_NUM");  
				   int num=Integer.parseInt(num_str);
				  
				   for (int i =0;i<num;i++){
					   int tmp =i+1;
					   String hostkey="SHARDPOOL_REDIS_HOST_"+tmp;  
					   String portkey="SHARDPOOL_REDIS_PORT_"+tmp; 	
					   
					   if(prty.containsKey(hostkey) &&  prty.containsKey(portkey) ){
						   String host=prty.getProperty(hostkey);  
						   String port=prty.getProperty(portkey);  					   
						   JedisShardInfo info = new JedisShardInfo(host,Integer.parseInt(port));						   
						   //info.setPassword("redis");
						   jdsInfoList.add(info);
					   }
						   
				   }
				   shardpool =new ShardedJedisPool(config, jdsInfoList, Hashing.MURMUR_HASH,
						   Sharded.DEFAULT_KEY_TAG_PATTERN);
			 	
				   }catch ( Exception e1){
					   e1.printStackTrace();  
				   }			 		 
			
		}
		
	}
	
public static ShardedJedis getShardedJedis(){
		
		createShardedJedis();
		ShardedJedis jds  = null;

		   try {			   	
			   jds = shardpool.getResource();		   
	        } catch (Exception e) {    
	            e.printStackTrace();
	        }
	
		return jds ;
	}
	
	public static void  returnShardedJedis(ShardedJedis pm ){
		
		shardpool.returnResource(pm);
	}
	
	
	
}
