/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.Protocol1_19_1To1_19_3;
import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage.ChatTypeStorage1_19_3;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_3Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.BitSetType;
import com.viaversion.viaversion.api.type.types.version.Types1_19;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import java.util.BitSet;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class EntityPackets1_19_3
extends EntityRewriter<ClientboundPackets1_19_3, Protocol1_19_1To1_19_3> {
    private static final BitSetType PROFILE_ACTIONS_ENUM_TYPE = new BitSetType(6);
    private static final int[] PROFILE_ACTIONS = new int[]{2, 4, 5};
    private static final int ADD_PLAYER = 0;
    private static final int INITIALIZE_CHAT = 1;
    private static final int UPDATE_GAMEMODE = 2;
    private static final int UPDATE_LISTED = 3;
    private static final int UPDATE_LATENCY = 4;
    private static final int UPDATE_DISPLAYNAME = 5;

    public EntityPackets1_19_3(Protocol1_19_1To1_19_3 protocol) {
        super(protocol, Types1_19.META_TYPES.optionalComponentType, Types1_19.META_TYPES.booleanType);
    }

    @Override
    protected void registerPackets() {
        this.registerMetadataRewriter(ClientboundPackets1_19_3.ENTITY_METADATA, Types1_19_3.METADATA_LIST, Types1_19.METADATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19_3.REMOVE_ENTITIES);
        this.registerTrackerWithData1_19(ClientboundPackets1_19_3.SPAWN_ENTITY, Entity1_19_3Types.FALLING_BLOCK);
        ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.JOIN_GAME, new PacketHandlers(){

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
                this.handler(EntityPackets1_19_3.this.dimensionDataHandler());
                this.handler(EntityPackets1_19_3.this.biomeSizeTracker());
                this.handler(EntityPackets1_19_3.this.worldDataTrackerHandlerByKey());
                this.handler(wrapper -> {
                    ChatTypeStorage1_19_3 chatTypeStorage = wrapper.user().get(ChatTypeStorage1_19_3.class);
                    chatTypeStorage.clear();
                    CompoundTag registry = wrapper.get(Type.NBT, 0);
                    ListTag chatTypes = (ListTag)((CompoundTag)registry.get("minecraft:chat_type")).get("value");
                    for (Tag chatType : chatTypes) {
                        CompoundTag chatTypeCompound = (CompoundTag)chatType;
                        NumberTag idTag = (NumberTag)chatTypeCompound.get("id");
                        chatTypeStorage.addChatType(idTag.asInt(), chatTypeCompound);
                    }
                });
            }
        });
        ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.handler(EntityPackets1_19_3.this.worldDataTrackerHandlerByKey());
                this.handler(wrapper -> {
                    byte keepDataMask = wrapper.read(Type.BYTE);
                    wrapper.write(Type.BOOLEAN, (keepDataMask & 1) != 0);
                });
            }
        });
        ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.PLAYER_INFO_UPDATE, ClientboundPackets1_19_1.PLAYER_INFO, wrapper -> {
            wrapper.cancel();
            BitSet actions = wrapper.read(PROFILE_ACTIONS_ENUM_TYPE);
            int entries = wrapper.read(Type.VAR_INT);
            if (actions.get(0)) {
                PacketWrapper playerInfoPacket = wrapper.create(ClientboundPackets1_19_1.PLAYER_INFO);
                playerInfoPacket.write(Type.VAR_INT, 0);
                playerInfoPacket.write(Type.VAR_INT, entries);
                for (int i = 0; i < entries; ++i) {
                    int gamemode;
                    ProfileKey profileKey;
                    playerInfoPacket.write(Type.UUID, wrapper.read(Type.UUID));
                    playerInfoPacket.write(Type.STRING, wrapper.read(Type.STRING));
                    int properties = wrapper.read(Type.VAR_INT);
                    playerInfoPacket.write(Type.VAR_INT, properties);
                    for (int j = 0; j < properties; ++j) {
                        playerInfoPacket.write(Type.STRING, wrapper.read(Type.STRING));
                        playerInfoPacket.write(Type.STRING, wrapper.read(Type.STRING));
                        playerInfoPacket.write(Type.OPTIONAL_STRING, wrapper.read(Type.OPTIONAL_STRING));
                    }
                    if (actions.get(1) && wrapper.read(Type.BOOLEAN).booleanValue()) {
                        wrapper.read(Type.UUID);
                        profileKey = wrapper.read(Type.PROFILE_KEY);
                    } else {
                        profileKey = null;
                    }
                    int n = gamemode = actions.get(2) ? wrapper.read(Type.VAR_INT) : 0;
                    if (actions.get(3)) {
                        wrapper.read(Type.BOOLEAN);
                    }
                    int latency = actions.get(4) ? wrapper.read(Type.VAR_INT) : 0;
                    JsonElement displayName = actions.get(5) ? wrapper.read(Type.OPTIONAL_COMPONENT) : null;
                    playerInfoPacket.write(Type.VAR_INT, gamemode);
                    playerInfoPacket.write(Type.VAR_INT, latency);
                    playerInfoPacket.write(Type.OPTIONAL_COMPONENT, displayName);
                    playerInfoPacket.write(Type.OPTIONAL_PROFILE_KEY, profileKey);
                }
                playerInfoPacket.send(Protocol1_19_1To1_19_3.class);
                return;
            }
            PlayerProfileUpdate[] updates = new PlayerProfileUpdate[entries];
            for (int i = 0; i < entries; ++i) {
                UUID uuid = wrapper.read(Type.UUID);
                int gamemode = 0;
                int latency = 0;
                JsonElement displayName = null;
                block8: for (int action : PROFILE_ACTIONS) {
                    if (!actions.get(action)) continue;
                    switch (action) {
                        case 2: {
                            gamemode = wrapper.read(Type.VAR_INT);
                            continue block8;
                        }
                        case 4: {
                            latency = wrapper.read(Type.VAR_INT);
                            continue block8;
                        }
                        case 5: {
                            displayName = wrapper.read(Type.OPTIONAL_COMPONENT);
                        }
                    }
                }
                updates[i] = new PlayerProfileUpdate(uuid, gamemode, latency, displayName);
            }
            if (actions.get(2)) {
                this.sendPlayerProfileUpdate(wrapper.user(), 1, updates);
            } else if (actions.get(4)) {
                this.sendPlayerProfileUpdate(wrapper.user(), 2, updates);
            } else if (actions.get(5)) {
                this.sendPlayerProfileUpdate(wrapper.user(), 3, updates);
            }
        });
        ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.PLAYER_INFO_REMOVE, ClientboundPackets1_19_1.PLAYER_INFO, wrapper -> {
            UUID[] uuids = wrapper.read(Type.UUID_ARRAY);
            wrapper.write(Type.VAR_INT, 4);
            wrapper.write(Type.VAR_INT, uuids.length);
            for (UUID uuid : uuids) {
                wrapper.write(Type.UUID, uuid);
            }
        });
    }

    private void sendPlayerProfileUpdate(UserConnection connection, int action, PlayerProfileUpdate[] updates) throws Exception {
        PacketWrapper playerInfoPacket = PacketWrapper.create(ClientboundPackets1_19_1.PLAYER_INFO, connection);
        playerInfoPacket.write(Type.VAR_INT, action);
        playerInfoPacket.write(Type.VAR_INT, updates.length);
        for (PlayerProfileUpdate update : updates) {
            playerInfoPacket.write(Type.UUID, update.uuid());
            if (action == 1) {
                playerInfoPacket.write(Type.VAR_INT, update.gamemode());
                continue;
            }
            if (action == 2) {
                playerInfoPacket.write(Type.VAR_INT, update.latency());
                continue;
            }
            if (action == 3) {
                playerInfoPacket.write(Type.OPTIONAL_COMPONENT, update.displayName());
                continue;
            }
            throw new IllegalArgumentException("Invalid action: " + action);
        }
        playerInfoPacket.send(Protocol1_19_1To1_19_3.class);
    }

    @Override
    public void registerRewrites() {
        this.filter().handler((event, meta) -> {
            int id = meta.metaType().typeId();
            if (id > 2) {
                meta.setMetaType(Types1_19.META_TYPES.byId(id - 1));
            } else if (id != 2) {
                meta.setMetaType(Types1_19.META_TYPES.byId(id));
            }
        });
        this.registerMetaTypeHandler(Types1_19.META_TYPES.itemType, Types1_19.META_TYPES.blockStateType, null, Types1_19.META_TYPES.particleType, Types1_19.META_TYPES.componentType, Types1_19.META_TYPES.optionalComponentType);
        this.filter().index(6).handler((event, meta) -> {
            int pose = (Integer)meta.value();
            if (pose == 10) {
                meta.setValue(0);
            } else if (pose > 10) {
                meta.setValue(pose - 1);
            }
        });
        this.filter().filterFamily(Entity1_19_3Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
            int data = (Integer)meta.getValue();
            meta.setValue(((Protocol1_19_1To1_19_3)this.protocol).getMappingData().getNewBlockStateId(data));
        });
        this.filter().type(Entity1_19_3Types.CAMEL).cancel(19);
        this.filter().type(Entity1_19_3Types.CAMEL).cancel(20);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        this.mapEntityTypeWithData(Entity1_19_3Types.CAMEL, Entity1_19_3Types.DONKEY).jsonName();
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return Entity1_19_3Types.getTypeFromId(typeId);
    }

    private static final class PlayerProfileUpdate {
        private final UUID uuid;
        private final int gamemode;
        private final int latency;
        private final JsonElement displayName;

        private PlayerProfileUpdate(UUID uuid, int gamemode, int latency, @Nullable JsonElement displayName) {
            this.uuid = uuid;
            this.gamemode = gamemode;
            this.latency = latency;
            this.displayName = displayName;
        }

        public UUID uuid() {
            return this.uuid;
        }

        public int gamemode() {
            return this.gamemode;
        }

        public int latency() {
            return this.latency;
        }

        public @Nullable JsonElement displayName() {
            return this.displayName;
        }
    }
}

