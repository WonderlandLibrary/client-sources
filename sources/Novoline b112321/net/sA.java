package net;

import com.google.common.util.concurrent.ListenableFutureTask;
import java.util.concurrent.Callable;

public class sA {
   public static ListenableFutureTask a(Callable var0) {
      return ListenableFutureTask.create(var0);
   }

   public static void a(ListenableFutureTask var0) {
      var0.run();
   }

   public static ListenableFutureTask a(Runnable var0, Object var1) {
      return ListenableFutureTask.create(var0, var1);
   }
}
