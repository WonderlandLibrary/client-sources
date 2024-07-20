/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.packets;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_3Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.BitSetType;
import com.viaversion.viaversion.api.type.types.version.Types1_19;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.Protocol1_19_3To1_19_1;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.BitSet;
import java.util.UUID;

public final class EntityPackets
extends EntityRewriter<ClientboundPackets1_19_1, Protocol1_19_3To1_19_1> {
    private static final BitSetType PROFILE_ACTIONS_ENUM_TYPE = new BitSetType(6);

    public EntityPackets(Protocol1_19_3To1_19_1 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_19_1.SPAWN_ENTITY, Entity1_19_3Types.FALLING_BLOCK);
        this.registerMetadataRewriter(ClientboundPackets1_19_1.ENTITY_METADATA, Types1_19.METADATA_LIST, Types1_19_3.METADATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19_1.REMOVE_ENTITIES);
        ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_1.JOIN_GAME, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.map(Type.STRING);
                this.handler(EntityPackets.this.dimensionDataHandler());
                this.handler(EntityPackets.this.biomeSizeTracker());
                this.handler(EntityPackets.this.worldDataTrackerHandlerByKey());
                this.handler(wrapper -> {
                    PacketWrapper enableFeaturesPacket = wrapper.create(ClientboundPackets1_19_3.UPDATE_ENABLED_FEATURES);
                    enableFeaturesPacket.write(Type.VAR_INT, 1);
                    enableFeaturesPacket.write(Type.STRING, "minecraft:vanilla");
                    enableFeaturesPacket.scheduleSend(Protocol1_19_3To1_19_1.class);
                });
            }
        });
        ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_1.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.handler(EntityPackets.this.worldDataTrackerHandlerByKey());
                this.handler(wrapper -> {
                    boolean keepAttributes = wrapper.read(Type.BOOLEAN);
                    byte keepDataMask = 2;
                    if (keepAttributes) {
                        keepDataMask = (byte)(keepDataMask | 1);
                    }
                    wrapper.write(Type.BYTE, keepDataMask);
                });
            }
        });
        ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_1.PLAYER_INFO, ClientboundPackets1_19_3.PLAYER_INFO_UPDATE, wrapper -> {
            int action = wrapper.read(Type.VAR_INT);
            if (action == 4) {
                int entries = wrapper.read(Type.VAR_INT);
                UUID[] uuidsToRemove = new UUID[entries];
                for (int i = 0; i < entries; ++i) {
                    uuidsToRemove[i] = wrapper.read(Type.UUID);
                }
                wrapper.write(Type.UUID_ARRAY, uuidsToRemove);
                wrapper.setPacketType(ClientboundPackets1_19_3.PLAYER_INFO_REMOVE);
                return;
            }
            BitSet set = new BitSet(6);
            if (action == 0) {
                set.set(0, 6);
            } else {
                set.set(action == 1 ? action + 1 : action + 2);
            }
            wrapper.write(PROFILE_ACTIONS_ENUM_TYPE, set);
            int entries = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < entries; ++i) {
                wrapper.passthrough(Type.UUID);
                if (action == 0) {
                    wrapper.passthrough(Type.STRING);
                    int properties = wrapper.passthrough(Type.VAR_INT);
                    for (int j = 0; j < properties; ++j) {
                        wrapper.passthrough(Type.STRING);
                        wrapper.passthrough(Type.STRING);
                        wrapper.passthrough(Type.OPTIONAL_STRING);
                    }
                    int gamemode = wrapper.read(Type.VAR_INT);
                    int ping = wrapper.read(Type.VAR_INT);
                    JsonElement displayName = wrapper.read(Type.OPTIONAL_COMPONENT);
                    wrapper.read(Type.OPTIONAL_PROFILE_KEY);
                    wrapper.write(Type.BOOLEAN, false);
                    wrapper.write(Type.VAR_INT, gamemode);
                    wrapper.write(Type.BOOLEAN, true);
                    wrapper.write(Type.VAR_INT, ping);
                    wrapper.write(Type.OPTIONAL_COMPONENT, displayName);
                    continue;
                }
                if (action == 1 || action == 2) {
                    wrapper.passthrough(Type.VAR_INT);
                    continue;
                }
                if (action != 3) continue;
                wrapper.passthrough(Type.OPTIONAL_COMPONENT);
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, meta) -> {
            int id = meta.metaType().typeId();
            meta.setMetaType(Types1_19_3.META_TYPES.byId(id >= 2 ? id + 1 : id));
        });
        this.registerMetaTypeHandler(Types1_19_3.META_TYPES.itemType, Types1_19_3.META_TYPES.blockStateType, null, Types1_19_3.META_TYPES.particleType);
        this.filter().index(6).handler((event, meta) -> {
            int pose = (Integer)meta.value();
            if (pose >= 10) {
                meta.setValue(pose + 1);
            }
        });
        this.filter().filterFamily(Entity1_19_3Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
            int data = (Integer)meta.getValue();
            meta.setValue(((Protocol1_19_3To1_19_1)this.protocol).getMappingData().getNewBlockStateId(data));
        });
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
    }

    @Override
    public EntityType typeFromId(int type2) {
        return Entity1_19_3Types.getTypeFromId(type2);
    }
}

