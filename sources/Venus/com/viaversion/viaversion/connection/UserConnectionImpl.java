/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.connection;

import com.google.common.cache.CacheBuilder;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketTracker;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.connection.ProtocolInfoImpl;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.util.ChatColorUtil;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

public class UserConnectionImpl
implements UserConnection {
    private static final AtomicLong IDS = new AtomicLong();
    private final long id = IDS.incrementAndGet();
    private final Map<Class<?>, StorableObject> storedObjects = new ConcurrentHashMap();
    private final Map<Class<? extends Protocol>, EntityTracker> entityTrackers = new HashMap<Class<? extends Protocol>, EntityTracker>();
    private final PacketTracker packetTracker = new PacketTracker(this);
    private final Set<UUID> passthroughTokens = Collections.newSetFromMap(CacheBuilder.newBuilder().expireAfterWrite(10L, TimeUnit.SECONDS).build().asMap());
    private final ProtocolInfo protocolInfo = new ProtocolInfoImpl(this);
    private final Channel channel;
    private final boolean clientSide;
    private boolean active = true;
    private boolean pendingDisconnect;
    private boolean packetLimiterEnabled = true;

    public UserConnectionImpl(@Nullable Channel channel, boolean bl) {
        this.channel = channel;
        this.clientSide = bl;
    }

    public UserConnectionImpl(@Nullable Channel channel) {
        this(channel, false);
    }

    @Override
    public <T extends StorableObject> @Nullable T get(Class<T> clazz) {
        return (T)this.storedObjects.get(clazz);
    }

    @Override
    public boolean has(Class<? extends StorableObject> clazz) {
        return this.storedObjects.containsKey(clazz);
    }

    @Override
    public <T extends StorableObject> @Nullable T remove(Class<T> clazz) {
        return (T)this.storedObjects.remove(clazz);
    }

    @Override
    public void put(StorableObject storableObject) {
        this.storedObjects.put(storableObject.getClass(), storableObject);
    }

    @Override
    public Collection<EntityTracker> getEntityTrackers() {
        return this.entityTrackers.values();
    }

    @Override
    public <T extends EntityTracker> @Nullable T getEntityTracker(Class<? extends Protocol> clazz) {
        return (T)this.entityTrackers.get(clazz);
    }

    @Override
    public void addEntityTracker(Class<? extends Protocol> clazz, EntityTracker entityTracker) {
        if (!this.entityTrackers.containsKey(clazz)) {
            this.entityTrackers.put(clazz, entityTracker);
        }
    }

    @Override
    public void clearStoredObjects(boolean bl) {
        if (bl) {
            this.storedObjects.values().removeIf(StorableObject::clearOnServerSwitch);
            for (EntityTracker entityTracker : this.entityTrackers.values()) {
                entityTracker.clearEntities();
                entityTracker.trackClientEntity();
            }
        } else {
            this.storedObjects.clear();
            this.entityTrackers.clear();
        }
    }

    @Override
    public void sendRawPacket(ByteBuf byteBuf) {
        this.sendRawPacket(byteBuf, true);
    }

    @Override
    public void scheduleSendRawPacket(ByteBuf byteBuf) {
        this.sendRawPacket(byteBuf, false);
    }

    private void sendRawPacket(ByteBuf byteBuf, boolean bl) {
        Runnable runnable = this.clientSide ? () -> this.lambda$sendRawPacket$0(byteBuf) : () -> this.lambda$sendRawPacket$1(byteBuf);
        if (bl) {
            runnable.run();
        } else {
            try {
                this.channel.eventLoop().submit(runnable);
            } catch (Throwable throwable) {
                byteBuf.release();
                throwable.printStackTrace();
            }
        }
    }

    @Override
    public ChannelFuture sendRawPacketFuture(ByteBuf byteBuf) {
        if (this.clientSide) {
            this.getChannel().pipeline().context(Via.getManager().getInjector().getDecoderName()).fireChannelRead(byteBuf);
            return this.getChannel().newSucceededFuture();
        }
        return this.channel.pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush(byteBuf);
    }

    @Override
    public PacketTracker getPacketTracker() {
        return this.packetTracker;
    }

    @Override
    public void disconnect(String string) {
        if (!this.channel.isOpen() || this.pendingDisconnect) {
            return;
        }
        this.pendingDisconnect = true;
        Via.getPlatform().runSync(() -> this.lambda$disconnect$2(string));
    }

    @Override
    public void sendRawPacketToServer(ByteBuf byteBuf) {
        if (this.clientSide) {
            this.sendRawPacketToServerClientSide(byteBuf, true);
        } else {
            this.sendRawPacketToServerServerSide(byteBuf, true);
        }
    }

    @Override
    public void scheduleSendRawPacketToServer(ByteBuf byteBuf) {
        if (this.clientSide) {
            this.sendRawPacketToServerClientSide(byteBuf, false);
        } else {
            this.sendRawPacketToServerServerSide(byteBuf, false);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void sendRawPacketToServerServerSide(ByteBuf byteBuf, boolean bl) {
        block9: {
            ByteBuf byteBuf2 = byteBuf.alloc().buffer();
            try {
                ChannelHandlerContext channelHandlerContext = PipelineUtil.getPreviousContext(Via.getManager().getInjector().getDecoderName(), this.channel.pipeline());
                if (this.shouldTransformPacket()) {
                    try {
                        Type.VAR_INT.writePrimitive(byteBuf2, 1000);
                        Type.UUID.write(byteBuf2, this.generatePassthroughToken());
                    } catch (Exception exception) {
                        throw new RuntimeException(exception);
                    }
                }
                byteBuf2.writeBytes(byteBuf);
                Runnable runnable = () -> this.lambda$sendRawPacketToServerServerSide$3(channelHandlerContext, byteBuf2);
                if (bl) {
                    runnable.run();
                    break block9;
                }
                try {
                    this.channel.eventLoop().submit(runnable);
                } catch (Throwable throwable) {
                    byteBuf2.release();
                    throw throwable;
                }
            } finally {
                byteBuf.release();
            }
        }
    }

    private void sendRawPacketToServerClientSide(ByteBuf byteBuf, boolean bl) {
        Runnable runnable = () -> this.lambda$sendRawPacketToServerClientSide$4(byteBuf);
        if (bl) {
            runnable.run();
        } else {
            try {
                this.getChannel().eventLoop().submit(runnable);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                byteBuf.release();
            }
        }
    }

    @Override
    public boolean checkServerboundPacket() {
        if (this.pendingDisconnect) {
            return true;
        }
        return !this.packetLimiterEnabled || !this.packetTracker.incrementReceived() || !this.packetTracker.exceedsMaxPPS();
    }

    @Override
    public boolean checkClientboundPacket() {
        this.packetTracker.incrementSent();
        return false;
    }

    @Override
    public boolean shouldTransformPacket() {
        return this.active;
    }

    @Override
    public void transformClientbound(ByteBuf byteBuf, Function<Throwable, Exception> function) throws Exception {
        this.transform(byteBuf, Direction.CLIENTBOUND, function);
    }

    @Override
    public void transformServerbound(ByteBuf byteBuf, Function<Throwable, Exception> function) throws Exception {
        this.transform(byteBuf, Direction.SERVERBOUND, function);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void transform(ByteBuf byteBuf, Direction direction, Function<Throwable, Exception> function) throws Exception {
        if (!byteBuf.isReadable()) {
            return;
        }
        int n = Type.VAR_INT.readPrimitive(byteBuf);
        if (n == 1000) {
            if (!this.passthroughTokens.remove(Type.UUID.read(byteBuf))) {
                throw new IllegalArgumentException("Invalid token");
            }
            return;
        }
        PacketWrapperImpl packetWrapperImpl = new PacketWrapperImpl(n, byteBuf, (UserConnection)this);
        try {
            this.protocolInfo.getPipeline().transform(direction, this.protocolInfo.getState(), packetWrapperImpl);
        } catch (CancelException cancelException) {
            throw function.apply(cancelException);
        }
        ByteBuf byteBuf2 = byteBuf.alloc().buffer();
        try {
            packetWrapperImpl.writeToBuffer(byteBuf2);
            byteBuf.clear().writeBytes(byteBuf2);
        } finally {
            byteBuf2.release();
        }
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public @Nullable Channel getChannel() {
        return this.channel;
    }

    @Override
    public ProtocolInfo getProtocolInfo() {
        return this.protocolInfo;
    }

    @Override
    public Map<Class<?>, StorableObject> getStoredObjects() {
        return this.storedObjects;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean bl) {
        this.active = bl;
    }

    @Override
    public boolean isPendingDisconnect() {
        return this.pendingDisconnect;
    }

    @Override
    public void setPendingDisconnect(boolean bl) {
        this.pendingDisconnect = bl;
    }

    @Override
    public boolean isClientSide() {
        return this.clientSide;
    }

    @Override
    public boolean shouldApplyBlockProtocol() {
        return !this.clientSide;
    }

    @Override
    public boolean isPacketLimiterEnabled() {
        return this.packetLimiterEnabled;
    }

    @Override
    public void setPacketLimiterEnabled(boolean bl) {
        this.packetLimiterEnabled = bl;
    }

    @Override
    public UUID generatePassthroughToken() {
        UUID uUID = UUID.randomUUID();
        this.passthroughTokens.add(uUID);
        return uUID;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        UserConnectionImpl userConnectionImpl = (UserConnectionImpl)object;
        return this.id == userConnectionImpl.id;
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    private void lambda$sendRawPacketToServerClientSide$4(ByteBuf byteBuf) {
        this.getChannel().pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush(byteBuf);
    }

    private void lambda$sendRawPacketToServerServerSide$3(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        if (channelHandlerContext != null) {
            channelHandlerContext.fireChannelRead(byteBuf);
        } else {
            this.channel.pipeline().fireChannelRead(byteBuf);
        }
    }

    private void lambda$disconnect$2(String string) {
        if (!Via.getPlatform().disconnect(this, ChatColorUtil.translateAlternateColorCodes(string))) {
            this.channel.close();
        }
    }

    private void lambda$sendRawPacket$1(ByteBuf byteBuf) {
        this.channel.pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush(byteBuf);
    }

    private void lambda$sendRawPacket$0(ByteBuf byteBuf) {
        this.getChannel().pipeline().context(Via.getManager().getInjector().getDecoderName()).fireChannelRead(byteBuf);
    }
}

