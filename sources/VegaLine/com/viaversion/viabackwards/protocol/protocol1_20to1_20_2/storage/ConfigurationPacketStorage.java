/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.storage;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.Protocol1_20To1_20_2;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.List;

public final class ConfigurationPacketStorage
implements StorableObject {
    private final List<QueuedPacket> rawPackets = new ArrayList<QueuedPacket>();
    private CompoundTag registry;
    private String[] enabledFeatures;
    private boolean finished;

    public CompoundTag registry() {
        Preconditions.checkNotNull(this.registry);
        return this.registry;
    }

    public void setRegistry(CompoundTag registry) {
        this.registry = registry;
    }

    public String[] enabledFeatures() {
        Preconditions.checkNotNull(this.enabledFeatures);
        return this.enabledFeatures;
    }

    public void setEnabledFeatures(String[] enabledFeatures) {
        this.enabledFeatures = enabledFeatures;
    }

    public void addRawPacket(PacketWrapper wrapper, PacketType type2) throws Exception {
        ByteBuf buf = Unpooled.buffer();
        wrapper.setId(-1);
        wrapper.writeToBuffer(buf);
        this.rawPackets.add(new QueuedPacket(buf, type2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sendQueuedPackets(UserConnection connection) throws Exception {
        for (QueuedPacket queuedPacket : this.rawPackets) {
            try {
                PacketWrapper packet = PacketWrapper.create(queuedPacket.packetType(), queuedPacket.buf(), connection);
                packet.send(Protocol1_20To1_20_2.class);
            } finally {
                queuedPacket.buf().release();
            }
        }
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public static final class QueuedPacket {
        private final ByteBuf buf;
        private final PacketType packetType;

        public QueuedPacket(ByteBuf buf, PacketType packetType) {
            this.buf = buf;
            this.packetType = packetType;
        }

        public ByteBuf buf() {
            return this.buf;
        }

        public PacketType packetType() {
            return this.packetType;
        }
    }
}

