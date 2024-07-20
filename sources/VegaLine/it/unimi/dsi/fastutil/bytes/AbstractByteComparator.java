/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteComparator;
import java.io.Serializable;

public abstract class AbstractByteComparator
implements ByteComparator,
Serializable {
    private static final long serialVersionUID = 0L;

    protected AbstractByteComparator() {
    }

    @Override
    public int compare(Byte ok1, Byte ok2) {
        return this.compare((byte)ok1, (byte)ok2);
    }

    @Override
    public abstract int compare(byte var1, byte var2);
}

