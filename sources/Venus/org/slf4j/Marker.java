/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j;

import java.io.Serializable;
import java.util.Iterator;

public interface Marker
extends Serializable {
    public static final String ANY_MARKER = "*";
    public static final String ANY_NON_NULL_MARKER = "+";

    public String getName();

    public void add(Marker var1);

    public boolean remove(Marker var1);

    @Deprecated
    public boolean hasChildren();

    public boolean hasReferences();

    public Iterator<Marker> iterator();

    public boolean contains(Marker var1);

    public boolean contains(String var1);

    public boolean equals(Object var1);

    public int hashCode();
}

