/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.status.StatusLogger;

public abstract class AbstractPatternConverter
implements PatternConverter {
    protected static final Logger LOGGER = StatusLogger.getLogger();
    private final String name;
    private final String style;

    protected AbstractPatternConverter(String string, String string2) {
        this.name = string;
        this.style = string2;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public String getStyleClass(Object object) {
        return this.style;
    }
}

