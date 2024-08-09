/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.spdy.SpdyDataFrame;
import io.netty.util.internal.PlatformDependent;
import java.util.Comparator;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

final class SpdySession {
    private final AtomicInteger activeLocalStreams = new AtomicInteger();
    private final AtomicInteger activeRemoteStreams = new AtomicInteger();
    private final Map<Integer, StreamState> activeStreams = PlatformDependent.newConcurrentHashMap();
    private final StreamComparator streamComparator = new StreamComparator(this);
    private final AtomicInteger sendWindowSize;
    private final AtomicInteger receiveWindowSize;

    SpdySession(int n, int n2) {
        this.sendWindowSize = new AtomicInteger(n);
        this.receiveWindowSize = new AtomicInteger(n2);
    }

    int numActiveStreams(boolean bl) {
        if (bl) {
            return this.activeRemoteStreams.get();
        }
        return this.activeLocalStreams.get();
    }

    boolean noActiveStreams() {
        return this.activeStreams.isEmpty();
    }

    boolean isActiveStream(int n) {
        return this.activeStreams.containsKey(n);
    }

    Map<Integer, StreamState> activeStreams() {
        TreeMap<Integer, StreamState> treeMap = new TreeMap<Integer, StreamState>(this.streamComparator);
        treeMap.putAll(this.activeStreams);
        return treeMap;
    }

    void acceptStream(int n, byte by, boolean bl, boolean bl2, int n2, int n3, boolean bl3) {
        StreamState streamState;
        if (!(bl && bl2 || (streamState = this.activeStreams.put(n, new StreamState(by, bl, bl2, n2, n3))) != null)) {
            if (bl3) {
                this.activeRemoteStreams.incrementAndGet();
            } else {
                this.activeLocalStreams.incrementAndGet();
            }
        }
    }

    private StreamState removeActiveStream(int n, boolean bl) {
        StreamState streamState = this.activeStreams.remove(n);
        if (streamState != null) {
            if (bl) {
                this.activeRemoteStreams.decrementAndGet();
            } else {
                this.activeLocalStreams.decrementAndGet();
            }
        }
        return streamState;
    }

    void removeStream(int n, Throwable throwable, boolean bl) {
        StreamState streamState = this.removeActiveStream(n, bl);
        if (streamState != null) {
            streamState.clearPendingWrites(throwable);
        }
    }

    boolean isRemoteSideClosed(int n) {
        StreamState streamState = this.activeStreams.get(n);
        return streamState == null || streamState.isRemoteSideClosed();
    }

    void closeRemoteSide(int n, boolean bl) {
        StreamState streamState = this.activeStreams.get(n);
        if (streamState != null) {
            streamState.closeRemoteSide();
            if (streamState.isLocalSideClosed()) {
                this.removeActiveStream(n, bl);
            }
        }
    }

    boolean isLocalSideClosed(int n) {
        StreamState streamState = this.activeStreams.get(n);
        return streamState == null || streamState.isLocalSideClosed();
    }

    void closeLocalSide(int n, boolean bl) {
        StreamState streamState = this.activeStreams.get(n);
        if (streamState != null) {
            streamState.closeLocalSide();
            if (streamState.isRemoteSideClosed()) {
                this.removeActiveStream(n, bl);
            }
        }
    }

    boolean hasReceivedReply(int n) {
        StreamState streamState = this.activeStreams.get(n);
        return streamState != null && streamState.hasReceivedReply();
    }

    void receivedReply(int n) {
        StreamState streamState = this.activeStreams.get(n);
        if (streamState != null) {
            streamState.receivedReply();
        }
    }

    int getSendWindowSize(int n) {
        if (n == 0) {
            return this.sendWindowSize.get();
        }
        StreamState streamState = this.activeStreams.get(n);
        return streamState != null ? streamState.getSendWindowSize() : -1;
    }

    int updateSendWindowSize(int n, int n2) {
        if (n == 0) {
            return this.sendWindowSize.addAndGet(n2);
        }
        StreamState streamState = this.activeStreams.get(n);
        return streamState != null ? streamState.updateSendWindowSize(n2) : -1;
    }

    int updateReceiveWindowSize(int n, int n2) {
        if (n == 0) {
            return this.receiveWindowSize.addAndGet(n2);
        }
        StreamState streamState = this.activeStreams.get(n);
        if (streamState == null) {
            return 1;
        }
        if (n2 > 0) {
            streamState.setReceiveWindowSizeLowerBound(0);
        }
        return streamState.updateReceiveWindowSize(n2);
    }

    int getReceiveWindowSizeLowerBound(int n) {
        if (n == 0) {
            return 1;
        }
        StreamState streamState = this.activeStreams.get(n);
        return streamState != null ? streamState.getReceiveWindowSizeLowerBound() : 0;
    }

