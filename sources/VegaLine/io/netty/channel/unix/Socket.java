/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.unix;

import io.netty.channel.ChannelException;
import io.netty.channel.unix.DatagramSocketAddress;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.unix.Errors;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.NativeInetAddress;
import io.netty.channel.unix.PeerCredentials;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThrowableUtil;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;

public final class Socket
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

    public Socket(int fd) {
        super(fd);
    }

    public void shutdown() throws IOException {
        this.shutdown(true, true);
    }

    public void shutdown(boolean read, boolean write) throws IOException {
        int newState;
        int oldState;
        do {
            if (Socket.isClosed(oldState = this.state)) {
                throw new ClosedChannelException();
            }
            newState = oldState;
            if (read && !Socket.isInputShutdown(newState)) {
                newState = Socket.inputShutdown(newState);
            }
            if (write && !Socket.isOutputShutdown(newState)) {
                newState = Socket.outputShutdown(newState);
            }
            if (newState != oldState) continue;
            return;
        } while (!this.casState(oldState, newState));
        int res = Socket.shutdown(this.fd, read, write);
        if (res < 0) {
            Errors.ioResult("shutdown", res, CONNECTION_RESET_SHUTDOWN_EXCEPTION, SHUTDOWN_CLOSED_CHANNEL_EXCEPTION);
        }
    }

    public boolean isShutdown() {
        int state = this.state;
        return Socket.isInputShutdown(state) && Socket.isOutputShutdown(state);
    }

    public boolean isInputShutdown() {
        return Socket.isInputShutdown(this.state);
    }

    public boolean isOutputShutdown() {
        return Socket.isOutputShutdown(this.state);
    }

    public int sendTo(ByteBuffer buf, int pos, int limit, InetAddress addr, int port) throws IOException {
        int scopeId;
        byte[] address;
        if (addr instanceof Inet6Address) {
            address = addr.getAddress();
            scopeId = ((Inet6Address)addr).getScopeId();
        } else {
            scopeId = 0;
            address = NativeInetAddress.ipv4MappedIpv6Address(addr.getAddress());
        }
        int res = Socket.sendTo(this.fd, buf, pos, limit, address, scopeId, port);
        if (res >= 0) {
            return res;
        }
        return Errors.ioResult("sendTo", res, SEND_TO_CONNECTION_RESET_EXCEPTION, SEND_TO_CLOSED_CHANNEL_EXCEPTION);
    }

    public int sendToAddress(long memoryAddress, int pos, int limit, InetAddress addr, int port) throws IOException {
        int scopeId;
        byte[] address;
        if (addr instanceof Inet6Address) {
            address = addr.getAddress();
            scopeId = ((Inet6Address)addr).getScopeId();
        } else {
            scopeId = 0;
            address = NativeInetAddress.ipv4MappedIpv6Address(addr.getAddress());
        }
        int res = Socket.sendToAddress(this.fd, memoryAddress, pos, limit, address, scopeId, port);
        if (res >= 0) {
            return res;
        }
        return Errors.ioResult("sendToAddress", res, SEND_TO_ADDRESS_CONNECTION_RESET_EXCEPTION, SEND_TO_ADDRESS_CLOSED_CHANNEL_EXCEPTION);
    }

    public int sendToAddresses(long memoryAddress, int length, InetAddress addr, int port) throws IOException {
        int scopeId;
        byte[] address;
        if (addr instanceof Inet6Address) {
            address = addr.getAddress();
            scopeId = ((Inet6Address)addr).getScopeId();
        } else {
            scopeId = 0;
            address = NativeInetAddress.ipv4MappedIpv6Address(addr.getAddress());
        }
        int res = Socket.sendToAddresses(this.fd, memoryAddress, length, address, scopeId, port);
        if (res >= 0) {
            return res;
        }
        return Errors.ioResult("sendToAddresses", res, CONNECTION_RESET_EXCEPTION_SENDMSG, SEND_TO_ADDRESSES_CLOSED_CHANNEL_EXCEPTION);
    }

    public DatagramSocketAddress recvFrom(ByteBuffer buf, int pos, int limit) throws IOException {
        return Socket.recvFrom(this.fd, buf, pos, limit);
    }

    public DatagramSocketAddress recvFromAddress(long memoryAddress, int pos, int limit) throws IOException {
        return Socket.recvFromAddress(this.fd, memoryAddress, pos, limit);
    }

    public boolean connect(SocketAddress socketAddress) throws IOException {
        int res;
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
            NativeInetAddress address = NativeInetAddress.newInstance(inetSocketAddress.getAddress());
            res = Socket.connect(this.fd, address.address, address.scopeId, inetSocketAddress.getPort());
        } else if (socketAddress instanceof DomainSocketAddress) {
            DomainSocketAddress unixDomainSocketAddress = (DomainSocketAddress)socketAddress;
            res = Socket.connectDomainSocket(this.fd, unixDomainSocketAddress.path().getBytes(CharsetUtil.UTF_8));
        } else {
            throw new Error("Unexpected SocketAddress implementation " + socketAddress);
        }
        if (res < 0) {
            if (res == Errors.ERRNO_EINPROGRESS_NEGATIVE) {
                return false;
            }
            Errors.throwConnectException("connect", CONNECT_REFUSED_EXCEPTION, res);
        }
        return true;
    }

    public boolean finishConnect() throws IOException {
        int res = Socket.finishConnect(this.fd);
        if (res < 0) {
            if (res == Errors.ERRNO_EINPROGRESS_NEGATIVE) {
                return false;
            }
            Errors.throwConnectException("finishConnect", FINISH_CONNECT_REFUSED_EXCEPTION, res);
        }
        return true;
    }

    public void bind(SocketAddress socketAddress) throws IOException {
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress addr = (InetSocketAddress)socketAddress;
            NativeInetAddress address = NativeInetAddress.newInstance(addr.getAddress());
            int res = Socket.bind(this.fd, address.address, address.scopeId, addr.getPort());
            if (res < 0) {
                throw Errors.newIOException("bind", res);
            }
        } else if (socketAddress instanceof DomainSocketAddress) {
            DomainSocketAddress addr = (DomainSocketAddress)socketAddress;
            int res = Socket.bindDomainSocket(this.fd, addr.path().getBytes(CharsetUtil.UTF_8));
            if (res < 0) {
                throw Errors.newIOException("bind", res);
            }
        } else {
            throw new Error("Unexpected SocketAddress implementation " + socketAddress);
        }
    }

    public void listen(int backlog) throws IOException {
        int res = Socket.listen(this.fd, backlog);
        if (res < 0) {
            throw Errors.newIOException("listen", res);
        }
    }

    public int accept(byte[] addr) throws IOException {
        int res = Socket.accept(this.fd, addr);
        if (res >= 0) {
            return res;
        }
        if (res == Errors.ERRNO_EAGAIN_NEGATIVE || res == Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
            return -1;
        }
        throw Errors.newIOException("accept", res);
    }

    public InetSocketAddress remoteAddress() {
        byte[] addr = Socket.remoteAddress(this.fd);
        if (addr == null) {
            return null;
        }
        return NativeInetAddress.address(addr, 0, addr.length);
    }

    public InetSocketAddress localAddress() {
        byte[] addr = Socket.localAddress(this.fd);
        if (addr == null) {
            return null;
        }
        return NativeInetAddress.address(addr, 0, addr.length);
    }

    public int getReceiveBufferSize() throws IOException {
        return Socket.getReceiveBufferSize(this.fd);
    }

    public int getSendBufferSize() throws IOException {
        return Socket.getSendBufferSize(this.fd);
    }

    public boolean isKeepAlive() throws IOException {
        return Socket.isKeepAlive(this.fd) != 0;
    }

    public boolean isTcpNoDelay() throws IOException {
        return Socket.isTcpNoDelay(this.fd) != 0;
    }

    public boolean isTcpCork() throws IOException {
        return Socket.isTcpCork(this.fd) != 0;
    }

    public int getSoLinger() throws IOException {
        return Socket.getSoLinger(this.fd);
    }

    public int getTcpDeferAccept() throws IOException {
        return Socket.getTcpDeferAccept(this.fd);
    }

    public boolean isTcpQuickAck() throws IOException {
        return Socket.isTcpQuickAck(this.fd) != 0;
    }

    public int getSoError() throws IOException {
        return Socket.getSoError(this.fd);
    }

    public PeerCredentials getPeerCredentials() throws IOException {
        return Socket.getPeerCredentials(this.fd);
    }

    public void setKeepAlive(boolean keepAlive) throws IOException {
        Socket.setKeepAlive(this.fd, keepAlive ? 1 : 0);
    }

    public void setReceiveBufferSize(int receiveBufferSize) throws IOException {
        Socket.setReceiveBufferSize(this.fd, receiveBufferSize);
    }

    public void setSendBufferSize(int sendBufferSize) throws IOException {
        Socket.setSendBufferSize(this.fd, sendBufferSize);
    }

    public void setTcpNoDelay(boolean tcpNoDelay) throws IOException {
        Socket.setTcpNoDelay(this.fd, tcpNoDelay ? 1 : 0);
    }

    public void setTcpCork(boolean tcpCork) throws IOException {
        Socket.setTcpCork(this.fd, tcpCork ? 1 : 0);
    }

    public void setSoLinger(int soLinger) throws IOException {
        Socket.setSoLinger(this.fd, soLinger);
    }

    public void setTcpDeferAccept(int deferAccept) throws IOException {
        Socket.setTcpDeferAccept(this.fd, deferAccept);
    }

    public void setTcpQuickAck(boolean quickAck) throws IOException {
        Socket.setTcpQuickAck(this.fd, quickAck ? 1 : 0);
    }

    @Override
    public String toString() {
        return "Socket{fd=" + this.fd + '}';
    }

    public static Socket newSocketStream() {
        int res = Socket.newSocketStreamFd();
        if (res < 0) {
            throw new ChannelException(Errors.newIOException("newSocketStream", res));
        }
        return new Socket(res);
    }

    public static Socket newSocketDgram() {
        int res = Socket.newSocketDgramFd();
        if (res < 0) {
            throw new ChannelException(Errors.newIOException("newSocketDgram", res));
        }
        return new Socket(res);
    }

    public static Socket newSocketDomain() {
        int res = Socket.newSocketDomainFd();
        if (res < 0) {
            throw new ChannelException(Errors.newIOException("newSocketDomain", res));
        }
        return new Socket(res);
    }

    private static native int shutdown(int var0, boolean var1, boolean var2);

    private static native int connect(int var0, byte[] var1, int var2, int var3);

    private static native int connectDomainSocket(int var0, byte[] var1);

    private static native int finishConnect(int var0);

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

    private static native int newSocketStreamFd();

    private static native int newSocketDgramFd();

    private static native int newSocketDomainFd();

    private static native int getReceiveBufferSize(int var0) throws IOException;

    private static native int getSendBufferSize(int var0) throws IOException;

    private static native int isKeepAlive(int var0) throws IOException;

    private static native int isTcpNoDelay(int var0) throws IOException;

    private static native int isTcpCork(int var0) throws IOException;

    private static native int getSoLinger(int var0) throws IOException;

    private static native int getSoError(int var0) throws IOException;

    private static native int getTcpDeferAccept(int var0) throws IOException;

    private static native int isTcpQuickAck(int var0) throws IOException;

    private static native PeerCredentials getPeerCredentials(int var0) throws IOException;

    private static native void setKeepAlive(int var0, int var1) throws IOException;

    private static native void setReceiveBufferSize(int var0, int var1) throws IOException;

    private static native void setSendBufferSize(int var0, int var1) throws IOException;

    private static native void setTcpNoDelay(int var0, int var1) throws IOException;

    private static native void setTcpCork(int var0, int var1) throws IOException;

    private static native void setSoLinger(int var0, int var1) throws IOException;

    private static native void setTcpDeferAccept(int var0, int var1) throws IOException;

    private static native void setTcpQuickAck(int var0, int var1) throws IOException;
}

