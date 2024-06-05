package ClassSub;

import java.util.*;

class Class69 extends LinkedHashMap
{
    final Class182 this$0;
    
    
    Class69(final Class182 this$0, final int n, final float n2, final boolean b) {
        this.this$0 = this$0;
        super(n, n2, b);
    }
    
    @Override
    protected boolean removeEldestEntry(final Map.Entry entry) {
        Class182.access$002(this.this$0, entry.getValue());
        Class182.access$102(this.this$0, Class182.access$000(this.this$0).id);
        return false;
    }
}
