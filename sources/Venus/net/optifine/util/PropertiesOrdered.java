/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class PropertiesOrdered
extends Properties {
    private Set<Object> keysOrdered = new LinkedHashSet<Object>();

    @Override
    public synchronized Object put(Object object, Object object2) {
        if (object instanceof String) {
            object = ((String)object).trim();
        }
        if (object2 instanceof String) {
            object2 = ((String)object2).trim();
        }
        this.keysOrdered.add(object);
        return super.put(object, object2);
    }

    @Override
    public Set<Object> keySet() {
        Set set = super.keySet();
        this.keysOrdered.retainAll(set);
        return Collections.unmodifiableSet(this.keysOrdered);
    }

    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(this.keySet());
    }
}

