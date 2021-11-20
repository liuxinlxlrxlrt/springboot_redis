package com.redis.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    //protected static Logger logger = Logger.getLogger(RedisUtil.class);

    private static String IP = "192.168.1.102";

    //Redis的端口号
    private static int PORT = 6379;

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 100;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 20;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 3000;

    private static int TIMEOUT = 3000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    //在return给pool时，是否提前进行validate操作；
    private static boolean TEST_ON_RETURN = true;

    private static JedisPool jedisPool = null;

    /**
     * redis过期时间,以秒为单位
     */
    public final static int EXRP_HOUR = 60 * 60; //一小时
    public final static int EXRP_DAY = 60 * 60 * 24; //一天
    public final static int EXRP_MONTH = 60 * 60 * 24 * 30; //一个月

    /**
     * 初始化Redis连接池
     */
    private static void initialPool() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            //jedis的最大活跃连接数
            config.setMaxTotal(MAX_ACTIVE);
            //jedis最大空闲连接数
            config.setMaxIdle(MAX_IDLE);
            //jedis池没有连接对象返回时，等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。
            //如果超过等待时间，则直接抛出JedisConnectionException
            config.setMaxWaitMillis(MAX_WAIT);
            //#从池中获取连接的时候，是否进行有效检查
            config.setTestOnBorrow(TEST_ON_BORROW);
            /**
             * #归还连接的时候，是否进行有效检查
             * jedis.pool.testOnReturn=true
             */
            //根据配置实例化jedis池
            jedisPool = new JedisPool(config, IP, PORT, TIMEOUT, "123456");
        } catch (Exception e) {
            //logger.error("First create JedisPool error : "+e);
            e.getMessage();
        }
    }


    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
        if (jedisPool == null) {
            initialPool();
        }
    }


    /**
     * 同步获取Jedis实例
     *
     * @return Jedis
     */
    public synchronized static Jedis getJedis() {
        if (jedisPool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
            e.getMessage();
            // logger.error("Get jedis error : "+e);
        }
        return jedis;
    }


    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null && jedisPool != null) {
            jedisPool.returnResource(jedis);
        }
    }

    public static Long add(String key, String... members) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.sadd(key, members);
        } catch (Exception e) {
            //logger.error("sadd  error : "+e);
            e.getMessage();
        }
        return res;
    }
}
