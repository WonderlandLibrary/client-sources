/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.convert;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DateTypeConverter {
    private static final Map<Class<? extends Date>, MethodHandle> CONSTRUCTORS = new ConcurrentHashMap<Class<? extends Date>, MethodHandle>();

    public static <D extends Date> D fromMillis(long l, Class<D> clazz) {
        try {
            return (D)CONSTRUCTORS.get(clazz).invoke(l);
        } catch (Throwable throwable) {
            return null;
        }
    }

    private DateTypeConverter() {
    }

    static {
        MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        for (Class clazz : Arrays.asList(Date.class, java.sql.Date.class, Time.class, Timestamp.class)) {
            try {
                CONSTRUCTORS.put(clazz, lookup.findConstructor(clazz, MethodType.methodType(Void.TYPE, Long.TYPE)));
            } catch (IllegalAccessException | NoSuchMethodException reflectiveOperationException) {}
        }
    }
}

