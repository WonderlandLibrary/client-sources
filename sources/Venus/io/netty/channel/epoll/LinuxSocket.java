/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.DefaultFileRegion;
import io.netty.channel.epoll.EpollTcpInfo;
import io.netty.channel.epoll.Native;
import io.netty.channel.unix.Errors;
import io.netty.channel.unix.NativeInetAddress;
import io.netty.channel.unix.PeerCredentials;
import io.netty.channel.unix.Socket;
import io.netty.util.internal.ThrowableUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.ClosedChannelException;

final class LinuxSocket
extends Socket {
    private static final long MAX_UINT32_T = 0xFFFFFFFFL;
    private static final Errors.NativeIoException SENDFILE_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException("syscall:sendfile(...)", Errors.ERRNO_EPIPE_NEGATIVE);
    private static final ClosedChannelException SENDFILE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Native.class, "sendfile(...)");

    public LinuxSocket(int n) {
        super(n);
    }

    void setTcpDeferAccept(int n) throws IOException {
        LinuxSocket.setTcpDeferAccept(this.intValue(), n);
    }

    void setTcpQuickAck(boolean bl) throws IOException {
        LinuxSocket.setTcpQuickAck(this.intValue(), bl ? 1 : 0);
    }

    void setTcpCork(boolean bl) throws IOException {
        LinuxSocket.setTcpCork(this.intValue(), bl ? 1 : 0);
    }

    void setTcpNotSentLowAt(long l) throws IOException {
        if (l < 0L || l > 0xFFFFFFFFL) {
            throw new IllegalArgumentException("tcpNotSentLowAt must be a uint32_t");
        }
        LinuxSocket.setTcpNotSentLowAt(this.intValue(), (int)l);
    }

    void setTcpFastOpen(int n) throws IOException {
        LinuxSocket.setTcpFastOpen(this.intValue(), n);
    }

    void setTcpFastOpenConnect(boolean bl) throws IOException {
        LinuxSocket.setTcpFastOpenConnect(this.intValue(), bl ? 1 : 0);
    }

    boolean isTcpFastOpenConnect() throws IOException {
        return LinuxSocket.isTcpFastOpenConnect(this.intValue()) != 0;
    }

    void setTcpKeepIdle(int n) throws IOException {
        LinuxSocket.setTcpKeepIdle(this.intValue(), n);
    }

    void setTcpKeepIntvl(int n) throws IOException {
        LinuxSocket.setTcpKeepIntvl(this.intValue(), n);
    }

    void setTcpKeepCnt(int n) throws IOException {
        LinuxSocket.setTcpKeepCnt(this.intValue(), n);
    }

    void setTcpUserTimeout(int n) throws IOException {
        LinuxSocket.setTcpUserTimeout(this.intValue(), n);
    }

    void setIpFreeBind(boolean bl) throws IOException {
        LinuxSocket.setIpFreeBind(this.intValue(), bl ? 1 : 0);
    }

    void setIpTransparent(boolean bl) throws IOException {
        LinuxSocket.setIpTransparent(this.intValue(), bl ? 1 : 0);
    }

    void setIpRecvOrigDestAddr(boolean bl) throws IOException {
        LinuxSocket.setIpRecvOrigDestAddr(this.intValue(), bl ? 1 : 0);
    }

    void getTcpInfo(EpollTcpInfo epollTcpInfo) throws IOException {
        LinuxSocket.getTcpInfo(this.intValue(), epollTcpInfo.info);
    }

    void setTcpMd5Sig(InetAddress inetAddress, byte[] byArray) throws IOException {
        NativeInetAddress nativeInetAddress = NativeInetAddress.newInstance(inetAddress);
        LinuxSocket.setTcpMd5Sig(this.intValue(), nativeInetAddress.address(), nativeInetAddress.scopeId(), byArray);
    }

    boolean isTcpCork() throws IOException {
        return LinuxSocket.isTcpCork(this.intValue()) != 0;
    }

    int getTcpDeferAccept() throws IOException {
        return LinuxSocket.getTcpDeferAccept(this.intValue());
    }

    boolean isTcpQuickAck() throws IOException {
        return LinuxSocket.isTcpQuickAck(this.intValue()) != 0;
    }

    long getTcpNotSentLowAt() throws IOException {
        return (long)LinuxSocket.getTcpNotSentLowAt(this.intValue()) & 0xFFFFFFFFL;
    }

    int getTcpKeepIdle() throws IOException {
        return LinuxSocket.getTcpKeepIdle(this.intValue());
    }

    int getTcpKeepIntvl() throws IOException {
        return LinuxSocket.getTcpKeepIntvl(this.intValue());
    }

    int getTcpKeepCnt() throws IOException {
        return LinuxSocket.getTcpKeepCnt(this.intValue());
    }

    int getTcpUserTimeout() throws IOException {
        return LinuxSocket.getTcpUserTimeout(this.intValue());
    }

    boolean isIpFreeBind() throws IOException {
        return LinuxSocket.isIpFreeBind(this.intValue()) != 0;
    }

    boolean isIpTransparent() throws IOException {
        return LinuxSocket.isIpTransparent(this.intValue()) != 0;
    }

    boolean isIpRecvOrigDestAddr() throws IOException {
        return LinuxSocket.isIpRecvOrigDestAddr(this.intValue()) != 0;
    }

    PeerCredentials getPeerCredentials() throws IOException {
        return LinuxSocket.getPeerCredentials(this.intValue());
    }

    long sendFile(DefaultFileRegion defaultFileRegion, long l, long l2, long l3) throws IOException {
        defaultFileRegion.open();
        long l4 = LinuxSocket.sendFile(this.intValue(), defaultFileRegion, l, l2, l3);
        if (l4 >= 0L) {
            return l4;
        }
        return Errors.ioResult("sendfile", (int)l4, SENDFILE_CONNECTION_RESET_EXCEPTION, SENDFILE_CLOSED_CHANNEL_EXCEPTION);
    }

    public static LinuxSocket newSocketStream() {
        return new LinuxSocket(LinuxSocket.newSocketStream0());
    }

    public static LinuxSocket newSocketDgram() {
        return new LinuxSocket(LinuxSocket.newSocketDgram0());
    }

    public static LinuxSocket newSocketDomain() {
        return new LinuxSocket(LinuxSocket.newSocketDomain0());
    }

    private static native long sendFile(int var0, DefaultFileRegion var1, long var2, long var4, long var6) throws IOException;

    private static native int getTcpDeferAccept(int var0) throws IOException;

    private static native int isTcpQuickAck(int var0) throws IOException;

    private static native int isTcpCork(int var0) throws IOException;

    private static native int getTcpNotSentLowAt(int var0) throws IOException;

    private static native int getTcpKeepIdle(int var0) throws IOException;

    private static native int getTcpKeepIntvl(int var0) throws IOException;

    private static native int getTcpKeepCnt(int var0) throws IOException;

    private static native int getTcpUserTimeout(int var0) throws IOException;

    private static native int isIpFreeBind(int var0) throws IOException;

    private static native int isIpTransparent(int var0) throws IOException;

    private static native int isIpRecvOrigDestAddr(int var0) throws IOException;

    private static native void getTcpInfo(int var0, long[] var1) throws IOException;

    private static native PeerCredentials getPeerCredentials(int var0) throws IOException;

    private static native int isTcpFastOpenConnect(int var0) throws IOException;

    private static native void setTcpDeferAccept(int var0, int var1) throws IOException;

    private static native void setTcpQuickAck(int var0, int var1) throws IOException;

    private static native void setTcpCork(int var0, int var1) throws IOException;

    private static native void setTcpNotSentLowAt(int var0, int var1) throws IOException;

    private static native void setTcpFastOpen(int var0, int var1) throws IOException;

    private static native void setTcpFastOpenConnect(int var0, int var1) throws IOException;

    private static native void setTcpKeepIdle(int var0, int var1) throws IOException;

    private static native void setTcpKeepIntvl(int var0, int var1) throws IOException;

    private static native void setTcpKeepCnt(int var0, int var1) throws IOException;

    private static native void setTcpUserTimeout(int var0, int var1) throws IOException;

    private static native void setIpFreeBind(int var0, int var1) throws IOException;

    private static native void setIpTransparent(int var0, int var1) throws IOException;

    private static native void setIpRecvOrigDestAddr(int var0, int var1) throws IOException;

    private static native void setTcpMd5Sig(int var0, byte[] var1, int var2, byte[] var3) throws IOException;
}

