/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.util.concurrent.atomic.AtomicLong;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="SequenceNumberPatternConverter", category="Converter")
@ConverterKeys(value={"sn", "sequenceNumber"})
@PerformanceSensitive(value={"allocation"})
public final class SequenceNumberPatternConverter
extends LogEventPatternConverter {
    private static final AtomicLong SEQUENCE = new AtomicLong();
    private static final SequenceNumberPatternConverter INSTANCE = new SequenceNumberPatternConverter();

    private SequenceNumberPatternConverter() {
        super("Sequence Number", "sn");
    }

    public static SequenceNumberPatternConverter newInstance(String[] options) {
        return INSTANCE;
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        toAppendTo.append(SEQUENCE.incrementAndGet());
    }
}

