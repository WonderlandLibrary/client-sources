/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharComparator;
import java.io.Serializable;

public abstract class AbstractCharComparator
implements CharComparator,
Serializable {
    private static final long serialVersionUID = 0L;

    protected AbstractCharComparator() {
    }

    @Override
    public int compare(Character ok1, Character ok2) {
        return this.compare(ok1.charValue(), ok2.charValue());
    }

    @Override
    public abstract int compare(char var1, char var2);
}

