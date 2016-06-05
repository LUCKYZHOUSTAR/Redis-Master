package com.fengzhuang.redisMasterSlaves;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.fengzhuang.redisConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisSlavesPool {

    private static JedisPool             slavepool = null;
    private static int                   slavesNum = 0;

    private static List<RedisSlavesInfo> Slavelist = new ArrayList<RedisSlavesInfo>();

    private static void initSalvesInfo() {
        if ((Slavelist.size() == slavesNum) && slavesNum > 0) {
            return;
        }
        Properties prty = redisConfig.getRedisConfig();
        slavesNum = 0;
        if (null != prty.getProperty("SLAVES_NUM")) {
            slavesNum = Integer.parseInt(prty.getProperty("SLAVES_NUM"));
        }
        for (int i = 1; i <= slavesNum; i++) {

            RedisSlavesInfo tmp = new RedisSlavesInfo();

            String maxactive = "POOL_SLAVE_REDIS_MAXACTIVE";
            String maxidle = "POOL_SLAVE_REDIS_MAXIDLE";
            String maxwait = "POOL_SLAVE_REDIS_MAXWAIT";
            String version = "POOL_SLAVE_REDIS_VERSION";
            String password = "POOL_SLAVE_REDIS_PASSWORD_" + i;
            String port = "POOL_SLAVE_REDIS_PORT_" + i;
            String host = "POOL_SLAVE_REDIS_HOST_" + i;

            tmp.setRedis_Host(prty.getProperty(host));
            tmp.setRedis_maxactive(prty.getProperty(maxactive));
            tmp.setRedis_maxidle(prty.getProperty(maxidle));
            tmp.setRedis_maxwait(prty.getProperty(maxwait));
            tmp.setRedis_password(prty.getProperty(password));
            tmp.setRedis_port(prty.getProperty(port));
            tmp.setRedis_version(prty.getProperty(version));
            tmp.setUsed(0);
            Slavelist.add(tmp);

        }

    }

    private static void CreateOnePool(int reflash) {

        initSalvesInfo();
        if (1 == reflash || slavepool == null) {

            int isok = 0;
            for (int i = 0; i < Slavelist.size(); i++) {

                RedisSlavesInfo tmp = Slavelist.get(i);

                if (1 == tmp.getUsed()) {
                    tmp.setUsed(-1);
                }
                if (-1 == tmp.getUsed()) {
                    continue;
                }

                tmp.setUsed(1);

                JedisPoolConfig config = new JedisPoolConfig();
                String MaxTotal = tmp.getRedis_maxactive();
                config.setMaxTotal(Integer.parseInt(MaxTotal));
                config.setMaxIdle(Integer.parseInt(tmp.getRedis_maxidle()));
                config.setMaxWaitMillis(Integer.parseInt(tmp.getRedis_maxwait()));
                config.setTestOnBorrow(true);

                try {
                    slavepool = new JedisPool(config, tmp.getRedis_Host(), Integer.parseInt(tmp
                        .getRedis_port()));

                    isok = 1;
                    break;
                } catch (Exception e1) {

                    tmp.setUsed(-1);

                }

            }
            if (1 == isok) {
                for (int i = 0; i < Slavelist.size(); i++) {

                    RedisSlavesInfo tmp = Slavelist.get(i);
                    if (-1 == tmp.getUsed()) {

                        System.out.println("Slave error :::: " + tmp.getRedis_Host() + ":"
                                           + tmp.getRedis_port());

                        tmp.setUsed(0);
                    }
                    if (1 == tmp.getUsed()) {
                        System.out.println("Slavelist:::: " + tmp.getRedis_Host() + ":"
                                           + tmp.getRedis_port());
                    }

                }

            }

        }

    }

    public static Jedis getJedis() {

        CreateOnePool(0);
        Jedis jds = null;

        try {
            jds = slavepool.getResource();

        } catch (Exception e) {
            //释放redis对象
            if (null != jds) {
                slavepool.returnBrokenResource(jds);
            }

            e.printStackTrace();
            CreateOnePool(1);
            try {
                jds = slavepool.getResource();
            } catch (Exception e1) {

                if (null != jds) {
                    slavepool.returnBrokenResource(jds);
                }
                e1.printStackTrace();
            }

        }

        return jds;
    }

    public static void returnJedis(Jedis pm) {

        slavepool.returnResource(pm);

    }

}
