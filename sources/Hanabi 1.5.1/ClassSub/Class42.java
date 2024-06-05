package ClassSub;

import java.util.*;

private class Class42
{
    private ArrayList points;
    final Class278 this$0;
    
    
    public Class42(final Class278 this$0) {
        this.this$0 = this$0;
        this.points = new ArrayList();
    }
    
    public boolean contains(final Class338 class338) {
        return this.points.contains(class338);
    }
    
    public void add(final Class338 class338) {
        this.points.add(class338);
    }
    
    public void remove(final Class338 class338) {
        this.points.remove(class338);
    }
    
    public int size() {
        return this.points.size();
    }
    
    public Class338 get(final int n) {
        return this.points.get(n);
    }
    
    public void clear() {
        this.points.clear();
    }
}
