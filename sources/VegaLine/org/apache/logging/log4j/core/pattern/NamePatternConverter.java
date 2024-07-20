/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.NameAbbreviator;
import org.apache.logging.log4j.util.PerformanceSensitive;

@PerformanceSensitive(value={"allocation"})
public abstract class NamePatternConverter
extends LogEventPatternConverter {
    private final NameAbbreviator abbreviator;

    protected NamePatternConverter(String name, String style, String[] options) {
        super(name, style);
        this.abbreviator = options != null && options.length > 0 ? NameAbbreviator.getAbbreviator(options[0]) : NameAbbreviator.getDefaultAbbreviator();
    }

    protected final void abbreviate(String original, StringBuilder destination) {
        this.abbreviator.abbreviate(original, destination);
    }
}

