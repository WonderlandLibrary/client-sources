package net;

import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

public class W5 {
   public static void b(ReadLock var0) {
      var0.lock();
   }

   public static void a(ReadLock var0) {
      var0.unlock();
   }
}
