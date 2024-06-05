// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.bind;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class BindManager
{
    private static final List<Bindable> BINDABLES;
    
    public void addBindables(final Bindable... bindables) {
        Collections.addAll(BindManager.BINDABLES, bindables);
    }
    
    public void addBindables(final List<Bindable> bindables) {
        BindManager.BINDABLES.addAll(bindables);
    }
    
    public void addBindable(final Bindable bindable) {
        BindManager.BINDABLES.add(bindable);
    }
    
    public List<Bindable> getBindables() {
        return BindManager.BINDABLES;
    }
    
    static {
        BINDABLES = new ArrayList<Bindable>();
    }
}
