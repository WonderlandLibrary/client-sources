/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2FlowController;
import io.netty.handler.codec.http2.Http2LocalFlowController;
import io.netty.handler.codec.http2.Http2NoMoreStreamIdsException;
import io.netty.handler.codec.http2.Http2RemoteFlowController;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.handler.codec.http2.Http2StreamVisitor;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.UnaryPromiseNotifier;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class DefaultHttp2Connection
implements Http2Connection {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultHttp2Connection.class);
    final IntObjectMap<Http2Stream> streamMap = new IntObjectHashMap<Http2Stream>();
    final PropertyKeyRegistry propertyKeyRegistry = new PropertyKeyRegistry(this, null);
    final ConnectionStream connectionStream = new ConnectionStream(this);
    final DefaultEndpoint<Http2LocalFlowController> localEndpoint;
    final DefaultEndpoint<Http2RemoteFlowController> remoteEndpoint;
    final List<Http2Connection.Listener> listeners = new ArrayList<Http2Connection.Listener>(4);
    final ActiveStreams activeStreams = new ActiveStreams(this, this.listeners);
    Promise<Void> closePromise;

    public DefaultHttp2Connection(boolean bl) {
        this(bl, 100);
    }

    public DefaultHttp2Connection(boolean bl, int n) {
        this.localEndpoint = new DefaultEndpoint(this, bl, bl ? Integer.MAX_VALUE : n);
        this.remoteEndpoint = new DefaultEndpoint(this, !bl, n);
        this.streamMap.put(this.connectionStream.id(), (Http2Stream)this.connectionStream);
    }

    final boolean isClosed() {
        return this.closePromise != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Future<Void> close(Promise<Void> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        if (this.closePromise != null) {
            if (this.closePromise != promise) {
                if (promise instanceof ChannelPromise && ((ChannelPromise)this.closePromise).isVoid()) {
                    this.closePromise = promise;
                } else {
                    this.closePromise.addListener(new UnaryPromiseNotifier<Void>(promise));
                }
            }
        } else {
            this.closePromise = promise;
        }
        if (this.isStreamMapEmpty()) {
            promise.trySuccess(null);
            return promise;
        }
        Iterator<IntObjectMap.PrimitiveEntry<Http2Stream>> iterator2 = this.streamMap.entries().iterator();
        if (this.activeStreams.allowModifications()) {
            this.activeStreams.incrementPendingIterations();
            try {
                while (iterator2.hasNext()) {
                    DefaultStream defaultStream = (DefaultStream)iterator2.next().value();
                    if (defaultStream.id() == 0) continue;
                    defaultStream.close(iterator2);
                }
            } finally {
                this.activeStreams.decrementPendingIterations();
            }
        } else {
            while (iterator2.hasNext()) {
                Http2Stream http2Stream = iterator2.next().value();
                if (http2Stream.id() == 0) continue;
                http2Stream.close();
            }
        }
        return this.closePromise;
    }

    @Override
    public void addListener(Http2Connection.Listener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(Http2Connection.Listener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public boolean isServer() {
        return this.localEndpoint.isServer();
    }

    @Override
    public Http2Stream connectionStream() {
        return this.connectionStream;
    }

    @Override
    public Http2Stream stream(int n) {
        return this.streamMap.get(n);
    }

    @Override
    public boolean streamMayHaveExisted(int n) {
        return this.remoteEndpoint.mayHaveCreatedStream(n) || this.localEndpoint.mayHaveCreatedStream(n);
    }

    @Override
    public int numActiveStreams() {
        return this.activeStreams.size();
    }

    @Override
    public Http2Stream forEachActiveStream(Http2StreamVisitor http2StreamVisitor) throws Http2Exception {
        return this.activeStreams.forEachActiveStream(http2StreamVisitor);
    }

    @Override
    public Http2Connection.Endpoint<Http2LocalFlowController> local() {
        return this.localEndpoint;
    }

    @Override
    public Http2Connection.Endpoint<Http2RemoteFlowController> remote() {
        return this.remoteEndpoint;
    }

    @Override
    public boolean goAwayReceived() {
        return DefaultEndpoint.access$100(this.localEndpoint) >= 0;
    }

    @Override
    public void goAwayReceived(int n, long l, ByteBuf byteBuf) {
        DefaultEndpoint.access$200(this.localEndpoint, n);
        for (int i = 0; i < this.listeners.size(); ++i) {
            try {
                this.listeners.get(i).onGoAwayReceived(n, l, byteBuf);
                continue;
            } catch (Throwable throwable) {
                logger.error("Caught Throwable from listener onGoAwayReceived.", throwable);
            }
        }
        try {
            this.forEachActiveStream(new Http2StreamVisitor(this, n){
                final int val$lastKnownStream;
                final DefaultHttp2Connection this$0;
                {
                    this.this$0 = defaultHttp2Connection;
                    this.val$lastKnownStream = n;
                }

                @Override
                public boolean visit(Http2Stream http2Stream) {
                    if (http2Stream.id() > this.val$lastKnownStream && this.this$0.localEndpoint.isValidStreamId(http2Stream.id())) {
                        http2Stream.close();
                    }
                    return false;
                }
            });
        } catch (Http2Exception http2Exception) {
            PlatformDependent.throwException(http2Exception);
        }
    }

    @Override
    public boolean goAwaySent() {
        return DefaultEndpoint.access$100(this.remoteEndpoint) >= 0;
    }

    @Override
    public void goAwaySent(int n, long l, ByteBuf byteBuf) {
        DefaultEndpoint.access$200(this.remoteEndpoint, n);
        for (int i = 0; i < this.listeners.size(); ++i) {
            try {
                this.listeners.get(i).onGoAwaySent(n, l, byteBuf);
                continue;
            } catch (Throwable throwable) {
                logger.error("Caught Throwable from listener onGoAwaySent.", throwable);
            }
        }
        try {
            this.forEachActiveStream(new Http2StreamVisitor(this, n){
                final int val$lastKnownStream;
                final DefaultHttp2Connection this$0;
                {
                    this.this$0 = defaultHttp2Connection;
                    this.val$lastKnownStream = n;
                }

                @Override
                public boolean visit(Http2Stream http2Stream) {
                    if (http2Stream.id() > this.val$lastKnownStream && this.this$0.remoteEndpoint.isValidStreamId(http2Stream.id())) {
                        http2Stream.close();
                    }
                    return false;
                }
            });
        } catch (Http2Exception http2Exception) {
            PlatformDependent.throwException(http2Exception);
        }
    }

    private boolean isStreamMapEmpty() {
        return this.streamMap.size() == 1;
    }

    void removeStream(DefaultStream defaultStream, Iterator<?> iterator2) {
        boolean bl;
        if (iterator2 == null) {
            bl = this.streamMap.remove(defaultStream.id()) != null;
        } else {
            iterator2.remove();
            bl = true;
        }
        if (bl) {
            for (int i = 0; i < this.listeners.size(); ++i) {
                try {
                    this.listeners.get(i).onStreamRemoved(defaultStream);
                    continue;
                } catch (Throwable throwable) {
                    logger.error("Caught Throwable from listener onStreamRemoved.", throwable);
                }
            }
            if (this.closePromise != null && this.isStreamMapEmpty()) {
                this.closePromise.trySuccess(null);
            }
        }
    }

    static Http2Stream.State activeState(int n, Http2Stream.State state, boolean bl, boolean bl2) throws Http2Exception {
        switch (3.$SwitchMap$io$netty$handler$codec$http2$Http2Stream$State[state.ordinal()]) {
            case 1: {
                return bl2 ? (bl ? Http2Stream.State.HALF_CLOSED_LOCAL : Http2Stream.State.HALF_CLOSED_REMOTE) : Http2Stream.State.OPEN;
            }
            case 2: {
                return Http2Stream.State.HALF_CLOSED_REMOTE;
            }
            case 3: {
                return Http2Stream.State.HALF_CLOSED_LOCAL;
            }
        }
        throw Http2Exception.streamError(n, Http2Error.PROTOCOL_ERROR, "Attempting to open a stream in an invalid state: " + (Object)((Object)state), new Object[0]);
    }

    void notifyHalfClosed(Http2Stream http2Stream) {
        for (int i = 0; i < this.listeners.size(); ++i) {
            try {
                this.listeners.get(i).onStreamHalfClosed(http2Stream);
                continue;
            } catch (Throwable throwable) {
                logger.error("Caught Throwable from listener onStreamHalfClosed.", throwable);
            }
        }
    }

    void notifyClosed(Http2Stream http2Stream) {
        for (int i = 0; i < this.listeners.size(); ++i) {
            try {
                this.listeners.get(i).onStreamClosed(http2Stream);
                continue;
            } catch (Throwable throwable) {
                logger.error("Caught Throwable from listener onStreamClosed.", throwable);
            }
        }
    }

    @Override
    public Http2Connection.PropertyKey newKey() {
        return this.propertyKeyRegistry.newKey();
    }

    final DefaultPropertyKey verifyKey(Http2Connection.PropertyKey propertyKey) {
        return ObjectUtil.checkNotNull((DefaultPropertyKey)propertyKey, "key").verifyConnection(this);
    }

    static InternalLogger access$400() {
        return logger;
    }

    private final class PropertyKeyRegistry {
        final List<DefaultPropertyKey> keys;
        final DefaultHttp2Connection this$0;

        private PropertyKeyRegistry(DefaultHttp2Connection defaultHttp2Connection) {
            this.this$0 = defaultHttp2Connection;
            this.keys = new ArrayList<DefaultPropertyKey>(4);
        }

        DefaultPropertyKey newKey() {
            DefaultPropertyKey defaultPropertyKey = new DefaultPropertyKey(this.this$0, this.keys.size());
            this.keys.add(defaultPropertyKey);
            return defaultPropertyKey;
        }

        int size() {
            return this.keys.size();
        }

        PropertyKeyRegistry(DefaultHttp2Connection defaultHttp2Connection, 1 var2_2) {
            this(defaultHttp2Connection);
        }
    }

    final class DefaultPropertyKey
    implements Http2Connection.PropertyKey {
        final int index;
        final DefaultHttp2Connection this$0;

        DefaultPropertyKey(DefaultHttp2Connection defaultHttp2Connection, int n) {
            this.this$0 = defaultHttp2Connection;
            this.index = n;
        }

        DefaultPropertyKey verifyConnection(Http2Connection http2Connection) {
            if (http2Connection != this.this$0) {
                throw new IllegalArgumentException("Using a key that was not created by this connection");
            }
            return this;
        }
    }

    private final class ActiveStreams {
        private final List<Http2Connection.Listener> listeners;
        private final Queue<Event> pendingEvents;
        private final Set<Http2Stream> streams;
        private int pendingIterations;
        final DefaultHttp2Connection this$0;

        public ActiveStreams(DefaultHttp2Connection defaultHttp2Connection, List<Http2Connection.Listener> list) {
            this.this$0 = defaultHttp2Connection;
            this.pendingEvents = new ArrayDeque<Event>(4);
            this.streams = new LinkedHashSet<Http2Stream>();
            this.listeners = list;
        }

        public int size() {
            return this.streams.size();
        }

        public void activate(DefaultStream defaultStream) {
            if (this.allowModifications()) {
                this.addToActiveStreams(defaultStream);
            } else {
                this.pendingEvents.add(new Event(this, defaultStream){
                    final DefaultStream val$stream;
                    final ActiveStreams this$1;
                    {
                        this.this$1 = activeStreams;
                        this.val$stream = defaultStream;
                    }

                    @Override
                    public void process() {
                        this.this$1.addToActiveStreams(this.val$stream);
                    }
                });
            }
        }

        public void deactivate(DefaultStream defaultStream, Iterator<?> iterator2) {
            if (this.allowModifications() || iterator2 != null) {
                this.removeFromActiveStreams(defaultStream, iterator2);
            } else {
                this.pendingEvents.add(new Event(this, defaultStream, iterator2){
                    final DefaultStream val$stream;
                    final Iterator val$itr;
                    final ActiveStreams this$1;
                    {
                        this.this$1 = activeStreams;
                        this.val$stream = defaultStream;
                        this.val$itr = iterator2;
                    }

                    @Override
                    public void process() {
                        this.this$1.removeFromActiveStreams(this.val$stream, this.val$itr);
                    }
                });
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public Http2Stream forEachActiveStream(Http2StreamVisitor http2StreamVisitor) throws Http2Exception {
            this.incrementPendingIterations();
            try {
                for (Http2Stream http2Stream : this.streams) {
                    if (http2StreamVisitor.visit(http2Stream)) continue;
                    Http2Stream http2Stream2 = http2Stream;
                    return http2Stream2;
                }
                Iterator<Http2Stream> iterator2 = null;
                return iterator2;
            } finally {
                this.decrementPendingIterations();
            }
        }

        void addToActiveStreams(DefaultStream defaultStream) {
            if (this.streams.add(defaultStream)) {
                ++defaultStream.createdBy().numActiveStreams;
                for (int i = 0; i < this.listeners.size(); ++i) {
                    try {
                        this.listeners.get(i).onStreamActive(defaultStream);
                        continue;
                    } catch (Throwable throwable) {
                        DefaultHttp2Connection.access$400().error("Caught Throwable from listener onStreamActive.", throwable);
                    }
                }
            }
        }

        void removeFromActiveStreams(DefaultStream defaultStream, Iterator<?> iterator2) {
            if (this.streams.remove(defaultStream)) {
                --defaultStream.createdBy().numActiveStreams;
                this.this$0.notifyClosed(defaultStream);
            }
            this.this$0.removeStream(defaultStream, iterator2);
        }

        boolean allowModifications() {
            return this.pendingIterations == 0;
        }

        void incrementPendingIterations() {
            ++this.pendingIterations;
        }

        void decrementPendingIterations() {
            --this.pendingIterations;
            if (this.allowModifications()) {
                Event event;
                while ((event = this.pendingEvents.poll()) != null) {
                    try {
                        event.process();
                    } catch (Throwable throwable) {
                        DefaultHttp2Connection.access$400().error("Caught Throwable while processing pending ActiveStreams$Event.", throwable);
                    }
                }
            }
        }
    }

    static interface Event {
        public void process();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class DefaultEndpoint<F extends Http2FlowController>
    implements Http2Connection.Endpoint<F> {
        private final boolean server;
        private int nextStreamIdToCreate;
        private int nextReservationStreamId;
        private int lastStreamKnownByPeer;
        private boolean pushToAllowed;
        private F flowController;
        private int maxStreams;
        private int maxActiveStreams;
        private final int maxReservedStreams;
        int numActiveStreams;
        int numStreams;
        static final boolean $assertionsDisabled = !DefaultHttp2Connection.class.desiredAssertionStatus();
        final DefaultHttp2Connection this$0;

        DefaultEndpoint(DefaultHttp2Connection defaultHttp2Connection, boolean bl, int n) {
            this.this$0 = defaultHttp2Connection;
            this.lastStreamKnownByPeer = -1;
            this.pushToAllowed = true;
            this.server = bl;
            if (bl) {
                this.nextStreamIdToCreate = 2;
                this.nextReservationStreamId = 0;
            } else {
                this.nextStreamIdToCreate = 1;
                this.nextReservationStreamId = 1;
            }
            this.pushToAllowed = !bl;
            this.maxActiveStreams = Integer.MAX_VALUE;
            this.maxReservedStreams = ObjectUtil.checkPositiveOrZero(n, "maxReservedStreams");
            this.updateMaxStreams();
        }

        @Override
        public int incrementAndGetNextStreamId() {
            return this.nextReservationStreamId >= 0 ? (this.nextReservationStreamId = this.nextReservationStreamId + 2) : this.nextReservationStreamId;
        }

        private void incrementExpectedStreamId(int n) {
            if (n > this.nextReservationStreamId && this.nextReservationStreamId >= 0) {
                this.nextReservationStreamId = n;
            }
            this.nextStreamIdToCreate = n + 2;
            ++this.numStreams;
        }

        @Override
        public boolean isValidStreamId(int n) {
            return n > 0 && this.server == ((n & 1) == 0);
        }

        @Override
        public boolean mayHaveCreatedStream(int n) {
            return this.isValidStreamId(n) && n <= this.lastStreamCreated();
        }

        @Override
        public boolean canOpenStream() {
            return this.numActiveStreams < this.maxActiveStreams;
        }

        @Override
        public DefaultStream createStream(int n, boolean bl) throws Http2Exception {
            Http2Stream.State state = DefaultHttp2Connection.activeState(n, Http2Stream.State.IDLE, this.isLocal(), bl);
            this.checkNewStreamAllowed(n, state);
            DefaultStream defaultStream = new DefaultStream(this.this$0, n, state);
            this.incrementExpectedStreamId(n);
            this.addStream(defaultStream);
            defaultStream.activate();
            return defaultStream;
        }

        @Override
        public boolean created(Http2Stream http2Stream) {
            return http2Stream instanceof DefaultStream && ((DefaultStream)http2Stream).createdBy() == this;
        }

        @Override
        public boolean isServer() {
            return this.server;
        }

        @Override
        public DefaultStream reservePushStream(int n, Http2Stream http2Stream) throws Http2Exception {
            if (http2Stream == null) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Parent stream missing", new Object[0]);
            }
            if (this.isLocal() ? !http2Stream.state().localSideOpen() : !http2Stream.state().remoteSideOpen()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d is not open for sending push promise", http2Stream.id());
            }
            if (!this.opposite().allowPushTo()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server push not allowed to opposite endpoint", new Object[0]);
            }
            Http2Stream.State state = this.isLocal() ? Http2Stream.State.RESERVED_LOCAL : Http2Stream.State.RESERVED_REMOTE;
            this.checkNewStreamAllowed(n, state);
            DefaultStream defaultStream = new DefaultStream(this.this$0, n, state);
            this.incrementExpectedStreamId(n);
            this.addStream(defaultStream);
            return defaultStream;
        }

        private void addStream(DefaultStream defaultStream) {
            this.this$0.streamMap.put(defaultStream.id(), (Http2Stream)defaultStream);
            for (int i = 0; i < this.this$0.listeners.size(); ++i) {
                try {
                    this.this$0.listeners.get(i).onStreamAdded(defaultStream);
                    continue;
                } catch (Throwable throwable) {
                    DefaultHttp2Connection.access$400().error("Caught Throwable from listener onStreamAdded.", throwable);
                }
            }
        }

        @Override
        public void allowPushTo(boolean bl) {
            if (bl && this.server) {
                throw new IllegalArgumentException("Servers do not allow push");
            }
            this.pushToAllowed = bl;
        }

        @Override
        public boolean allowPushTo() {
            return this.pushToAllowed;
        }

        @Override
        public int numActiveStreams() {
            return this.numActiveStreams;
        }

        @Override
        public int maxActiveStreams() {
            return this.maxActiveStreams;
        }

        @Override
        public void maxActiveStreams(int n) {
            this.maxActiveStreams = n;
            this.updateMaxStreams();
        }

        @Override
        public int lastStreamCreated() {
            return this.nextStreamIdToCreate > 1 ? this.nextStreamIdToCreate - 2 : 0;
        }

        @Override
        public int lastStreamKnownByPeer() {
            return this.lastStreamKnownByPeer;
        }

        private void lastStreamKnownByPeer(int n) {
            this.lastStreamKnownByPeer = n;
        }

        @Override
        public F flowController() {
            return this.flowController;
        }

        @Override
        public void flowController(F f) {
            this.flowController = (Http2FlowController)ObjectUtil.checkNotNull(f, "flowController");
        }

        @Override
        public Http2Connection.Endpoint<? extends Http2FlowController> opposite() {
            return this.isLocal() ? this.this$0.remoteEndpoint : this.this$0.localEndpoint;
        }

        private void updateMaxStreams() {
            this.maxStreams = (int)Math.min(Integer.MAX_VALUE, (long)this.maxActiveStreams + (long)this.maxReservedStreams);
        }

        private void checkNewStreamAllowed(int n, Http2Stream.State state) throws Http2Exception {
            boolean bl;
            if (!$assertionsDisabled && state == Http2Stream.State.IDLE) {
                throw new AssertionError();
            }
            if (this.this$0.goAwayReceived() && n > this.this$0.localEndpoint.lastStreamKnownByPeer()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Cannot create stream %d since this endpoint has received a GOAWAY frame with last stream id %d.", n, this.this$0.localEndpoint.lastStreamKnownByPeer());
            }
            if (!this.isValidStreamId(n)) {
                if (n < 0) {
                    throw new Http2NoMoreStreamIdsException();
                }
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Request stream %d is not correct for %s connection", n, this.server ? "server" : "client");
            }
            if (n < this.nextStreamIdToCreate) {
                throw Http2Exception.closedStreamError(Http2Error.PROTOCOL_ERROR, "Request stream %d is behind the next expected stream %d", n, this.nextStreamIdToCreate);
            }
            if (this.nextStreamIdToCreate <= 0) {
                throw Http2Exception.connectionError(Http2Error.REFUSED_STREAM, "Stream IDs are exhausted for this endpoint.", new Object[0]);
            }
            boolean bl2 = bl = state == Http2Stream.State.RESERVED_LOCAL || state == Http2Stream.State.RESERVED_REMOTE;
            if (!bl && !this.canOpenStream() || bl && this.numStreams >= this.maxStreams) {
                throw Http2Exception.streamError(n, Http2Error.REFUSED_STREAM, "Maximum active streams violated for this endpoint.", new Object[0]);
            }
            if (this.this$0.isClosed()) {
                throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, "Attempted to create stream id %d after connection was closed", n);
            }
        }

        private boolean isLocal() {
            return this == this.this$0.localEndpoint;
        }

        @Override
        public Http2Stream reservePushStream(int n, Http2Stream http2Stream) throws Http2Exception {
            return this.reservePushStream(n, http2Stream);
        }

        @Override
        public Http2Stream createStream(int n, boolean bl) throws Http2Exception {
            return this.createStream(n, bl);
        }

        static int access$100(DefaultEndpoint defaultEndpoint) {
            return defaultEndpoint.lastStreamKnownByPeer;
        }

        static void access$200(DefaultEndpoint defaultEndpoint, int n) {
            defaultEndpoint.lastStreamKnownByPeer(n);
        }
    }

    private final class ConnectionStream
    extends DefaultStream {
        final DefaultHttp2Connection this$0;

        ConnectionStream(DefaultHttp2Connection defaultHttp2Connection) {
            this.this$0 = defaultHttp2Connection;
            super(defaultHttp2Connection, 0, Http2Stream.State.IDLE);
        }

        @Override
        public boolean isResetSent() {
            return true;
        }

        @Override
        DefaultEndpoint<? extends Http2FlowController> createdBy() {
            return null;
        }

        @Override
        public Http2Stream resetSent() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Http2Stream open(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Http2Stream close() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Http2Stream closeLocalSide() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Http2Stream closeRemoteSide() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Http2Stream headersSent(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isHeadersSent() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Http2Stream pushPromiseSent() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isPushPromiseSent() {
            throw new UnsupportedOperationException();
        }
    }

    private class DefaultStream
    implements Http2Stream {
        private static final byte META_STATE_SENT_RST = 1;
        private static final byte META_STATE_SENT_HEADERS = 2;
        private static final byte META_STATE_SENT_TRAILERS = 4;
        private static final byte META_STATE_SENT_PUSHPROMISE = 8;
        private static final byte META_STATE_RECV_HEADERS = 16;
        private static final byte META_STATE_RECV_TRAILERS = 32;
        private final int id;
        private final PropertyMap properties;
        private Http2Stream.State state;
        private byte metaState;
        final DefaultHttp2Connection this$0;

        DefaultStream(DefaultHttp2Connection defaultHttp2Connection, int n, Http2Stream.State state) {
            this.this$0 = defaultHttp2Connection;
            this.properties = new PropertyMap(this, null);
            this.id = n;
            this.state = state;
        }

        @Override
        public final int id() {
            return this.id;
        }

        @Override
        public final Http2Stream.State state() {
            return this.state;
        }

        @Override
        public boolean isResetSent() {
            return (this.metaState & 1) != 0;
        }

        @Override
        public Http2Stream resetSent() {
            this.metaState = (byte)(this.metaState | 1);
            return this;
        }

        @Override
        public Http2Stream headersSent(boolean bl) {
            if (!bl) {
                this.metaState = (byte)(this.metaState | (this.isHeadersSent() ? 4 : 2));
            }
            return this;
        }

        @Override
        public boolean isHeadersSent() {
            return (this.metaState & 2) != 0;
        }

        @Override
        public boolean isTrailersSent() {
            return (this.metaState & 4) != 0;
        }

        @Override
        public Http2Stream headersReceived(boolean bl) {
            if (!bl) {
                this.metaState = (byte)(this.metaState | (this.isHeadersReceived() ? 32 : 16));
            }
            return this;
        }

        @Override
        public boolean isHeadersReceived() {
            return (this.metaState & 0x10) != 0;
        }

        @Override
        public boolean isTrailersReceived() {
            return (this.metaState & 0x20) != 0;
        }

        @Override
        public Http2Stream pushPromiseSent() {
            this.metaState = (byte)(this.metaState | 8);
            return this;
        }

        @Override
        public boolean isPushPromiseSent() {
            return (this.metaState & 8) != 0;
        }

        @Override
        public final <V> V setProperty(Http2Connection.PropertyKey propertyKey, V v) {
            return this.properties.add(this.this$0.verifyKey(propertyKey), v);
        }

        @Override
        public final <V> V getProperty(Http2Connection.PropertyKey propertyKey) {
            return this.properties.get(this.this$0.verifyKey(propertyKey));
        }

        @Override
        public final <V> V removeProperty(Http2Connection.PropertyKey propertyKey) {
            return this.properties.remove(this.this$0.verifyKey(propertyKey));
        }

        @Override
        public Http2Stream open(boolean bl) throws Http2Exception {
            this.state = DefaultHttp2Connection.activeState(this.id, this.state, this.isLocal(), bl);
            if (!this.createdBy().canOpenStream()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Maximum active streams violated for this endpoint.", new Object[0]);
            }
            this.activate();
            return this;
        }

        void activate() {
            if (this.state == Http2Stream.State.HALF_CLOSED_LOCAL) {
                this.headersSent(true);
            } else if (this.state == Http2Stream.State.HALF_CLOSED_REMOTE) {
                this.headersReceived(true);
            }
            this.this$0.activeStreams.activate(this);
        }

        Http2Stream close(Iterator<?> iterator2) {
            if (this.state == Http2Stream.State.CLOSED) {
                return this;
            }
            this.state = Http2Stream.State.CLOSED;
            --this.createdBy().numStreams;
            this.this$0.activeStreams.deactivate(this, iterator2);
            return this;
        }

        @Override
        public Http2Stream close() {
            return this.close(null);
        }

        @Override
        public Http2Stream closeLocalSide() {
            switch (3.$SwitchMap$io$netty$handler$codec$http2$Http2Stream$State[this.state.ordinal()]) {
                case 4: {
                    this.state = Http2Stream.State.HALF_CLOSED_LOCAL;
                    this.this$0.notifyHalfClosed(this);
                    break;
                }
                case 5: {
                    break;
                }
                default: {
                    this.close();
                }
            }
            return this;
        }

        @Override
        public Http2Stream closeRemoteSide() {
            switch (3.$SwitchMap$io$netty$handler$codec$http2$Http2Stream$State[this.state.ordinal()]) {
                case 4: {
                    this.state = Http2Stream.State.HALF_CLOSED_REMOTE;
                    this.this$0.notifyHalfClosed(this);
                    break;
                }
                case 6: {
                    break;
                }
                default: {
                    this.close();
                }
            }
            return this;
        }

        DefaultEndpoint<? extends Http2FlowController> createdBy() {
            return this.this$0.localEndpoint.isValidStreamId(this.id) ? this.this$0.localEndpoint : this.this$0.remoteEndpoint;
        }

        final boolean isLocal() {
            return this.this$0.localEndpoint.isValidStreamId(this.id);
        }

        private class PropertyMap {
            Object[] values;
            final DefaultStream this$1;

            private PropertyMap(DefaultStream defaultStream) {
                this.this$1 = defaultStream;
                this.values = EmptyArrays.EMPTY_OBJECTS;
            }

            <V> V add(DefaultPropertyKey defaultPropertyKey, V v) {
                this.resizeIfNecessary(defaultPropertyKey.index);
                Object object = this.values[defaultPropertyKey.index];
                this.values[defaultPropertyKey.index] = v;
                return (V)object;
            }

            <V> V get(DefaultPropertyKey defaultPropertyKey) {
                if (defaultPropertyKey.index >= this.values.length) {
                    return null;
                }
                return (V)this.values[defaultPropertyKey.index];
            }

            <V> V remove(DefaultPropertyKey defaultPropertyKey) {
                Object object = null;
                if (defaultPropertyKey.index < this.values.length) {
                    object = this.values[defaultPropertyKey.index];
                    this.values[defaultPropertyKey.index] = null;
                }
                return (V)object;
            }

            void resizeIfNecessary(int n) {
                if (n >= this.values.length) {
                    this.values = Arrays.copyOf(this.values, this.this$1.this$0.propertyKeyRegistry.size());
                }
            }

            PropertyMap(DefaultStream defaultStream, 1 var2_2) {
                this(defaultStream);
            }
        }
    }
}

