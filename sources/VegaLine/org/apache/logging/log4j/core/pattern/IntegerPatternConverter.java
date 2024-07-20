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

    public static IntegerPatternConverter newInstance(String[] options) {
        return INSTANCE;
    }

    @Override
    public void format(StringBuilder toAppendTo, Object ... objects) {
        for (int i = 0; i < objects.length; ++i) {
            if (objects[i] instanceof Integer) {
                this.format(objects[i], toAppendTo);
                break;
            }
            if (!(objects[i] instanceof NotANumber)) continue;
            toAppendTo.append("\u0000");
            break;
        }
    }

    @Override
    public void format(Object obj, StringBuilder toAppendTo) {
        if (obj instanceof Integer) {
            toAppendTo.append((Integer)obj);
        } else if (obj instanceof Date) {
            toAppendTo.append(((Date)obj).getTime());
        }
    }
}

