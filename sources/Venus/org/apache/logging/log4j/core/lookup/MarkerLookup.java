/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.AbstractLookup;

@Plugin(name="marker", category="Lookup")
public class MarkerLookup
extends AbstractLookup {
    static final String MARKER = "marker";

    @Override
    public String lookup(LogEvent logEvent, String string) {
        Marker marker = logEvent == null ? null : logEvent.getMarker();
        return marker == null ? null : marker.getName();
    }

    @Override
    public String lookup(String string) {
        return MarkerManager.exists(string) ? string : null;
    }
}

