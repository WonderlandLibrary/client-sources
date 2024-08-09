/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Error;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Http2Exception
extends Exception {
    private static final long serialVersionUID = -6941186345430164209L;
    private final Http2Error error;
    private final ShutdownHint shutdownHint;

    public Http2Exception(Http2Error http2Error) {
        this(http2Error, ShutdownHint.HARD_SHUTDOWN);
    }

    public Http2Exception(Http2Error http2Error, ShutdownHint shutdownHint) {
        this.error = ObjectUtil.checkNotNull(http2Error, "error");
        this.shutdownHint = ObjectUtil.checkNotNull(shutdownHint, "shutdownHint");
    }

    public Http2Exception(Http2Error http2Error, String string) {
        this(http2Error, string, ShutdownHint.HARD_SHUTDOWN);
    }

    public Http2Exception(Http2Error http2Error, String string, ShutdownHint shutdownHint) {
        super(string);
        this.error = ObjectUtil.checkNotNull(http2Error, "error");
        this.shutdownHint = ObjectUtil.checkNotNull(shutdownHint, "shutdownHint");
    }

    public Http2Exception(Http2Error http2Error, String string, Throwable throwable) {
        this(http2Error, string, throwable, ShutdownHint.HARD_SHUTDOWN);
    }

    public Http2Exception(Http2Error http2Error, String string, Throwable throwable, ShutdownHint shutdownHint) {
        super(string, throwable);
        this.error = ObjectUtil.checkNotNull(http2Error, "error");
        this.shutdownHint = ObjectUtil.checkNotNull(shutdownHint, "shutdownHint");
    }

    public Http2Error error() {
        return this.error;
    }

    public ShutdownHint shutdownHint() {
        return this.shutdownHint;
    }

    public static Http2Exception connectionError(Http2Error http2Error, String string, Object ... objectArray) {
        return new Http2Exception(http2Error, String.format(string, objectArray));
    }

    public static Http2Exception connectionError(Http2Error http2Error, Throwable throwable, String string, Object ... objectArray) {
        return new Http2Exception(http2Error, String.format(string, objectArray), throwable);
    }

    public static Http2Exception closedStreamError(Http2Error http2Error, String string, Object ... objectArray) {
        return new ClosedStreamCreationException(http2Error, String.format(string, objectArray));
    }

    public static Http2Exception streamError(int n, Http2Error http2Error, String string, Object ... objectArray) {
        return 0 == n ? Http2Exception.connectionError(http2Error, string, objectArray) : new StreamException(n, http2Error, String.format(string, objectArray));
    }

    public static Http2Exception streamError(int n, Http2Error http2Error, Throwable throwable, String string, Object ... objectArray) {
        return 0 == n ? Http2Exception.connectionError(http2Error, throwable, string, objectArray) : new StreamException(n, http2Error, String.format(string, objectArray), throwable);
    }

    public static Http2Exception headerListSizeError(int n, Http2Error http2Error, boolean bl, String string, Object ... objectArray) {
        return 0 == n ? Http2Exception.connectionError(http2Error, string, objectArray) : new HeaderListSizeException(n, http2Error, String.format(string, objectArray), bl);
    }

    public static boolean isStreamError(Http2Exception http2Exception) {
        return http2Exception instanceof StreamException;
    }

    public static int streamId(Http2Exception http2Exception) {
        return Http2Exception.isStreamError(http2Exception) ? ((StreamException)http2Exception).streamId() : 0;
    }

    public static final class CompositeStreamException
    extends Http2Exception
    implements Iterable<StreamException> {
        private static final long serialVersionUID = 7091134858213711015L;
        private final List<StreamException> exceptions;

        public CompositeStreamException(Http2Error http2Error, int n) {
            super(http2Error, ShutdownHint.NO_SHUTDOWN);
            this.exceptions = new ArrayList<StreamException>(n);
        }

        public void add(StreamException streamException) {
            this.exceptions.add(streamException);
        }

        @Override
        public Iterator<StreamException> iterator() {
            return this.exceptions.iterator();
        }
    }

    public static final class HeaderListSizeException
    extends StreamException {
        private static final long serialVersionUID = -8807603212183882637L;
        private final boolean decode;

        HeaderListSizeException(int n, Http2Error http2Error, String string, boolean bl) {
            super(n, http2Error, string);
            this.decode = bl;
        }

        public boolean duringDecode() {
            return this.decode;
        }
    }

    public static class StreamException
    extends Http2Exception {
        private static final long serialVersionUID = 602472544416984384L;
        private final int streamId;

        StreamException(int n, Http2Error http2Error, String string) {
            super(http2Error, string, ShutdownHint.NO_SHUTDOWN);
            this.streamId = n;
        }

        StreamException(int n, Http2Error http2Error, String string, Throwable throwable) {
            super(http2Error, string, throwable, ShutdownHint.NO_SHUTDOWN);
            this.streamId = n;
        }

        public int streamId() {
            return this.streamId;
        }
    }

    public static final class ClosedStreamCreationException
    extends Http2Exception {
        private static final long serialVersionUID = -6746542974372246206L;

        public ClosedStreamCreationException(Http2Error http2Error) {
            super(http2Error);
        }

        public ClosedStreamCreationException(Http2Error http2Error, String string) {
            super(http2Error, string);
        }

        public ClosedStreamCreationException(Http2Error http2Error, String string, Throwable throwable) {
            super(http2Error, string, throwable);
        }
    }

    public static enum ShutdownHint {
        NO_SHUTDOWN,
        GRACEFUL_SHUTDOWN,
        HARD_SHUTDOWN;

    }
}

