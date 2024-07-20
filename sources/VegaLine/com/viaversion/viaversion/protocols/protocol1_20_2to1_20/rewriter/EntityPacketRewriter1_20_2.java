/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_20_2to1_20.rewriter;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_20;
import com.viaversion.viaversion.api.type.types.version.Types1_20_2;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.Protocol1_20_2To1_20;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.storage.ConfigurationState;
import com.viaversion.viaversion.rewriter.EntityRewriter;

public final class EntityPacketRewriter1_20_2
extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_20_2To1_20> {
    public EntityPacketRewriter1_20_2(Protocol1_20_2To1_20 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_19_4.SPAWN_ENTITY, Entity1_19_4Types.FALLING_BLOCK);
        this.registerMetadataRewriter(ClientboundPackets1_19_4.ENTITY_METADATA, Types1_20.METADATA_LIST, Types1_20_2.METADATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19_4.REMOVE_ENTITIES);
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.SPAWN_PLAYER, ClientboundPackets1_20_2.SPAWN_ENTITY, wrapper -> {
            wrapper.passthrough(Type.VAR_INT);
            wrapper.passthrough(Type.UUID);
            wrapper.write(Type.VAR_INT, Entity1_19_4Types.PLAYER.getId());
            wrapper.passthrough(Type.DOUBLE);
            wrapper.passthrough(Type.DOUBLE);
            wrapper.passthrough(Type.DOUBLE);
            byte yaw = wrapper.read(Type.BYTE);
            wrapper.passthrough(Type.BYTE);
            wrapper.write(Type.BYTE, yaw);
            wrapper.write(Type.BYTE, yaw);
            wrapper.write(Type.VAR_INT, 0);
            wrapper.write(Type.SHORT, (short)0);
            wrapper.write(Type.SHORT, (short)0);
            wrapper.write(Type.SHORT, (short)0);
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.JOIN_GAME, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    wrapper.passthrough(Type.INT);
                    wrapper.passthrough(Type.BOOLEAN);
                    byte gamemode = wrapper.read(Type.UNSIGNED_BYTE).byteValue();
                    byte previousGamemode = wrapper.read(Type.BYTE);
                    wrapper.passthrough(Type.STRING_ARRAY);
                    CompoundTag dimensionRegistry = wrapper.read(Type.NBT);
                    String dimensionType = wrapper.read(Type.STRING);
                    String world = wrapper.read(Type.STRING);
                    long seed = wrapper.read(Type.LONG);
                    EntityPacketRewriter1_20_2.this.trackBiomeSize(wrapper.user(), dimensionRegistry);
                    EntityPacketRewriter1_20_2.this.cacheDimensionData(wrapper.user(), dimensionRegistry);
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.write(Type.BOOLEAN, false);
                    wrapper.write(Type.STRING, dimensionType);
                    wrapper.write(Type.STRING, world);
                    wrapper.write(Type.LONG, seed);
                    wrapper.write(Type.BYTE, gamemode);
                    wrapper.write(Type.BYTE, previousGamemode);
                    ConfigurationState configurationBridge = wrapper.user().get(ConfigurationState.class);
                    if (!configurationBridge.setLastDimensionRegistry(dimensionRegistry)) {
                        PacketWrapper clientInformationPacket = configurationBridge.clientInformationPacket(wrapper.user());
                        if (clientInformationPacket != null) {
                            clientInformationPacket.sendToServer(Protocol1_20_2To1_20.class);
                        }
                        return;
                    }
                    if (configurationBridge.bridgePhase() == ConfigurationState.BridgePhase.NONE) {
                        PacketWrapper configurationPacket = wrapper.create(ClientboundPackets1_20_2.START_CONFIGURATION);
                        configurationPacket.send(Protocol1_20_2To1_20.class);
                        configurationBridge.setBridgePhase(ConfigurationState.BridgePhase.REENTERING_CONFIGURATION);
                        configurationBridge.setJoinGamePacket(wrapper);
                        wrapper.cancel();
                        return;
                    }
                    Protocol1_20_2To1_20.sendConfigurationPackets(wrapper.user(), dimensionRegistry, null);
                    configurationBridge.setJoinGamePacket(wrapper);
                    wrapper.cancel();
                });
                this.handler(EntityPacketRewriter1_20_2.this.worldDataTrackerHandlerByKey());
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    wrapper.passthrough(Type.STRING);
                    wrapper.passthrough(Type.STRING);
                    wrapper.passthrough(Type.LONG);
                    wrapper.write(Type.BYTE, wrapper.read(Type.UNSIGNED_BYTE).byteValue());
                    wrapper.passthrough(Type.BYTE);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    byte dataToKeep = wrapper.read(Type.BYTE);
                    wrapper.passthrough(Type.OPTIONAL_GLOBAL_POSITION);
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.write(Type.BYTE, dataToKeep);
                });
                this.handler(EntityPacketRewriter1_20_2.this.worldDataTrackerHandlerByKey());
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.ENTITY_EFFECT, wrapper -> {
            wrapper.passthrough(Type.VAR_INT);
            wrapper.write(Type.VAR_INT, wrapper.read(Type.VAR_INT) - 1);
            wrapper.passthrough(Type.BYTE);
            wrapper.passthrough(Type.VAR_INT);
            wrapper.passthrough(Type.BYTE);
            if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                wrapper.write(Type.NAMELESS_NBT, wrapper.read(Type.NBT));
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.REMOVE_ENTITY_EFFECT, wrapper -> {
            wrapper.passthrough(Type.VAR_INT);
            wrapper.write(Type.VAR_INT, wrapper.read(Type.VAR_INT) - 1);
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, meta) -> meta.setMetaType(Types1_20_2.META_TYPES.byId(meta.metaType().typeId())));
        this.registerMetaTypeHandler(null, Types1_20_2.META_TYPES.blockStateType, Types1_20_2.META_TYPES.optionalBlockStateType, Types1_20_2.META_TYPES.particleType);
        this.filter().filterFamily(Entity1_19_4Types.DISPLAY).addIndex(10);
        this.filter().filterFamily(Entity1_19_4Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
            int blockState = (Integer)meta.value();
            meta.setValue(((Protocol1_20_2To1_20)this.protocol).getMappingData().getNewBlockStateId(blockState));
        });
    }

    @Override
    public EntityType typeFromId(int type2) {
        return Entity1_19_4Types.getTypeFromId(type2);
    }
}

