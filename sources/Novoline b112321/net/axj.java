package net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class axj {
   public static Future a(ExecutorService var0, Runnable var1) {
      return var0.submit(var1);
   }
}
