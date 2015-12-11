package com.github.avidyalalala.thread;

import com.github.avidyalalala.cglib.proxy.SayHello;
import com.github.avidyalalala.thread.pojo.InvokeResult;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by houlina02 on 15/12/11.
 */
public class ParallelInvoke {

    CountDownLatch latch;
    ExecutorService executors;
    List<String> urls;

    public ParallelInvoke(List<String> urls) {
        this.urls=urls;
        int threadSize=urls.size();
        this.latch = new CountDownLatch(threadSize);
        this.executors = Executors.newFixedThreadPool(threadSize);
    }

    public InvokeResult doInvoke() throws InterruptedException, MalformedURLException {
        final InvokeResult aic=new InvokeResult();

        for(int i =0;i<this.urls.size();i++){
            final URL url=new URL(urls.get(i));
            executors.submit(new InvokeThread(i, url, aic));
        }

        latch.await();
        executors.shutdown();

        System.out.println("out of pool: " + latch.getCount());
        System.out.println(ToStringBuilder.reflectionToString(aic));
        return aic;
    }

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        String[] urls={"http://www.meituan.com?i=0","http://i.meituan.com/auto.html?i=1","http://hahaha.com:9999/haha.do?i=2","https://stackoverflow.com?i=3","ftp://hahaha.com?i=4"};
        ParallelInvoke pi = new ParallelInvoke(Arrays.asList(urls));
        pi.doInvoke();
        return;
    }

    class InvokeThread implements Runnable{
        URL url;
        int index;
        InvokeResult ir;

        public InvokeThread(int index,URL url, InvokeResult ir) {
            if(url.toString().trim().isEmpty()){
                System.out.println("url "+index+" is empty.!!!! you are not a good person.");
                return;
            }

            this.index=index;
            this.url=url;
            this.ir=ir;
        }

        @Override
        public void run() {

            //for no reason, just assume the invoking process will last 1 seconds
            try {
                System.out.println(latch.getCount() + " index:" + index);

                Thread.sleep(3000);
                latch.countDown();
                int order=(int)latch.getCount();
                System.out.println(order + " index:" + index);

                switch (order){
                    case 0:
                        ir.setHost(url.getHost());
                        break;
                    case 1:
                        ir.setPath(url.getPath());
                        break;
                    case 2:
                        ir.setPort(String.valueOf(url.getPort()));
                        break;
                    case 3:
                        ir.setProtocal(url.getProtocol());
                        break;
                    case 4:
                        ir.setQuery(url.getQuery());
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
