/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.DefaultFileRegion;
import io.netty.channel.epoll.EpollEventArray;
import io.netty.channel.epoll.EpollTcpInfo;
import io.netty.channel.epoll.NativeDatagramPacketArray;
import io.netty.channel.epoll.NativeStaticallyReferencedJniMethods;
import io.netty.channel.unix.Errors;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.NativeInetAddress;
import io.netty.util.internal.NativeLibraryLoader;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThrowableUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.ClosedChannelException;
import java.util.Locale;

public final class Native {
    public static final int EPOLLIN;
    public static final int EPOLLOUT;
    public static final int EPOLLRDHUP;
    public static final int EPOLLET;
    public static final int EPOLLERR;
    public static final int IOV_MAX;
    public static final int UIO_MAX_IOV;
    public static final boolean IS_SUPPORTING_SENDMMSG;
    public static final boolean IS_SUPPORTING_TCP_FASTOPEN;
    public static final long SSIZE_MAX;
    public static final int TCP_MD5SIG_MAXKEYLEN;
    public static final String KERNEL_VERSION;
    private static final Errors.NativeIoException SENDFILE_CONNECTION_RESET_EXCEPTION;
    private static final Errors.NativeIoException SENDMMSG_CONNECTION_RESET_EXCEPTION;
    private static final Errors.NativeIoException SPLICE_CONNECTION_RESET_EXCEPTION;
    private static final ClosedChannelException SENDFILE_CLOSED_CHANNEL_EXCEPTION;
    private static final ClosedChannelException SENDMMSG_CLOSED_CHANNEL_EXCEPTION;
    private static final ClosedChannelException SPLICE_CLOSED_CHANNEL_EXCEPTION;

    public static FileDescriptor newEventFd() {
        return new FileDescriptor(Native.eventFd());
    }

    private static native int eventFd();

    public static native void eventFdWrite(int var0, long var1);

    public static native void eventFdRead(int var0);

    public static FileDescriptor newEpollCreate() {
        return new FileDescriptor(Native.epollCreate());
    }

    private static native int epollCreate();

    public static int epollWait(int efd, EpollEventArray events, int timeout) throws IOException {
        int ready = Native.epollWait0(efd, events.memoryAddress(), events.length(), timeout);
        if (ready < 0) {
            throw Errors.newIOException("epoll_wait", ready);
        }
        return ready;
    }

    private static native int epollWait0(int var0, long var1, int var3, int var4);

    public static void epollCtlAdd(int efd, int fd, int flags) throws IOException {
        int res = Native.epollCtlAdd0(efd, fd, flags);
        if (res < 0) {
            throw Errors.newIOException("epoll_ctl", res);
        }
    }

    private static native int epollCtlAdd0(int var0, int var1, int var2);

    public static void epollCtlMod(int efd, int fd, int flags) throws IOException {
        int res = Native.epollCtlMod0(efd, fd, flags);
        if (res < 0) {
            throw Errors.newIOException("epoll_ctl", res);
        }
    }

    private static native int epollCtlMod0(int var0, int var1, int var2);

    public static void epollCtlDel(int efd, int fd) throws IOException {
        int res = Native.epollCtlDel0(efd, fd);
        if (res < 0) {
            throw Errors.newIOException("epoll_ctl", res);
        }
    }

    private static native int epollCtlDel0(int var0, int var1);

    public static int splice(int fd, long offIn, int fdOut, long offOut, long len) throws IOException {
        int res = Native.splice0(fd, offIn, fdOut, offOut, len);
        if (res >= 0) {
            return res;
        }
        return Errors.ioResult("splice", res, SPLICE_CONNECTION_RESET_EXCEPTION, SPLICE_CLOSED_CHANNEL_EXCEPTION);
    }

    private static native int splice0(int var0, long var1, int var3, long var4, long var6);

    public static long sendfile(int dest, DefaultFileRegion src, long baseOffset, long offset, long length) throws IOException {
        src.open();
        long res = Native.sendfile0(dest, src, baseOffset, offset, length);
        if (res >= 0L) {
            return res;
        }
        return Errors.ioResult("sendfile", (int)res, SENDFILE_CONNECTION_RESET_EXCEPTION, SENDFILE_CLOSED_CHANNEL_EXCEPTION);
    }

    private static native long sendfile0(int var0, DefaultFileRegion var1, long var2, long var4, long var6) throws IOException;

    public static int sendmmsg(int fd, NativeDatagramPacketArray.NativeDatagramPacket[] msgs, int offset, int len) throws IOException {
        int res = Native.sendmmsg0(fd, msgs, offset, len);
        if (res >= 0) {
            return res;
        }
        return Errors.ioResult("sendmmsg", res, SENDMMSG_CONNECTION_RESET_EXCEPTION, SENDMMSG_CLOSED_CHANNEL_EXCEPTION);
    }

    private static native int sendmmsg0(int var0, NativeDatagramPacketArray.NativeDatagramPacket[] var1, int var2, int var3);

    public static int recvFd(int fd) throws IOException {
        int res = Native.recvFd0(fd);
        if (res > 0) {
            return res;
        }
        if (res == 0) {
            return -1;
        }
        if (res == Errors.ERRNO_EAGAIN_NEGATIVE || res == Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
            return 0;
        }
        throw Errors.newIOException("recvFd", res);
    }

