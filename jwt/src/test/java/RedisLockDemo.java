import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 *
 * @author dlf
 * @date 2023/6/5 17:20
 */
@Component
public class RedisLockDemo {


    private final RedissonClient redissonClient;

    public RedisLockDemo(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public static void main(String[] args) throws InterruptedException {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + "120.53.125.32" + ":" + "6380").setDatabase(0).setPassword("dlf123");

        RedisLockDemo redisLockDemo = new RedisLockDemo(Redisson.create(config));

        redisLockDemo.reentrantLock();

        new Thread(redisLockDemo::reentrantLock_expire, "线程一").start();
        TimeUnit.SECONDS.sleep(30);
        new Thread(redisLockDemo::reentrantLock_expire, "线程二").start();
        TimeUnit.SECONDS.sleep(30);
    }

    /**
     * 加锁
     */
    public void reentrantLock() {
        RLock lock = redissonClient.getLock("reentrant-lock-no-expire");
        //没有获取到锁，返回
        //默认的看门狗模式
        lock.lock();
        long startTime = System.currentTimeMillis();

        try {
            //模拟业务超时
            System.err.println("模拟业务开始!!!");
            TimeUnit.SECONDS.sleep(60);
            System.err.println("模拟业务结束,耗时:" + (System.currentTimeMillis() - startTime) / 1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.err.println("手动释放锁,共耗时=" + (System.currentTimeMillis() - startTime) / 1000);
            lock.unlock();
        }

    }


    /**
     * 加锁，指定锁的时长是25s
     */
    public void reentrantLock_expire() {
        RLock lock = redissonClient.getLock("reentrant-lock-no-expire");
        long startTime = System.currentTimeMillis();
        lock.lock(25, TimeUnit.SECONDS);

        System.out.println(Thread.currentThread().getName() + "获取到锁");
        try {
            // 模拟业务操作耗时
            System.out.println(Thread.currentThread().getName() + "模拟业务开始");
            TimeUnit.SECONDS.sleep(60);
            System.out.println(Thread.currentThread().getName() + "模拟业务结束,共耗时=" + (System.currentTimeMillis() - startTime) / 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "手动释放锁,共耗时=" + (System.currentTimeMillis() - startTime) / 1000);
            lock.unlock();
        }
    }

    public void release() {
        this.redissonClient.shutdown();
    }
}
