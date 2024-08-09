/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.group;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelId;
import io.netty.channel.ServerChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.ChannelMatchers;
import io.netty.channel.group.CombinedIterator;
import io.netty.channel.group.DefaultChannelGroupFuture;
import io.netty.channel.group.VoidChannelGroupFuture;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultChannelGroup
extends AbstractSet<Channel>
implements ChannelGroup {
    private static final AtomicInteger nextId = new AtomicInteger();
    private final String name;
    private final EventExecutor executor;
    private final ConcurrentMap<ChannelId, Channel> serverChannels = PlatformDependent.newConcurrentHashMap();
    private final ConcurrentMap<ChannelId, Channel> nonServerChannels = PlatformDependent.newConcurrentHashMap();
    private final ChannelFutureListener remover = new ChannelFutureListener(this){
        final DefaultChannelGroup this$0;
        {
            this.this$0 = defaultChannelGroup;
        }

        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            this.this$0.remove(channelFuture.channel());
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    };
    private final VoidChannelGroupFuture voidFuture = new VoidChannelGroupFuture(this);
    private final boolean stayClosed;
    private volatile boolean closed;

    public DefaultChannelGroup(EventExecutor eventExecutor) {
        this(eventExecutor, false);
    }

    public DefaultChannelGroup(String string, EventExecutor eventExecutor) {
        this(string, eventExecutor, false);
    }

    public DefaultChannelGroup(EventExecutor eventExecutor, boolean bl) {
        this("group-0x" + Integer.toHexString(nextId.incrementAndGet()), eventExecutor, bl);
    }

    public DefaultChannelGroup(String string, EventExecutor eventExecutor, boolean bl) {
        if (string == null) {
            throw new NullPointerException("name");
        }
        this.name = string;
        this.executor = eventExecutor;
        this.stayClosed = bl;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Channel find(ChannelId channelId) {
        Channel channel = (Channel)this.nonServerChannels.get(channelId);
        if (channel != null) {
            return channel;
        }
        return (Channel)this.serverChannels.get(channelId);
    }

    @Override
    public boolean isEmpty() {
        return this.nonServerChannels.isEmpty() && this.serverChannels.isEmpty();
    }

    @Override
    public int size() {
        return this.nonServerChannels.size() + this.serverChannels.size();
    }

    @Override
    public boolean contains(Object object) {
        if (object instanceof ServerChannel) {
            return this.serverChannels.containsValue(object);
        }
        if (object instanceof Channel) {
            return this.nonServerChannels.containsValue(object);
        }
        return true;
    }

    @Override
    public boolean add(Channel channel) {
        boolean bl;
        ConcurrentMap<ChannelId, Channel> concurrentMap = channel instanceof ServerChannel ? this.serverChannels : this.nonServerChannels;
        boolean bl2 = bl = concurrentMap.putIfAbsent(channel.id(), channel) == null;
        if (bl) {
            channel.closeFuture().addListener(this.remover);
        }
        if (this.stayClosed && this.closed) {
            channel.close();
        }
        return bl;
    }

    @Override
    public boolean remove(Object object) {
        Channel channel = null;
        if (object instanceof ChannelId) {
            channel = (Channel)this.nonServerChannels.remove(object);
            if (channel == null) {
                channel = (Channel)this.serverChannels.remove(object);
            }
        } else if (object instanceof Channel) {
            channel = (Channel)object;
            channel = channel instanceof ServerChannel ? (Channel)this.serverChannels.remove(channel.id()) : (Channel)this.nonServerChannels.remove(channel.id());
        }
        if (channel == null) {
            return true;
        }
        channel.closeFuture().removeListener(this.remover);
        return false;
    }

    @Override
    public void clear() {
        this.nonServerChannels.clear();
        this.serverChannels.clear();
    }

    @Override
    public Iterator<Channel> iterator() {
        return new CombinedIterator<Channel>(this.serverChannels.values().iterator(), this.nonServerChannels.values().iterator());
    }

    @Override
    public Object[] toArray() {
        ArrayList arrayList = new ArrayList(this.size());
        arrayList.addAll(this.serverChannels.values());
        arrayList.addAll(this.nonServerChannels.values());
        return arrayList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] TArray) {
        ArrayList arrayList = new ArrayList(this.size());
        arrayList.addAll(this.serverChannels.values());
        arrayList.addAll(this.nonServerChannels.values());
        return arrayList.toArray(TArray);
    }

    @Override
    public ChannelGroupFuture close() {
        return this.close(ChannelMatchers.all());
    }

    @Override
    public ChannelGroupFuture disconnect() {
        return this.disconnect(ChannelMatchers.all());
    }

    @Override
    public ChannelGroupFuture deregister() {
        return this.deregister(ChannelMatchers.all());
    }

    @Override
    public ChannelGroupFuture write(Object object) {
        return this.write(object, ChannelMatchers.all());
    }

    private static Object safeDuplicate(Object object) {
        if (object instanceof ByteBuf) {
            return ((ByteBuf)object).retainedDuplicate();
        }
        if (object instanceof ByteBufHolder) {
            return ((ByteBufHolder)object).retainedDuplicate();
        }
        return ReferenceCountUtil.retain(object);
    }

    @Override
    public ChannelGroupFuture write(Object object, ChannelMatcher channelMatcher) {
        return this.write(object, channelMatcher, true);
    }

    @Override
    public ChannelGroupFuture write(Object object, ChannelMatcher channelMatcher, boolean bl) {
        ChannelGroupFuture channelGroupFuture;
        if (object == null) {
            throw new NullPointerException("message");
        }
        if (channelMatcher == null) {
            throw new NullPointerException("matcher");
        }
        if (bl) {
            for (Channel channel : this.nonServerChannels.values()) {
                if (!channelMatcher.matches(channel)) continue;
                channel.write(DefaultChannelGroup.safeDuplicate(object), channel.voidPromise());
            }
            channelGroupFuture = this.voidFuture;
        } else {
            LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>(this.size());
            for (Channel channel : this.nonServerChannels.values()) {
                if (!channelMatcher.matches(channel)) continue;
                linkedHashMap.put(channel, channel.write(DefaultChannelGroup.safeDuplicate(object)));
            }
            channelGroupFuture = new DefaultChannelGroupFuture((ChannelGroup)this, linkedHashMap, this.executor);
        }
        ReferenceCountUtil.release(object);
        return channelGroupFuture;
    }

    @Override
    public ChannelGroup flush() {
        return this.flush(ChannelMatchers.all());
    }

    @Override
    public ChannelGroupFuture flushAndWrite(Object object) {
        return this.writeAndFlush(object);
    }

    @Override
    public ChannelGroupFuture writeAndFlush(Object object) {
        return this.writeAndFlush(object, ChannelMatchers.all());
    }

    @Override
    public ChannelGroupFuture disconnect(ChannelMatcher channelMatcher) {
        if (channelMatcher == null) {
            throw new NullPointerException("matcher");
        }
        LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>(this.size());
        for (Channel channel : this.serverChannels.values()) {
            if (!channelMatcher.matches(channel)) continue;
            linkedHashMap.put(channel, channel.disconnect());
        }
        for (Channel channel : this.nonServerChannels.values()) {
            if (!channelMatcher.matches(channel)) continue;
            linkedHashMap.put(channel, channel.disconnect());
        }
        return new DefaultChannelGroupFuture((ChannelGroup)this, linkedHashMap, this.executor);
    }

    @Override
    public ChannelGroupFuture close(ChannelMatcher channelMatcher) {
        if (channelMatcher == null) {
            throw new NullPointerException("matcher");
        }
        LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>(this.size());
        if (this.stayClosed) {
            this.closed = true;
        }
        for (Channel channel : this.serverChannels.values()) {
            if (!channelMatcher.matches(channel)) continue;
            linkedHashMap.put(channel, channel.close());
        }
        for (Channel channel : this.nonServerChannels.values()) {
            if (!channelMatcher.matches(channel)) continue;
            linkedHashMap.put(channel, channel.close());
        }
        return new DefaultChannelGroupFuture((ChannelGroup)this, linkedHashMap, this.executor);
    }

    @Override
    public ChannelGroupFuture deregister(ChannelMatcher channelMatcher) {
        if (channelMatcher == null) {
            throw new NullPointerException("matcher");
        }
        LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>(this.size());
        for (Channel channel : this.serverChannels.values()) {
            if (!channelMatcher.matches(channel)) continue;
            linkedHashMap.put(channel, channel.deregister());
        }
        for (Channel channel : this.nonServerChannels.values()) {
            if (!channelMatcher.matches(channel)) continue;
            linkedHashMap.put(channel, channel.deregister());
        }
        return new DefaultChannelGroupFuture((ChannelGroup)this, linkedHashMap, this.executor);
    }

    @Override
    public ChannelGroup flush(ChannelMatcher channelMatcher) {
        for (Channel channel : this.nonServerChannels.values()) {
            if (!channelMatcher.matches(channel)) continue;
            channel.flush();
        }
        return this;
    }

    @Override
    public ChannelGroupFuture flushAndWrite(Object object, ChannelMatcher channelMatcher) {
        return this.writeAndFlush(object, channelMatcher);
    }

    @Override
    public ChannelGroupFuture writeAndFlush(Object object, ChannelMatcher channelMatcher) {
        return this.writeAndFlush(object, channelMatcher, true);
    }

    @Override
    public ChannelGroupFuture writeAndFlush(Object object, ChannelMatcher channelMatcher, boolean bl) {
        ChannelGroupFuture channelGroupFuture;
        if (object == null) {
            throw new NullPointerException("message");
        }
        if (bl) {
            for (Channel channel : this.nonServerChannels.values()) {
                if (!channelMatcher.matches(channel)) continue;
                channel.writeAndFlush(DefaultChannelGroup.safeDuplicate(object), channel.voidPromise());
            }
            channelGroupFuture = this.voidFuture;
        } else {
            LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>(this.size());
            for (Channel channel : this.nonServerChannels.values()) {
                if (!channelMatcher.matches(channel)) continue;
                linkedHashMap.put(channel, channel.writeAndFlush(DefaultChannelGroup.safeDuplicate(object)));
            }
            channelGroupFuture = new DefaultChannelGroupFuture((ChannelGroup)this, linkedHashMap, this.executor);
        }
        ReferenceCountUtil.release(object);
        return channelGroupFuture;
    }

    @Override
    public ChannelGroupFuture newCloseFuture() {
        return this.newCloseFuture(ChannelMatchers.all());
    }

    @Override
    public ChannelGroupFuture newCloseFuture(ChannelMatcher channelMatcher) {
        LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>(this.size());
        for (Channel channel : this.serverChannels.values()) {
            if (!channelMatcher.matches(channel)) continue;
            linkedHashMap.put(channel, channel.closeFuture());
        }
        for (Channel channel : this.nonServerChannels.values()) {
            if (!channelMatcher.matches(channel)) continue;
            linkedHashMap.put(channel, channel.closeFuture());
        }
        return new DefaultChannelGroupFuture((ChannelGroup)this, linkedHashMap, this.executor);
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return this == object;
    }

    @Override
    public int compareTo(ChannelGroup channelGroup) {
        int n = this.name().compareTo(channelGroup.name());
        if (n != 0) {
            return n;
        }
        return System.identityHashCode(this) - System.identityHashCode(channelGroup);
    }

    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + "(name: " + this.name() + ", size: " + this.size() + ')';
    }

    @Override
    public boolean add(Object object) {
        return this.add((Channel)object);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ChannelGroup)object);
    }
}

