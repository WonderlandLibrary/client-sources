package ninja.leaping.configurate.transformation;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.Map.Entry;
import net.s;
import ninja.leaping.configurate.ConfigurationNode;

class VersionedTransformation extends s {
   private final Object[] versionPath;
   private final SortedMap versionTransformations;

   VersionedTransformation(Object[] var1, SortedMap var2) {
      this.versionPath = var1;
      this.versionTransformations = var2;
   }

   public void apply(ConfigurationNode var1) {
      ConfigurationNode var3 = var1.getNode(this.versionPath);
      int var4 = var3.getInt(-1);
      s.b();
      Iterator var5 = this.versionTransformations.entrySet().iterator();
      if(var5.hasNext()) {
         Entry var6 = (Entry)var5.next();
         if(((Integer)var6.getKey()).intValue() <= var4) {
            ;
         }

         ((s)var6.getValue()).apply(var1);
         var4 = ((Integer)var6.getKey()).intValue();
      }

      var3.setValue(Integer.valueOf(var4));
   }
}
