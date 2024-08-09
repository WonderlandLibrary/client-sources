/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.filter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

@Plugin(name="RegexFilter", category="Core", elementType="filter", printObject=true)
public final class RegexFilter
extends AbstractFilter {
    private static final int DEFAULT_PATTERN_FLAGS = 0;
    private final Pattern pattern;
    private final boolean useRawMessage;

    private RegexFilter(boolean bl, Pattern pattern, Filter.Result result, Filter.Result result2) {
        super(result, result2);
        this.pattern = pattern;
        this.useRawMessage = bl;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object ... objectArray) {
        return this.filter(string);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object object, Throwable throwable) {
        if (object == null) {
            return this.onMismatch;
        }
        return this.filter(object.toString());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
        if (message == null) {
            return this.onMismatch;
        }
        String string = this.useRawMessage ? message.getFormat() : message.getFormattedMessage();
        return this.filter(string);
    }

    @Override
    public Filter.Result filter(LogEvent logEvent) {
        String string = this.useRawMessage ? logEvent.getMessage().getFormat() : logEvent.getMessage().getFormattedMessage();
        return this.filter(string);
    }

    private Filter.Result filter(String string) {
        if (string == null) {
            return this.onMismatch;
        }
        Matcher matcher = this.pattern.matcher(string);
        return matcher.matches() ? this.onMatch : this.onMismatch;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("useRaw=").append(this.useRawMessage);
        stringBuilder.append(", pattern=").append(this.pattern.toString());
        return stringBuilder.toString();
    }

    @PluginFactory
    public static RegexFilter createFilter(@PluginAttribute(value="regex") String string, @PluginElement(value="PatternFlags") String[] stringArray, @PluginAttribute(value="useRawMsg") Boolean bl, @PluginAttribute(value="onMatch") Filter.Result result, @PluginAttribute(value="onMismatch") Filter.Result result2) throws IllegalArgumentException, IllegalAccessException {
        if (string == null) {
            LOGGER.error("A regular expression must be provided for RegexFilter");
            return null;
        }
        return new RegexFilter(bl, Pattern.compile(string, RegexFilter.toPatternFlags(stringArray)), result, result2);
    }

    private static int toPatternFlags(String[] stringArray) throws IllegalArgumentException, IllegalAccessException {
        int n;
        if (stringArray == null || stringArray.length == 0) {
            return 1;
        }
        Field[] fieldArray = Pattern.class.getDeclaredFields();
        Comparator<Field> comparator = new Comparator<Field>(){

            @Override
            public int compare(Field field, Field field2) {
                return field.getName().compareTo(field2.getName());
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Field)object, (Field)object2);
            }
        };
        Arrays.sort(fieldArray, comparator);
        Object[] objectArray = new String[fieldArray.length];
        for (n = 0; n < fieldArray.length; ++n) {
            objectArray[n] = fieldArray[n].getName();
        }
        n = 0;
        for (String string : stringArray) {
            int n2 = Arrays.binarySearch(objectArray, string);
            if (n2 < 0) continue;
            Field field = fieldArray[n2];
            n |= field.getInt(Pattern.class);
        }
        return n;
    }
}

