package ninja.leaping.configurate.util;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

enum MapFactories$DefaultFactory$2 {
   public ConcurrentMap create() {
      return new ConcurrentSkipListMap();
   }
}
