/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j;

import java.io.Serializable;

public interface Marker
extends Serializable {
    public Marker addParents(Marker ... var1);

    public boolean equals(Object var1);

    public String getName();

    public Marker[] getParents();

    public int hashCode();

    public boolean hasParents();

    public boolean isInstanceOf(Marker var1);

    public boolean isInstanceOf(String var1);

    public boolean remove(Marker var1);

    public Marker setParents(Marker ... var1);
}

