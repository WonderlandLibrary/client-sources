/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.status;

import java.io.Closeable;
import java.util.EventListener;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.status.StatusData;

public interface StatusListener
extends Closeable,
EventListener {
    public void log(StatusData var1);

    public Level getStatusLevel();
}

