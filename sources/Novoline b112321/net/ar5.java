package net;

import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class ar5 {
   public static void a(WriteLock var0) {
      var0.lock();
   }

   public static void b(WriteLock var0) {
      var0.unlock();
   }
}
