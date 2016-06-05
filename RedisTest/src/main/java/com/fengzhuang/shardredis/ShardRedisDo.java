package com.fengzhuang.shardredis;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.ShardedJedis;

import com.fengzhuang.ConstantsRedis;
import com.fengzhuang.redisFactory.BaseRedis;

public class ShardRedisDo extends BaseRedis {

	private ShardedJedis shardredis_instance = null;

	public ShardRedisDo() {
		super.setRedis_type(ConstantsRedis.REDIS_TYPE_SHARED);
		init();

	}

	private void init() {

		if (null == shardredis_instance) {
			shardredis_instance = RedisShardPool.getShardedJedis();
		}
	}

	private ShardedJedis getJedis() {
		init();
		return shardredis_instance;
	}

	public Map<String, String> JedisHgetAll(String key) {

		return getJedis().hgetAll(key);

	}

	public long jedisHset(String key, String field, String Value) {

		return getJedis().hset(key, field, Value);
	}

	public String jedisHget(String key, String field) {

		if (getJedis().hexists(key, field)) {
			return getJedis().hget(key, field);
		}
		return null;
	}

	public boolean jedisHexists(String key, String field) {

		return getJedis().hexists(key, field);
	}

	public long jedisHdel(String key, String field) {

		return getJedis().hdel(key, field);
	}

	public long jedisdel(String key) {
		return getJedis().del(key);
	}

	@Override
	public String jedisget(String key) {
		return getJedis().get(key);
	}

	public void releaseJedis() {
		if (null != shardredis_instance) {
			RedisShardPool.returnShardedJedis(shardredis_instance);
			shardredis_instance = null;
		}

	}

	public static void main(String[] argv) throws Exception {

		ShardRedisDo re = new ShardRedisDo();

		re.jedisHset("aaa", "bbbb", "77777");
		String value = re.jedisHget("aaa", "bbbb");

		re.jedisHset("zzz", "cccc", "666666");
		String value2 = re.jedisHget("zzz", "cccc");

		System.out.println(value);
		System.out.println(value2);
	}

	@Override
	public int jedisset(String key, String value) {
		if (null == getJedis().set(key, value)) {
			return -1;
		}
		return 0;
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
