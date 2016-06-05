package com.fengzhuang;

public class RedisPoolException extends BusinessException {

    private static final long serialVersionUID = 7638092149459530444L;

    public RedisPoolException(String msg) {
        super(ConstantsRedis.JEDISPOOL_IS_NULL, "JEDISPOOL_IS_NULL [" + msg + "]");
    }

}
