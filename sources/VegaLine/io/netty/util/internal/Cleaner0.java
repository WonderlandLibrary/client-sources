/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.PlatformDependent0;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

final class Cleaner0 {
    private static final long CLEANER_FIELD_OFFSET;
    private static final Method CLEAN_METHOD;
    private static final boolean CLEANER_IS_RUNNABLE;
    private static final InternalLogger logger;

    static void freeDirectBuffer(ByteBuffer buffer) {
        if (CLEANER_FIELD_OFFSET == -1L || !buffer.isDirect()) {
            return;
        }
        assert (CLEAN_METHOD != null || CLEANER_IS_RUNNABLE) : "CLEANER_FIELD_OFFSET != -1 implies CLEAN_METHOD != null or CLEANER_IS_RUNNABLE == true";
        try {
            Object cleaner = PlatformDependent0.getObject(buffer, CLEANER_FIELD_OFFSET);
            if (cleaner != null) {
                if (CLEANER_IS_RUNNABLE) {
                    ((Runnable)cleaner).run();
                } else {
                    CLEAN_METHOD.invoke(cleaner, new Object[0]);
                }
            }
        } catch (Throwable throwable) {
            // empty catch block
        }
    }

    private Cleaner0() {
    }

    static {
        logger = InternalLoggerFactory.getInstance(Cleaner0.class);
        ByteBuffer direct = ByteBuffer.allocateDirect(1);
        long fieldOffset = -1L;
        Method clean = null;
        boolean cleanerIsRunnable = false;
        Throwable error = null;
        if (PlatformDependent0.hasUnsafe()) {
            try {
                Field cleanerField = direct.getClass().getDeclaredField("cleaner");
                fieldOffset = PlatformDependent0.objectFieldOffset(cleanerField);
                Object cleaner = PlatformDependent0.getObject(direct, fieldOffset);
                try {
                    Runnable runnable = (Runnable)cleaner;
                    runnable.run();
                    cleanerIsRunnable = true;
                } catch (ClassCastException ignored) {
                    clean = cleaner.getClass().getDeclaredMethod("clean", new Class[0]);
                    clean.invoke(cleaner, new Object[0]);
                }
            } catch (Throwable t) {
                fieldOffset = -1L;
                clean = null;
                cleanerIsRunnable = false;
                error = t;
            }
        }
        if (error == null) {
            logger.debug("java.nio.ByteBuffer.cleaner(): available");
        } else {
            logger.debug("java.nio.ByteBuffer.cleaner(): unavailable", error);
        }
        CLEANER_FIELD_OFFSET = fieldOffset;
        CLEAN_METHOD = clean;
        CLEANER_IS_RUNNABLE = cleanerIsRunnable;
        Cleaner0.freeDirectBuffer(direct);
    }
}

