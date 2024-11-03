package net.silentclient.client.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Multithreading {
	private static final AtomicInteger counter = new AtomicInteger(0);
	  
	  private static final ScheduledExecutorService RUNNABLE_POOL;
	  
	  public static ThreadPoolExecutor POOL;
	  
	  static {
	    RUNNABLE_POOL = Executors.newScheduledThreadPool(10, r -> new Thread(r, "Essential Thread " + counter.incrementAndGet()));
	    POOL = new ThreadPoolExecutor(10, 30, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r, "Thread " + counter.incrementAndGet()));
	  }
	  
	  public static ScheduledFuture<?> schedule(Runnable r, long delay, TimeUnit unit) {
	    return RUNNABLE_POOL.schedule(r, delay, unit);
	  }
	  
	  public static void runAsync(Runnable runnable) {
	    POOL.execute(runnable);
	  }
}
