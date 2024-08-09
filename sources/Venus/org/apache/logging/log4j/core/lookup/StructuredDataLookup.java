/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.apache.logging.log4j.message.StructuredDataMessage;

@Plugin(name="sd", category="Lookup")
public class StructuredDataLookup
implements StrLookup {
    @Override
    public String lookup(String string) {
        return null;
    }

    @Override
    public String lookup(LogEvent logEvent, String string) {
        if (logEvent == null || !(logEvent.getMessage() instanceof StructuredDataMessage)) {
            return null;
        }
        StructuredDataMessage structuredDataMessage = (StructuredDataMessage)logEvent.getMessage();
        if (string.equalsIgnoreCase("id")) {
            return structuredDataMessage.getId().getName();
        }
        if (string.equalsIgnoreCase("type")) {
            return structuredDataMessage.getType();
        }
        return structuredDataMessage.get(string);
    }
}

