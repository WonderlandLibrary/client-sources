/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ArrayPatternConverter;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.datetime.FastDateFormat;
import org.apache.logging.log4j.core.util.datetime.FixedDateFormat;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="DatePatternConverter", category="Converter")
@ConverterKeys(value={"d", "date"})
@PerformanceSensitive(value={"allocation"})
public final class DatePatternConverter
extends LogEventPatternConverter
implements ArrayPatternConverter {
    private static final String UNIX_FORMAT = "UNIX";
    private static final String UNIX_MILLIS_FORMAT = "UNIX_MILLIS";
    private final String[] options;
    private final ThreadLocal<Formatter> threadLocalFormatter = new ThreadLocal();
    private final AtomicReference<CachedTime> cachedTime;
    private final Formatter formatter;

    private DatePatternConverter(String[] stringArray) {
        super("Date", "date");
        this.options = stringArray == null ? null : Arrays.copyOf(stringArray, stringArray.length);
        this.formatter = this.createFormatter(stringArray);
        this.cachedTime = new AtomicReference<CachedTime>(new CachedTime(this, System.currentTimeMillis()));
    }

    private Formatter createFormatter(String[] stringArray) {
        FixedDateFormat fixedDateFormat = FixedDateFormat.createIfSupported(stringArray);
        if (fixedDateFormat != null) {
            return DatePatternConverter.createFixedFormatter(fixedDateFormat);
        }
        return DatePatternConverter.createNonFixedFormatter(stringArray);
    }

    public static DatePatternConverter newInstance(String[] stringArray) {
        return new DatePatternConverter(stringArray);
    }

    private static Formatter createFixedFormatter(FixedDateFormat fixedDateFormat) {
        return new FixedFormatter(fixedDateFormat);
    }

    private static Formatter createNonFixedFormatter(String[] stringArray) {
        Objects.requireNonNull(stringArray);
        if (stringArray.length == 0) {
            throw new IllegalArgumentException("options array must have at least one element");
        }
        Objects.requireNonNull(stringArray[0]);
        String string = stringArray[0];
        if (UNIX_FORMAT.equals(string)) {
            return new UnixFormatter(null);
        }
        if (UNIX_MILLIS_FORMAT.equals(string)) {
            return new UnixMillisFormatter(null);
        }
        FixedDateFormat.FixedFormat fixedFormat = FixedDateFormat.FixedFormat.lookup(string);
        String string2 = fixedFormat == null ? string : fixedFormat.getPattern();
        TimeZone timeZone = null;
        if (stringArray.length > 1 && stringArray[5] != null) {
            timeZone = TimeZone.getTimeZone(stringArray[5]);
        }
        try {
            FastDateFormat fastDateFormat = FastDateFormat.getInstance(string2, timeZone);
            return new PatternFormatter(fastDateFormat);
        } catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.warn("Could not instantiate FastDateFormat with pattern " + string2, (Throwable)illegalArgumentException);
            return DatePatternConverter.createFixedFormatter(FixedDateFormat.create(FixedDateFormat.FixedFormat.DEFAULT, timeZone));
        }
    }

    public void format(Date date, StringBuilder stringBuilder) {
        this.format(date.getTime(), stringBuilder);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        this.format(logEvent.getTimeMillis(), stringBuilder);
    }

    public void format(long l, StringBuilder stringBuilder) {
        if (Constants.ENABLE_THREADLOCALS) {
            this.formatWithoutAllocation(l, stringBuilder);
        } else {
            this.formatWithoutThreadLocals(l, stringBuilder);
        }
    }

    private void formatWithoutAllocation(long l, StringBuilder stringBuilder) {
        this.getThreadLocalFormatter().formatToBuffer(l, stringBuilder);
    }

    private Formatter getThreadLocalFormatter() {
        Formatter formatter = this.threadLocalFormatter.get();
        if (formatter == null) {
            formatter = this.createFormatter(this.options);
            this.threadLocalFormatter.set(formatter);
        }
        return formatter;
    }

    private void formatWithoutThreadLocals(long l, StringBuilder stringBuilder) {
        CachedTime cachedTime = this.cachedTime.get();
        if (l != cachedTime.timestampMillis) {
            CachedTime cachedTime2 = new CachedTime(this, l);
            cachedTime = this.cachedTime.compareAndSet(cachedTime, cachedTime2) ? cachedTime2 : this.cachedTime.get();
        }
        stringBuilder.append(cachedTime.formatted);
    }

    @Override
    public void format(Object object, StringBuilder stringBuilder) {
        if (object instanceof Date) {
            this.format((Date)object, stringBuilder);
        }
        super.format(object, stringBuilder);
    }

    @Override
    public void format(StringBuilder stringBuilder, Object ... objectArray) {
        for (Object object : objectArray) {
            if (!(object instanceof Date)) continue;
            this.format(object, stringBuilder);
            break;
        }
    }

    public String getPattern() {
        return this.formatter.toPattern();
    }

    static Formatter access$100(DatePatternConverter datePatternConverter) {
        return datePatternConverter.formatter;
    }

    static class 1 {
    }

    private final class CachedTime {
        public long timestampMillis;
        public String formatted;
        final DatePatternConverter this$0;

        public CachedTime(DatePatternConverter datePatternConverter, long l) {
            this.this$0 = datePatternConverter;
            this.timestampMillis = l;
            this.formatted = DatePatternConverter.access$100(datePatternConverter).format(this.timestampMillis);
        }
    }

    private static final class UnixMillisFormatter
    extends Formatter {
        private UnixMillisFormatter() {
            super(null);
        }

        @Override
        String format(long l) {
            return Long.toString(l);
        }

        @Override
        void formatToBuffer(long l, StringBuilder stringBuilder) {
            stringBuilder.append(l);
        }

        UnixMillisFormatter(1 var1_1) {
            this();
        }
    }

    private static final class UnixFormatter
    extends Formatter {
        private UnixFormatter() {
            super(null);
        }

        @Override
        String format(long l) {
            return Long.toString(l / 1000L);
        }

        @Override
        void formatToBuffer(long l, StringBuilder stringBuilder) {
            stringBuilder.append(l / 1000L);
        }

        UnixFormatter(1 var1_1) {
            this();
        }
    }

    private static final class FixedFormatter
    extends Formatter {
        private final FixedDateFormat fixedDateFormat;
        private final char[] cachedBuffer = new char[64];
        private int length = 0;

        FixedFormatter(FixedDateFormat fixedDateFormat) {
            super(null);
            this.fixedDateFormat = fixedDateFormat;
        }

        @Override
        String format(long l) {
            return this.fixedDateFormat.format(l);
        }

        @Override
        void formatToBuffer(long l, StringBuilder stringBuilder) {
            if (this.previousTime != l) {
                this.length = this.fixedDateFormat.format(l, this.cachedBuffer, 0);
            }
            stringBuilder.append(this.cachedBuffer, 0, this.length);
        }

        @Override
        public String toPattern() {
            return this.fixedDateFormat.getFormat();
        }
    }

    private static final class PatternFormatter
    extends Formatter {
        private final FastDateFormat fastDateFormat;
        private final StringBuilder cachedBuffer = new StringBuilder(64);

        PatternFormatter(FastDateFormat fastDateFormat) {
            super(null);
            this.fastDateFormat = fastDateFormat;
        }

        @Override
        String format(long l) {
            return this.fastDateFormat.format(l);
        }

        @Override
        void formatToBuffer(long l, StringBuilder stringBuilder) {
            if (this.previousTime != l) {
                this.cachedBuffer.setLength(0);
                this.fastDateFormat.format(l, this.cachedBuffer);
            }
            stringBuilder.append((CharSequence)this.cachedBuffer);
        }

        @Override
        public String toPattern() {
            return this.fastDateFormat.getPattern();
        }
    }

    private static abstract class Formatter {
        long previousTime;

        private Formatter() {
        }

        abstract String format(long var1);

        abstract void formatToBuffer(long var1, StringBuilder var3);

        public String toPattern() {
            return null;
        }

        Formatter(1 var1_1) {
            this();
        }
    }
}

