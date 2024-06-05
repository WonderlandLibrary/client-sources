package ClassSub;

import java.util.*;

class Class276 extends LinkedHashMap
{
    final Class139 this$0;
    
    
    Class276(final Class139 this$0, final int n, final float n2, final boolean b) {
        this.this$0 = this$0;
        super(n, n2, b);
    }
    
    @Override
    protected boolean removeEldestEntry(final Map.Entry entry) {
        final Class139.Class60 class60 = entry.getValue();
        if (class60 != null) {
            Class139.access$002(this.this$0, class60.id);
        }
        return this.size() > 200;
    }
}
