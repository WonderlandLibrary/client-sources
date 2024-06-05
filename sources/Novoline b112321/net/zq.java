package net;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class zq {
   public static Lock a(ReadWriteLock var0) {
      return var0.readLock();
   }

   public static Lock b(ReadWriteLock var0) {
      return var0.writeLock();
   }
}
