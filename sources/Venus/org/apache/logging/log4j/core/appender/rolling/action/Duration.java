/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Duration
implements Serializable,
Comparable<Duration> {
    private static final long serialVersionUID = -3756810052716342061L;
    public static final Duration ZERO = new Duration(0L);
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_DAY = 86400;
    private static final Pattern PATTERN = Pattern.compile("P?(?:([0-9]+)D)?(T?(?:([0-9]+)H)?(?:([0-9]+)M)?(?:([0-9]+)?S)?)?", 2);
    private final long seconds;

    private Duration(long l) {
        this.seconds = l;
    }

    public static Duration parse(CharSequence charSequence) {
        Objects.requireNonNull(charSequence, "text");
        Matcher matcher = PATTERN.matcher(charSequence);
        if (matcher.matches() && !"T".equals(matcher.group(2))) {
            String string = matcher.group(1);
            String string2 = matcher.group(3);
            String string3 = matcher.group(4);
            String string4 = matcher.group(5);
            if (string != null || string2 != null || string3 != null || string4 != null) {
                long l = Duration.parseNumber(charSequence, string, 86400, "days");
                long l2 = Duration.parseNumber(charSequence, string2, 3600, "hours");
                long l3 = Duration.parseNumber(charSequence, string3, 60, "minutes");
                long l4 = Duration.parseNumber(charSequence, string4, 1, "seconds");
                try {
                    return Duration.create(l, l2, l3, l4);
                } catch (ArithmeticException arithmeticException) {
                    throw new IllegalArgumentException("Text cannot be parsed to a Duration (overflow) " + charSequence, arithmeticException);
                }
            }
        }
        throw new IllegalArgumentException("Text cannot be parsed to a Duration: " + charSequence);
    }

    private static long parseNumber(CharSequence charSequence, String string, int n, String string2) {
        if (string == null) {
            return 0L;
        }
        try {
            long l = Long.parseLong(string);
            return l * (long)n;
        } catch (Exception exception) {
            throw new IllegalArgumentException("Text cannot be parsed to a Duration: " + string2 + " (in " + charSequence + ")", exception);
        }
    }

    private static Duration create(long l, long l2, long l3, long l4) {
        return Duration.create(l + l2 + l3 + l4);
    }

    private static Duration create(long l) {
        if (l == 0L) {
            return ZERO;
        }
        return new Duration(l);
    }

    public long toMillis() {
        return this.seconds * 1000L;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Duration)) {
            return true;
        }
        Duration duration = (Duration)object;
        return duration.seconds == this.seconds;
    }

    public int hashCode() {
        return (int)(this.seconds ^ this.seconds >>> 32);
    }

    public String toString() {
        if (this == ZERO) {
            return "PT0S";
        }
        long l = this.seconds / 86400L;
        long l2 = this.seconds % 86400L / 3600L;
        int n = (int)(this.seconds % 3600L / 60L);
        int n2 = (int)(this.seconds % 60L);
        StringBuilder stringBuilder = new StringBuilder(24);
        stringBuilder.append("P");
        if (l != 0L) {
            stringBuilder.append(l).append('D');
        }
        if ((l2 | (long)n | (long)n2) != 0L) {
            stringBuilder.append('T');
        }
        if (l2 != 0L) {
            stringBuilder.append(l2).append('H');
        }
        if (n != 0) {
            stringBuilder.append(n).append('M');
        }
        if (n2 == 0 && stringBuilder.length() > 0) {
            return stringBuilder.toString();
        }
        stringBuilder.append(n2).append('S');
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Duration duration) {
        return Long.signum(this.toMillis() - duration.toMillis());
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Duration)object);
    }
}

