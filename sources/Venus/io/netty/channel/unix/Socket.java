/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.unix;

import io.netty.channel.ChannelException;
import io.netty.channel.unix.DatagramSocketAddress;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.unix.Errors;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.LimitsStaticallyReferencedJniMethods;
import io.netty.channel.unix.NativeInetAddress;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import io.netty.util.internal.ThrowableUtil;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Socket
extends FileDescriptor {
    private static final ClosedChannelException SHUTDOWN_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Socket.class, "shutdown(..)");
    private static final ClosedChannelException SEND_TO_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Socket.class, "sendTo(..)");
    private static final ClosedChannelException SEND_TO_ADDRESS_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Socket.class, "sendToAddress(..)");
    private static final ClosedChannelException SEND_TO_ADDRESSES_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Socket.class, "sendToAddresses(..)");
    private static final Errors.NativeIoException SEND_TO_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:sendto", Errors.ERRNO_EPIPE_NEGATIVE), Socket.class, "sendTo(..)");
    private static final Errors.NativeIoException SEND_TO_ADDRESS_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:sendto", Errors.ERRNO_EPIPE_NEGATIVE), Socket.class, "sendToAddress");
    private static final Errors.NativeIoException CONNECTION_RESET_EXCEPTION_SENDMSG = ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:sendmsg", Errors.ERRNO_EPIPE_NEGATIVE), Socket.class, "sendToAddresses(..)");
    private static final Errors.NativeIoException CONNECTION_RESET_SHUTDOWN_EXCEPTION = ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:shutdown", Errors.ERRNO_ECONNRESET_NEGATIVE), Socket.class, "shutdown");
    private static final Errors.NativeConnectException FINISH_CONNECT_REFUSED_EXCEPTION = ThrowableUtil.unknownStackTrace(new Errors.NativeConnectException("syscall:getsockopt", Errors.ERROR_ECONNREFUSED_NEGATIVE), Socket.class, "finishConnect(..)");
    private static final Errors.NativeConnectException CONNECT_REFUSED_EXCEPTION = ThrowableUtil.unknownStackTrace(new Errors.NativeConnectException("syscall:connect", Errors.ERROR_ECONNREFUSED_NEGATIVE), Socket.class, "connect(..)");
    public static final int UDS_SUN_PATH_SIZE = LimitsStaticallyReferencedJniMethods.udsSunPathSize();
    private static final AtomicBoolean INITIALIZED = new AtomicBoolean();

    public Socket(int n) {
        super(n);
    }

    public final void shutdown() throws IOException {
        this.shutdown(true, false);
    }

    public final void shutdown(boolean bl, boolean bl2) throws IOException {
        int n;
        int n2;
        do {
            if (Socket.isClosed(n2 = this.state)) {
                throw new ClosedChannelException();
            }
            n = n2;
            if (bl && !Socket.isInputShutdown(n)) {
                n = Socket.inputShutdown(n);
            }
            if (bl2 && !Socket.isOutputShutdown(n)) {
                n = Socket.outputShutdown(n);
            }
            if (n != n2) continue;
            return;
        } while (!this.casState(n2, n));
        n2 = Socket.shutdown(this.fd, bl, bl2);
        if (n2 < 0) {
            Errors.ioResult("shutdown", n2, CONNECTION_RESET_SHUTDOWN_EXCEPTION, SHUTDOWN_CLOSED_CHANNEL_EXCEPTION);
        }
    }

    public final boolean isShutdown() {
        int n = this.state;
        return Socket.isInputShutdown(n) && Socket.isOutputShutdown(n);
    }

    public final boolean isInputShutdown() {
        return Socket.isInputShutdown(this.state);
    }

    public final boolean isOutputShutdown() {
        return Socket.isOutputShutdown(this.state);
    }

    public final int sendTo(ByteBuffer byteBuffer, int n, int n2, InetAddress inetAddress, int n3) throws IOException {
        int n4;
        byte[] byArray;
        if (inetAddress instanceof Inet6Address) {
            byArray = inetAddress.getAddress();
            n4 = ((Inet6Address)inetAddress).getScopeId();
        } else {
            n4 = 0;
            byArray = NativeInetAddress.ipv4MappedIpv6Address(inetAddress.getAddress());
        }
        int n5 = Socket.sendTo(this.fd, byteBuffer, n, n2, byArray, n4, n3);
        if (n5 >= 0) {
            return n5;
        }
        if (n5 == Errors.ERROR_ECONNREFUSED_NEGATIVE) {
            throw new PortUnreachableException("sendTo failed");
        }
        return Errors.ioResult("sendTo", n5, SEND_TO_CONNECTION_RESET_EXCEPTION, SEND_TO_CLOSED_CHANNEL_EXCEPTION);
    }

    public final int sendToAddress(long l, int n, int n2, InetAddress inetAddress, int n3) throws IOException {
        int n4;
        byte[] byArray;
        if (inetAddress instanceof Inet6Address) {
            byArray = inetAddress.getAddress();
            n4 = ((Inet6Address)inetAddress).getScopeId();
        } else {
            n4 = 0;
            byArray = NativeInetAddress.ipv4MappedIpv6Address(inetAddress.getAddress());
        }
        int n5 = Socket.sendToAddress(this.fd, l, n, n2, byArray, n4, n3);
        if (n5 >= 0) {
            return n5;
        }
        if (n5 == Errors.ERROR_ECONNREFUSED_NEGATIVE) {
            throw new PortUnreachableException("sendToAddress failed");
        }
        return Errors.ioResult("sendToAddress", n5, SEND_TO_ADDRESS_CONNECTION_RESET_EXCEPTION, SEND_TO_ADDRESS_CLOSED_CHANNEL_EXCEPTION);
    }

    public final int sendToAddresses(long l, int n, InetAddress inetAddress, int n2) throws IOException {
        int n3;
        byte[] byArray;
        if (inetAddress instanceof Inet6Address) {
            byArray = inetAddress.getAddress();
            n3 = ((Inet6Address)inetAddress).getScopeId();
        } else {
            n3 = 0;
            byArray = NativeInetAddress.ipv4MappedIpv6Address(inetAddress.getAddress());
        }
        int n4 = Socket.sendToAddresses(this.fd, l, n, byArray, n3, n2);
        if (n4 >= 0) {
            return n4;
        }
        if (n4 == Errors.ERROR_ECONNREFUSED_NEGATIVE) {
            throw new PortUnreachableException("sendToAddresses failed");
        }
        return Errors.ioResult("sendToAddresses", n4, CONNECTION_RESET_EXCEPTION_SENDMSG, SEND_TO_ADDRESSES_CLOSED_CHANNEL_EXCEPTION);
    }

    public final DatagramSocketAddress recvFrom(ByteBuffer byteBuffer, int n, int n2) throws IOException {
        return Socket.recvFrom(this.fd, byteBuffer, n, n2);
    }

    public final DatagramSocketAddress recvFromAddress(long l, int n, int n2) throws IOException {
        return Socket.recvFromAddress(this.fd, l, n, n2);
    }

    public final int recvFd() throws IOException {
        int n = Socket.recvFd(this.fd);
        if (n > 0) {
            return n;
        }
        if (n == 0) {
            return 1;
        }
        if (n == Errors.ERRNO_EAGAIN_NEGATIVE || n == Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
            return 1;
        }
        throw Errors.newIOException("recvFd", n);
    }

    public final int sendFd(int n) throws IOException {
        int n2 = Socket.sendFd(this.fd, n);
        if (n2 >= 0) {
            return n2;
        }
        if (n2 == Errors.ERRNO_EAGAIN_NEGATIVE || n2 == Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
            return 1;
        }
        throw Errors.newIOException("sendFd", n2);
    }

    public final boolean connect(SocketAddress socketAddress) throws IOException {
        int n;
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
            NativeInetAddress nativeInetAddress = NativeInetAddress.newInstance(inetSocketAddress.getAddress());
            n = Socket.connect(this.fd, nativeInetAddress.address, nativeInetAddress.scopeId, inetSocketAddress.getPort());
        } else if (socketAddress instanceof DomainSocketAddress) {
            DomainSocketAddress domainSocketAddress = (DomainSocketAddress)socketAddress;
            n = Socket.connectDomainSocket(this.fd, domainSocketAddress.path().getBytes(CharsetUtil.UTF_8));
        } else {
            throw new Error("Unexpected SocketAddress implementation " + socketAddress);
        }
        if (n < 0) {
            if (n == Errors.ERRNO_EINPROGRESS_NEGATIVE) {
                return true;
            }
            Errors.throwConnectException("connect", CONNECT_REFUSED_EXCEPTION, n);
        }
        return false;
    }

    public final boolean finishConnect() throws IOException {
        int n = Socket.finishConnect(this.fd);
        if (n < 0) {
            if (n == Errors.ERRNO_EINPROGRESS_NEGATIVE) {
                return true;
            }
            Errors.throwConnectException("finishConnect", FINISH_CONNECT_REFUSED_EXCEPTION, n);
        }
        return false;
    }

    public final void disconnect() throws IOException {
        int n = Socket.disconnect(this.fd);
        if (n < 0) {
            Errors.throwConnectException("disconnect", FINISH_CONNECT_REFUSED_EXCEPTION, n);
        }
    }

    public final void bind(SocketAddress socketAddress) throws IOException {
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
            NativeInetAddress nativeInetAddress = NativeInetAddress.newInstance(inetSocketAddress.getAddress());
            int n = Socket.bind(this.fd, nativeInetAddress.address, nativeInetAddress.scopeId, inetSocketAddress.getPort());
            if (n < 0) {
                throw Errors.newIOException("bind", n);
            }
        } else if (socketAddress instanceof DomainSocketAddress) {
            DomainSocketAddress domainSocketAddress = (DomainSocketAddress)socketAddress;
            int n = Socket.bindDomainSocket(this.fd, domainSocketAddress.path().getBytes(CharsetUtil.UTF_8));
            if (n < 0) {
                throw Errors.newIOException("bind", n);
            }
        } else {
            throw new Error("Unexpected SocketAddress implementation " + socketAddress);
        }
    }

    public final void listen(int n) throws IOException {
        int n2 = Socket.listen(this.fd, n);
        if (n2 < 0) {
            throw Errors.newIOException("listen", n2);
        }
    }

    public final int accept(byte[] byArray) throws IOException {
        int n = Socket.accept(this.fd, byArray);
        if (n >= 0) {
            return n;
        }
        if (n == Errors.ERRNO_EAGAIN_NEGATIVE || n == Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
            return 1;
        }
        throw Errors.newIOException("accept", n);
    }

    public final InetSocketAddress remoteAddress() {
        byte[] byArray = Socket.remoteAddress(this.fd);
        return byArray == null ? null : NativeInetAddress.address(byArray, 0, byArray.length);
    }

    public final InetSocketAddress localAddress() {
        byte[] byArray = Socket.localAddress(this.fd);
        return byArray == null ? null : NativeInetAddress.address(byArray, 0, byArray.length);
    }

    public final int getReceiveBufferSize() throws IOException {
        return Socket.getReceiveBufferSize(this.fd);
    }

    public final int getSendBufferSize() throws IOException {
        return Socket.getSendBufferSize(this.fd);
    }

    public final boolean isKeepAlive() throws IOException {
        return Socket.isKeepAlive(this.fd) != 0;
    }

    public final boolean isTcpNoDelay() throws IOException {
        return Socket.isTcpNoDelay(this.fd) != 0;
    }

    public final boolean isReuseAddress() throws IOException {
        return Socket.isReuseAddress(this.fd) != 0;
    }

    public final boolean isReusePort() throws IOException {
        return Socket.isReusePort(this.fd) != 0;
    }

    public final boolean isBroadcast() throws IOException {
        return Socket.isBroadcast(this.fd) != 0;
    }

    public final int getSoLinger() throws IOException {
        return Socket.getSoLinger(this.fd);
    }

    public final int getSoError() throws IOException {
        return Socket.getSoError(this.fd);
    }

    public final int getTrafficClass() throws IOException {
        return Socket.getTrafficClass(this.fd);
    }

    public final void setKeepAlive(boolean bl) throws IOException {
        Socket.setKeepAlive(this.fd, bl ? 1 : 0);
    }

    public final void setReceiveBufferSize(int n) throws IOException {
        Socket.setReceiveBufferSize(this.fd, n);
    }

    public final void setSendBufferSize(int n) throws IOException {
        Socket.setSendBufferSize(this.fd, n);
    }

    public final void setTcpNoDelay(boolean bl) throws IOException {
        Socket.setTcpNoDelay(this.fd, bl ? 1 : 0);
    }

    public final void setSoLinger(int n) throws IOException {
        Socket.setSoLinger(this.fd, n);
    }

    public final void setReuseAddress(boolean bl) throws IOException {
        Socket.setReuseAddress(this.fd, bl ? 1 : 0);
    }

    public final void setReusePort(boolean bl) throws IOException {
        Socket.setReusePort(this.fd, bl ? 1 : 0);
    }

    public final void setBroadcast(boolean bl) throws IOException {
        Socket.setBroadcast(this.fd, bl ? 1 : 0);
    }

    public final void setTrafficClass(int n) throws IOException {
        Socket.setTrafficClass(this.fd, n);
    }

    @Override
    public String toString() {
        return "Socket{fd=" + this.fd + '}';
    }

    public static Socket newSocketStream() {
        return new Socket(Socket.newSocketStream0());
    }

    public static Socket newSocketDgram() {
        return new Socket(Socket.newSocketDgram0());
    }

    public static Socket newSocketDomain() {
        return new Socket(Socket.newSocketDomain0());
    }

    public static void initialize() {
        if (INITIALIZED.compareAndSet(false, false)) {
            Socket.initialize(NetUtil.isIpV4StackPreferred());
        }
    }

    protected static int newSocketStream0() {
        int n = Socket.newSocketStreamFd();
        if (n < 0) {
            throw new ChannelException(Errors.newIOException("newSocketStream", n));
        }
        return n;
    }

    protected static int newSocketDgram0() {
        int n = Socket.newSocketDgramFd();
        if (n < 0) {
            throw new ChannelException(Errors.newIOException("newSocketDgram", n));
        }
        return n;
    }

    protected static int newSocketDomain0() {
        int n = Socket.newSocketDomainFd();
        if (n < 0) {
            throw new ChannelException(Errors.newIOException("newSocketDomain", n));
        }
        return n;
    }

    private static native int shutdown(int var0, boolean var1, boolean var2);

    private static native int connect(int var0, byte[] var1, int var2, int var3);

    private static native int connectDomainSocket(int var0, byte[] var1);

    private static native int finishConnect(int var0);

    private static native int disconnect(int var0);

    private static native int bind(int var0, byte[] var1, int var2, int var3);

    private static native int bindDomainSocket(int var0, byte[] var1);

    private static native int listen(int var0, int var1);

    private static native int accept(int var0, byte[] var1);

    private static native byte[] remoteAddress(int var0);

    private static native byte[] localAddress(int var0);

    private static native int sendTo(int var0, ByteBuffer var1, int var2, int var3, byte[] var4, int var5, int var6);

    private static native int sendToAddress(int var0, long var1, int var3, int var4, byte[] var5, int var6, int var7);

    private static native int sendToAddresses(int var0, long var1, int var3, byte[] var4, int var5, int var6);

    private static native DatagramSocketAddress recvFrom(int var0, ByteBuffer var1, int var2, int var3) throws IOException;

    private static native DatagramSocketAddress recvFromAddress(int var0, long var1, int var3, int var4) throws IOException;

    private static native int recvFd(int var0);

    private static native int sendFd(int var0, int var1);

    private static native int newSocketStreamFd();

    private static native int newSocketDgramFd();

    private static native int newSocketDomainFd();

    private static native int isReuseAddress(int var0) throws IOException;

    private static native int isReusePort(int var0) throws IOException;

    private static native int getReceiveBufferSize(int var0) throws IOException;

    private static native int getSendBufferSize(int var0) throws IOException;

    private static native int isKeepAlive(int var0) throws IOException;

    private static native int isTcpNoDelay(int var0) throws IOException;

    private static native int isBroadcast(int var0) throws IOException;

    private static native int getSoLinger(int var0) throws IOException;

    private static native int getSoError(int var0) throws IOException;

    private static native int getTrafficClass(int var0) throws IOException;

    private static native void setReuseAddress(int var0, int var1) throws IOException;

    private static native void setReusePort(int var0, int var1) throws IOException;

    private static native void setKeepAlive(int var0, int var1) throws IOException;

    private static native void setReceiveBufferSize(int var0, int var1) throws IOException;

    private static native void setSendBufferSize(int var0, int var1) throws IOException;

    private static native void setTcpNoDelay(int var0, int var1) throws IOException;

    private static native void setSoLinger(int var0, int var1) throws IOException;

    private static native void setBroadcast(int var0, int var1) throws IOException;

    private static native void setTrafficClass(int var0, int var1) throws IOException;

    private static native void initialize(boolean var0);
}

