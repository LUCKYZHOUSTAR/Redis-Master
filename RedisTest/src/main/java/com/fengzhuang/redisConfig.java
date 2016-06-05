package com.fengzhuang;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;

import com.fengzhuang.redisFactory.BaseRedis;
import com.fengzhuang.redisFactory.GenerateRedis;



public class redisConfig {
	
	public static Properties getRedisConfig() {
		
		 Properties prty = new Properties();  
		 try { 
				String batisFileName="redislist.properties";
				Reader reader = Resources.getResourceAsReader(batisFileName);
			 	prty.load(reader);  
		    } 
		 catch (IOException e1) {    
		      e1.printStackTrace();
		      System.out.println("error dbCfg.properties");
		      return null;
		    }    
		 
		 
		 return prty;
		
	}
	public static void main(String[] argv) throws Exception {
		
		BaseRedis myredis = GenerateRedis.GenRedis();
		myredis.jedisset("test", "2");
		System.out.println(myredis.jedisget("test1"));
		// 关闭连接
		myredis.releaseJedis();
		
	}
	
	
	
}
