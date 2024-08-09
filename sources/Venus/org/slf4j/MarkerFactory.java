/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j;

import org.slf4j.IMarkerFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.Util;
import org.slf4j.spi.SLF4JServiceProvider;

public class MarkerFactory {
    static IMarkerFactory MARKER_FACTORY;

    private MarkerFactory() {
    }

    public static Marker getMarker(String string) {
        return MARKER_FACTORY.getMarker(string);
    }

    public static Marker getDetachedMarker(String string) {
        return MARKER_FACTORY.getDetachedMarker(string);
    }

    public static IMarkerFactory getIMarkerFactory() {
        return MARKER_FACTORY;
    }

    static {
        SLF4JServiceProvider sLF4JServiceProvider = LoggerFactory.getProvider();
        if (sLF4JServiceProvider != null) {
            MARKER_FACTORY = sLF4JServiceProvider.getMarkerFactory();
        } else {
            Util.report("Failed to find provider");
            Util.report("Defaulting to BasicMarkerFactory.");
            MARKER_FACTORY = new BasicMarkerFactory();
        }
    }
}

