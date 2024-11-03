package com.viaversion.viaversion.api.scheduler;

import java.util.concurrent.TimeUnit;

public interface Scheduler {
   Task execute(Runnable var1);

   Task schedule(Runnable var1, long var2, TimeUnit var4);

   Task scheduleRepeating(Runnable var1, long var2, long var4, TimeUnit var6);

   void shutdown();
}
