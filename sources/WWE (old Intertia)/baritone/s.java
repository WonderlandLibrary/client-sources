package baritone;

import baritone.api.cache.*;
import java.util.*;

public final class s implements IRememberedInventory
{
    public final List<aip> a;
    private int b;
    public int a;
    
    private s() {
        super();
        this.a = new ArrayList<aip>();
    }
    
    @Override
    public final List<aip> getContents() {
        return Collections.unmodifiableList((List<? extends aip>)this.a);
    }
    
    @Override
    public final int getSize() {
        return this.a;
    }
    
    s(final byte b) {
        this();
    }
    
    static List a(final s s) {
        return s.a;
    }
    
    static int a(final s s, final int a) {
        return s.a = a;
    }
    
    static int b(final s s, final int b) {
        return s.b = b;
    }
    
    static int a(final s s) {
        return s.b;
    }
}
