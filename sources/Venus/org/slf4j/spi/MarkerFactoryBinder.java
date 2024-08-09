/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.spi;

import org.slf4j.IMarkerFactory;

public interface MarkerFactoryBinder {
    public IMarkerFactory getMarkerFactory();

    public String getMarkerFactoryClassStr();
}

