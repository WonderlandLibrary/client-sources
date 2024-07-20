/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import java.util.Set;

public interface BooleanSet
extends BooleanCollection,
Set<Boolean> {
    @Override
    public BooleanIterator iterator();

    public boolean remove(boolean var1);
}

