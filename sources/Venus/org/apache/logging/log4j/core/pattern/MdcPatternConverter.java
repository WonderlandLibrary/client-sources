/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.StringBuilders;
import org.apache.logging.log4j.util.TriConsumer;

@Plugin(name="MdcPatternConverter", category="Converter")
@ConverterKeys(value={"X", "mdc", "MDC"})
@PerformanceSensitive(value={"allocation"})
public final class MdcPatternConverter
extends LogEventPatternConverter {
    private static final ThreadLocal<StringBuilder> threadLocal = new ThreadLocal();
    private static final int DEFAULT_STRING_BUILDER_SIZE = 64;
    private static final int MAX_STRING_BUILDER_SIZE = Constants.MAX_REUSABLE_MESSAGE_SIZE;
    private final String key;
    private final String[] keys;
    private final boolean full;
    private static final TriConsumer<String, Object, StringBuilder> WRITE_KEY_VALUES_INTO = new TriConsumer<String, Object, StringBuilder>(){

        @Override
        public void accept(String string, Object object, StringBuilder stringBuilder) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(string).append('=');
            StringBuilders.appendValue(stringBuilder, object);
        }

        @Override
        public void accept(Object object, Object object2, Object object3) {
            this.accept((String)object, object2, (StringBuilder)object3);
        }
    };

    private MdcPatternConverter(String[] stringArray) {
        super(stringArray != null && stringArray.length > 0 ? "MDC{" + stringArray[0] + '}' : "MDC", "mdc");
        if (stringArray != null && stringArray.length > 0) {
            this.full = false;
            if (stringArray[0].indexOf(44) > 0) {
                this.keys = stringArray[0].split(",");
                for (int i = 0; i < this.keys.length; ++i) {
                    this.keys[i] = this.keys[i].trim();
                }
                this.key = null;
            } else {
                this.keys = null;
                this.key = stringArray[0];
            }
        } else {
            this.full = true;
            this.key = null;
            this.keys = null;
        }
    }

    public static MdcPatternConverter newInstance(String[] stringArray) {
        return new MdcPatternConverter(stringArray);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        Object v;
        ReadOnlyStringMap readOnlyStringMap = logEvent.getContextData();
        if (this.full) {
            if (readOnlyStringMap == null || readOnlyStringMap.size() == 0) {
                stringBuilder.append("{}");
                return;
            }
            MdcPatternConverter.appendFully(readOnlyStringMap, stringBuilder);
        } else if (this.keys != null) {
            if (readOnlyStringMap == null || readOnlyStringMap.size() == 0) {
                stringBuilder.append("{}");
                return;
            }
            MdcPatternConverter.appendSelectedKeys(this.keys, readOnlyStringMap, stringBuilder);
        } else if (readOnlyStringMap != null && (v = readOnlyStringMap.getValue(this.key)) != null) {
            StringBuilders.appendValue(stringBuilder, v);
        }
    }

    private static void appendFully(ReadOnlyStringMap readOnlyStringMap, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = MdcPatternConverter.getStringBuilder();
        stringBuilder2.append("{");
        readOnlyStringMap.forEach(WRITE_KEY_VALUES_INTO, stringBuilder2);
        stringBuilder2.append('}');
        stringBuilder.append((CharSequence)stringBuilder2);
        MdcPatternConverter.trimToMaxSize(stringBuilder2);
    }

    private static void appendSelectedKeys(String[] stringArray, ReadOnlyStringMap readOnlyStringMap, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = MdcPatternConverter.getStringBuilder();
        stringBuilder2.append("{");
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            Object v = readOnlyStringMap.getValue(string);
            if (v == null) continue;
            if (stringBuilder2.length() > 1) {
                stringBuilder2.append(", ");
            }
            stringBuilder2.append(string).append('=');
            StringBuilders.appendValue(stringBuilder2, v);
        }
        stringBuilder2.append('}');
        stringBuilder.append((CharSequence)stringBuilder2);
        MdcPatternConverter.trimToMaxSize(stringBuilder2);
    }

    private static StringBuilder getStringBuilder() {
        StringBuilder stringBuilder = threadLocal.get();
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder(64);
            threadLocal.set(stringBuilder);
        }
        stringBuilder.setLength(0);
        return stringBuilder;
    }

    private static void trimToMaxSize(StringBuilder stringBuilder) {
        if (stringBuilder.length() > MAX_STRING_BUILDER_SIZE) {
            stringBuilder.setLength(MAX_STRING_BUILDER_SIZE);
            stringBuilder.trimToSize();
        }
    }
}

