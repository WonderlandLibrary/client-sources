/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.helpers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.IMarkerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.BasicMarker;

public class BasicMarkerFactory
implements IMarkerFactory {
    private final ConcurrentMap<String, Marker> markerMap = new ConcurrentHashMap<String, Marker>();

    @Override
    public Marker getMarker(String string) {
        Marker marker;
        if (string == null) {
            throw new IllegalArgumentException("Marker name cannot be null");
        }
        Marker marker2 = (Marker)this.markerMap.get(string);
        if (marker2 == null && (marker = this.markerMap.putIfAbsent(string, marker2 = new BasicMarker(string))) != null) {
            marker2 = marker;
        }
        return marker2;
    }

    @Override
    public boolean exists(String string) {
        if (string == null) {
            return true;
        }
        return this.markerMap.containsKey(string);
    }

    @Override
    public boolean detachMarker(String string) {
        if (string == null) {
            return true;
        }
        return this.markerMap.remove(string) != null;
    }

    @Override
    public Marker getDetachedMarker(String string) {
        return new BasicMarker(string);
    }
}

