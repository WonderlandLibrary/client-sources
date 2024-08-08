package org.spongepowered.asm.lib.tree.analysis;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

class SmallSet extends AbstractSet implements Iterator {
   Object e1;
   Object e2;

   static final Set emptySet() {
      return new SmallSet((Object)null, (Object)null);
   }

   SmallSet(Object var1, Object var2) {
      this.e1 = var1;
      this.e2 = var2;
   }

   public Iterator iterator() {
      return new SmallSet(this.e1, this.e2);
   }

   public int size() {
      return this.e1 == null ? 0 : (this.e2 == null ? 1 : 2);
   }

   public boolean hasNext() {
      return this.e1 != null;
   }

   public Object next() {
      if (this.e1 == null) {
         throw new NoSuchElementException();
      } else {
         Object var1 = this.e1;
         this.e1 = this.e2;
         this.e2 = null;
         return var1;
      }
   }

   public void remove() {
   }

   Set union(SmallSet var1) {
      if (var1.e1 == this.e1 && var1.e2 == this.e2 || var1.e1 == this.e2 && var1.e2 == this.e1) {
         return this;
      } else if (var1.e1 == null) {
         return this;
      } else if (this.e1 == null) {
         return var1;
      } else {
         if (var1.e2 == null) {
            if (this.e2 == null) {
               return new SmallSet(this.e1, var1.e1);
            }

            if (var1.e1 == this.e1 || var1.e1 == this.e2) {
               return this;
            }
         }

         if (this.e2 != null || this.e1 != var1.e1 && this.e1 != var1.e2) {
            HashSet var2 = new HashSet(4);
            var2.add(this.e1);
            if (this.e2 != null) {
               var2.add(this.e2);
            }

            var2.add(var1.e1);
            if (var1.e2 != null) {
               var2.add(var1.e2);
            }

            return var2;
         } else {
            return var1;
         }
      }
   }
}
