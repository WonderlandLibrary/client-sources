/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.epoll.EpollEventArray;
import io.netty.channel.epoll.NativeDatagramPacketArray;
import io.netty.channel.epoll.NativeStaticallyReferencedJniMethods;
import io.netty.channel.unix.Errors;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import io.netty.util.internal.NativeLibraryLoader;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.Locale;

public final class Native {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Native.class);
    public static final int EPOLLIN;
    public static final int EPOLLOUT;
    public static final int EPOLLRDHUP;
    public static final int EPOLLET;
    public static final int EPOLLERR;
    public static final boolean IS_SUPPORTING_SENDMMSG;
    public static final boolean IS_SUPPORTING_TCP_FASTOPEN;
    public static final int TCP_MD5SIG_MAXKEYLEN;
    public static final String KERNEL_VERSION;
    private static final Errors.NativeIoException SENDMMSG_CONNECTION_RESET_EXCEPTION;
    private static final Errors.NativeIoException SPLICE_CONNECTION_RESET_EXCEPTION;
    private static final ClosedChannelException SENDMMSG_CLOSED_CHANNEL_EXCEPTION;
    private static final ClosedChannelException SPLICE_CLOSED_CHANNEL_EXCEPTION;

    public static FileDescriptor newEventFd() {
        return new FileDescriptor(Native.eventFd());
    }

    public static FileDescriptor newTimerFd() {
        return new FileDescriptor(Native.timerFd());
    }

    private static native int eventFd();

    private static native int timerFd();

    public static native void eventFdWrite(int var0, long var1);

    public static native void eventFdRead(int var0);

    static native void timerFdRead(int var0);

    public static FileDescriptor newEpollCreate() {
        return new FileDescriptor(Native.epollCreate());
    }

    private static native int epollCreate();

    public static int epollWait(FileDescriptor fileDescriptor, EpollEventArray epollEventArray, FileDescriptor fileDescriptor2, int n, int n2) throws IOException {
        int n3 = Native.epollWait0(fileDescriptor.intValue(), epollEventArray.memoryAddress(), epollEventArray.length(), fileDescriptor2.intValue(), n, n2);
        if (n3 < 0) {
            throw Errors.newIOException("epoll_wait", n3);
        }
        return n3;
    }

    private static native int epollWait0(int var0, long var1, int var3, int var4, int var5, int var6);

    public static void epollCtlAdd(int n, int n2, int n3) throws IOException {
        int n4 = Native.epollCtlAdd0(n, n2, n3);
        if (n4 < 0) {
            throw Errors.newIOException("epoll_ctl", n4);
        }
    }

    private static native int epollCtlAdd0(int var0, int var1, int var2);

    public static void epollCtlMod(int n, int n2, int n3) throws IOException {
        int n4 = Native.epollCtlMod0(n, n2, n3);
        if (n4 < 0) {
            throw Errors.newIOException("epoll_ctl", n4);
        }
    }

    private static native int epollCtlMod0(int var0, int var1, int var2);

    public static void epollCtlDel(int n, int n2) throws IOException {
        int n3 = Native.epollCtlDel0(n, n2);
        if (n3 < 0) {
            throw Errors.newIOException("epoll_ctl", n3);
        }
    }

    private static native int epollCtlDel0(int var0, int var1);

    public static int splice(int n, long l, int n2, long l2, long l3) throws IOException {
        int n3 = Native.splice0(n, l, n2, l2, l3);
        if (n3 >= 0) {
            return n3;
        }
        return Errors.ioResult("splice", n3, SPLICE_CONNECTION_RESET_EXCEPTION, SPLICE_CLOSED_CHANNEL_EXCEPTION);
    }

    private static native int splice0(int var0, long var1, int var3, long var4, long var6);

    public static int sendmmsg(int n, NativeDatagramPacketArray.NativeDatagramPacket[] nativeDatagramPacketArray, int n2, int n3) throws IOException {
        int n4 = Native.sendmmsg0(n, nativeDatagramPacketArray, n2, n3);
        if (n4 >= 0) {
            return n4;
        }
        return Errors.ioResult("sendmmsg", n4, SENDMMSG_CONNECTION_RESET_EXCEPTION, SENDMMSG_CLOSED_CHANNEL_EXCEPTION);
    }

    private static native int sendmmsg0(int var0, NativeDatagramPacketArray.NativeDatagramPacket[] var1, int var2, int var3);

    public static native int sizeofEpollEvent();

    public static native int offsetofEpollData();

    private static void loadNativeLibrary() {
        String string = SystemPropertyUtil.get("os.name").toLowerCase(Locale.UK).trim();
        if (!string.startsWith("linux")) {
            throw new IllegalStateException("Only supported on Linux");
        }
        String string2 = "netty_transport_native_epoll";
        String string3 = string2 + '_' + PlatformDependent.normalizedArch();
        ClassLoader classLoader = PlatformDependent.getClassLoader(Native.class);
        try {
            NativeLibraryLoader.load(string3, classLoader);
        } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            try {
                NativeLibraryLoader.load(string2, classLoader);
                logger.debug("Failed to load {}", (Object)string3, (Object)unsatisfiedLinkError);
            } catch (UnsatisfiedLinkError unsatisfiedLinkError2) {
                ThrowableUtil.addSuppressed((Throwable)unsatisfiedLinkError, unsatisfiedLinkError2);
                throw unsatisfiedLinkError;
            }
        }
    }

    private Native() {
    }

    static {
        try {
            Native.offsetofEpollData();
        } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            Native.loadNativeLibrary();
        }
        Socket.initialize();
        EPOLLIN = NativeStaticallyReferencedJniMethods.epollin();
        EPOLLOUT = NativeStaticallyReferencedJniMethods.epollout();
        EPOLLRDHUP = NativeStaticallyReferencedJniMethods.epollrdhup();
        EPOLLET = NativeStaticallyReferencedJniMethods.epollet();
        EPOLLERR = NativeStaticallyReferencedJniMethods.epollerr();
        IS_SUPPORTING_SENDMMSG = NativeStaticallyReferencedJniMethods.isSupportingSendmmsg();
        IS_SUPPORTING_TCP_FASTOPEN = NativeStaticallyReferencedJniMethods.isSupportingTcpFastopen();
        TCP_MD5SIG_MAXKEYLEN = NativeStaticallyReferencedJniMethods.tcpMd5SigMaxKeyLen();
        KERNEL_VERSION = NativeStaticallyReferencedJniMethods.kernelVersion();
        SENDMMSG_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Native.class, "sendmmsg(...)");
        SPLICE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Native.class, "splice(...)");
        SENDMMSG_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException("syscall:sendmmsg(...)", Errors.ERRNO_EPIPE_NEGATIVE);
        SPLICE_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException("syscall:splice(...)", Errors.ERRNO_EPIPE_NEGATIVE);
    }
}

