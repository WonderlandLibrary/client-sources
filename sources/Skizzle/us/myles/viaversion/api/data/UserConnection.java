/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelFuture
 *  io.netty.channel.ChannelHandlerContext
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.data;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaVersionConfig;
import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.exception.CancelException;
import us.myles.ViaVersion.packets.Direction;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import us.myles.ViaVersion.util.PipelineUtil;
import us.myles.viaversion.libs.bungeecordchat.api.ChatColor;

public class UserConnection {
    private static final AtomicLong IDS = new AtomicLong();
    private final long id = IDS.incrementAndGet();
    private final Channel channel;
    private final boolean clientSide;
    Map<Class, StoredObject> storedObjects = new ConcurrentHashMap<Class, StoredObject>();
    private ProtocolInfo protocolInfo;
    private boolean active = true;
    private boolean pendingDisconnect;
    private Object lastPacket;
    private long sentPackets;
    private long receivedPackets;
    private long startTime;
    private long intervalPackets;
    private long packetsPerSecond = -1L;
    private int secondsObserved;
    private int warnings;

    public UserConnection(@Nullable Channel channel, boolean clientSide) {
        this.channel = channel;
        this.clientSide = clientSide;
    }

    public UserConnection(@Nullable Channel channel) {
        this(channel, false);
    }

    @Nullable
    public <T extends StoredObject> T get(Class<T> objectClass) {
        return (T)this.storedObjects.get(objectClass);
    }

    public boolean has(Class<? extends StoredObject> objectClass) {
        return this.storedObjects.containsKey(objectClass);
    }

    public void put(StoredObject object) {
        this.storedObjects.put(object.getClass(), object);
    }

    public void clearStoredObjects() {
        this.storedObjects.clear();
    }

    public void sendRawPacket(ByteBuf packet, boolean currentThread) {
        Runnable act = this.clientSide ? () -> this.getChannel().pipeline().context(Via.getManager().getInjector().getDecoderName()).fireChannelRead((Object)packet) : () -> this.channel.pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush((Object)packet);
        if (currentThread) {
            act.run();
        } else {
            try {
                this.channel.eventLoop().submit(act);
            }
            catch (Throwable e) {
                packet.release();
                e.printStackTrace();
            }
        }
    }

    public ChannelFuture sendRawPacketFuture(ByteBuf packet) {
        if (this.clientSide) {
            return this.sendRawPacketFutureClientSide(packet);
        }
        return this.sendRawPacketFutureServerSide(packet);
    }

    private ChannelFuture sendRawPacketFutureServerSide(ByteBuf packet) {
        return this.channel.pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush((Object)packet);
    }

    private ChannelFuture sendRawPacketFutureClientSide(ByteBuf packet) {
        this.getChannel().pipeline().context(Via.getManager().getInjector().getDecoderName()).fireChannelRead((Object)packet);
        return this.getChannel().newSucceededFuture();
    }

    public void sendRawPacket(ByteBuf packet) {
        this.sendRawPacket(packet, false);
    }

    public void incrementSent() {
        ++this.sentPackets;
    }

    public boolean incrementReceived() {
        long diff = System.currentTimeMillis() - this.startTime;
        if (diff >= 1000L) {
            this.packetsPerSecond = this.intervalPackets;
            this.startTime = System.currentTimeMillis();
            this.intervalPackets = 1L;
            return true;
        }
        ++this.intervalPackets;
        ++this.receivedPackets;
        return false;
    }

    public boolean exceedsMaxPPS() {
        if (this.clientSide) {
            return false;
        }
        ViaVersionConfig conf = Via.getConfig();
        if (conf.getMaxPPS() > 0 && this.packetsPerSecond >= (long)conf.getMaxPPS()) {
            this.disconnect(conf.getMaxPPSKickMessage().replace("%pps", Long.toString(this.packetsPerSecond)));
            return true;
        }
        if (conf.getMaxWarnings() > 0 && conf.getTrackingPeriod() > 0) {
            if (this.secondsObserved > conf.getTrackingPeriod()) {
                this.warnings = 0;
                this.secondsObserved = 1;
            } else {
                ++this.secondsObserved;
                if (this.packetsPerSecond >= (long)conf.getWarningPPS()) {
                    ++this.warnings;
                }
                if (this.warnings >= conf.getMaxWarnings()) {
                    this.disconnect(conf.getMaxWarningsKickMessage().replace("%pps", Long.toString(this.packetsPerSecond)));
                    return true;
                }
            }
        }
        return false;
    }

    public void disconnect(String reason) {
        if (!this.channel.isOpen() || this.pendingDisconnect) {
            return;
        }
        this.pendingDisconnect = true;
        Via.getPlatform().runSync(() -> {
            if (!Via.getPlatform().disconnect(this, ChatColor.translateAlternateColorCodes('&', reason))) {
                this.channel.close();
            }
        });
    }