    void updateAllSendWindowSizes(int n) {
        for (StreamState streamState : this.activeStreams.values()) {
            streamState.updateSendWindowSize(n);
        }
    }

    void updateAllReceiveWindowSizes(int n) {
        for (StreamState streamState : this.activeStreams.values()) {
            streamState.updateReceiveWindowSize(n);
            if (n >= 0) continue;
            streamState.setReceiveWindowSizeLowerBound(n);
        }
    }

    boolean putPendingWrite(int n, PendingWrite pendingWrite) {
        StreamState streamState = this.activeStreams.get(n);
        return streamState != null && streamState.putPendingWrite(pendingWrite);
    }

    PendingWrite getPendingWrite(int n) {
        if (n == 0) {
            for (Map.Entry<Integer, StreamState> entry : this.activeStreams().entrySet()) {
                PendingWrite pendingWrite;
                StreamState streamState = entry.getValue();
                if (streamState.getSendWindowSize() <= 0 || (pendingWrite = streamState.getPendingWrite()) == null) continue;
                return pendingWrite;
            }
            return null;
        }
        StreamState streamState = this.activeStreams.get(n);
        return streamState != null ? streamState.getPendingWrite() : null;
    }

    PendingWrite removePendingWrite(int n) {
        StreamState streamState = this.activeStreams.get(n);
        return streamState != null ? streamState.removePendingWrite() : null;
    }

    static Map access$000(SpdySession spdySession) {
        return spdySession.activeStreams;
    }

    public static final class PendingWrite {
        final SpdyDataFrame spdyDataFrame;
        final ChannelPromise promise;

        PendingWrite(SpdyDataFrame spdyDataFrame, ChannelPromise channelPromise) {
            this.spdyDataFrame = spdyDataFrame;
            this.promise = channelPromise;
        }

        void fail(Throwable throwable) {
            this.spdyDataFrame.release();
            this.promise.setFailure(throwable);
        }
    }

    private final class StreamComparator
    implements Comparator<Integer> {
        final SpdySession this$0;

        StreamComparator(SpdySession spdySession) {
            this.this$0 = spdySession;
        }

        @Override
        public int compare(Integer n, Integer n2) {
            StreamState streamState = (StreamState)SpdySession.access$000(this.this$0).get(n);
            StreamState streamState2 = (StreamState)SpdySession.access$000(this.this$0).get(n2);
            int n3 = streamState.getPriority() - streamState2.getPriority();
            if (n3 != 0) {
                return n3;
            }
            return n - n2;
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((Integer)object, (Integer)object2);
        }
    }

    private static final class StreamState {
        private final byte priority;
        private boolean remoteSideClosed;
        private boolean localSideClosed;
        private boolean receivedReply;
        private final AtomicInteger sendWindowSize;
        private final AtomicInteger receiveWindowSize;
        private int receiveWindowSizeLowerBound;
        private final Queue<PendingWrite> pendingWriteQueue = new ConcurrentLinkedQueue<PendingWrite>();

        StreamState(byte by, boolean bl, boolean bl2, int n, int n2) {
            this.priority = by;
            this.remoteSideClosed = bl;
            this.localSideClosed = bl2;
            this.sendWindowSize = new AtomicInteger(n);
            this.receiveWindowSize = new AtomicInteger(n2);
        }

        byte getPriority() {
            return this.priority;
        }

        boolean isRemoteSideClosed() {
            return this.remoteSideClosed;
        }

        void closeRemoteSide() {
            this.remoteSideClosed = true;
        }

        boolean isLocalSideClosed() {
            return this.localSideClosed;
        }

        void closeLocalSide() {
            this.localSideClosed = true;
        }

        boolean hasReceivedReply() {
            return this.receivedReply;
        }

        void receivedReply() {
            this.receivedReply = true;
        }

        int getSendWindowSize() {
            return this.sendWindowSize.get();
        }

        int updateSendWindowSize(int n) {
            return this.sendWindowSize.addAndGet(n);
        }

        int updateReceiveWindowSize(int n) {
            return this.receiveWindowSize.addAndGet(n);
        }

        int getReceiveWindowSizeLowerBound() {
            return this.receiveWindowSizeLowerBound;
        }

        void setReceiveWindowSizeLowerBound(int n) {
            this.receiveWindowSizeLowerBound = n;
        }

        boolean putPendingWrite(PendingWrite pendingWrite) {
            return this.pendingWriteQueue.offer(pendingWrite);
        }

        PendingWrite getPendingWrite() {
            return this.pendingWriteQueue.peek();
        }

        PendingWrite removePendingWrite() {
            return this.pendingWriteQueue.poll();
        }

        void clearPendingWrites(Throwable throwable) {
            PendingWrite pendingWrite;
            while ((pendingWrite = this.pendingWriteQueue.poll()) != null) {
                pendingWrite.fail(throwable);
            }
        }
    }
}

