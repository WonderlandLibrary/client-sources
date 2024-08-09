/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

public final class FileSize {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final long KB = 1024L;
    private static final long MB = 0x100000L;
    private static final long GB = 0x40000000L;
    private static final Pattern VALUE_PATTERN = Pattern.compile("([0-9]+([\\.,][0-9]+)?)\\s*(|K|M|G)B?", 2);

    private FileSize() {
    }

    public static long parse(String string, long l) {
        Matcher matcher = VALUE_PATTERN.matcher(string);
        if (matcher.matches()) {
            try {
                long l2 = NumberFormat.getNumberInstance(Locale.getDefault()).parse(matcher.group(1)).longValue();
                String string2 = matcher.group(3);
                if (string2.isEmpty()) {
                    return l2;
                }
                if (string2.equalsIgnoreCase("K")) {
                    return l2 * 1024L;
                }
                if (string2.equalsIgnoreCase("M")) {
                    return l2 * 0x100000L;
                }
                if (string2.equalsIgnoreCase("G")) {
                    return l2 * 0x40000000L;
                }
                LOGGER.error("FileSize units not recognized: " + string);
                return l;
            } catch (ParseException parseException) {
                LOGGER.error("FileSize unable to parse numeric part: " + string, (Throwable)parseException);
                return l;
            }
        }
        LOGGER.error("FileSize unable to parse bytes: " + string);
        return l;
    }
}

