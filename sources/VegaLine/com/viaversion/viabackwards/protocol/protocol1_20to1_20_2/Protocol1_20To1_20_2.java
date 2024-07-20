/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter.BlockItemPacketRewriter1_20_2;
import com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter.EntityPacketRewriter1_20_2;
import com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.storage.ConfigurationPacketStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.Protocol1_20_2To1_20;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ServerboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ServerboundPackets1_20_2;
import java.util.UUID;

public final class Protocol1_20To1_20_2
extends BackwardsProtocol<ClientboundPackets1_20_2, ClientboundPackets1_19_4, ServerboundPackets1_20_2, ServerboundPackets1_19_4> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.20.2", "1.20", Protocol1_20_2To1_20.class);
    private final EntityPacketRewriter1_20_2 entityPacketRewriter = new EntityPacketRewriter1_20_2(this);
    private final BlockItemPacketRewriter1_20_2 itemPacketRewriter = new BlockItemPacketRewriter1_20_2(this);

    public Protocol1_20To1_20_2() {
        super(ClientboundPackets1_20_2.class, ClientboundPackets1_19_4.class, ServerboundPackets1_20_2.class, ServerboundPackets1_19_4.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        SoundRewriter<ClientboundPackets1_20_2> soundRewriter = new SoundRewriter<ClientboundPackets1_20_2>(this);
        soundRewriter.register1_19_3Sound(ClientboundPackets1_20_2.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_20_2.ENTITY_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_20_2.STOP_SOUND);
        this.registerClientbound(ClientboundPackets1_20_2.DISPLAY_SCOREBOARD, (PacketWrapper wrapper) -> {
            int slot = wrapper.read(Type.VAR_INT);
            wrapper.write(Type.BYTE, (byte)slot);
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE.getId(), ClientboundLoginPackets.GAME_PROFILE.getId(), (PacketWrapper wrapper) -> {
            wrapper.user().put(new ConfigurationPacketStorage());
            wrapper.user().getProtocolInfo().setClientState(State.LOGIN);
            wrapper.create(ServerboundLoginPackets.LOGIN_ACKNOWLEDGED).sendToServer(Protocol1_20To1_20_2.class);
        });
        this.registerClientbound(State.CONFIGURATION, ClientboundConfigurationPackets1_20_2.FINISH_CONFIGURATION.getId(), ClientboundConfigurationPackets1_20_2.FINISH_CONFIGURATION.getId(), (PacketWrapper wrapper) -> {
            wrapper.cancel();
            wrapper.user().getProtocolInfo().setServerState(State.PLAY);
            wrapper.user().get(ConfigurationPacketStorage.class).setFinished(true);
            wrapper.create(ServerboundConfigurationPackets1_20_2.FINISH_CONFIGURATION).sendToServer(Protocol1_20To1_20_2.class);
            wrapper.user().getProtocolInfo().setClientState(State.PLAY);
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), (PacketWrapper wrapper) -> {
            wrapper.passthrough(Type.STRING);
            UUID uuid = wrapper.read(Type.OPTIONAL_UUID);
            wrapper.write(Type.UUID, uuid != null ? uuid : new UUID(0L, 0L));
        });
        this.registerClientbound(ClientboundPackets1_20_2.START_CONFIGURATION, null, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            wrapper.user().getProtocolInfo().setServerState(State.CONFIGURATION);
            PacketWrapper configAcknowledgedPacket = wrapper.create(ServerboundPackets1_20_2.CONFIGURATION_ACKNOWLEDGED);
            configAcknowledgedPacket.sendToServer(Protocol1_20To1_20_2.class);
            wrapper.user().getProtocolInfo().setClientState(State.CONFIGURATION);
            wrapper.user().put(new ConfigurationPacketStorage());
        });
        this.cancelClientbound(ClientboundPackets1_20_2.PONG_RESPONSE);
        this.registerClientbound(State.CONFIGURATION, ClientboundConfigurationPackets1_20_2.DISCONNECT.getId(), ClientboundPackets1_19_4.DISCONNECT.getId());
        this.registerClientbound(State.CONFIGURATION, ClientboundConfigurationPackets1_20_2.KEEP_ALIVE.getId(), ClientboundPackets1_19_4.KEEP_ALIVE.getId());
        this.registerClientbound(State.CONFIGURATION, ClientboundConfigurationPackets1_20_2.RESOURCE_PACK.getId(), ClientboundPackets1_19_4.RESOURCE_PACK.getId());
        this.registerClientbound(State.CONFIGURATION, ClientboundConfigurationPackets1_20_2.REGISTRY_DATA.getId(), -1, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            CompoundTag registry = wrapper.read(Type.NAMELESS_NBT);
            this.entityPacketRewriter.trackBiomeSize(wrapper.user(), registry);
            this.entityPacketRewriter.cacheDimensionData(wrapper.user(), registry);
            wrapper.user().get(ConfigurationPacketStorage.class).setRegistry(registry);
        });
        this.registerClientbound(State.CONFIGURATION, ClientboundConfigurationPackets1_20_2.UPDATE_ENABLED_FEATURES.getId(), -1, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            String[] enabledFeatures = wrapper.read(Type.STRING_ARRAY);
            wrapper.user().get(ConfigurationPacketStorage.class).setEnabledFeatures(enabledFeatures);
        });
        this.registerClientbound(State.CONFIGURATION, ClientboundConfigurationPackets1_20_2.UPDATE_TAGS.getId(), -1, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            wrapper.user().get(ConfigurationPacketStorage.class).addRawPacket(wrapper, ClientboundPackets1_19_4.TAGS);
        });
        this.registerClientbound(State.CONFIGURATION, ClientboundConfigurationPackets1_20_2.CUSTOM_PAYLOAD.getId(), -1, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            wrapper.user().get(ConfigurationPacketStorage.class).addRawPacket(wrapper, ClientboundPackets1_19_4.PLUGIN_MESSAGE);
        });
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper wrapper) throws Exception {
        ConfigurationPacketStorage configurationPacketStorage = wrapper.user().get(ConfigurationPacketStorage.class);
        if (configurationPacketStorage == null || configurationPacketStorage.isFinished()) {
            super.transform(direction, state, wrapper);
            return;
        }
        if (direction == Direction.CLIENTBOUND) {
            super.transform(direction, State.CONFIGURATION, wrapper);
            return;
        }
        int id = wrapper.getId();
        if (id == ServerboundPackets1_19_4.CLIENT_SETTINGS.getId()) {
            wrapper.setPacketType(ServerboundConfigurationPackets1_20_2.CLIENT_INFORMATION);
        } else if (id == ServerboundPackets1_19_4.PLUGIN_MESSAGE.getId()) {
            wrapper.setPacketType(ServerboundConfigurationPackets1_20_2.CUSTOM_PAYLOAD);
        } else if (id == ServerboundPackets1_19_4.KEEP_ALIVE.getId()) {
            wrapper.setPacketType(ServerboundConfigurationPackets1_20_2.KEEP_ALIVE);
        } else if (id == ServerboundPackets1_19_4.PONG.getId()) {
            wrapper.setPacketType(ServerboundConfigurationPackets1_20_2.PONG);
        } else if (id == ServerboundPackets1_19_4.RESOURCE_PACK_STATUS.getId()) {
            wrapper.setPacketType(ServerboundConfigurationPackets1_20_2.RESOURCE_PACK);
        } else {
            throw CancelException.generate();
        }
    }

    @Override
    protected void registerConfigurationChangeHandlers() {
    }

    @Override
    public void init(UserConnection connection) {
        this.addEntityTracker(connection, new EntityTrackerBase(connection, Entity1_19_4Types.PLAYER));
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    @Override
    public EntityRewriter<Protocol1_20To1_20_2> getEntityRewriter() {
        return this.entityPacketRewriter;
    }

    @Override
    public ItemRewriter<Protocol1_20To1_20_2> getItemRewriter() {
        return this.itemPacketRewriter;
    }
}

