package com.redis.utils;

import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * Redis实现分布式锁
 */
public class MayiktRedisLock {
    //SetNx key返回1
    private static final int lockSuccess = 1;

    /**
     * 获取锁
     *
     * @param lockKey      在Redis中创建的key值
     * @param notLockTimie 尝试获取锁超时时间
     * @return 返回lock成功值
     */
    public String getLock(String lockKey, int notLockTimie, int timeOut) {
        //获取Redis连接
        Jedis jedis = RedisUtil.getJedis();
        // 计算我们尝试获取锁超时时间
        Long endTime = System.currentTimeMillis() + notLockTimie;
        //  当前系统时间小于endTime说明获取锁没有超时 继续循环 否则情况下推出循环
        while (System.currentTimeMillis() < endTime) {
            String lockValue = UUID.randomUUID().toString();
            // 当多个不同的jvm同时创建一个相同的rediskey 只要谁能够创建成功谁就能够获取锁
            if (jedis.setnx(lockKey, lockValue) == lockSuccess) {
                // 加上有效期
                jedis.expire(lockKey, timeOut / 1000);
                return lockValue;
                // 退出循环
            }
            // 否则情况下 继续循环
        }
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 释放锁 其实就是将该key删除
     *
     * @return
     */
    public boolean unLock(String lockKey, String lockValue) {
        //获取Redis连接
        Jedis jedis = RedisUtil.getJedis();
        try {
            // 判断获取锁的时候保证自己删除自己
            if (lockValue.equals(jedis.get(lockKey))) {
                return jedis.del(lockKey) > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }
}
