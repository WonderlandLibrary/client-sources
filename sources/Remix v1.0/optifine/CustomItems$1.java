package optifine;

import java.util.*;

static final class CustomItems$1 implements Comparator {
    @Override
    public int compare(final Object o1, final Object o2) {
        final CustomItemProperties cip1 = (CustomItemProperties)o1;
        final CustomItemProperties cip2 = (CustomItemProperties)o2;
        return (cip1.layer != cip2.layer) ? (cip1.layer - cip2.layer) : ((cip1.weight != cip2.weight) ? (cip2.weight - cip1.weight) : (cip1.basePath.equals(cip2.basePath) ? cip1.name.compareTo(cip2.name) : cip1.basePath.compareTo(cip2.basePath)));
    }
}