/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.group;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupException;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.util.concurrent.BlockingOperationException;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ImmediateEventExecutor;
import io.netty.util.concurrent.Promise;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class DefaultChannelGroupFuture
extends DefaultPromise<Void>
implements ChannelGroupFuture {
    private final ChannelGroup group;
    private final Map<Channel, ChannelFuture> futures;
    private int successCount;
    private int failureCount;
    private final ChannelFutureListener childListener = new ChannelFutureListener(this){
        static final boolean $assertionsDisabled = !DefaultChannelGroupFuture.class.desiredAssertionStatus();
        final DefaultChannelGroupFuture this$0;
        {
            this.this$0 = defaultChannelGroupFuture;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            boolean bl;
            boolean bl2 = channelFuture.isSuccess();
            Iterable<ChannelFuture> iterable = this.this$0;
            synchronized (iterable) {
                if (bl2) {
                    DefaultChannelGroupFuture.access$008(this.this$0);
                } else {
                    DefaultChannelGroupFuture.access$108(this.this$0);
                }
                boolean bl3 = bl = DefaultChannelGroupFuture.access$000(this.this$0) + DefaultChannelGroupFuture.access$100(this.this$0) == DefaultChannelGroupFuture.access$200(this.this$0).size();
                if (!$assertionsDisabled && DefaultChannelGroupFuture.access$000(this.this$0) + DefaultChannelGroupFuture.access$100(this.this$0) > DefaultChannelGroupFuture.access$200(this.this$0).size()) {
                    throw new AssertionError();
                }
            }
            if (bl) {
                if (DefaultChannelGroupFuture.access$100(this.this$0) > 0) {
                    iterable = new ArrayList(DefaultChannelGroupFuture.access$100(this.this$0));
                    for (ChannelFuture channelFuture2 : DefaultChannelGroupFuture.access$200(this.this$0).values()) {
                        if (channelFuture2.isSuccess()) continue;
                        iterable.add(new DefaultEntry<Channel, Throwable>(channelFuture2.channel(), channelFuture2.cause()));
                    }
                    DefaultChannelGroupFuture.access$300(this.this$0, new ChannelGroupException((Collection<Map.Entry<Channel, Throwable>>)iterable));
                } else {
                    DefaultChannelGroupFuture.access$400(this.this$0);
                }
            }
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    };

    DefaultChannelGroupFuture(ChannelGroup channelGroup, Collection<ChannelFuture> collection, EventExecutor eventExecutor) {
        super(eventExecutor);
        if (channelGroup == null) {
            throw new NullPointerException("group");
        }
        if (collection == null) {
            throw new NullPointerException("futures");
        }
        this.group = channelGroup;
        LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>();
        for (ChannelFuture channelFuture : collection) {
            linkedHashMap.put(channelFuture.channel(), channelFuture);
        }
        this.futures = Collections.unmodifiableMap(linkedHashMap);
        for (ChannelFuture channelFuture : this.futures.values()) {
            channelFuture.addListener(this.childListener);
        }
        if (this.futures.isEmpty()) {
            this.setSuccess0();
        }
    }

    DefaultChannelGroupFuture(ChannelGroup channelGroup, Map<Channel, ChannelFuture> map, EventExecutor eventExecutor) {
        super(eventExecutor);
        this.group = channelGroup;
        this.futures = Collections.unmodifiableMap(map);
        for (ChannelFuture channelFuture : this.futures.values()) {
            channelFuture.addListener(this.childListener);
        }
        if (this.futures.isEmpty()) {
            this.setSuccess0();
        }
    }

    @Override
    public ChannelGroup group() {
        return this.group;
    }

    @Override
    public ChannelFuture find(Channel channel) {
        return this.futures.get(channel);
    }

    @Override
    public Iterator<ChannelFuture> iterator() {
        return this.futures.values().iterator();
    }

    @Override
    public synchronized boolean isPartialSuccess() {
        return this.successCount != 0 && this.successCount != this.futures.size();
    }

    @Override
    public synchronized boolean isPartialFailure() {
        return this.failureCount != 0 && this.failureCount != this.futures.size();
    }

    @Override
    public DefaultChannelGroupFuture addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.addListener(genericFutureListener);
        return this;
    }

    @Override
    public DefaultChannelGroupFuture addListeners(GenericFutureListener<? extends Future<? super Void>> ... genericFutureListenerArray) {
        super.addListeners(genericFutureListenerArray);
        return this;
    }

    @Override
    public DefaultChannelGroupFuture removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.removeListener(genericFutureListener);
        return this;
    }

    @Override
    public DefaultChannelGroupFuture removeListeners(GenericFutureListener<? extends Future<? super Void>> ... genericFutureListenerArray) {
        super.removeListeners(genericFutureListenerArray);
        return this;
    }

    @Override
    public DefaultChannelGroupFuture await() throws InterruptedException {
        super.await();
        return this;
    }

    @Override
    public DefaultChannelGroupFuture awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    @Override
    public DefaultChannelGroupFuture syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    @Override
    public DefaultChannelGroupFuture sync() throws InterruptedException {
        super.sync();
        return this;
    }

    @Override
    public ChannelGroupException cause() {
        return (ChannelGroupException)super.cause();
    }

    private void setSuccess0() {
        super.setSuccess(null);
    }

    private void setFailure0(ChannelGroupException channelGroupException) {
        super.setFailure(channelGroupException);
    }

    public DefaultChannelGroupFuture setSuccess(Void void_) {
        throw new IllegalStateException();
    }

    @Override
    public boolean trySuccess(Void void_) {
        throw new IllegalStateException();
    }

    public DefaultChannelGroupFuture setFailure(Throwable throwable) {
        throw new IllegalStateException();
    }

    @Override
    public boolean tryFailure(Throwable throwable) {
        throw new IllegalStateException();
    }

    @Override
    protected void checkDeadLock() {
        EventExecutor eventExecutor = this.executor();
        if (eventExecutor != null && eventExecutor != ImmediateEventExecutor.INSTANCE && eventExecutor.inEventLoop()) {
            throw new BlockingOperationException();
        }
    }

    @Override
    public Promise syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public Promise sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public Promise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public Promise await() throws InterruptedException {
        return this.await();
    }

    @Override
    public Promise removeListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.removeListeners(genericFutureListenerArray);
    }

    @Override
    public Promise removeListener(GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }

    @Override
    public Promise addListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.addListeners(genericFutureListenerArray);
    }

    @Override
    public Promise addListener(GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }

    @Override
    public Throwable cause() {
        return this.cause();
    }

    @Override
    public Promise setFailure(Throwable throwable) {
        return this.setFailure(throwable);
    }

    @Override
    public boolean trySuccess(Object object) {
        return this.trySuccess((Void)object);
    }

    @Override
    public Promise setSuccess(Object object) {
        return this.setSuccess((Void)object);
    }

    @Override
    public Future awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public Future await() throws InterruptedException {
        return this.await();
    }

    @Override
    public Future syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public Future sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public Future removeListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.removeListeners(genericFutureListenerArray);
    }

    @Override
    public Future removeListener(GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }

    @Override
    public Future addListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.addListeners(genericFutureListenerArray);
    }

    @Override
    public Future addListener(GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }

    @Override
    public ChannelGroupFuture sync() throws InterruptedException {
        return this.sync();
    }

    @Override
    public ChannelGroupFuture syncUninterruptibly() {
        return this.syncUninterruptibly();
    }

    @Override
    public ChannelGroupFuture awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }

    @Override
    public ChannelGroupFuture await() throws InterruptedException {
        return this.await();
    }

    @Override
    public ChannelGroupFuture removeListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.removeListeners(genericFutureListenerArray);
    }

    @Override
    public ChannelGroupFuture removeListener(GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }

    @Override
    public ChannelGroupFuture addListeners(GenericFutureListener[] genericFutureListenerArray) {
        return this.addListeners(genericFutureListenerArray);
    }

    @Override
    public ChannelGroupFuture addListener(GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }

    static int access$008(DefaultChannelGroupFuture defaultChannelGroupFuture) {
        return defaultChannelGroupFuture.successCount++;
    }

    static int access$108(DefaultChannelGroupFuture defaultChannelGroupFuture) {
        return defaultChannelGroupFuture.failureCount++;
    }

    static int access$000(DefaultChannelGroupFuture defaultChannelGroupFuture) {
        return defaultChannelGroupFuture.successCount;
    }

    static int access$100(DefaultChannelGroupFuture defaultChannelGroupFuture) {
        return defaultChannelGroupFuture.failureCount;
    }

    static Map access$200(DefaultChannelGroupFuture defaultChannelGroupFuture) {
        return defaultChannelGroupFuture.futures;
    }

    static void access$300(DefaultChannelGroupFuture defaultChannelGroupFuture, ChannelGroupException channelGroupException) {
        defaultChannelGroupFuture.setFailure0(channelGroupException);
    }

    static void access$400(DefaultChannelGroupFuture defaultChannelGroupFuture) {
        defaultChannelGroupFuture.setSuccess0();
    }

    private static final class DefaultEntry<K, V>
    implements Map.Entry<K, V> {
        private final K key;
        private final V value;

        DefaultEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V v) {
            throw new UnsupportedOperationException("read-only");
        }
    }
}

