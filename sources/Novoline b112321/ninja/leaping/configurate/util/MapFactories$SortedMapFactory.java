package ninja.leaping.configurate.util;

import java.util.Comparator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import net.Qq;
import net.aIp;
import ninja.leaping.configurate.util.MapFactory;

final class MapFactories$SortedMapFactory implements MapFactory {
   private final Comparator comparator;

   private MapFactories$SortedMapFactory(Comparator var1) {
      this.comparator = var1;
   }

   public ConcurrentMap create() {
      return new ConcurrentSkipListMap(this.comparator);
   }

   public boolean equals(Object var1) {
      String[] var2 = aIp.c();
      return var1 instanceof MapFactories$SortedMapFactory && this.comparator.equals(((MapFactories$SortedMapFactory)var1).comparator);
   }

   public int hashCode() {
      return this.comparator.hashCode();
   }

   public String toString() {
      return "SortedMapFactory{comparator=" + this.comparator + '}';
   }

   MapFactories$SortedMapFactory(Comparator var1, Qq var2) {
      this(var1);
   }
}
