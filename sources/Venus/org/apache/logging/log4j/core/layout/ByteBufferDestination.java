/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.nio.ByteBuffer;

public interface ByteBufferDestination {
    public ByteBuffer getByteBuffer();

    public ByteBuffer drain(ByteBuffer var1);
}

