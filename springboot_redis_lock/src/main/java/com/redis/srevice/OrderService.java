package com.redis.srevice;

import com.redis.utils.MayiktRedisLock;
import org.apache.commons.lang3.StringUtils;
/**
 * Redis实现分布式锁
 */
public class OrderService {

    private static final String LOCKKEY="mayikt_lockkey";

    public static void servie(){
        //1.获取锁
        MayiktRedisLock mayiktRedisLock = new MayiktRedisLock();
        String lockvalue=mayiktRedisLock.getLock(LOCKKEY,5000,5000);
        //如果获取锁失败了
        if (StringUtils.isEmpty(lockvalue)){
            System.out.println(Thread.currentThread().getName()+"获取锁失败了");
            return;
        }
        //2、执行业务逻辑
        System.out.println(Thread.currentThread().getName()+"获取锁成功，lockvalue："+lockvalue);
        //3.释放锁
        mayiktRedisLock.unLock(LOCKKEY,lockvalue);
        /**
         * 如果哪一天释放锁出了bug，程序走到获取锁，执行逻辑，没有走到释放锁，第二次执行时，获取锁失败了
         * 原因：锁没有被释放，key一直存在，再去获取锁的时候超时时间之内创建key失败，死锁了
         * 如何避免死锁：对key设置一个有效期
         */
    }

    public static void main(String[] args) {
        servie();
    }
}
/**仅限单机版
 * 1、尝试获取锁为什么没有次数限制？
 * 因为次数不好通过认为控制，1s内可以调用redis几百几千次
 * 2、业务逻辑如果在5s内没有执行完毕呢？
 * 答、（1）、锁的超时时间是根据业务场景预估的
 *    （2）、可以自己延迟锁的时间
 *    （3）、在提交事务的时候检查是否锁超时，如果已经超时则回滚（手动弄回滚），否则提交（业务逻辑未执行完毕，锁超时了，所有的业务逻辑应该整个回滚）
 *      （如何检查？ 开始和结束做个计算）
 */
