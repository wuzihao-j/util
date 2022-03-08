package com.wzh.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wuzihao3
 * @date 2022/2/23
 */
public class ExecutorTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(200));

        submit(threadPoolExecutor, 1000);
        Thread.sleep(20);
        System.out.println("getActiveCount: " + threadPoolExecutor.getActiveCount());
        System.out.println("getTaskCount: " + threadPoolExecutor.getTaskCount());
        System.out.println("getPoolSize: " + threadPoolExecutor.getPoolSize());
        System.out.println("queue.size: " + threadPoolExecutor.getQueue().size());
        System.out.println("----------------------------------------------------------------");
        submit(threadPoolExecutor, 1000);
        Thread.sleep(20);
        System.out.println("getActiveCount: " + threadPoolExecutor.getActiveCount());
        System.out.println("getTaskCount: " + threadPoolExecutor.getTaskCount());
        System.out.println("getPoolSize: " + threadPoolExecutor.getPoolSize());
        System.out.println("queue.size: " + threadPoolExecutor.getQueue().size());
        System.out.println("----------------------------------------------------------------");
        submit(threadPoolExecutor, 1000);
        Thread.sleep(20);
        System.out.println("getActiveCount: " + threadPoolExecutor.getActiveCount());
        System.out.println("getTaskCount: " + threadPoolExecutor.getTaskCount());
        System.out.println("getPoolSize: " + threadPoolExecutor.getPoolSize());
        System.out.println("queue.size: " + threadPoolExecutor.getQueue().size());
        System.out.println("----------------------------------------------------------------");
        Thread.sleep(20);
        System.out.println("getActiveCount: " + threadPoolExecutor.getActiveCount());
        System.out.println("getTaskCount: " + threadPoolExecutor.getTaskCount());
        System.out.println("getPoolSize: " + threadPoolExecutor.getPoolSize());
        System.out.println("queue.size: " + threadPoolExecutor.getQueue().size());
        System.out.println("----------------------------------------------------------------");
        Thread.sleep(10000);
    }

    private static void submit(ThreadPoolExecutor threadPoolExecutor, int count) {
        for (int i = 0; i < count; i++) {
            try {
                threadPoolExecutor.submit(() -> {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
            }
        }

    }
}