    public void sendRawPacketToServer(ByteBuf packet, boolean currentThread) {
        if (this.clientSide) {
            this.sendRawPacketToServerClientSide(packet, currentThread);
        } else {
            this.sendRawPacketToServerServerSide(packet, currentThread);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void sendRawPacketToServerServerSide(ByteBuf packet, boolean currentThread) {
        block8: {
            ByteBuf buf = packet.alloc().buffer();
            try {
                ChannelHandlerContext context = PipelineUtil.getPreviousContext(Via.getManager().getInjector().getDecoderName(), this.channel.pipeline());
                try {
                    Type.VAR_INT.writePrimitive(buf, 1000);
                }
                catch (Exception e) {
                    Via.getPlatform().getLogger().warning("Type.VAR_INT.write thrown an exception: " + e);
                }
                buf.writeBytes(packet);
                Runnable act = () -> {
                    if (context != null) {
                        context.fireChannelRead((Object)buf);
                    } else {
                        this.channel.pipeline().fireChannelRead((Object)buf);
                    }
                };
                if (currentThread) {
                    act.run();
                    break block8;
                }
                try {
                    this.channel.eventLoop().submit(act);
                }
                catch (Throwable t) {
                    buf.release();
                    throw t;
                }
            }
            finally {
                packet.release();
            }
        }
    }

    private void sendRawPacketToServerClientSide(ByteBuf packet, boolean currentThread) {
        Runnable act = () -> this.getChannel().pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush((Object)packet);
        if (currentThread) {
            act.run();
        } else {
            try {
                this.getChannel().eventLoop().submit(act);
            }
            catch (Throwable e) {
                e.printStackTrace();
                packet.release();
            }
        }
    }

    public void sendRawPacketToServer(ByteBuf packet) {
        this.sendRawPacketToServer(packet, false);
    }

    public boolean checkIncomingPacket() {
        if (this.clientSide) {
            return this.checkClientBound();
        }
        return this.checkServerBound();
    }

    private boolean checkClientBound() {
        this.incrementSent();
        return true;
    }

    private boolean checkServerBound() {
        if (this.pendingDisconnect) {
            return false;
        }
        return !this.incrementReceived() || !this.exceedsMaxPPS();
    }

    public boolean checkOutgoingPacket() {
        if (this.clientSide) {
            return this.checkServerBound();
        }
        return this.checkClientBound();
    }

    public boolean shouldTransformPacket() {
        return this.active;
    }

    public void transformOutgoing(ByteBuf buf, Function<Throwable, Exception> cancelSupplier) throws Exception {
        if (!buf.isReadable()) {
            return;
        }
        this.transform(buf, this.clientSide ? Direction.INCOMING : Direction.OUTGOING, cancelSupplier);
    }

    public void transformIncoming(ByteBuf buf, Function<Throwable, Exception> cancelSupplier) throws Exception {
        if (!buf.isReadable()) {
            return;
        }
        this.transform(buf, this.clientSide ? Direction.OUTGOING : Direction.INCOMING, cancelSupplier);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void transform(ByteBuf buf, Direction direction, Function<Throwable, Exception> cancelSupplier) throws Exception {
        int id = Type.VAR_INT.readPrimitive(buf);
        if (id == 1000) {
            return;
        }
        PacketWrapper wrapper = new PacketWrapper(id, buf, this);
        try {
            this.protocolInfo.getPipeline().transform(direction, this.protocolInfo.getState(), wrapper);
        }
        catch (CancelException ex) {
            throw cancelSupplier.apply(ex);
        }
        ByteBuf transformed = buf.alloc().buffer();
        try {
            wrapper.writeToBuffer(transformed);
            buf.clear().writeBytes(transformed);
        }
        finally {
            transformed.release();
        }
    }

    public long getId() {
        return this.id;
    }

    @Nullable
    public Channel getChannel() {
        return this.channel;
    }

    @Nullable
    public ProtocolInfo getProtocolInfo() {
        return this.protocolInfo;
    }

    public void setProtocolInfo(@Nullable ProtocolInfo protocolInfo) {
        this.protocolInfo = protocolInfo;
        if (protocolInfo != null) {
            this.storedObjects.put(ProtocolInfo.class, protocolInfo);
        } else {
            this.storedObjects.remove(ProtocolInfo.class);
        }
    }

    public Map<Class, StoredObject> getStoredObjects() {
        return this.storedObjects;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPendingDisconnect() {
        return this.pendingDisconnect;
    }

    public void setPendingDisconnect(boolean pendingDisconnect) {
        this.pendingDisconnect = pendingDisconnect;
    }

    @Nullable
    public Object getLastPacket() {
        return this.lastPacket;
    }

    public void setLastPacket(@Nullable Object lastPacket) {
        this.lastPacket = lastPacket;
    }

    public long getSentPackets() {
        return this.sentPackets;
    }

    public void setSentPackets(long sentPackets) {
        this.sentPackets = sentPackets;
    }

    public long getReceivedPackets() {
        return this.receivedPackets;
    }

    public void setReceivedPackets(long receivedPackets) {
        this.receivedPackets = receivedPackets;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getIntervalPackets() {
        return this.intervalPackets;
    }

    public void setIntervalPackets(long intervalPackets) {
        this.intervalPackets = intervalPackets;
    }

    public long getPacketsPerSecond() {
        return this.packetsPerSecond;
    }

    public void setPacketsPerSecond(long packetsPerSecond) {
        this.packetsPerSecond = packetsPerSecond;
    }

    public int getSecondsObserved() {
        return this.secondsObserved;
    }

    public void setSecondsObserved(int secondsObserved) {
        this.secondsObserved = secondsObserved;
    }

    public int getWarnings() {
        return this.warnings;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        UserConnection that = (UserConnection)o;
        return this.id == that.id;
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public boolean isClientSide() {
        return this.clientSide;
    }

    public boolean shouldApplyBlockProtocol() {
        return !this.clientSide;
    }
}

