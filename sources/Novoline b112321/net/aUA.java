package net;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class aUA {
   public static WriteLock b(ReentrantReadWriteLock var0) {
      return var0.writeLock();
   }

   public static ReadLock a(ReentrantReadWriteLock var0) {
      return var0.readLock();
   }
}
