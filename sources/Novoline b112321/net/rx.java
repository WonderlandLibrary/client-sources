package net;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class rx {
   public static Callable a(Runnable var0) {
      return Executors.callable(var0);
   }

   public static ExecutorService a(ThreadFactory var0) {
      return Executors.newCachedThreadPool(var0);
   }

   public static ScheduledExecutorService a() {
      return Executors.newSingleThreadScheduledExecutor();
   }

   public static ExecutorService a(int var0, ThreadFactory var1) {
      return Executors.newFixedThreadPool(var0, var1);
   }
}
