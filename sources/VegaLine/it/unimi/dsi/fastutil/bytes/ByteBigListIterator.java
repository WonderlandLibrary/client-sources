/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;

public interface ByteBigListIterator
extends ByteBidirectionalIterator,
BigListIterator<Byte> {
    public void set(byte var1);

    public void add(byte var1);

    public void set(Byte var1);

    public void add(Byte var1);
}

