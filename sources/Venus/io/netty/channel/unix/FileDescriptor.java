/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.unix;

import io.netty.channel.unix.Errors;
import io.netty.channel.unix.Limits;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class FileDescriptor {
    private static final ClosedChannelException WRITE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), FileDescriptor.class, "write(..)");
    private static final ClosedChannelException WRITE_ADDRESS_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), FileDescriptor.class, "writeAddress(..)");
    private static final ClosedChannelException WRITEV_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), FileDescriptor.class, "writev(..)");
    private static final ClosedChannelException WRITEV_ADDRESSES_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), FileDescriptor.class, "writevAddresses(..)");
    private static final ClosedChannelException READ_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), FileDescriptor.class, "read(..)");
    private static final ClosedChannelException READ_ADDRESS_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), FileDescriptor.class, "readAddress(..)");
    private static final Errors.NativeIoException WRITE_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:write", Errors.ERRNO_EPIPE_NEGATIVE), FileDescriptor.class, "write(..)");
    private static final Errors.NativeIoException WRITE_ADDRESS_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:write", Errors.ERRNO_EPIPE_NEGATIVE), FileDescriptor.class, "writeAddress(..)");
    private static final Errors.NativeIoException WRITEV_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:writev", Errors.ERRNO_EPIPE_NEGATIVE), FileDescriptor.class, "writev(..)");
    private static final Errors.NativeIoException WRITEV_ADDRESSES_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:writev", Errors.ERRNO_EPIPE_NEGATIVE), FileDescriptor.class, "writeAddresses(..)");
    private static final Errors.NativeIoException READ_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:read", Errors.ERRNO_ECONNRESET_NEGATIVE), FileDescriptor.class, "read(..)");
    private static final Errors.NativeIoException READ_ADDRESS_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:read", Errors.ERRNO_ECONNRESET_NEGATIVE), FileDescriptor.class, "readAddress(..)");
    private static final AtomicIntegerFieldUpdater<FileDescriptor> stateUpdater = AtomicIntegerFieldUpdater.newUpdater(FileDescriptor.class, "state");
    private static final int STATE_CLOSED_MASK = 1;
    private static final int STATE_INPUT_SHUTDOWN_MASK = 2;
    private static final int STATE_OUTPUT_SHUTDOWN_MASK = 4;
    private static final int STATE_ALL_MASK = 7;
    volatile int state;
    final int fd;

    public FileDescriptor(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("fd must be >= 0");
        }
        this.fd = n;
    }

    public final int intValue() {
        return this.fd;
    }

    public void close() throws IOException {
        int n;
        do {
            if (!FileDescriptor.isClosed(n = this.state)) continue;
            return;
        } while (!this.casState(n, n | 7));
        n = FileDescriptor.close(this.fd);
        if (n < 0) {
            throw Errors.newIOException("close", n);
        }
    }

    public boolean isOpen() {
        return !FileDescriptor.isClosed(this.state);
    }

    public final int write(ByteBuffer byteBuffer, int n, int n2) throws IOException {
        int n3 = FileDescriptor.write(this.fd, byteBuffer, n, n2);
        if (n3 >= 0) {
            return n3;
        }
        return Errors.ioResult("write", n3, WRITE_CONNECTION_RESET_EXCEPTION, WRITE_CLOSED_CHANNEL_EXCEPTION);
    }

    public final int writeAddress(long l, int n, int n2) throws IOException {
        int n3 = FileDescriptor.writeAddress(this.fd, l, n, n2);
        if (n3 >= 0) {
            return n3;
        }
        return Errors.ioResult("writeAddress", n3, WRITE_ADDRESS_CONNECTION_RESET_EXCEPTION, WRITE_ADDRESS_CLOSED_CHANNEL_EXCEPTION);
    }

    public final long writev(ByteBuffer[] byteBufferArray, int n, int n2, long l) throws IOException {
        long l2 = FileDescriptor.writev(this.fd, byteBufferArray, n, Math.min(Limits.IOV_MAX, n2), l);
        if (l2 >= 0L) {
            return l2;
        }
        return Errors.ioResult("writev", (int)l2, WRITEV_CONNECTION_RESET_EXCEPTION, WRITEV_CLOSED_CHANNEL_EXCEPTION);
    }

    public final long writevAddresses(long l, int n) throws IOException {
        long l2 = FileDescriptor.writevAddresses(this.fd, l, n);
        if (l2 >= 0L) {
            return l2;
        }
        return Errors.ioResult("writevAddresses", (int)l2, WRITEV_ADDRESSES_CONNECTION_RESET_EXCEPTION, WRITEV_ADDRESSES_CLOSED_CHANNEL_EXCEPTION);
    }

    public final int read(ByteBuffer byteBuffer, int n, int n2) throws IOException {
        int n3 = FileDescriptor.read(this.fd, byteBuffer, n, n2);
        if (n3 > 0) {
            return n3;
        }
        if (n3 == 0) {
            return 1;
        }
        return Errors.ioResult("read", n3, READ_CONNECTION_RESET_EXCEPTION, READ_CLOSED_CHANNEL_EXCEPTION);
    }

    public final int readAddress(long l, int n, int n2) throws IOException {
        int n3 = FileDescriptor.readAddress(this.fd, l, n, n2);
        if (n3 > 0) {
            return n3;
        }
        if (n3 == 0) {
            return 1;
        }
        return Errors.ioResult("readAddress", n3, READ_ADDRESS_CONNECTION_RESET_EXCEPTION, READ_ADDRESS_CLOSED_CHANNEL_EXCEPTION);
    }

    public String toString() {
        return "FileDescriptor{fd=" + this.fd + '}';
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof FileDescriptor)) {
            return true;
        }
        return this.fd == ((FileDescriptor)object).fd;
    }

    public int hashCode() {
        return this.fd;
    }

    public static FileDescriptor from(String string) throws IOException {
        ObjectUtil.checkNotNull(string, "path");
        int n = FileDescriptor.open(string);
        if (n < 0) {
            throw Errors.newIOException("open", n);
        }
        return new FileDescriptor(n);
    }

    public static FileDescriptor from(File file) throws IOException {
        return FileDescriptor.from(ObjectUtil.checkNotNull(file, "file").getPath());
    }

    public static FileDescriptor[] pipe() throws IOException {
        long l = FileDescriptor.newPipe();
        if (l < 0L) {
            throw Errors.newIOException("newPipe", (int)l);
        }
        return new FileDescriptor[]{new FileDescriptor((int)(l >>> 32)), new FileDescriptor((int)l)};
    }

    final boolean casState(int n, int n2) {
        return stateUpdater.compareAndSet(this, n, n2);
    }

    static boolean isClosed(int n) {
        return (n & 1) != 0;
    }

    static boolean isInputShutdown(int n) {
        return (n & 2) != 0;
    }

    static boolean isOutputShutdown(int n) {
        return (n & 4) != 0;
    }

    static int inputShutdown(int n) {
        return n | 2;
    }

    static int outputShutdown(int n) {
        return n | 4;
    }

    private static native int open(String var0);

    private static native int close(int var0);

    private static native int write(int var0, ByteBuffer var1, int var2, int var3);

    private static native int writeAddress(int var0, long var1, int var3, int var4);

    private static native long writev(int var0, ByteBuffer[] var1, int var2, int var3, long var4);

    private static native long writevAddresses(int var0, long var1, int var3);

    private static native int read(int var0, ByteBuffer var1, int var2, int var3);

    private static native int readAddress(int var0, long var1, int var3, int var4);

    private static native long newPipe();
}

