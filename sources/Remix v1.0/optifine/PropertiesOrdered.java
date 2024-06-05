package optifine;

import java.util.*;

public class PropertiesOrdered extends Properties
{
    private Set<Object> keysOrdered;
    
    public PropertiesOrdered() {
        this.keysOrdered = new LinkedHashSet<Object>();
    }
    
    @Override
    public synchronized Object put(final Object key, final Object value) {
        this.keysOrdered.add(key);
        return super.put(key, value);
    }
    
    @Override
    public Set<Object> keySet() {
        final Set keysParent = super.keySet();
        this.keysOrdered.retainAll(keysParent);
        return Collections.unmodifiableSet((Set<?>)this.keysOrdered);
    }
    
    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(this.keySet());
    }
}
