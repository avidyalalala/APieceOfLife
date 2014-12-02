package com.github.avidyalalala.thread;

import java.util.concurrent.ConcurrentHashMap;
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
    public static final int threadCount=5;
    public static ConcurrentHashMap<Integer,int[]> splitPool=new ConcurrentHashMap<Integer, int[]>();
    public static String[] task={"零","一","二","三","四","五","六","七","八","九","十"};

    /**
     * long[] 是 [0, 100)
     * @param total
     * @return
     */
    public static void initSplitPool(int total){
        int unit= (int) Math.floor(total/threadCount);

        for(int i=0;i<threadCount-1;i++){
            splitPool.put(i,new int[]{i*unit,(i+1)*unit});
        }
        splitPool.put(threadCount-1,new int[]{(threadCount-1)*unit, total});
    }

    public static void handleUnit(int start, int end, int signal){
        System.out.println("thread "+signal+"is handling:");
        for(int index=start;index<end;index++){
            System.out.println(task[index]);
        }
    }
  /**
   * 换一下ThreadPool 里的数字 fixedPoolSize，与i的数字threadCount，看看会发生什么。
   * @param args
   */
    public static void main(String[] args) {

        initSplitPool(task.length);


        final AtomicInteger index=new AtomicInteger(0);
        final int fixedPoolSize=5;

        //初始化线程池，为线程安排工作，分配工作数量
        ExecutorService executors = Executors.newFixedThreadPool(fixedPoolSize);
        for(int i=0;i<threadCount;i++){
            final int finalI = i;
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        int id = index.getAndIncrement();
                        System.out.println("thread "+finalI+" getting the atomicInteger is "+id);

                        int[] range= splitPool.get(finalI);
                        handleUnit(range[0], range[1],finalI);

                    } catch (Exception e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            });
        }
        executors.shutdown();
    }
}
