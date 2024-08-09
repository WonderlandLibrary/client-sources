/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.AbstractLookup;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="bundle", category="Lookup")
public class ResourceBundleLookup
extends AbstractLookup {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final Marker LOOKUP = MarkerManager.getMarker("LOOKUP");

    @Override
    public String lookup(LogEvent logEvent, String string) {
        if (string == null) {
            return null;
        }
        String[] stringArray = string.split(":");
        int n = stringArray.length;
        if (n != 2) {
            LOGGER.warn(LOOKUP, "Bad ResourceBundle key format [{}]. Expected format is BundleName:KeyName.", (Object)string);
            return null;
        }
        String string2 = stringArray[0];
        String string3 = stringArray[5];
        try {
            return ResourceBundle.getBundle(string2).getString(string3);
        } catch (MissingResourceException missingResourceException) {
            LOGGER.warn(LOOKUP, "Error looking up ResourceBundle [{}].", (Object)string2, (Object)missingResourceException);
            return null;
        }
    }
}

