/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionAdapter;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2RemoteFlowController;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.handler.codec.http2.Http2StreamVisitor;
import io.netty.handler.codec.http2.StreamByteDistributor;
import io.netty.handler.codec.http2.WeightedFairQueueByteDistributor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;
import java.util.Deque;

public class DefaultHttp2RemoteFlowController
implements Http2RemoteFlowController {
    private static final InternalLogger logger;
    private static final int MIN_WRITABLE_CHUNK = 32768;
    private final Http2Connection connection;
    private final Http2Connection.PropertyKey stateKey;
    private final StreamByteDistributor streamByteDistributor;
    private final FlowState connectionState;
    private int initialWindowSize = 65535;
    private WritabilityMonitor monitor;
    private ChannelHandlerContext ctx;
    static final boolean $assertionsDisabled;

    public DefaultHttp2RemoteFlowController(Http2Connection http2Connection) {
        this(http2Connection, (Http2RemoteFlowController.Listener)null);
    }

    public DefaultHttp2RemoteFlowController(Http2Connection http2Connection, StreamByteDistributor streamByteDistributor) {
        this(http2Connection, streamByteDistributor, null);
    }

    public DefaultHttp2RemoteFlowController(Http2Connection http2Connection, Http2RemoteFlowController.Listener listener) {
        this(http2Connection, new WeightedFairQueueByteDistributor(http2Connection), listener);
    }

    public DefaultHttp2RemoteFlowController(Http2Connection http2Connection, StreamByteDistributor streamByteDistributor, Http2RemoteFlowController.Listener listener) {
        this.connection = ObjectUtil.checkNotNull(http2Connection, "connection");
        this.streamByteDistributor = ObjectUtil.checkNotNull(streamByteDistributor, "streamWriteDistributor");
        this.stateKey = http2Connection.newKey();
        this.connectionState = new FlowState(this, http2Connection.connectionStream());
        http2Connection.connectionStream().setProperty(this.stateKey, this.connectionState);
        this.listener(listener);
        this.monitor.windowSize(this.connectionState, this.initialWindowSize);
        http2Connection.addListener(new Http2ConnectionAdapter(this){
            final DefaultHttp2RemoteFlowController this$0;
            {
                this.this$0 = defaultHttp2RemoteFlowController;
            }

            @Override
            public void onStreamAdded(Http2Stream http2Stream) {
                http2Stream.setProperty(DefaultHttp2RemoteFlowController.access$000(this.this$0), new FlowState(this.this$0, http2Stream));
            }

            @Override
            public void onStreamActive(Http2Stream http2Stream) {
                DefaultHttp2RemoteFlowController.access$300(this.this$0).windowSize(DefaultHttp2RemoteFlowController.access$100(this.this$0, http2Stream), DefaultHttp2RemoteFlowController.access$200(this.this$0));
            }

            @Override
            public void onStreamClosed(Http2Stream http2Stream) {
                DefaultHttp2RemoteFlowController.access$100(this.this$0, http2Stream).cancel(Http2Error.STREAM_CLOSED, null);
            }

            @Override
            public void onStreamHalfClosed(Http2Stream http2Stream) {
                if (Http2Stream.State.HALF_CLOSED_LOCAL == http2Stream.state()) {
                    DefaultHttp2RemoteFlowController.access$100(this.this$0, http2Stream).cancel(Http2Error.STREAM_CLOSED, null);
                }
            }
        });
    }

    @Override
    public void channelHandlerContext(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
        this.ctx = ObjectUtil.checkNotNull(channelHandlerContext, "ctx");
        this.channelWritabilityChanged();
        if (this.isChannelWritable()) {
            this.writePendingBytes();
        }
    }

    @Override
    public ChannelHandlerContext channelHandlerContext() {
        return this.ctx;
    }

    @Override
    public void initialWindowSize(int n) throws Http2Exception {
        if (!$assertionsDisabled && this.ctx != null && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        this.monitor.initialWindowSize(n);
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
    public boolean isWritable(Http2Stream http2Stream) {
        return this.monitor.isWritable(this.state(http2Stream));
    }

    @Override
    public void channelWritabilityChanged() throws Http2Exception {
        this.monitor.channelWritabilityChange();
    }

    @Override
    public void updateDependencyTree(int n, int n2, short s, boolean bl) {
        if (!($assertionsDisabled || s >= 1 && s <= 256)) {
            throw new AssertionError((Object)"Invalid weight");
        }
        if (!$assertionsDisabled && n == n2) {
            throw new AssertionError((Object)"A stream cannot depend on itself");
        }
        if (!($assertionsDisabled || n > 0 && n2 >= 0)) {
            throw new AssertionError((Object)"childStreamId must be > 0. parentStreamId must be >= 0.");
        }
        this.streamByteDistributor.updateDependencyTree(n, n2, s, bl);
    }

    private boolean isChannelWritable() {
        return this.ctx != null && this.isChannelWritable0();
    }

    private boolean isChannelWritable0() {
        return this.ctx.channel().isWritable();
    }

    @Override
    public void listener(Http2RemoteFlowController.Listener listener) {
        this.monitor = listener == null ? new WritabilityMonitor(this, null) : new ListenerWritabilityMonitor(this, listener);
    }

    @Override
    public void incrementWindowSize(Http2Stream http2Stream, int n) throws Http2Exception {
        if (!$assertionsDisabled && this.ctx != null && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        this.monitor.incrementWindowSize(this.state(http2Stream), n);
    }

    @Override
    public void addFlowControlled(Http2Stream http2Stream, Http2RemoteFlowController.FlowControlled flowControlled) {
        if (!$assertionsDisabled && this.ctx != null && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        ObjectUtil.checkNotNull(flowControlled, "frame");
        try {
            this.monitor.enqueueFrame(this.state(http2Stream), flowControlled);
        } catch (Throwable throwable) {
            flowControlled.error(this.ctx, throwable);
        }
    }

    @Override
    public boolean hasFlowControlled(Http2Stream http2Stream) {
        return this.state(http2Stream).hasFrame();
    }

    private FlowState state(Http2Stream http2Stream) {
        return (FlowState)http2Stream.getProperty(this.stateKey);
    }

    private int connectionWindowSize() {
        return this.connectionState.windowSize();
    }

    private int minUsableChannelBytes() {
        return Math.max(this.ctx.channel().config().getWriteBufferLowWaterMark(), 32768);
    }

    private int maxUsableChannelBytes() {
        int n = (int)Math.min(Integer.MAX_VALUE, this.ctx.channel().bytesBeforeUnwritable());
        int n2 = n > 0 ? Math.max(n, this.minUsableChannelBytes()) : 0;
        return Math.min(this.connectionState.windowSize(), n2);
    }

    private int writableBytes() {
        return Math.min(this.connectionWindowSize(), this.maxUsableChannelBytes());
    }

    @Override
    public void writePendingBytes() throws Http2Exception {
        this.monitor.writePendingBytes();
    }

    static Http2Connection.PropertyKey access$000(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController) {
        return defaultHttp2RemoteFlowController.stateKey;
    }

    static FlowState access$100(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController, Http2Stream http2Stream) {
        return defaultHttp2RemoteFlowController.state(http2Stream);
    }

    static int access$200(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController) {
        return defaultHttp2RemoteFlowController.initialWindowSize;
    }

    static WritabilityMonitor access$300(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController) {
        return defaultHttp2RemoteFlowController.monitor;
    }

    static ChannelHandlerContext access$500(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController) {
        return defaultHttp2RemoteFlowController.ctx;
    }

    static StreamByteDistributor access$600(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController) {
        return defaultHttp2RemoteFlowController.streamByteDistributor;
    }

    static int access$700(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController) {
        return defaultHttp2RemoteFlowController.connectionWindowSize();
    }

    static FlowState access$800(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController) {
        return defaultHttp2RemoteFlowController.connectionState;
    }

    static int access$900(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController) {
        return defaultHttp2RemoteFlowController.writableBytes();
    }

    static boolean access$1000(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController) {
        return defaultHttp2RemoteFlowController.isChannelWritable0();
    }

    static int access$202(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController, int n) {
        defaultHttp2RemoteFlowController.initialWindowSize = n;
        return defaultHttp2RemoteFlowController.initialWindowSize;
    }

    static Http2Connection access$1100(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController) {
        return defaultHttp2RemoteFlowController.connection;
    }

    static boolean access$1200(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController) {
        return defaultHttp2RemoteFlowController.isChannelWritable();
    }

    static InternalLogger access$1400() {
        return logger;
    }

    static {
        $assertionsDisabled = !DefaultHttp2RemoteFlowController.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(DefaultHttp2RemoteFlowController.class);
    }

    private final class ListenerWritabilityMonitor
    extends WritabilityMonitor
    implements Http2StreamVisitor {
        private final Http2RemoteFlowController.Listener listener;
        final DefaultHttp2RemoteFlowController this$0;

        ListenerWritabilityMonitor(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController, Http2RemoteFlowController.Listener listener) {
            this.this$0 = defaultHttp2RemoteFlowController;
            super(defaultHttp2RemoteFlowController, null);
            this.listener = listener;
        }

        @Override
        public boolean visit(Http2Stream http2Stream) throws Http2Exception {
            FlowState flowState = DefaultHttp2RemoteFlowController.access$100(this.this$0, http2Stream);
            if (this.isWritable(flowState) != flowState.markedWritability()) {
                this.notifyWritabilityChanged(flowState);
            }
            return false;
        }

        @Override
        void windowSize(FlowState flowState, int n) {
            super.windowSize(flowState, n);
            try {
                this.checkStateWritability(flowState);
            } catch (Http2Exception http2Exception) {
                throw new RuntimeException("Caught unexpected exception from window", http2Exception);
            }
        }

        @Override
        void incrementWindowSize(FlowState flowState, int n) throws Http2Exception {
            super.incrementWindowSize(flowState, n);
            this.checkStateWritability(flowState);
        }

        @Override
        void initialWindowSize(int n) throws Http2Exception {
            super.initialWindowSize(n);
            if (this.isWritableConnection()) {
                this.checkAllWritabilityChanged();
            }
        }

        @Override
        void enqueueFrame(FlowState flowState, Http2RemoteFlowController.FlowControlled flowControlled) throws Http2Exception {
            super.enqueueFrame(flowState, flowControlled);
            this.checkConnectionThenStreamWritabilityChanged(flowState);
        }

        @Override
        void stateCancelled(FlowState flowState) {
            try {
                this.checkConnectionThenStreamWritabilityChanged(flowState);
            } catch (Http2Exception http2Exception) {
                throw new RuntimeException("Caught unexpected exception from checkAllWritabilityChanged", http2Exception);
            }
        }

        @Override
        void channelWritabilityChange() throws Http2Exception {
            if (DefaultHttp2RemoteFlowController.access$800(this.this$0).markedWritability() != DefaultHttp2RemoteFlowController.access$1200(this.this$0)) {
                this.checkAllWritabilityChanged();
            }
        }

        private void checkStateWritability(FlowState flowState) throws Http2Exception {
            if (this.isWritable(flowState) != flowState.markedWritability()) {
                if (flowState == DefaultHttp2RemoteFlowController.access$800(this.this$0)) {
                    this.checkAllWritabilityChanged();
                } else {
                    this.notifyWritabilityChanged(flowState);
                }
            }
        }

        private void notifyWritabilityChanged(FlowState flowState) {
            flowState.markedWritability(!flowState.markedWritability());
            try {
                this.listener.writabilityChanged(FlowState.access$1300(flowState));
            } catch (Throwable throwable) {
                DefaultHttp2RemoteFlowController.access$1400().error("Caught Throwable from listener.writabilityChanged", throwable);
            }
        }

        private void checkConnectionThenStreamWritabilityChanged(FlowState flowState) throws Http2Exception {
            if (this.isWritableConnection() != DefaultHttp2RemoteFlowController.access$800(this.this$0).markedWritability()) {
                this.checkAllWritabilityChanged();
            } else if (this.isWritable(flowState) != flowState.markedWritability()) {
                this.notifyWritabilityChanged(flowState);
            }
        }

        private void checkAllWritabilityChanged() throws Http2Exception {
            DefaultHttp2RemoteFlowController.access$800(this.this$0).markedWritability(this.isWritableConnection());
            DefaultHttp2RemoteFlowController.access$1100(this.this$0).forEachActiveStream(this);
        }
    }

    private class WritabilityMonitor
    implements StreamByteDistributor.Writer {
        private boolean inWritePendingBytes;
        private long totalPendingBytes;
        final DefaultHttp2RemoteFlowController this$0;

        private WritabilityMonitor(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController) {
            this.this$0 = defaultHttp2RemoteFlowController;
        }

        @Override
        public final void write(Http2Stream http2Stream, int n) {
            DefaultHttp2RemoteFlowController.access$100(this.this$0, http2Stream).writeAllocatedBytes(n);
        }

        void channelWritabilityChange() throws Http2Exception {
        }

        void stateCancelled(FlowState flowState) {
        }

        void windowSize(FlowState flowState, int n) {
            flowState.windowSize(n);
        }

        void incrementWindowSize(FlowState flowState, int n) throws Http2Exception {
            flowState.incrementStreamWindow(n);
        }

        void enqueueFrame(FlowState flowState, Http2RemoteFlowController.FlowControlled flowControlled) throws Http2Exception {
            flowState.enqueueFrame(flowControlled);
        }

        final void incrementPendingBytes(int n) {
            this.totalPendingBytes += (long)n;
        }

        final boolean isWritable(FlowState flowState) {
            return this.isWritableConnection() && flowState.isWritable();
        }

        final void writePendingBytes() throws Http2Exception {
            if (this.inWritePendingBytes) {
                return;
            }
            this.inWritePendingBytes = true;
            try {
                int n = DefaultHttp2RemoteFlowController.access$900(this.this$0);
                while (DefaultHttp2RemoteFlowController.access$600(this.this$0).distribute(n, this) && (n = DefaultHttp2RemoteFlowController.access$900(this.this$0)) > 0 && DefaultHttp2RemoteFlowController.access$1000(this.this$0)) {
                }
            } finally {
                this.inWritePendingBytes = false;
            }
        }

        void initialWindowSize(int n) throws Http2Exception {
            if (n < 0) {
                throw new IllegalArgumentException("Invalid initial window size: " + n);
            }
            int n2 = n - DefaultHttp2RemoteFlowController.access$200(this.this$0);
            DefaultHttp2RemoteFlowController.access$202(this.this$0, n);
            DefaultHttp2RemoteFlowController.access$1100(this.this$0).forEachActiveStream(new Http2StreamVisitor(this, n2){
                final int val$delta;
                final WritabilityMonitor this$1;
                {
                    this.this$1 = writabilityMonitor;
                    this.val$delta = n;
                }

                @Override
                public boolean visit(Http2Stream http2Stream) throws Http2Exception {
                    DefaultHttp2RemoteFlowController.access$100(this.this$1.this$0, http2Stream).incrementStreamWindow(this.val$delta);
                    return false;
                }
            });
            if (n2 > 0 && DefaultHttp2RemoteFlowController.access$1200(this.this$0)) {
                this.writePendingBytes();
            }
        }

        final boolean isWritableConnection() {
            return (long)DefaultHttp2RemoteFlowController.access$800(this.this$0).windowSize() - this.totalPendingBytes > 0L && DefaultHttp2RemoteFlowController.access$1200(this.this$0);
        }

        WritabilityMonitor(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController, 1 var2_2) {
            this(defaultHttp2RemoteFlowController);
        }
    }

    private final class FlowState
    implements StreamByteDistributor.StreamState {
        private final Http2Stream stream;
        private final Deque<Http2RemoteFlowController.FlowControlled> pendingWriteQueue;
        private int window;
        private long pendingBytes;
        private boolean markedWritable;
        private boolean writing;
        private boolean cancelled;
        static final boolean $assertionsDisabled = !DefaultHttp2RemoteFlowController.class.desiredAssertionStatus();
        final DefaultHttp2RemoteFlowController this$0;

        FlowState(DefaultHttp2RemoteFlowController defaultHttp2RemoteFlowController, Http2Stream http2Stream) {
            this.this$0 = defaultHttp2RemoteFlowController;
            this.stream = http2Stream;
            this.pendingWriteQueue = new ArrayDeque<Http2RemoteFlowController.FlowControlled>(2);
        }

        boolean isWritable() {
            return (long)this.windowSize() > this.pendingBytes() && !this.cancelled;
        }

        @Override
        public Http2Stream stream() {
            return this.stream;
        }

        boolean markedWritability() {
            return this.markedWritable;
        }

        void markedWritability(boolean bl) {
            this.markedWritable = bl;
        }

        @Override
        public int windowSize() {
            return this.window;
        }

        void windowSize(int n) {
            this.window = n;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        int writeAllocatedBytes(int n) {
            int n2;
            int n3 = n;
            Throwable throwable = null;
            try {
                int n4;
                Http2RemoteFlowController.FlowControlled flowControlled;
                if (!$assertionsDisabled && this.writing) {
                    throw new AssertionError();
                }
                this.writing = true;
                boolean bl = false;
                while (!(this.cancelled || (flowControlled = this.peek()) == null || (n4 = Math.min(n, this.writableWindow())) <= 0 && flowControlled.size() > 0)) {
                    bl = true;
                    int n5 = flowControlled.size();
                    try {
                        flowControlled.write(DefaultHttp2RemoteFlowController.access$500(this.this$0), Math.max(0, n4));
                        if (flowControlled.size() != 0) continue;
                        this.pendingWriteQueue.remove();
                        flowControlled.writeComplete();
                    } finally {
                        n -= n5 - flowControlled.size();
                    }
                }
                if (!bl) {
                    n4 = -1;
                    return n4;
                }
            } catch (Throwable throwable2) {
                this.cancelled = true;
                throwable = throwable2;
            } finally {
                this.writing = false;
                int n6 = n3 - n;
                this.decrementPendingBytes(n6, false);
                this.decrementFlowControlWindow(n6);
                if (this.cancelled) {
                    this.cancel(Http2Error.INTERNAL_ERROR, throwable);
                }
            }
            return n2;
        }

        int incrementStreamWindow(int n) throws Http2Exception {
            if (n > 0 && Integer.MAX_VALUE - n < this.window) {
                throw Http2Exception.streamError(this.stream.id(), Http2Error.FLOW_CONTROL_ERROR, "Window size overflow for stream: %d", this.stream.id());
            }
            this.window += n;
            DefaultHttp2RemoteFlowController.access$600(this.this$0).updateStreamableBytes(this);
            return this.window;
        }

        private int writableWindow() {
            return Math.min(this.window, DefaultHttp2RemoteFlowController.access$700(this.this$0));
        }

        @Override
        public long pendingBytes() {
            return this.pendingBytes;
        }

        void enqueueFrame(Http2RemoteFlowController.FlowControlled flowControlled) {
            Http2RemoteFlowController.FlowControlled flowControlled2 = this.pendingWriteQueue.peekLast();
            if (flowControlled2 == null) {
                this.enqueueFrameWithoutMerge(flowControlled);
                return;
            }
            int n = flowControlled2.size();
            if (flowControlled2.merge(DefaultHttp2RemoteFlowController.access$500(this.this$0), flowControlled)) {
                this.incrementPendingBytes(flowControlled2.size() - n, true);
                return;
            }
            this.enqueueFrameWithoutMerge(flowControlled);
        }

        private void enqueueFrameWithoutMerge(Http2RemoteFlowController.FlowControlled flowControlled) {
            this.pendingWriteQueue.offer(flowControlled);
            this.incrementPendingBytes(flowControlled.size(), true);
        }

        @Override
        public boolean hasFrame() {
            return !this.pendingWriteQueue.isEmpty();
        }

        private Http2RemoteFlowController.FlowControlled peek() {
            return this.pendingWriteQueue.peek();
        }

        void cancel(Http2Error http2Error, Throwable throwable) {
            this.cancelled = true;
            if (this.writing) {
                return;
            }
            Http2RemoteFlowController.FlowControlled flowControlled = this.pendingWriteQueue.poll();
            if (flowControlled != null) {
                Http2Exception http2Exception = Http2Exception.streamError(this.stream.id(), http2Error, throwable, "Stream closed before write could take place", new Object[0]);
                do {
                    this.writeError(flowControlled, http2Exception);
                } while ((flowControlled = this.pendingWriteQueue.poll()) != null);
            }
            DefaultHttp2RemoteFlowController.access$600(this.this$0).updateStreamableBytes(this);
            DefaultHttp2RemoteFlowController.access$300(this.this$0).stateCancelled(this);
        }

        private void incrementPendingBytes(int n, boolean bl) {
            this.pendingBytes += (long)n;
            DefaultHttp2RemoteFlowController.access$300(this.this$0).incrementPendingBytes(n);
            if (bl) {
                DefaultHttp2RemoteFlowController.access$600(this.this$0).updateStreamableBytes(this);
            }
        }

        private void decrementPendingBytes(int n, boolean bl) {
            this.incrementPendingBytes(-n, bl);
        }

        private void decrementFlowControlWindow(int n) {
            try {
                int n2 = -n;
                DefaultHttp2RemoteFlowController.access$800(this.this$0).incrementStreamWindow(n2);
                this.incrementStreamWindow(n2);
            } catch (Http2Exception http2Exception) {
                throw new IllegalStateException("Invalid window state when writing frame: " + http2Exception.getMessage(), http2Exception);
            }
        }

        private void writeError(Http2RemoteFlowController.FlowControlled flowControlled, Http2Exception http2Exception) {
            if (!$assertionsDisabled && DefaultHttp2RemoteFlowController.access$500(this.this$0) == null) {
                throw new AssertionError();
            }
            this.decrementPendingBytes(flowControlled.size(), true);
            flowControlled.error(DefaultHttp2RemoteFlowController.access$500(this.this$0), http2Exception);
        }

        static Http2Stream access$1300(FlowState flowState) {
            return flowState.stream;
        }
    }
}

