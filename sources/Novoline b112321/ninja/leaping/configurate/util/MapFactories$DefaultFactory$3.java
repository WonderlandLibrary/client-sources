package ninja.leaping.configurate.util;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentMap;
import ninja.leaping.configurate.util.MapFactories$SynchronizedWrapper;

enum MapFactories$DefaultFactory$3 {
   public ConcurrentMap create() {
      return new MapFactories$SynchronizedWrapper(new LinkedHashMap());
   }
}
