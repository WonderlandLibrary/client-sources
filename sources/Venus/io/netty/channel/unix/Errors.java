/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.unix;

import io.netty.channel.unix.ErrorsStaticallyReferencedJniMethods;
import io.netty.util.internal.EmptyArrays;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NotYetConnectedException;

public final class Errors {
    public static final int ERRNO_ENOENT_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoENOENT();
    public static final int ERRNO_ENOTCONN_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoENOTCONN();
    public static final int ERRNO_EBADF_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEBADF();
    public static final int ERRNO_EPIPE_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEPIPE();
    public static final int ERRNO_ECONNRESET_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoECONNRESET();
    public static final int ERRNO_EAGAIN_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEAGAIN();
    public static final int ERRNO_EWOULDBLOCK_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEWOULDBLOCK();
    public static final int ERRNO_EINPROGRESS_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEINPROGRESS();
    public static final int ERROR_ECONNREFUSED_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errorECONNREFUSED();
    public static final int ERROR_EISCONN_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errorEISCONN();
    public static final int ERROR_EALREADY_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errorEALREADY();
    public static final int ERROR_ENETUNREACH_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errorENETUNREACH();
    private static final String[] ERRORS = new String[512];

    static void throwConnectException(String string, NativeConnectException nativeConnectException, int n) throws IOException {
        if (n == nativeConnectException.expectedErr()) {
            throw nativeConnectException;
        }
        if (n == ERROR_EALREADY_NEGATIVE) {
            throw new ConnectionPendingException();
        }
        if (n == ERROR_ENETUNREACH_NEGATIVE) {
            throw new NoRouteToHostException();
        }
        if (n == ERROR_EISCONN_NEGATIVE) {
            throw new AlreadyConnectedException();
        }
        if (n == ERRNO_ENOENT_NEGATIVE) {
            throw new FileNotFoundException();
        }
        throw new ConnectException(string + "(..) failed: " + ERRORS[-n]);
    }

    public static NativeIoException newConnectionResetException(String string, int n) {
        NativeIoException nativeIoException = Errors.newIOException(string, n);
        nativeIoException.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        return nativeIoException;
    }

    public static NativeIoException newIOException(String string, int n) {
        return new NativeIoException(string, n);
    }

    public static int ioResult(String string, int n, NativeIoException nativeIoException, ClosedChannelException closedChannelException) throws IOException {
        if (n == ERRNO_EAGAIN_NEGATIVE || n == ERRNO_EWOULDBLOCK_NEGATIVE) {
            return 1;
        }
        if (n == nativeIoException.expectedErr()) {
            throw nativeIoException;
        }
        if (n == ERRNO_EBADF_NEGATIVE) {
            throw closedChannelException;
        }
        if (n == ERRNO_ENOTCONN_NEGATIVE) {
            throw new NotYetConnectedException();
        }
        if (n == ERRNO_ENOENT_NEGATIVE) {
            throw new FileNotFoundException();
        }
        throw Errors.newIOException(string, n);
    }

    private Errors() {
    }

    static String[] access$000() {
        return ERRORS;
    }

    static {
        for (int i = 0; i < ERRORS.length; ++i) {
            Errors.ERRORS[i] = ErrorsStaticallyReferencedJniMethods.strError(i);
        }
    }

    static final class NativeConnectException
    extends ConnectException {
        private static final long serialVersionUID = -5532328671712318161L;
        private final int expectedErr;

        NativeConnectException(String string, int n) {
            super(string + "(..) failed: " + Errors.access$000()[-n]);
            this.expectedErr = n;
        }

        int expectedErr() {
            return this.expectedErr;
        }
    }

    public static final class NativeIoException
    extends IOException {
        private static final long serialVersionUID = 8222160204268655526L;
        private final int expectedErr;

        public NativeIoException(String string, int n) {
            super(string + "(..) failed: " + Errors.access$000()[-n]);
            this.expectedErr = n;
        }

        public int expectedErr() {
            return this.expectedErr;
        }
    }
}

