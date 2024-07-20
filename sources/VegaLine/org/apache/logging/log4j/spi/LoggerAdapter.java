/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.io.Closeable;

public interface LoggerAdapter<L>
extends Closeable {
    public L getLogger(String var1);
}

