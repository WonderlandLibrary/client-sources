/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionAdapter;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.handler.codec.http2.StreamByteDistributor;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayDeque;
import java.util.Deque;

public final class UniformStreamByteDistributor
implements StreamByteDistributor {
    private final Http2Connection.PropertyKey stateKey;
    private final Deque<State> queue = new ArrayDeque<State>(4);
    private int minAllocationChunk = 1024;
    private long totalStreamableBytes;

    public UniformStreamByteDistributor(Http2Connection http2Connection) {
        this.stateKey = http2Connection.newKey();
        Http2Stream http2Stream = http2Connection.connectionStream();
        http2Stream.setProperty(this.stateKey, new State(this, http2Stream));
        http2Connection.addListener(new Http2ConnectionAdapter(this){
            final UniformStreamByteDistributor this$0;
            {
                this.this$0 = uniformStreamByteDistributor;
            }

            @Override
            public void onStreamAdded(Http2Stream http2Stream) {
                http2Stream.setProperty(UniformStreamByteDistributor.access$000(this.this$0), new State(this.this$0, http2Stream));
            }

            @Override
            public void onStreamClosed(Http2Stream http2Stream) {
                UniformStreamByteDistributor.access$100(this.this$0, http2Stream).close();
            }
        });
    }

    public void minAllocationChunk(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("minAllocationChunk must be > 0");
        }
        this.minAllocationChunk = n;
    }

    @Override
    public void updateStreamableBytes(StreamByteDistributor.StreamState streamState) {
        this.state(streamState.stream()).updateStreamableBytes(Http2CodecUtil.streamableBytes(streamState), streamState.hasFrame(), streamState.windowSize());
    }

    @Override
    public void updateDependencyTree(int n, int n2, short s, boolean bl) {
    }

    @Override
    public boolean distribute(int n, StreamByteDistributor.Writer writer) throws Http2Exception {
        int n2 = this.queue.size();
        if (n2 == 0) {
            return this.totalStreamableBytes > 0L;
        }
        int n3 = Math.max(this.minAllocationChunk, n / n2);
        State state = this.queue.pollFirst();
        do {
            state.enqueued = false;
            if (state.windowNegative) continue;
            if (n == 0 && state.streamableBytes > 0) {
                this.queue.addFirst(state);
                state.enqueued = true;
                break;
            }
            int n4 = Math.min(n3, Math.min(n, state.streamableBytes));
            n -= n4;
            state.write(n4, writer);
        } while ((state = this.queue.pollFirst()) != null);
        return this.totalStreamableBytes > 0L;
    }

    private State state(Http2Stream http2Stream) {
        return (State)ObjectUtil.checkNotNull(http2Stream, "stream").getProperty(this.stateKey);
    }

    static Http2Connection.PropertyKey access$000(UniformStreamByteDistributor uniformStreamByteDistributor) {
        return uniformStreamByteDistributor.stateKey;
    }

    static State access$100(UniformStreamByteDistributor uniformStreamByteDistributor, Http2Stream http2Stream) {
        return uniformStreamByteDistributor.state(http2Stream);
    }

    static long access$200(UniformStreamByteDistributor uniformStreamByteDistributor) {
        return uniformStreamByteDistributor.totalStreamableBytes;
    }

    static long access$202(UniformStreamByteDistributor uniformStreamByteDistributor, long l) {
        uniformStreamByteDistributor.totalStreamableBytes = l;
        return uniformStreamByteDistributor.totalStreamableBytes;
    }

    static Deque access$300(UniformStreamByteDistributor uniformStreamByteDistributor) {
        return uniformStreamByteDistributor.queue;
    }

    private final class State {
        final Http2Stream stream;
        int streamableBytes;
        boolean windowNegative;
        boolean enqueued;
        boolean writing;
        static final boolean $assertionsDisabled = !UniformStreamByteDistributor.class.desiredAssertionStatus();
        final UniformStreamByteDistributor this$0;

        State(UniformStreamByteDistributor uniformStreamByteDistributor, Http2Stream http2Stream) {
            this.this$0 = uniformStreamByteDistributor;
            this.stream = http2Stream;
        }

        void updateStreamableBytes(int n, boolean bl, int n2) {
            if (!$assertionsDisabled && !bl && n != 0) {
                throw new AssertionError((Object)("hasFrame: " + bl + " newStreamableBytes: " + n));
            }
            int n3 = n - this.streamableBytes;
            if (n3 != 0) {
                this.streamableBytes = n;
                UniformStreamByteDistributor.access$202(this.this$0, UniformStreamByteDistributor.access$200(this.this$0) + (long)n3);
            }
            boolean bl2 = this.windowNegative = n2 < 0;
            if (bl && (n2 > 0 || n2 == 0 && !this.writing)) {
                this.addToQueue();
            }
        }

        void write(int n, StreamByteDistributor.Writer writer) throws Http2Exception {
            this.writing = true;
            try {
                writer.write(this.stream, n);
            } catch (Throwable throwable) {
                throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, throwable, "byte distribution write error", new Object[0]);
            } finally {
                this.writing = false;
            }
        }

        void addToQueue() {
            if (!this.enqueued) {
                this.enqueued = true;
                UniformStreamByteDistributor.access$300(this.this$0).addLast(this);
            }
        }

        void removeFromQueue() {
            if (this.enqueued) {
                this.enqueued = false;
                UniformStreamByteDistributor.access$300(this.this$0).remove(this);
            }
        }

        void close() {
            this.removeFromQueue();
            this.updateStreamableBytes(0, false, 1);
        }
    }
}

