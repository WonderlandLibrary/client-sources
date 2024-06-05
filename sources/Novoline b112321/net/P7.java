package net;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

enum P7 {
   public ConcurrentMap a() {
      return new ConcurrentHashMap();
   }
}
