package optifine;

import java.util.Comparator;

public class CustomItemsComparator implements Comparator {
  public CustomItemsComparator() {}
  
  public int compare(Object o1, Object o2) {
    CustomItemProperties p1 = (CustomItemProperties)o1;
    CustomItemProperties p2 = (CustomItemProperties)o2;
    return !Config.equals(basePath, basePath) ? basePath.compareTo(basePath) : weight != weight ? weight - weight : name.compareTo(name);
  }
}
