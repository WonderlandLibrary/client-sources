/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.epoll.Native;
import io.netty.channel.unix.FileDescriptor;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;

public final class Epoll {
    private static final Throwable UNAVAILABILITY_CAUSE;

    public static boolean isAvailable() {
        return UNAVAILABILITY_CAUSE == null;
    }

    public static void ensureAvailability() {
        if (UNAVAILABILITY_CAUSE != null) {
            throw (Error)new UnsatisfiedLinkError("failed to load the required native library").initCause(UNAVAILABILITY_CAUSE);
        }
    }

    public static Throwable unavailabilityCause() {
        return UNAVAILABILITY_CAUSE;
    }

    private Epoll() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        Throwable throwable = null;
        if (SystemPropertyUtil.getBoolean("io.netty.transport.noNative", false)) {
            throwable = new UnsupportedOperationException("Native transport was explicit disabled with -Dio.netty.transport.noNative=true");
        } else {
            FileDescriptor fileDescriptor = null;
            FileDescriptor fileDescriptor2 = null;
            try {
                fileDescriptor = Native.newEpollCreate();
                fileDescriptor2 = Native.newEventFd();
            } catch (Throwable throwable2) {
                throwable = throwable2;
            } finally {
                if (fileDescriptor != null) {
                    try {
                        fileDescriptor.close();
                    } catch (Exception exception) {}
                }
                if (fileDescriptor2 != null) {
                    try {
                        fileDescriptor2.close();
                    } catch (Exception exception) {}
                }
            }
        }
        UNAVAILABILITY_CAUSE = throwable != null ? throwable : (PlatformDependent.hasUnsafe() ? null : new IllegalStateException("sun.misc.Unsafe not available", PlatformDependent.getUnsafeUnavailabilityCause()));
    }
}

