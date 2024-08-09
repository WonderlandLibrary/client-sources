/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.util.UUID;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.util.UuidUtil;

@Plugin(name="UuidPatternConverter", category="Converter")
@ConverterKeys(value={"u", "uuid"})
public final class UuidPatternConverter
extends LogEventPatternConverter {
    private final boolean isRandom;

    private UuidPatternConverter(boolean bl) {
        super("u", "uuid");
        this.isRandom = bl;
    }

    public static UuidPatternConverter newInstance(String[] stringArray) {
        if (stringArray.length == 0) {
            return new UuidPatternConverter(false);
        }
        if (stringArray.length > 1 || !stringArray[0].equalsIgnoreCase("RANDOM") && !stringArray[0].equalsIgnoreCase("Time")) {
            LOGGER.error("UUID Pattern Converter only accepts a single option with the value \"RANDOM\" or \"TIME\"");
        }
        return new UuidPatternConverter(stringArray[0].equalsIgnoreCase("RANDOM"));
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        UUID uUID = this.isRandom ? UUID.randomUUID() : UuidUtil.getTimeBasedUuid();
        stringBuilder.append(uUID.toString());
    }
}

