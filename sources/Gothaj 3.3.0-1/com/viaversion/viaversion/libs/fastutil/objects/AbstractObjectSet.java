package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Set;

public abstract class AbstractObjectSet<K> extends AbstractObjectCollection<K> implements Cloneable, ObjectSet<K> {
   protected AbstractObjectSet() {
   }

   @Override
   public abstract ObjectIterator<K> iterator();

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Set)) {
         return false;
      } else {
         Set<?> s = (Set<?>)o;
         return s.size() != this.size() ? false : this.containsAll(s);
      }
   }

   @Override
   public int hashCode() {
      int h = 0;
      int n = this.size();
      ObjectIterator<K> i = this.iterator();

      while (n-- != 0) {
         K k = i.next();
         h += k == null ? 0 : k.hashCode();
      }

      return h;
   }
}
