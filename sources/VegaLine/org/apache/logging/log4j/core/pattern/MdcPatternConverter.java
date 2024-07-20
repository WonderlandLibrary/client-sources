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
        public void accept(String key, Object value, StringBuilder sb) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(key).append('=');
            StringBuilders.appendValue(sb, value);
        }
    };

    private MdcPatternConverter(String[] options) {
        super(options != null && options.length > 0 ? "MDC{" + options[0] + '}' : "MDC", "mdc");
        if (options != null && options.length > 0) {
            this.full = false;
            if (options[0].indexOf(44) > 0) {
                this.keys = options[0].split(",");
                for (int i = 0; i < this.keys.length; ++i) {
                    this.keys[i] = this.keys[i].trim();
                }
                this.key = null;
            } else {
                this.keys = null;
                this.key = options[0];
            }
        } else {
            this.full = true;
            this.key = null;
            this.keys = null;
        }
    }

    public static MdcPatternConverter newInstance(String[] options) {
        return new MdcPatternConverter(options);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        Object value;
        ReadOnlyStringMap contextData = event.getContextData();
        if (this.full) {
            if (contextData == null || contextData.size() == 0) {
                toAppendTo.append("{}");
                return;
            }
            MdcPatternConverter.appendFully(contextData, toAppendTo);
        } else if (this.keys != null) {
            if (contextData == null || contextData.size() == 0) {
                toAppendTo.append("{}");
                return;
            }
            MdcPatternConverter.appendSelectedKeys(this.keys, contextData, toAppendTo);
        } else if (contextData != null && (value = contextData.getValue(this.key)) != null) {
            StringBuilders.appendValue(toAppendTo, value);
        }
    }

    private static void appendFully(ReadOnlyStringMap contextData, StringBuilder toAppendTo) {
        StringBuilder sb = MdcPatternConverter.getStringBuilder();
        sb.append("{");
        contextData.forEach(WRITE_KEY_VALUES_INTO, sb);
        sb.append('}');
        toAppendTo.append((CharSequence)sb);
        MdcPatternConverter.trimToMaxSize(sb);
    }

    private static void appendSelectedKeys(String[] keys, ReadOnlyStringMap contextData, StringBuilder toAppendTo) {
        StringBuilder sb = MdcPatternConverter.getStringBuilder();
        sb.append("{");
        for (int i = 0; i < keys.length; ++i) {
            String theKey = keys[i];
            Object value = contextData.getValue(theKey);
            if (value == null) continue;
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(theKey).append('=');
            StringBuilders.appendValue(sb, value);
        }
        sb.append('}');
        toAppendTo.append((CharSequence)sb);
        MdcPatternConverter.trimToMaxSize(sb);
    }

    private static StringBuilder getStringBuilder() {
        StringBuilder result = threadLocal.get();
        if (result == null) {
            result = new StringBuilder(64);
            threadLocal.set(result);
        }
        result.setLength(0);
        return result;
    }

    private static void trimToMaxSize(StringBuilder stringBuilder) {
        if (stringBuilder.length() > MAX_STRING_BUILDER_SIZE) {
            stringBuilder.setLength(MAX_STRING_BUILDER_SIZE);
            stringBuilder.trimToSize();
        }
    }
}

