/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_20_2to1_20.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.Protocol1_20_2To1_20;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ConfigurationState
implements StorableObject {
    private final List<QueuedPacket> packetQueue = new ArrayList<QueuedPacket>();
    private BridgePhase bridgePhase = BridgePhase.NONE;
    private QueuedPacket joinGamePacket;
    private boolean queuedJoinGame;
    private CompoundTag lastDimensionRegistry;
    private ClientInformation clientInformation;

    public BridgePhase bridgePhase() {
        return this.bridgePhase;
    }

    public void setBridgePhase(BridgePhase bridgePhase) {
        this.bridgePhase = bridgePhase;
    }

    public @Nullable CompoundTag lastDimensionRegistry() {
        return this.lastDimensionRegistry;
    }

    public boolean setLastDimensionRegistry(CompoundTag dimensionRegistry) {
        boolean equals = Objects.equals(this.lastDimensionRegistry, dimensionRegistry);
        this.lastDimensionRegistry = dimensionRegistry;
        return !equals;
    }

    public void setClientInformation(ClientInformation clientInformation) {
        this.clientInformation = clientInformation;
    }

    public void addPacketToQueue(PacketWrapper wrapper, boolean clientbound) throws Exception {
        this.packetQueue.add(this.toQueuedPacket(wrapper, clientbound, false));
    }

    private QueuedPacket toQueuedPacket(PacketWrapper wrapper, boolean clientbound, boolean skipCurrentPipeline) throws Exception {
        ByteBuf copy = Unpooled.buffer();
        PacketType packetType = wrapper.getPacketType();
        int packetId = wrapper.getId();
        wrapper.setId(-1);
        wrapper.writeToBuffer(copy);
        return new QueuedPacket(copy, clientbound, packetType, packetId, skipCurrentPipeline);
    }

    public void setJoinGamePacket(PacketWrapper wrapper) throws Exception {
        this.joinGamePacket = this.toQueuedPacket(wrapper, true, true);
        this.queuedJoinGame = true;
    }

    @Override
    public boolean clearOnServerSwitch() {
        return false;
    }

    @Override
    public void onRemove() {
        for (QueuedPacket packet : this.packetQueue) {
            packet.buf().release();
        }
        if (this.joinGamePacket != null) {
            this.joinGamePacket.buf().release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sendQueuedPackets(UserConnection connection) throws Exception {
        PacketWrapper clientInformationPacket;
        boolean hasJoinGamePacket;
        boolean bl = hasJoinGamePacket = this.joinGamePacket != null;
        if (hasJoinGamePacket) {
            this.packetQueue.add(0, this.joinGamePacket);
            this.joinGamePacket = null;
        }
        if ((clientInformationPacket = this.clientInformationPacket(connection)) != null) {
            this.packetQueue.add(hasJoinGamePacket ? 1 : 0, this.toQueuedPacket(clientInformationPacket, false, true));
        }
        QueuedPacket[] queuedPackets = this.packetQueue.toArray(new QueuedPacket[0]);
        this.packetQueue.clear();
        for (QueuedPacket packet : queuedPackets) {
            try {
                PacketWrapper queuedWrapper = packet.packetType() != null ? PacketWrapper.create(packet.packetType(), packet.buf(), connection) : PacketWrapper.create(packet.packetId(), packet.buf(), connection);
                if (packet.clientbound()) {
                    queuedWrapper.send(Protocol1_20_2To1_20.class, packet.skipCurrentPipeline());
                    continue;
                }
                queuedWrapper.sendToServer(Protocol1_20_2To1_20.class, packet.skipCurrentPipeline());
            } finally {
                packet.buf().release();
            }
        }
    }

    public void clear() {
        this.packetQueue.clear();
        this.bridgePhase = BridgePhase.NONE;
        this.queuedJoinGame = false;
    }

    public boolean queuedOrSentJoinGame() {
        return this.queuedJoinGame;
    }

    public @Nullable PacketWrapper clientInformationPacket(UserConnection connection) {
        if (this.clientInformation == null) {
            return null;
        }
        PacketWrapper settingsPacket = PacketWrapper.create(ServerboundPackets1_19_4.CLIENT_SETTINGS, connection);
        settingsPacket.write(Type.STRING, this.clientInformation.language);
        settingsPacket.write(Type.BYTE, this.clientInformation.viewDistance);
        settingsPacket.write(Type.VAR_INT, this.clientInformation.chatVisibility);
        settingsPacket.write(Type.BOOLEAN, this.clientInformation.showChatColors);
        settingsPacket.write(Type.UNSIGNED_BYTE, this.clientInformation.modelCustomization);
        settingsPacket.write(Type.VAR_INT, this.clientInformation.mainHand);
        settingsPacket.write(Type.BOOLEAN, this.clientInformation.textFiltering);
        settingsPacket.write(Type.BOOLEAN, this.clientInformation.allowListing);
        return settingsPacket;
    }

    public static final class ClientInformation {
        private final String language;
        private final byte viewDistance;
        private final int chatVisibility;
        private final boolean showChatColors;
        private final short modelCustomization;
        private final int mainHand;
        private final boolean textFiltering;
        private final boolean allowListing;

        public ClientInformation(String language, byte viewDistance, int chatVisibility, boolean showChatColors, short modelCustomization, int mainHand, boolean textFiltering, boolean allowListing) {
            this.language = language;
            this.viewDistance = viewDistance;
            this.chatVisibility = chatVisibility;
            this.showChatColors = showChatColors;
            this.modelCustomization = modelCustomization;
            this.mainHand = mainHand;
            this.textFiltering = textFiltering;
            this.allowListing = allowListing;
        }
    }

    public static final class QueuedPacket {
        private final ByteBuf buf;
        private final boolean clientbound;
        private final PacketType packetType;
        private final int packetId;
        private final boolean skipCurrentPipeline;

        private QueuedPacket(ByteBuf buf, boolean clientbound, PacketType packetType, int packetId, boolean skipCurrentPipeline) {
            this.buf = buf;
            this.clientbound = clientbound;
            this.packetType = packetType;
            this.packetId = packetId;
            this.skipCurrentPipeline = skipCurrentPipeline;
        }

        public ByteBuf buf() {
            return this.buf;
        }

        public boolean clientbound() {
            return this.clientbound;
        }

        public int packetId() {
            return this.packetId;
        }

        public @Nullable PacketType packetType() {
            return this.packetType;
        }

        public boolean skipCurrentPipeline() {
            return this.skipCurrentPipeline;
        }

        public String toString() {
            return "QueuedPacket{buf=" + this.buf + ", clientbound=" + this.clientbound + ", packetType=" + this.packetType + ", packetId=" + this.packetId + ", skipCurrentPipeline=" + this.skipCurrentPipeline + '}';
        }
    }

    public static enum BridgePhase {
        NONE,
        PROFILE_SENT,
        CONFIGURATION,
        REENTERING_CONFIGURATION;

    }
}

