package com.vodafone.util;

import java.time.Duration;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public enum RedisUtil {

	INSTANCE;
	
    private final JedisPool pool;
    private static final int EXPIRY_TIME_IN_MILLISECOND = 1*60*1000; // 1 minute


    RedisUtil() {
        pool = new JedisPool(buildPoolConfig(), "localhost");
    }
    
    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    public void sadd(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.sadd(key, value);
            jedis.expire(key, EXPIRY_TIME_IN_MILLISECOND);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void srem(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.srem(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    
    public String get(String key) {
    	Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.get(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    
    public void set(String key, String value) {
    	Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.set(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}
