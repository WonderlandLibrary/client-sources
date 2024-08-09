/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.util.Date;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.AbstractPatternConverter;
import org.apache.logging.log4j.core.pattern.ArrayPatternConverter;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.NotANumber;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="IntegerPatternConverter", category="FileConverter")
@ConverterKeys(value={"i", "index"})
@PerformanceSensitive(value={"allocation"})
public final class IntegerPatternConverter
extends AbstractPatternConverter
implements ArrayPatternConverter {
    private static final IntegerPatternConverter INSTANCE = new IntegerPatternConverter();

    private IntegerPatternConverter() {
        super("Integer", "integer");
    }

    public static IntegerPatternConverter newInstance(String[] stringArray) {
        return INSTANCE;
    }

    @Override
    public void format(StringBuilder stringBuilder, Object ... objectArray) {
        for (int i = 0; i < objectArray.length; ++i) {
            if (objectArray[i] instanceof Integer) {
                this.format(objectArray[i], stringBuilder);
                break;
            }
            if (!(objectArray[i] instanceof NotANumber)) continue;
            stringBuilder.append("\u0000");
            break;
        }
    }

    @Override
    public void format(Object object, StringBuilder stringBuilder) {
        if (object instanceof Integer) {
            stringBuilder.append((Integer)object);
        } else if (object instanceof Date) {
            stringBuilder.append(((Date)object).getTime());
        }
    }
}

