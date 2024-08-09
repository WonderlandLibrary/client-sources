/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionAdapter;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2LocalFlowController;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.handler.codec.http2.Http2StreamVisitor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultHttp2LocalFlowController
implements Http2LocalFlowController {
    public static final float DEFAULT_WINDOW_UPDATE_RATIO = 0.5f;
    private final Http2Connection connection;
    private final Http2Connection.PropertyKey stateKey;
    private Http2FrameWriter frameWriter;
    private ChannelHandlerContext ctx;
    private float windowUpdateRatio;
    private int initialWindowSize = 65535;
    private static final FlowState REDUCED_FLOW_STATE;
    static final boolean $assertionsDisabled;

    public DefaultHttp2LocalFlowController(Http2Connection http2Connection) {
        this(http2Connection, 0.5f, false);
    }

    public DefaultHttp2LocalFlowController(Http2Connection http2Connection, float f, boolean bl) {
        this.connection = ObjectUtil.checkNotNull(http2Connection, "connection");
        this.windowUpdateRatio(f);
        this.stateKey = http2Connection.newKey();
        DefaultState defaultState = bl ? new AutoRefillState(this, http2Connection.connectionStream(), this.initialWindowSize) : new DefaultState(this, http2Connection.connectionStream(), this.initialWindowSize);
        http2Connection.connectionStream().setProperty(this.stateKey, defaultState);
        http2Connection.addListener(new Http2ConnectionAdapter(this){
            final DefaultHttp2LocalFlowController this$0;
            {
                this.this$0 = defaultHttp2LocalFlowController;
            }

            @Override
            public void onStreamAdded(Http2Stream http2Stream) {
                http2Stream.setProperty(DefaultHttp2LocalFlowController.access$000(this.this$0), DefaultHttp2LocalFlowController.access$100());
            }

            @Override
            public void onStreamActive(Http2Stream http2Stream) {
                http2Stream.setProperty(DefaultHttp2LocalFlowController.access$000(this.this$0), new DefaultState(this.this$0, http2Stream, DefaultHttp2LocalFlowController.access$200(this.this$0)));
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void onStreamClosed(Http2Stream http2Stream) {
                try {
                    FlowState flowState = DefaultHttp2LocalFlowController.access$300(this.this$0, http2Stream);
                    int n = flowState.unconsumedBytes();
                    if (DefaultHttp2LocalFlowController.access$400(this.this$0) != null && n > 0) {
                        DefaultHttp2LocalFlowController.access$500(this.this$0).consumeBytes(n);
                        flowState.consumeBytes(n);
                    }
                } catch (Http2Exception http2Exception) {
                    PlatformDependent.throwException(http2Exception);
                } finally {
                    http2Stream.setProperty(DefaultHttp2LocalFlowController.access$000(this.this$0), DefaultHttp2LocalFlowController.access$100());
                }
            }
        });
    }

    @Override
    public DefaultHttp2LocalFlowController frameWriter(Http2FrameWriter http2FrameWriter) {
        this.frameWriter = ObjectUtil.checkNotNull(http2FrameWriter, "frameWriter");
        return this;
    }

    @Override
    public void channelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.ctx = ObjectUtil.checkNotNull(channelHandlerContext, "ctx");
    }

    @Override
    public void initialWindowSize(int n) throws Http2Exception {
        if (!$assertionsDisabled && this.ctx != null && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        int n2 = n - this.initialWindowSize;
        this.initialWindowSize = n;
        WindowUpdateVisitor windowUpdateVisitor = new WindowUpdateVisitor(this, n2);
        this.connection.forEachActiveStream(windowUpdateVisitor);
        windowUpdateVisitor.throwIfError();
    }

    @Override
    public int initialWindowSize() {
        return this.initialWindowSize;
    }

    @Override
    public int windowSize(Http2Stream http2Stream) {
        return this.state(http2Stream).windowSize();
    }

    @Override
    public int initialWindowSize(Http2Stream http2Stream) {
        return this.state(http2Stream).initialWindowSize();
    }

    @Override
    public void incrementWindowSize(Http2Stream http2Stream, int n) throws Http2Exception {
        if (!($assertionsDisabled || this.ctx != null && this.ctx.executor().inEventLoop())) {
            throw new AssertionError();
        }
        FlowState flowState = this.state(http2Stream);
        flowState.incrementInitialStreamWindow(n);
        flowState.writeWindowUpdateIfNeeded();
    }

    @Override
    public boolean consumeBytes(Http2Stream http2Stream, int n) throws Http2Exception {
        if (!($assertionsDisabled || this.ctx != null && this.ctx.executor().inEventLoop())) {
            throw new AssertionError();
        }
        if (n < 0) {
            throw new IllegalArgumentException("numBytes must not be negative");
        }
        if (n == 0) {
            return true;
        }
        if (http2Stream != null && !DefaultHttp2LocalFlowController.isClosed(http2Stream)) {
            if (http2Stream.id() == 0) {
                throw new UnsupportedOperationException("Returning bytes for the connection window is not supported");
            }
            boolean bl = this.connectionState().consumeBytes(n);
            return bl |= this.state(http2Stream).consumeBytes(n);
        }
        return true;
    }

    @Override
    public int unconsumedBytes(Http2Stream http2Stream) {
        return this.state(http2Stream).unconsumedBytes();
    }

    private static void checkValidRatio(float f) {
        if (Double.compare(f, 0.0) <= 0 || Double.compare(f, 1.0) >= 0) {
            throw new IllegalArgumentException("Invalid ratio: " + f);
        }
    }

    public void windowUpdateRatio(float f) {
        if (!$assertionsDisabled && this.ctx != null && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        DefaultHttp2LocalFlowController.checkValidRatio(f);
        this.windowUpdateRatio = f;
    }

    public float windowUpdateRatio() {
        return this.windowUpdateRatio;
    }

    public void windowUpdateRatio(Http2Stream http2Stream, float f) throws Http2Exception {
        if (!($assertionsDisabled || this.ctx != null && this.ctx.executor().inEventLoop())) {
            throw new AssertionError();
        }
        DefaultHttp2LocalFlowController.checkValidRatio(f);
        FlowState flowState = this.state(http2Stream);
        flowState.windowUpdateRatio(f);
        flowState.writeWindowUpdateIfNeeded();
    }

    public float windowUpdateRatio(Http2Stream http2Stream) throws Http2Exception {
        return this.state(http2Stream).windowUpdateRatio();
    }

    @Override
    public void receiveFlowControlledFrame(Http2Stream http2Stream, ByteBuf byteBuf, int n, boolean bl) throws Http2Exception {
        if (!($assertionsDisabled || this.ctx != null && this.ctx.executor().inEventLoop())) {
            throw new AssertionError();
        }
        int n2 = byteBuf.readableBytes() + n;
        FlowState flowState = this.connectionState();
        flowState.receiveFlowControlledFrame(n2);
        if (http2Stream != null && !DefaultHttp2LocalFlowController.isClosed(http2Stream)) {
            FlowState flowState2 = this.state(http2Stream);
            flowState2.endOfStream(bl);
            flowState2.receiveFlowControlledFrame(n2);
        } else if (n2 > 0) {
            flowState.consumeBytes(n2);
        }
    }

    private FlowState connectionState() {
        return (FlowState)this.connection.connectionStream().getProperty(this.stateKey);
    }

    private FlowState state(Http2Stream http2Stream) {
        return (FlowState)http2Stream.getProperty(this.stateKey);
    }

    private static boolean isClosed(Http2Stream http2Stream) {
        return http2Stream.state() == Http2Stream.State.CLOSED;
    }

    @Override
    public Http2LocalFlowController frameWriter(Http2FrameWriter http2FrameWriter) {
        return this.frameWriter(http2FrameWriter);
    }

    static Http2Connection.PropertyKey access$000(DefaultHttp2LocalFlowController defaultHttp2LocalFlowController) {
        return defaultHttp2LocalFlowController.stateKey;
    }

    static FlowState access$100() {
        return REDUCED_FLOW_STATE;
    }

    static int access$200(DefaultHttp2LocalFlowController defaultHttp2LocalFlowController) {
        return defaultHttp2LocalFlowController.initialWindowSize;
    }

    static FlowState access$300(DefaultHttp2LocalFlowController defaultHttp2LocalFlowController, Http2Stream http2Stream) {
        return defaultHttp2LocalFlowController.state(http2Stream);
    }

    static ChannelHandlerContext access$400(DefaultHttp2LocalFlowController defaultHttp2LocalFlowController) {
        return defaultHttp2LocalFlowController.ctx;
    }

    static FlowState access$500(DefaultHttp2LocalFlowController defaultHttp2LocalFlowController) {
        return defaultHttp2LocalFlowController.connectionState();
    }

    static float access$600(DefaultHttp2LocalFlowController defaultHttp2LocalFlowController) {
        return defaultHttp2LocalFlowController.windowUpdateRatio;
    }

    static Http2FrameWriter access$700(DefaultHttp2LocalFlowController defaultHttp2LocalFlowController) {
        return defaultHttp2LocalFlowController.frameWriter;
    }

    static {
        $assertionsDisabled = !DefaultHttp2LocalFlowController.class.desiredAssertionStatus();
        REDUCED_FLOW_STATE = new FlowState(){

            @Override
            public int windowSize() {
                return 1;
            }

            @Override
            public int initialWindowSize() {
                return 1;
            }

            @Override
            public void window(int n) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void incrementInitialStreamWindow(int n) {
            }

            @Override
            public boolean writeWindowUpdateIfNeeded() throws Http2Exception {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean consumeBytes(int n) throws Http2Exception {
                return true;
            }

            @Override
            public int unconsumedBytes() {
                return 1;
            }

            @Override
            public float windowUpdateRatio() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void windowUpdateRatio(float f) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void receiveFlowControlledFrame(int n) throws Http2Exception {
                throw new UnsupportedOperationException();
            }

            @Override
            public void incrementFlowControlWindows(int n) throws Http2Exception {
            }

            @Override
            public void endOfStream(boolean bl) {
                throw new UnsupportedOperationException();
            }
        };
    }

    private final class WindowUpdateVisitor
    implements Http2StreamVisitor {
        private Http2Exception.CompositeStreamException compositeException;
        private final int delta;
        final DefaultHttp2LocalFlowController this$0;

        public WindowUpdateVisitor(DefaultHttp2LocalFlowController defaultHttp2LocalFlowController, int n) {
            this.this$0 = defaultHttp2LocalFlowController;
            this.delta = n;
        }

        @Override
        public boolean visit(Http2Stream http2Stream) throws Http2Exception {
            try {
                FlowState flowState = DefaultHttp2LocalFlowController.access$300(this.this$0, http2Stream);
                flowState.incrementFlowControlWindows(this.delta);
                flowState.incrementInitialStreamWindow(this.delta);
            } catch (Http2Exception.StreamException streamException) {
                if (this.compositeException == null) {
                    this.compositeException = new Http2Exception.CompositeStreamException(streamException.error(), 4);
                }
                this.compositeException.add(streamException);
            }
            return false;
        }

        public void throwIfError() throws Http2Exception.CompositeStreamException {
            if (this.compositeException != null) {
                throw this.compositeException;
            }
        }
    }

    private static interface FlowState {
        public int windowSize();

        public int initialWindowSize();

        public void window(int var1);

        public void incrementInitialStreamWindow(int var1);

        public boolean writeWindowUpdateIfNeeded() throws Http2Exception;

        public boolean consumeBytes(int var1) throws Http2Exception;

        public int unconsumedBytes();

        public float windowUpdateRatio();

        public void windowUpdateRatio(float var1);

        public void receiveFlowControlledFrame(int var1) throws Http2Exception;

        public void incrementFlowControlWindows(int var1) throws Http2Exception;

        public void endOfStream(boolean var1);
    }

    private class DefaultState
    implements FlowState {
        private final Http2Stream stream;
        private int window;
        private int processedWindow;
        private int initialStreamWindowSize;
        private float streamWindowUpdateRatio;
        private int lowerBound;
        private boolean endOfStream;
        static final boolean $assertionsDisabled = !DefaultHttp2LocalFlowController.class.desiredAssertionStatus();
        final DefaultHttp2LocalFlowController this$0;

        public DefaultState(DefaultHttp2LocalFlowController defaultHttp2LocalFlowController, Http2Stream http2Stream, int n) {
            this.this$0 = defaultHttp2LocalFlowController;
            this.stream = http2Stream;
            this.window(n);
            this.streamWindowUpdateRatio = DefaultHttp2LocalFlowController.access$600(defaultHttp2LocalFlowController);
        }

        @Override
        public void window(int n) {
            if (!$assertionsDisabled && DefaultHttp2LocalFlowController.access$400(this.this$0) != null && !DefaultHttp2LocalFlowController.access$400(this.this$0).executor().inEventLoop()) {
                throw new AssertionError();
            }
            this.processedWindow = this.initialStreamWindowSize = n;
            this.window = this.initialStreamWindowSize;
        }

        @Override
        public int windowSize() {
            return this.window;
        }

        @Override
        public int initialWindowSize() {
            return this.initialStreamWindowSize;
        }

        @Override
        public void endOfStream(boolean bl) {
            this.endOfStream = bl;
        }

        @Override
        public float windowUpdateRatio() {
            return this.streamWindowUpdateRatio;
        }

        @Override
        public void windowUpdateRatio(float f) {
            if (!$assertionsDisabled && DefaultHttp2LocalFlowController.access$400(this.this$0) != null && !DefaultHttp2LocalFlowController.access$400(this.this$0).executor().inEventLoop()) {
                throw new AssertionError();
            }
            this.streamWindowUpdateRatio = f;
        }

        @Override
        public void incrementInitialStreamWindow(int n) {
            int n2 = (int)Math.min(Integer.MAX_VALUE, Math.max(0L, (long)this.initialStreamWindowSize + (long)n));
            n = n2 - this.initialStreamWindowSize;
            this.initialStreamWindowSize += n;
        }

        @Override
        public void incrementFlowControlWindows(int n) throws Http2Exception {
            if (n > 0 && this.window > Integer.MAX_VALUE - n) {
                throw Http2Exception.streamError(this.stream.id(), Http2Error.FLOW_CONTROL_ERROR, "Flow control window overflowed for stream: %d", this.stream.id());
            }
            this.window += n;
            this.processedWindow += n;
            this.lowerBound = n < 0 ? n : 0;
        }

        @Override
        public void receiveFlowControlledFrame(int n) throws Http2Exception {
            if (!$assertionsDisabled && n < 0) {
                throw new AssertionError();
            }
            this.window -= n;
            if (this.window < this.lowerBound) {
                throw Http2Exception.streamError(this.stream.id(), Http2Error.FLOW_CONTROL_ERROR, "Flow control window exceeded for stream: %d", this.stream.id());
            }
        }

        private void returnProcessedBytes(int n) throws Http2Exception {
            if (this.processedWindow - n < this.window) {
                throw Http2Exception.streamError(this.stream.id(), Http2Error.INTERNAL_ERROR, "Attempting to return too many bytes for stream %d", this.stream.id());
            }
            this.processedWindow -= n;
        }

        @Override
        public boolean consumeBytes(int n) throws Http2Exception {
            this.returnProcessedBytes(n);
            return this.writeWindowUpdateIfNeeded();
        }

        @Override
        public int unconsumedBytes() {
            return this.processedWindow - this.window;
        }

        @Override
        public boolean writeWindowUpdateIfNeeded() throws Http2Exception {
            if (this.endOfStream || this.initialStreamWindowSize <= 0) {
                return true;
            }
            int n = (int)((float)this.initialStreamWindowSize * this.streamWindowUpdateRatio);
            if (this.processedWindow <= n) {
                this.writeWindowUpdate();
                return false;
            }
            return true;
        }

        private void writeWindowUpdate() throws Http2Exception {
            int n = this.initialStreamWindowSize - this.processedWindow;
            try {
                this.incrementFlowControlWindows(n);
            } catch (Throwable throwable) {
                throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, throwable, "Attempting to return too many bytes for stream %d", this.stream.id());
            }
            DefaultHttp2LocalFlowController.access$700(this.this$0).writeWindowUpdate(DefaultHttp2LocalFlowController.access$400(this.this$0), this.stream.id(), n, DefaultHttp2LocalFlowController.access$400(this.this$0).newPromise());
        }
    }

    private final class AutoRefillState
    extends DefaultState {
        final DefaultHttp2LocalFlowController this$0;

        public AutoRefillState(DefaultHttp2LocalFlowController defaultHttp2LocalFlowController, Http2Stream http2Stream, int n) {
            this.this$0 = defaultHttp2LocalFlowController;
            super(defaultHttp2LocalFlowController, http2Stream, n);
        }

        @Override
        public void receiveFlowControlledFrame(int n) throws Http2Exception {
            super.receiveFlowControlledFrame(n);
            super.consumeBytes(n);
        }

        @Override
        public boolean consumeBytes(int n) throws Http2Exception {
            return true;
        }
    }
}

