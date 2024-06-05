package net.optifine;

import java.util.Comparator;
import net.optifine.CustomItemProperties;
import net.optifine.MatchBlock;

final class CustomItems$1 implements Comparator {
   public int compare(Object var1, Object var2) {
      CustomItemProperties var4 = (CustomItemProperties)var1;
      MatchBlock.b();
      CustomItemProperties var5 = (CustomItemProperties)var2;
      return var4.layer != var5.layer?var4.layer - var5.layer:(var4.weight != var5.weight?var5.weight - var4.weight:(!var4.basePath.equals(var5.basePath)?var4.basePath.compareTo(var5.basePath):var4.name.compareTo(var5.name)));
   }
}
