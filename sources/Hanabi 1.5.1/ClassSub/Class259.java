package ClassSub;

import java.util.*;

public class Class259
{
    private static Class259 single;
    private ArrayList deferred;
    private int total;
    
    
    public static Class259 get() {
        return Class259.single;
    }
    
    public static void setDeferredLoading(final boolean deferredLoading) {
        Class259.single = new Class259();
        Class144.get().setDeferredLoading(deferredLoading);
    }
    
    public static boolean isDeferredLoading() {
        return Class144.get().isDeferredLoading();
    }
    
    private Class259() {
        this.deferred = new ArrayList();
    }
    
    public void add(final Class79 class79) {
        ++this.total;
        this.deferred.add(class79);
    }
    
    public void remove(final Class79 class79) {
        Class301.info("Early loading of deferred resource due to req: " + class79.getDescription());
        --this.total;
        this.deferred.remove(class79);
    }
    
    public int getTotalResources() {
        return this.total;
    }
    
    public int getRemainingResources() {
        return this.deferred.size();
    }
    
    public Class79 getNext() {
        if (this.deferred.size() == 0) {
            return null;
        }
        return this.deferred.remove(0);
    }
    
    static {
        Class259.single = new Class259();
    }
}
