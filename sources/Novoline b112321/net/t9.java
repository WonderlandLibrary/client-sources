package net;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Future;

public class t9 {
   public static Object a(Future var0) {
      return Futures.getUnchecked(var0);
   }

   public static ListenableFuture a(Object var0) {
      return Futures.immediateFuture(var0);
   }

   public static CheckedFuture a(Exception var0) {
      return Futures.immediateFailedCheckedFuture(var0);
   }

   public static ListenableFuture a(Iterable var0) {
      return Futures.allAsList(var0);
   }

   public static void a(ListenableFuture var0, FutureCallback var1) {
      Futures.addCallback(var0, var1);
   }
}
