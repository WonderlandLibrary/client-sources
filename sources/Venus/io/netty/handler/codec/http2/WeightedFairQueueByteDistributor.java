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
import io.netty.util.collection.IntCollections;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.netty.util.internal.DefaultPriorityQueue;
import io.netty.util.internal.EmptyPriorityQueue;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.PriorityQueue;
import io.netty.util.internal.PriorityQueueNode;
import io.netty.util.internal.SystemPropertyUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class WeightedFairQueueByteDistributor
implements StreamByteDistributor {
    static final int INITIAL_CHILDREN_MAP_SIZE;
    private static final int DEFAULT_MAX_STATE_ONLY_SIZE = 5;
    private final Http2Connection.PropertyKey stateKey;
    private final IntObjectMap<State> stateOnlyMap;
    private final PriorityQueue<State> stateOnlyRemovalQueue;
    private final Http2Connection connection;
    private final State connectionState;
    private int allocationQuantum = 1024;
    private final int maxStateOnlySize;
    static final boolean $assertionsDisabled;

    public WeightedFairQueueByteDistributor(Http2Connection http2Connection) {
        this(http2Connection, 5);
    }

    public WeightedFairQueueByteDistributor(Http2Connection http2Connection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("maxStateOnlySize: " + n + " (expected: >0)");
        }
        if (n == 0) {
            this.stateOnlyMap = IntCollections.emptyMap();
            this.stateOnlyRemovalQueue = EmptyPriorityQueue.instance();
        } else {
            this.stateOnlyMap = new IntObjectHashMap<State>(n);
            this.stateOnlyRemovalQueue = new DefaultPriorityQueue<State>(StateOnlyComparator.INSTANCE, n + 2);
        }
        this.maxStateOnlySize = n;
        this.connection = http2Connection;
        this.stateKey = http2Connection.newKey();
        Http2Stream http2Stream = http2Connection.connectionStream();
        this.connectionState = new State(this, http2Stream, 16);
        http2Stream.setProperty(this.stateKey, this.connectionState);
        http2Connection.addListener(new Http2ConnectionAdapter(this){
            final WeightedFairQueueByteDistributor this$0;
            {
                this.this$0 = weightedFairQueueByteDistributor;
            }

            @Override
            public void onStreamAdded(Http2Stream http2Stream) {
                State state = (State)WeightedFairQueueByteDistributor.access$000(this.this$0).remove(http2Stream.id());
                if (state == null) {
                    state = new State(this.this$0, http2Stream);
                    ArrayList<ParentChangedEvent> arrayList = new ArrayList<ParentChangedEvent>(1);
                    WeightedFairQueueByteDistributor.access$100(this.this$0).takeChild(state, false, arrayList);
                    this.this$0.notifyParentChanged(arrayList);
                } else {
                    WeightedFairQueueByteDistributor.access$200(this.this$0).removeTyped(state);
                    state.stream = http2Stream;
                }
                switch (2.$SwitchMap$io$netty$handler$codec$http2$Http2Stream$State[http2Stream.state().ordinal()]) {
                    case 1: 
                    case 2: {
                        state.setStreamReservedOrActivated();
                        break;
                    }
                }
                http2Stream.setProperty(WeightedFairQueueByteDistributor.access$300(this.this$0), state);
            }

            @Override
            public void onStreamActive(Http2Stream http2Stream) {
                WeightedFairQueueByteDistributor.access$400(this.this$0, http2Stream).setStreamReservedOrActivated();
            }

            @Override
            public void onStreamClosed(Http2Stream http2Stream) {
                WeightedFairQueueByteDistributor.access$400(this.this$0, http2Stream).close();
            }

            @Override
            public void onStreamRemoved(Http2Stream http2Stream) {
                State state = WeightedFairQueueByteDistributor.access$400(this.this$0, http2Stream);
                state.stream = null;
                if (WeightedFairQueueByteDistributor.access$500(this.this$0) == 0) {
                    state.parent.removeChild(state);
                    return;
                }
                if (WeightedFairQueueByteDistributor.access$200(this.this$0).size() == WeightedFairQueueByteDistributor.access$500(this.this$0)) {
                    State state2 = (State)WeightedFairQueueByteDistributor.access$200(this.this$0).peek();
                    if (StateOnlyComparator.INSTANCE.compare(state2, state) >= 0) {
                        state.parent.removeChild(state);
                        return;
                    }
                    WeightedFairQueueByteDistributor.access$200(this.this$0).poll();
                    state2.parent.removeChild(state2);
                    WeightedFairQueueByteDistributor.access$000(this.this$0).remove(state2.streamId);
                }
                WeightedFairQueueByteDistributor.access$200(this.this$0).add(state);
                WeightedFairQueueByteDistributor.access$000(this.this$0).put(state.streamId, state);
            }
        });
    }

    @Override
    public void updateStreamableBytes(StreamByteDistributor.StreamState streamState) {
        this.state(streamState.stream()).updateStreamableBytes(Http2CodecUtil.streamableBytes(streamState), streamState.hasFrame() && streamState.windowSize() >= 0);
    }

    @Override
    public void updateDependencyTree(int n, int n2, short s, boolean bl) {
        ArrayList<ParentChangedEvent> arrayList;
        State state;
        State state2 = this.state(n);
        if (state2 == null) {
            if (this.maxStateOnlySize == 0) {
                return;
            }
            state2 = new State(this, n);
            this.stateOnlyRemovalQueue.add(state2);
            this.stateOnlyMap.put(n, state2);
        }
        if ((state = this.state(n2)) == null) {
            if (this.maxStateOnlySize == 0) {
                return;
            }
            state = new State(this, n2);
            this.stateOnlyRemovalQueue.add(state);
            this.stateOnlyMap.put(n2, state);
            arrayList = new ArrayList(1);
            this.connectionState.takeChild(state, false, arrayList);
            this.notifyParentChanged(arrayList);
        }
        if (state2.activeCountForTree != 0 && state2.parent != null) {
            state2.parent.totalQueuedWeights += (long)(s - state2.weight);
        }
        state2.weight = s;
        if (state != state2.parent || bl && state.children.size() != 1) {
            if (state.isDescendantOf(state2)) {
                arrayList = new ArrayList(2 + (bl ? state.children.size() : 0));
                state2.parent.takeChild(state, false, arrayList);
            } else {
                arrayList = new ArrayList<ParentChangedEvent>(1 + (bl ? state.children.size() : 0));
            }
            state.takeChild(state2, bl, arrayList);
            this.notifyParentChanged(arrayList);
        }
        while (this.stateOnlyRemovalQueue.size() > this.maxStateOnlySize) {
            arrayList = (State)this.stateOnlyRemovalQueue.poll();
            ((State)((Object)arrayList)).parent.removeChild((State)((Object)arrayList));
            this.stateOnlyMap.remove(((State)((Object)arrayList)).streamId);
        }
    }

    @Override
    public boolean distribute(int n, StreamByteDistributor.Writer writer) throws Http2Exception {
        int n2;
        if (this.connectionState.activeCountForTree == 0) {
            return true;
        }
        do {
            n2 = this.connectionState.activeCountForTree;
            n -= this.distributeToChildren(n, writer, this.connectionState);
        } while (this.connectionState.activeCountForTree != 0 && (n > 0 || n2 != this.connectionState.activeCountForTree));
        return this.connectionState.activeCountForTree != 0;
    }

    public void allocationQuantum(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("allocationQuantum must be > 0");
        }
        this.allocationQuantum = n;
    }

    private int distribute(int n, StreamByteDistributor.Writer writer, State state) throws Http2Exception {
        if (state.isActive()) {
            int n2 = Math.min(n, state.streamableBytes);
            state.write(n2, writer);
            if (n2 == 0 && n != 0) {
                state.updateStreamableBytes(state.streamableBytes, true);
            }
            return n2;
        }
        return this.distributeToChildren(n, writer, state);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int distributeToChildren(int n, StreamByteDistributor.Writer writer, State state) throws Http2Exception {
        long l = state.totalQueuedWeights;
        State state2 = state.pollPseudoTimeQueue();
        State state3 = state.peekPseudoTimeQueue();
        state2.setDistributing();
        try {
            if (!$assertionsDisabled && state3 != null && state3.pseudoTimeToWrite < state2.pseudoTimeToWrite) {
                throw new AssertionError((Object)("nextChildState[" + state3.streamId + "].pseudoTime(" + state3.pseudoTimeToWrite + ") <  childState[" + state2.streamId + "].pseudoTime(" + state2.pseudoTimeToWrite + ")"));
            }
            int n2 = this.distribute(state3 == null ? n : Math.min(n, (int)Math.min((state3.pseudoTimeToWrite - state2.pseudoTimeToWrite) * (long)state2.weight / l + (long)this.allocationQuantum, Integer.MAX_VALUE)), writer, state2);
            state.pseudoTime += (long)n2;
            state2.updatePseudoTime(state, n2, l);
            int n3 = n2;
            return n3;
        } finally {
            state2.unsetDistributing();
            if (state2.activeCountForTree != 0) {
                state.offerPseudoTimeQueue(state2);
            }
        }
    }

    private State state(Http2Stream http2Stream) {
        return (State)http2Stream.getProperty(this.stateKey);
    }

    private State state(int n) {
        Http2Stream http2Stream = this.connection.stream(n);
        return http2Stream != null ? this.state(http2Stream) : this.stateOnlyMap.get(n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean isChild(int n, int n2, short s) {
        State state = this.state(n2);
        if (!state.children.containsKey(n)) return false;
        State state2 = this.state(n);
        if (state2.parent != state) return false;
        if (state2.weight != s) return false;
        return true;
    }

    int numChildren(int n) {
        State state = this.state(n);
        return state == null ? 0 : state.children.size();
    }

    void notifyParentChanged(List<ParentChangedEvent> list) {
        for (int i = 0; i < list.size(); ++i) {
            ParentChangedEvent parentChangedEvent = list.get(i);
            this.stateOnlyRemovalQueue.priorityChanged(parentChangedEvent.state);
            if (parentChangedEvent.state.parent == null || parentChangedEvent.state.activeCountForTree == 0) continue;
            parentChangedEvent.state.parent.offerAndInitializePseudoTime(parentChangedEvent.state);
            parentChangedEvent.state.parent.activeCountChangeForTree(parentChangedEvent.state.activeCountForTree);
        }
    }

    static IntObjectMap access$000(WeightedFairQueueByteDistributor weightedFairQueueByteDistributor) {
        return weightedFairQueueByteDistributor.stateOnlyMap;
    }

    static State access$100(WeightedFairQueueByteDistributor weightedFairQueueByteDistributor) {
        return weightedFairQueueByteDistributor.connectionState;
    }

    static PriorityQueue access$200(WeightedFairQueueByteDistributor weightedFairQueueByteDistributor) {
        return weightedFairQueueByteDistributor.stateOnlyRemovalQueue;
    }

    static Http2Connection.PropertyKey access$300(WeightedFairQueueByteDistributor weightedFairQueueByteDistributor) {
        return weightedFairQueueByteDistributor.stateKey;
    }

    static State access$400(WeightedFairQueueByteDistributor weightedFairQueueByteDistributor, Http2Stream http2Stream) {
        return weightedFairQueueByteDistributor.state(http2Stream);
    }

    static int access$500(WeightedFairQueueByteDistributor weightedFairQueueByteDistributor) {
        return weightedFairQueueByteDistributor.maxStateOnlySize;
    }

    static {
        $assertionsDisabled = !WeightedFairQueueByteDistributor.class.desiredAssertionStatus();
        INITIAL_CHILDREN_MAP_SIZE = Math.max(1, SystemPropertyUtil.getInt("io.netty.http2.childrenMapSize", 2));
    }

    private static final class ParentChangedEvent {
        final State state;
        final State oldParent;

        ParentChangedEvent(State state, State state2) {
            this.state = state;
            this.oldParent = state2;
        }
    }

    private final class State
    implements PriorityQueueNode {
        private static final byte STATE_IS_ACTIVE = 1;
        private static final byte STATE_IS_DISTRIBUTING = 2;
        private static final byte STATE_STREAM_ACTIVATED = 4;
        Http2Stream stream;
        State parent;
        IntObjectMap<State> children;
        private final PriorityQueue<State> pseudoTimeQueue;
        final int streamId;
        int streamableBytes;
        int dependencyTreeDepth;
        int activeCountForTree;
        private int pseudoTimeQueueIndex;
        private int stateOnlyQueueIndex;
        long pseudoTimeToWrite;
        long pseudoTime;
        long totalQueuedWeights;
        private byte flags;
        short weight;
        static final boolean $assertionsDisabled = !WeightedFairQueueByteDistributor.class.desiredAssertionStatus();
        final WeightedFairQueueByteDistributor this$0;

        State(WeightedFairQueueByteDistributor weightedFairQueueByteDistributor, int n) {
            this(weightedFairQueueByteDistributor, n, null, 0);
        }

        State(WeightedFairQueueByteDistributor weightedFairQueueByteDistributor, Http2Stream http2Stream) {
            this(weightedFairQueueByteDistributor, http2Stream, 0);
        }

        State(WeightedFairQueueByteDistributor weightedFairQueueByteDistributor, Http2Stream http2Stream, int n) {
            this(weightedFairQueueByteDistributor, http2Stream.id(), http2Stream, n);
        }

        State(WeightedFairQueueByteDistributor weightedFairQueueByteDistributor, int n, Http2Stream http2Stream, int n2) {
            this.this$0 = weightedFairQueueByteDistributor;
            this.children = IntCollections.emptyMap();
            this.pseudoTimeQueueIndex = -1;
            this.stateOnlyQueueIndex = -1;
            this.weight = (short)16;
            this.stream = http2Stream;
            this.streamId = n;
            this.pseudoTimeQueue = new DefaultPriorityQueue<State>(StatePseudoTimeComparator.INSTANCE, n2);
        }

        boolean isDescendantOf(State state) {
            State state2 = this.parent;
            while (state2 != null) {
                if (state2 == state) {
                    return false;
                }
                state2 = state2.parent;
            }
            return true;
        }

        void takeChild(State state, boolean bl, List<ParentChangedEvent> list) {
            this.takeChild(null, state, bl, list);
        }

        void takeChild(Iterator<IntObjectMap.PrimitiveEntry<State>> iterator2, State state, boolean bl, List<ParentChangedEvent> list) {
            Object object;
            State state2 = state.parent;
            if (state2 != this) {
                list.add(new ParentChangedEvent(state, state2));
                state.setParent(this);
                if (iterator2 != null) {
                    iterator2.remove();
                } else if (state2 != null) {
                    state2.children.remove(state.streamId);
                }
                this.initChildrenIfEmpty();
                object = this.children.put(state.streamId, state);
                if (!$assertionsDisabled && object != null) {
                    throw new AssertionError((Object)"A stream with the same stream ID was already in the child map.");
                }
            }
            if (bl && !this.children.isEmpty()) {
                object = this.removeAllChildrenExcept(state).entries().iterator();
                while (object.hasNext()) {
                    state.takeChild((Iterator<IntObjectMap.PrimitiveEntry<State>>)object, object.next().value(), false, list);
                }
            }
        }

        void removeChild(State state) {
            if (this.children.remove(state.streamId) != null) {
                ArrayList<ParentChangedEvent> arrayList = new ArrayList<ParentChangedEvent>(1 + state.children.size());
                arrayList.add(new ParentChangedEvent(state, state.parent));
                state.setParent(null);
                Iterator<IntObjectMap.PrimitiveEntry<State>> iterator2 = state.children.entries().iterator();
                while (iterator2.hasNext()) {
                    this.takeChild(iterator2, iterator2.next().value(), false, arrayList);
                }
                this.this$0.notifyParentChanged(arrayList);
            }
        }

        private IntObjectMap<State> removeAllChildrenExcept(State state) {
            state = this.children.remove(state.streamId);
            IntObjectMap<State> intObjectMap = this.children;
            this.initChildren();
            if (state != null) {
                this.children.put(state.streamId, state);
            }
            return intObjectMap;
        }

        private void setParent(State state) {
            if (this.activeCountForTree != 0 && this.parent != null) {
                this.parent.removePseudoTimeQueue(this);
                this.parent.activeCountChangeForTree(-this.activeCountForTree);
            }
            this.parent = state;
            this.dependencyTreeDepth = state == null ? Integer.MAX_VALUE : state.dependencyTreeDepth + 1;
        }

        private void initChildrenIfEmpty() {
            if (this.children == IntCollections.emptyMap()) {
                this.initChildren();
            }
        }

        private void initChildren() {
            this.children = new IntObjectHashMap<State>(INITIAL_CHILDREN_MAP_SIZE);
        }

        void write(int n, StreamByteDistributor.Writer writer) throws Http2Exception {
            if (!$assertionsDisabled && this.stream == null) {
                throw new AssertionError();
            }
            try {
                writer.write(this.stream, n);
            } catch (Throwable throwable) {
                throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, throwable, "byte distribution write error", new Object[0]);
            }
        }

        void activeCountChangeForTree(int n) {
            if (!$assertionsDisabled && this.activeCountForTree + n < 0) {
                throw new AssertionError();
            }
            this.activeCountForTree += n;
            if (this.parent != null) {
                if (!$assertionsDisabled && this.activeCountForTree == n && this.pseudoTimeQueueIndex != -1 && !this.parent.pseudoTimeQueue.containsTyped(this)) {
                    throw new AssertionError((Object)("State[" + this.streamId + "].activeCountForTree changed from 0 to " + n + " is in a pseudoTimeQueue, but not in parent[ " + this.parent.streamId + "]'s pseudoTimeQueue"));
                }
                if (this.activeCountForTree == 0) {
                    this.parent.removePseudoTimeQueue(this);
                } else if (this.activeCountForTree == n && !this.isDistributing()) {
                    this.parent.offerAndInitializePseudoTime(this);
                }
                this.parent.activeCountChangeForTree(n);
            }
        }

        void updateStreamableBytes(int n, boolean bl) {
            if (this.isActive() != bl) {
                if (bl) {
                    this.activeCountChangeForTree(1);
                    this.setActive();
                } else {
                    this.activeCountChangeForTree(-1);
                    this.unsetActive();
                }
            }
            this.streamableBytes = n;
        }

        void updatePseudoTime(State state, int n, long l) {
            if (!($assertionsDisabled || this.streamId != 0 && n >= 0)) {
                throw new AssertionError();
            }
            this.pseudoTimeToWrite = Math.min(this.pseudoTimeToWrite, state.pseudoTime) + (long)n * l / (long)this.weight;
        }

        void offerAndInitializePseudoTime(State state) {
            state.pseudoTimeToWrite = this.pseudoTime;
            this.offerPseudoTimeQueue(state);
        }

        void offerPseudoTimeQueue(State state) {
            this.pseudoTimeQueue.offer(state);
            this.totalQueuedWeights += (long)state.weight;
        }

        State pollPseudoTimeQueue() {
            State state = (State)this.pseudoTimeQueue.poll();
            this.totalQueuedWeights -= (long)state.weight;
            return state;
        }

        void removePseudoTimeQueue(State state) {
            if (this.pseudoTimeQueue.removeTyped(state)) {
                this.totalQueuedWeights -= (long)state.weight;
            }
        }

        State peekPseudoTimeQueue() {
            return (State)this.pseudoTimeQueue.peek();
        }

        void close() {
            this.updateStreamableBytes(0, true);
            this.stream = null;
        }

        boolean wasStreamReservedOrActivated() {
            return (this.flags & 4) != 0;
        }

        void setStreamReservedOrActivated() {
            this.flags = (byte)(this.flags | 4);
        }

        boolean isActive() {
            return (this.flags & 1) != 0;
        }

        private void setActive() {
            this.flags = (byte)(this.flags | 1);
        }

        private void unsetActive() {
            this.flags = (byte)(this.flags & 0xFFFFFFFE);
        }

        boolean isDistributing() {
            return (this.flags & 2) != 0;
        }

        void setDistributing() {
            this.flags = (byte)(this.flags | 2);
        }

        void unsetDistributing() {
            this.flags = (byte)(this.flags & 0xFFFFFFFD);
        }

        @Override
        public int priorityQueueIndex(DefaultPriorityQueue<?> defaultPriorityQueue) {
            return defaultPriorityQueue == WeightedFairQueueByteDistributor.access$200(this.this$0) ? this.stateOnlyQueueIndex : this.pseudoTimeQueueIndex;
        }

        @Override
        public void priorityQueueIndex(DefaultPriorityQueue<?> defaultPriorityQueue, int n) {
            if (defaultPriorityQueue == WeightedFairQueueByteDistributor.access$200(this.this$0)) {
                this.stateOnlyQueueIndex = n;
            } else {
                this.pseudoTimeQueueIndex = n;
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(256 * (this.activeCountForTree > 0 ? this.activeCountForTree : 1));
            this.toString(stringBuilder);
            return stringBuilder.toString();
        }

        private void toString(StringBuilder stringBuilder) {
            stringBuilder.append("{streamId ").append(this.streamId).append(" streamableBytes ").append(this.streamableBytes).append(" activeCountForTree ").append(this.activeCountForTree).append(" pseudoTimeQueueIndex ").append(this.pseudoTimeQueueIndex).append(" pseudoTimeToWrite ").append(this.pseudoTimeToWrite).append(" pseudoTime ").append(this.pseudoTime).append(" flags ").append(this.flags).append(" pseudoTimeQueue.size() ").append(this.pseudoTimeQueue.size()).append(" stateOnlyQueueIndex ").append(this.stateOnlyQueueIndex).append(" parent.streamId ").append(this.parent == null ? -1 : this.parent.streamId).append("} [");
            if (!this.pseudoTimeQueue.isEmpty()) {
                for (State state : this.pseudoTimeQueue) {
                    state.toString(stringBuilder);
                    stringBuilder.append(", ");
                }
                stringBuilder.setLength(stringBuilder.length() - 2);
            }
            stringBuilder.append(']');
        }
    }

    private static final class StatePseudoTimeComparator
    implements Comparator<State>,
    Serializable {
        private static final long serialVersionUID = -1437548640227161828L;
        static final StatePseudoTimeComparator INSTANCE = new StatePseudoTimeComparator();

        private StatePseudoTimeComparator() {
        }

        @Override
        public int compare(State state, State state2) {
            return MathUtil.compare(state.pseudoTimeToWrite, state2.pseudoTimeToWrite);
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((State)object, (State)object2);
        }
    }

    private static final class StateOnlyComparator
    implements Comparator<State>,
    Serializable {
        private static final long serialVersionUID = -4806936913002105966L;
        static final StateOnlyComparator INSTANCE = new StateOnlyComparator();

        private StateOnlyComparator() {
        }

        @Override
        public int compare(State state, State state2) {
            boolean bl = state.wasStreamReservedOrActivated();
            if (bl != state2.wasStreamReservedOrActivated()) {
                return bl ? -1 : 1;
            }
            int n = state2.dependencyTreeDepth - state.dependencyTreeDepth;
            return n != 0 ? n : state.streamId - state2.streamId;
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((State)object, (State)object2);
        }
    }
}

