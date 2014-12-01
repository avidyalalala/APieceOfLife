package com.github.avidyalalala.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-12-1
 * Time: 下午5:28
 * To change this template use File | Settings | File Templates.
 */
public class ExcutorPool {
    /**
     * 换一下ThreadPool 里的数字 fixedPoolSize，与i的数字threadCount，看看会发生什么。
     * @param args
     */
    public static void main(String[] args) {
        final AtomicInteger index=new AtomicInteger(0);
        final int fixedPoolSize=5;
        final int threadCount=10;

        //初始化线程池，为线程安排工作，分配工作数量
        ExecutorService executors = Executors.newFixedThreadPool(fixedPoolSize);
        for(int i=0;i<threadCount;i++){
            final int finalI = i;
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("finalI"+finalI);
                        int id = index.getAndIncrement();
                        System.out.println(id);
                    } catch (Exception e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            });
        }
    }
}
