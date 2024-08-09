/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.pattern.ArrayPatternConverter;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.util.OptionConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;

@PerformanceSensitive(value={"allocation"})
public final class LiteralPatternConverter
extends LogEventPatternConverter
implements ArrayPatternConverter {
    private final String literal;
    private final Configuration config;
    private final boolean substitute;

    public LiteralPatternConverter(Configuration configuration, String string, boolean bl) {
        super("Literal", "literal");
        this.literal = bl ? OptionConverter.convertSpecialChars(string) : string;
        this.config = configuration;
        this.substitute = configuration != null && string.contains("${");
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        stringBuilder.append(this.substitute ? this.config.getStrSubstitutor().replace(logEvent, this.literal) : this.literal);
    }

    @Override
    public void format(Object object, StringBuilder stringBuilder) {
        stringBuilder.append(this.substitute ? this.config.getStrSubstitutor().replace(this.literal) : this.literal);
    }

    @Override
    public void format(StringBuilder stringBuilder, Object ... objectArray) {
        stringBuilder.append(this.substitute ? this.config.getStrSubstitutor().replace(this.literal) : this.literal);
    }

    public String getLiteral() {
        return this.literal;
    }

    @Override
    public boolean isVariable() {
        return true;
    }

    public String toString() {
        return "LiteralPatternConverter[literal=" + this.literal + ", config=" + this.config + ", substitute=" + this.substitute + "]";
    }
}

