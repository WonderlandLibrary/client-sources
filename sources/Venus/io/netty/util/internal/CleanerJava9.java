/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.Cleaner;
import io.netty.util.internal.PlatformDependent0;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

final class CleanerJava9
implements Cleaner {
    private static final InternalLogger logger;
    private static final Method INVOKE_CLEANER;

    CleanerJava9() {
    }

    static boolean isSupported() {
        return INVOKE_CLEANER != null;
    }

    @Override
    public void freeDirectBuffer(ByteBuffer byteBuffer) {
        try {
            INVOKE_CLEANER.invoke(PlatformDependent0.UNSAFE, byteBuffer);
        } catch (Throwable throwable) {
            PlatformDependent0.throwException(throwable);
        }
    }

    static {
        Throwable throwable;
        Method method;
        logger = InternalLoggerFactory.getInstance(CleanerJava9.class);
        if (PlatformDependent0.hasUnsafe()) {
            Object object;
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1);
            try {
                Method method2 = PlatformDependent0.UNSAFE.getClass().getDeclaredMethod("invokeCleaner", ByteBuffer.class);
                method2.invoke(PlatformDependent0.UNSAFE, byteBuffer);
                object = method2;
            } catch (NoSuchMethodException noSuchMethodException) {
                object = noSuchMethodException;
            } catch (InvocationTargetException invocationTargetException) {
                object = invocationTargetException;
            } catch (IllegalAccessException illegalAccessException) {
                object = illegalAccessException;
            }
            if (object instanceof Throwable) {
                method = null;
                throwable = (Throwable)object;
            } else {
                method = (Method)object;
                throwable = null;
            }
        } else {
            method = null;
            throwable = new UnsupportedOperationException("sun.misc.Unsafe unavailable");
        }
        if (throwable == null) {
            logger.debug("java.nio.ByteBuffer.cleaner(): available");
        } else {
            logger.debug("java.nio.ByteBuffer.cleaner(): unavailable", throwable);
        }
        INVOKE_CLEANER = method;
    }
}