    private static native int recvFd0(int var0);

    public static int sendFd(int socketFd, int fd) throws IOException {
        int res = Native.sendFd0(socketFd, fd);
        if (res >= 0) {
            return res;
        }
        if (res == Errors.ERRNO_EAGAIN_NEGATIVE || res == Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
            return -1;
        }
        throw Errors.newIOException("sendFd", res);
    }

    private static native int sendFd0(int var0, int var1);

    public static native int isReuseAddress(int var0) throws IOException;

    public static native int isReusePort(int var0) throws IOException;

    public static native int getTcpNotSentLowAt(int var0) throws IOException;

    public static native int getTrafficClass(int var0) throws IOException;

    public static native int isBroadcast(int var0) throws IOException;

    public static native int getTcpKeepIdle(int var0) throws IOException;

    public static native int getTcpKeepIntvl(int var0) throws IOException;

    public static native int getTcpKeepCnt(int var0) throws IOException;

    public static native int getTcpUserTimeout(int var0) throws IOException;

    public static native int isIpFreeBind(int var0) throws IOException;

    public static native void setReuseAddress(int var0, int var1) throws IOException;

    public static native void setReusePort(int var0, int var1) throws IOException;

    public static native void setTcpFastopen(int var0, int var1) throws IOException;

    public static native void setTcpNotSentLowAt(int var0, int var1) throws IOException;

    public static native void setTrafficClass(int var0, int var1) throws IOException;

    public static native void setBroadcast(int var0, int var1) throws IOException;

    public static native void setTcpKeepIdle(int var0, int var1) throws IOException;

    public static native void setTcpKeepIntvl(int var0, int var1) throws IOException;

    public static native void setTcpKeepCnt(int var0, int var1) throws IOException;

    public static native void setTcpUserTimeout(int var0, int var1) throws IOException;

    public static native void setIpFreeBind(int var0, int var1) throws IOException;

    public static void tcpInfo(int fd, EpollTcpInfo info) throws IOException {
        Native.tcpInfo0(fd, info.info);
    }

    private static native void tcpInfo0(int var0, int[] var1) throws IOException;

    public static void setTcpMd5Sig(int fd, InetAddress address, byte[] key) throws IOException {
        NativeInetAddress a = NativeInetAddress.newInstance(address);
        Native.setTcpMd5Sig0(fd, a.address(), a.scopeId(), key);
    }

    private static native void setTcpMd5Sig0(int var0, byte[] var1, int var2, byte[] var3) throws IOException;

    public static native int sizeofEpollEvent();

    public static native int offsetofEpollData();

    private Native() {
    }

    private static void loadNativeLibrary() {
        String name = SystemPropertyUtil.get("os.name").toLowerCase(Locale.UK).trim();
        if (!name.startsWith("linux")) {
            throw new IllegalStateException("Only supported on Linux");
        }
        NativeLibraryLoader.load(SystemPropertyUtil.get("io.netty.packagePrefix", "").replace('.', '-') + "netty-transport-native-epoll", PlatformDependent.getClassLoader(Native.class));
    }

    static {
        try {
            Native.offsetofEpollData();
        } catch (UnsatisfiedLinkError ignore) {
            Native.loadNativeLibrary();
        }
        EPOLLIN = NativeStaticallyReferencedJniMethods.epollin();
        EPOLLOUT = NativeStaticallyReferencedJniMethods.epollout();
        EPOLLRDHUP = NativeStaticallyReferencedJniMethods.epollrdhup();
        EPOLLET = NativeStaticallyReferencedJniMethods.epollet();
        EPOLLERR = NativeStaticallyReferencedJniMethods.epollerr();
        IOV_MAX = NativeStaticallyReferencedJniMethods.iovMax();
        UIO_MAX_IOV = NativeStaticallyReferencedJniMethods.uioMaxIov();
        IS_SUPPORTING_SENDMMSG = NativeStaticallyReferencedJniMethods.isSupportingSendmmsg();
        IS_SUPPORTING_TCP_FASTOPEN = NativeStaticallyReferencedJniMethods.isSupportingTcpFastopen();
        SSIZE_MAX = NativeStaticallyReferencedJniMethods.ssizeMax();
        TCP_MD5SIG_MAXKEYLEN = NativeStaticallyReferencedJniMethods.tcpMd5SigMaxKeyLen();
        KERNEL_VERSION = NativeStaticallyReferencedJniMethods.kernelVersion();
        SENDFILE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Native.class, "sendfile(...)");
        SENDMMSG_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Native.class, "sendmmsg(...)");
        SPLICE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Native.class, "splice(...)");
        SENDFILE_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException("syscall:sendfile(...)", Errors.ERRNO_EPIPE_NEGATIVE);
        SENDMMSG_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException("syscall:sendmmsg(...)", Errors.ERRNO_EPIPE_NEGATIVE);
        SPLICE_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException("syscall:splice(...)", Errors.ERRNO_EPIPE_NEGATIVE);
    }
}

