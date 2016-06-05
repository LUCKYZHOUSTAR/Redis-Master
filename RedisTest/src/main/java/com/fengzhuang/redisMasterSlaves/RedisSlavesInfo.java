package com.fengzhuang.redisMasterSlaves;

public class RedisSlavesInfo {
	
	private String redis_port;
	private String redis_Host;
	private String redis_maxactive;
	private String redis_password;
	private String redis_maxidle;
	private String redis_maxwait;
	private int used=0;
	private String redis_version;
	
	public String getRedis_port() {
		return redis_port;
	}
	public void setRedis_port(String redis_port) {
		this.redis_port = redis_port;
	}
	public String getRedis_Host() {
		return redis_Host;
	}
	public void setRedis_Host(String redis_Host) {
		this.redis_Host = redis_Host;
	}
	public String getRedis_maxactive() {
		return redis_maxactive;
	}
	public void setRedis_maxactive(String redis_maxactive) {
		this.redis_maxactive = redis_maxactive;
	}
	public String getRedis_password() {
		return redis_password;
	}
	public void setRedis_password(String redis_password) {
		this.redis_password = redis_password;
	}
	public String getRedis_maxidle() {
		return redis_maxidle;
	}
	public void setRedis_maxidle(String redis_maxidel) {
		this.redis_maxidle = redis_maxidel;
	}
	public String getRedis_maxwait() {
		return redis_maxwait;
	}
	public void setRedis_maxwait(String redis_maxwait) {
		this.redis_maxwait = redis_maxwait;
	}
	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	public String getRedis_version() {
		return redis_version;
	}
	public void setRedis_version(String redis_version) {
		this.redis_version = redis_version;
	}
	
	
}
