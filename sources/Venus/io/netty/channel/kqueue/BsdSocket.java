/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.channel.DefaultFileRegion;
import io.netty.channel.kqueue.AcceptFilter;
import io.netty.channel.kqueue.Native;
import io.netty.channel.unix.Errors;
import io.netty.channel.unix.PeerCredentials;
import io.netty.channel.unix.Socket;
import io.netty.util.internal.ThrowableUtil;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;

final class BsdSocket
extends Socket {
    private static final Errors.NativeIoException SENDFILE_CONNECTION_RESET_EXCEPTION;
    private static final ClosedChannelException SENDFILE_CLOSED_CHANNEL_EXCEPTION;
    private static final int APPLE_SND_LOW_AT_MAX = 131072;
    private static final int FREEBSD_SND_LOW_AT_MAX = 32768;
    static final int BSD_SND_LOW_AT_MAX;

    BsdSocket(int n) {
        super(n);
    }

    void setAcceptFilter(AcceptFilter acceptFilter) throws IOException {
        BsdSocket.setAcceptFilter(this.intValue(), acceptFilter.filterName(), acceptFilter.filterArgs());
    }

    void setTcpNoPush(boolean bl) throws IOException {
        BsdSocket.setTcpNoPush(this.intValue(), bl ? 1 : 0);
    }

    void setSndLowAt(int n) throws IOException {
        BsdSocket.setSndLowAt(this.intValue(), n);
    }

    boolean isTcpNoPush() throws IOException {
        return BsdSocket.getTcpNoPush(this.intValue()) != 0;
    }

    int getSndLowAt() throws IOException {
        return BsdSocket.getSndLowAt(this.intValue());
    }

    AcceptFilter getAcceptFilter() throws IOException {
        String[] stringArray = BsdSocket.getAcceptFilter(this.intValue());
        return stringArray == null ? AcceptFilter.PLATFORM_UNSUPPORTED : new AcceptFilter(stringArray[0], stringArray[5]);
    }

    PeerCredentials getPeerCredentials() throws IOException {
        return BsdSocket.getPeerCredentials(this.intValue());
    }

    long sendFile(DefaultFileRegion defaultFileRegion, long l, long l2, long l3) throws IOException {
        defaultFileRegion.open();
        long l4 = BsdSocket.sendFile(this.intValue(), defaultFileRegion, l, l2, l3);
        if (l4 >= 0L) {
            return l4;
        }
        return Errors.ioResult("sendfile", (int)l4, SENDFILE_CONNECTION_RESET_EXCEPTION, SENDFILE_CLOSED_CHANNEL_EXCEPTION);
    }

    public static BsdSocket newSocketStream() {
        return new BsdSocket(BsdSocket.newSocketStream0());
    }

    public static BsdSocket newSocketDgram() {
        return new BsdSocket(BsdSocket.newSocketDgram0());
    }

    public static BsdSocket newSocketDomain() {
        return new BsdSocket(BsdSocket.newSocketDomain0());
    }

    private static native long sendFile(int var0, DefaultFileRegion var1, long var2, long var4, long var6) throws IOException;

    private static native String[] getAcceptFilter(int var0) throws IOException;

    private static native int getTcpNoPush(int var0) throws IOException;

    private static native int getSndLowAt(int var0) throws IOException;

    private static native PeerCredentials getPeerCredentials(int var0) throws IOException;

    private static native void setAcceptFilter(int var0, String var1, String var2) throws IOException;

    private static native void setTcpNoPush(int var0, int var1) throws IOException;

    private static native void setSndLowAt(int var0, int var1) throws IOException;

    static {
        SENDFILE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Native.class, "sendfile(..)");
        BSD_SND_LOW_AT_MAX = Math.min(131072, 32768);
        SENDFILE_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException("syscall:sendfile", Errors.ERRNO_EPIPE_NEGATIVE);
    }
}

