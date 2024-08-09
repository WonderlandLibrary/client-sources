/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j;

import java.io.Closeable;
import java.util.Deque;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.helpers.Util;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

public class MDC {
    static final String NULL_MDCA_URL = "http://www.slf4j.org/codes.html#null_MDCA";
    private static final String MDC_APAPTER_CANNOT_BE_NULL_MESSAGE = "MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA";
    static final String NO_STATIC_MDC_BINDER_URL = "http://www.slf4j.org/codes.html#no_static_mdc_binder";
    static MDCAdapter mdcAdapter;

    private MDC() {
    }

    public static void put(String string, String string2) throws IllegalArgumentException {
        if (string == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (mdcAdapter == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        mdcAdapter.put(string, string2);
    }

    public static MDCCloseable putCloseable(String string, String string2) throws IllegalArgumentException {
        MDC.put(string, string2);
        return new MDCCloseable(string, null);
    }

    public static String get(String string) throws IllegalArgumentException {
        if (string == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (mdcAdapter == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        return mdcAdapter.get(string);
    }

    public static void remove(String string) throws IllegalArgumentException {
        if (string == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (mdcAdapter == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        mdcAdapter.remove(string);
    }

    public static void clear() {
        if (mdcAdapter == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        mdcAdapter.clear();
    }

    public static Map<String, String> getCopyOfContextMap() {
        if (mdcAdapter == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        return mdcAdapter.getCopyOfContextMap();
    }

    public static void setContextMap(Map<String, String> map) {
        if (mdcAdapter == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        mdcAdapter.setContextMap(map);
    }

    public static MDCAdapter getMDCAdapter() {
        return mdcAdapter;
    }

    public static void pushByKey(String string, String string2) {
        if (mdcAdapter == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        mdcAdapter.pushByKey(string, string2);
    }

    public static String popByKey(String string) {
        if (mdcAdapter == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        return mdcAdapter.popByKey(string);
    }

    public Deque<String> getCopyOfDequeByKey(String string) {
        if (mdcAdapter == null) {
            throw new IllegalStateException(MDC_APAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        return mdcAdapter.getCopyOfDequeByKey(string);
    }

    static {
        SLF4JServiceProvider sLF4JServiceProvider = LoggerFactory.getProvider();
        if (sLF4JServiceProvider != null) {
            mdcAdapter = sLF4JServiceProvider.getMDCAdapter();
        } else {
            Util.report("Failed to find provider.");
            Util.report("Defaulting to no-operation MDCAdapter implementation.");
            mdcAdapter = new NOPMDCAdapter();
        }
    }

    public static class MDCCloseable
    implements Closeable {
        private final String key;

        private MDCCloseable(String string) {
            this.key = string;
        }

        @Override
        public void close() {
            MDC.remove(this.key);
        }

        MDCCloseable(String string, 1 var2_2) {
            this(string);
        }
    }
}

