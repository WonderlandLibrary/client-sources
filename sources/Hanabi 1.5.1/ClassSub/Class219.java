package ClassSub;

import java.util.*;

public class Class219 implements Comparator<Class93>
{
    final Class171 this$0;
    
    
    public Class219(final Class171 this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public int compare(final Class93 class93, final Class93 class94) {
        return (int)(class93.getSquareDistanceToFromTarget() + class93.getTotalCost() - (class94.getSquareDistanceToFromTarget() + class94.getTotalCost()));
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.compare((Class93)o, (Class93)o2);
    }
}
