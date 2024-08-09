/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="date", category="Lookup")
public class DateLookup
implements StrLookup {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final Marker LOOKUP = MarkerManager.getMarker("LOOKUP");

    @Override
    public String lookup(String string) {
        return this.formatDate(System.currentTimeMillis(), string);
    }

    @Override
    public String lookup(LogEvent logEvent, String string) {
        return this.formatDate(logEvent.getTimeMillis(), string);
    }

    private String formatDate(long l, String string) {
        DateFormat dateFormat = null;
        if (string != null) {
            try {
                dateFormat = new SimpleDateFormat(string);
            } catch (Exception exception) {
                LOGGER.error(LOOKUP, "Invalid date format: [{}], using default", (Object)string, (Object)exception);
            }
        }
        if (dateFormat == null) {
            dateFormat = DateFormat.getInstance();
        }
        return dateFormat.format(new Date(l));
    }
}

