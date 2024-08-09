/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import org.apache.logging.log4j.core.layout.ByteBufferDestination;

public interface Encoder<T> {
    public void encode(T var1, ByteBufferDestination var2);
}

