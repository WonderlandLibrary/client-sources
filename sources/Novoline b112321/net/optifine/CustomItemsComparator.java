package net.optifine;

import java.util.Comparator;
import net.optifine.Config;
import net.optifine.CustomItemProperties;
import net.optifine.MatchBlock;

public class CustomItemsComparator implements Comparator {
   public int compare(Object var1, Object var2) {
      MatchBlock.b();
      CustomItemProperties var4 = (CustomItemProperties)var1;
      CustomItemProperties var5 = (CustomItemProperties)var2;
      return var4.weight != var5.weight?var5.weight - var4.weight:(!Config.equals(var4.basePath, var5.basePath)?var4.basePath.compareTo(var5.basePath):var4.name.compareTo(var5.name));
   }
}
