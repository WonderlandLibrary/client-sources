package ninja.leaping.configurate.transformation;

import java.util.Comparator;
import net.s;

class NodePathComparator implements Comparator {
   public int compare(Object[] var1, Object[] var2) {
      s.b();
      int var4 = 0;
      if(var4 < Math.min(var1.length, var2.length)) {
         if(var1[var4] != s.c && var2[var4] != s.c) {
            if(var1[var4] instanceof Comparable) {
               int var5 = ((Comparable)var1[var4]).compareTo(var2[var4]);
               switch(var5) {
               case 0:
               default:
                  return var5;
               }
            }

            return var1[var4].equals(var2[var4])?0:Integer.compare(var1[var4].hashCode(), var2[var4].hashCode());
         }

         if(var1[var4] != s.c || var2[var4] != s.c) {
            return var1[var4] == s.c?1:-1;
         }

         ++var4;
      }

      return Integer.compare(var2.length, var1.length);
   }
}
