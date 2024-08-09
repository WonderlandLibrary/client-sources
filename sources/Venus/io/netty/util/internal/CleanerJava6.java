/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.Cleaner;
import io.netty.util.internal.PlatformDependent0;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

final class CleanerJava6
implements Cleaner {
    private static final long CLEANER_FIELD_OFFSET;
    private static final Method CLEAN_METHOD;
    private static final InternalLogger logger;

    CleanerJava6() {
    }

    static boolean isSupported() {
        return CLEANER_FIELD_OFFSET != -1L;
    }

    @Override
    public void freeDirectBuffer(ByteBuffer byteBuffer) {
        if (!byteBuffer.isDirect()) {
            return;
        }
        try {
            Object object = PlatformDependent0.getObject(byteBuffer, CLEANER_FIELD_OFFSET);
            if (object != null) {
                CLEAN_METHOD.invoke(object, new Object[0]);
            }
        } catch (Throwable throwable) {
            PlatformDependent0.throwException(throwable);
        }
    }

    static {
        logger = InternalLoggerFactory.getInstance(CleanerJava6.class);
        long l = -1L;
        Method method = null;
        Throwable throwable = null;
        if (PlatformDependent0.hasUnsafe()) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1);
            try {
                Field field = byteBuffer.getClass().getDeclaredField("cleaner");
                l = PlatformDependent0.objectFieldOffset(field);
                Object object = PlatformDependent0.getObject(byteBuffer, l);
                method = object.getClass().getDeclaredMethod("clean", new Class[0]);
                method.invoke(object, new Object[0]);
            } catch (Throwable throwable2) {
                l = -1L;
                method = null;
                throwable = throwable2;
            }
        } else {
            throwable = new UnsupportedOperationException("sun.misc.Unsafe unavailable");
        }
        if (throwable == null) {
            logger.debug("java.nio.ByteBuffer.cleaner(): available");
        } else {
            logger.debug("java.nio.ByteBuffer.cleaner(): unavailable", throwable);
        }
        CLEANER_FIELD_OFFSET = l;
        CLEAN_METHOD = method;
    }
}

