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
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
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
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
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

    public EntityPackets1_19_3(Protocol1_19_1To1_19_3 protocol1_19_1To1_19_3) {
        super(protocol1_19_1To1_19_3, Types1_19.META_TYPES.optionalComponentType, Types1_19.META_TYPES.booleanType);
    }

    @Override
    protected void registerPackets() {
        this.registerMetadataRewriter(ClientboundPackets1_19_3.ENTITY_METADATA, Types1_19_3.METADATA_LIST, Types1_19.METADATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19_3.REMOVE_ENTITIES);
        this.registerTrackerWithData1_19(ClientboundPackets1_19_3.SPAWN_ENTITY, Entity1_19_3Types.FALLING_BLOCK);
        ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets1_19_3 this$0;
            {
                this.this$0 = entityPackets1_19_3;
            }

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
                this.handler(this.this$0.dimensionDataHandler());
                this.handler(this.this$0.biomeSizeTracker());
                this.handler(this.this$0.worldDataTrackerHandlerByKey());
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ChatTypeStorage1_19_3 chatTypeStorage1_19_3 = packetWrapper.user().get(ChatTypeStorage1_19_3.class);
                chatTypeStorage1_19_3.clear();
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                ListTag listTag = (ListTag)((CompoundTag)compoundTag.get("minecraft:chat_type")).get("value");
                for (Tag tag : listTag) {
                    CompoundTag compoundTag2 = (CompoundTag)tag;
                    NumberTag numberTag = (NumberTag)compoundTag2.get("id");
                    chatTypeStorage1_19_3.addChatType(numberTag.asInt(), compoundTag2);
                }
            }
        });
        ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.RESPAWN, new PacketHandlers(this){
            final EntityPackets1_19_3 this$0;
            {
                this.this$0 = entityPackets1_19_3;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.handler(this.this$0.worldDataTrackerHandlerByKey());
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by = packetWrapper.read(Type.BYTE);
                packetWrapper.write(Type.BOOLEAN, (by & 1) != 0);
            }
        });
        ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.PLAYER_INFO_UPDATE, ClientboundPackets1_19_1.PLAYER_INFO, this::lambda$registerPackets$0);
        ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.PLAYER_INFO_REMOVE, ClientboundPackets1_19_1.PLAYER_INFO, EntityPackets1_19_3::lambda$registerPackets$1);
    }

    private void sendPlayerProfileUpdate(UserConnection userConnection, int n, PlayerProfileUpdate[] playerProfileUpdateArray) throws Exception {
        PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_19_1.PLAYER_INFO, userConnection);
        packetWrapper.write(Type.VAR_INT, n);
        packetWrapper.write(Type.VAR_INT, playerProfileUpdateArray.length);
        for (PlayerProfileUpdate playerProfileUpdate : playerProfileUpdateArray) {
            packetWrapper.write(Type.UUID, playerProfileUpdate.uuid());
            if (n == 1) {
                packetWrapper.write(Type.VAR_INT, playerProfileUpdate.gamemode());
                continue;
            }
            if (n == 2) {
                packetWrapper.write(Type.VAR_INT, playerProfileUpdate.latency());
                continue;
            }
            if (n == 3) {
                packetWrapper.write(Type.OPTIONAL_COMPONENT, playerProfileUpdate.displayName());
                continue;
            }
            throw new IllegalArgumentException("Invalid action: " + n);
        }
        packetWrapper.send(Protocol1_19_1To1_19_3.class);
    }

    @Override
    public void registerRewrites() {
        this.filter().handler(EntityPackets1_19_3::lambda$registerRewrites$2);
        this.registerMetaTypeHandler(Types1_19.META_TYPES.itemType, Types1_19.META_TYPES.blockStateType, null, Types1_19.META_TYPES.particleType, Types1_19.META_TYPES.componentType, Types1_19.META_TYPES.optionalComponentType);
        this.filter().index(6).handler(EntityPackets1_19_3::lambda$registerRewrites$3);
        this.filter().filterFamily(Entity1_19_3Types.MINECART_ABSTRACT).index(11).handler(this::lambda$registerRewrites$4);
        this.filter().type(Entity1_19_3Types.CAMEL).cancel(19);
        this.filter().type(Entity1_19_3Types.CAMEL).cancel(20);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        this.mapEntityTypeWithData(Entity1_19_3Types.CAMEL, Entity1_19_3Types.DONKEY).jsonName();
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_19_3Types.getTypeFromId(n);
    }

    private void lambda$registerRewrites$4(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.getValue();
        metadata.setValue(((Protocol1_19_1To1_19_3)this.protocol).getMappingData().getNewBlockStateId(n));
    }

    private static void lambda$registerRewrites$3(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = (Integer)metadata.value();
        if (n == 10) {
            metadata.setValue(0);
        } else if (n > 10) {
            metadata.setValue(n - 1);
        }
    }

    private static void lambda$registerRewrites$2(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        int n = metadata.metaType().typeId();
        if (n > 2) {
            metadata.setMetaType(Types1_19.META_TYPES.byId(n - 1));
        } else if (n != 2) {
            metadata.setMetaType(Types1_19.META_TYPES.byId(n));
        }
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        UUID[] uUIDArray = packetWrapper.read(Type.UUID_ARRAY);
        packetWrapper.write(Type.VAR_INT, 4);
        packetWrapper.write(Type.VAR_INT, uUIDArray.length);
        for (UUID uUID : uUIDArray) {
            packetWrapper.write(Type.UUID, uUID);
        }
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.cancel();
        BitSet bitSet = packetWrapper.read(PROFILE_ACTIONS_ENUM_TYPE);
        int n = packetWrapper.read(Type.VAR_INT);
        if (bitSet.get(1)) {
            PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_19_1.PLAYER_INFO);
            packetWrapper2.write(Type.VAR_INT, 0);
            packetWrapper2.write(Type.VAR_INT, n);
            for (int i = 0; i < n; ++i) {
                int n2;
                ProfileKey profileKey;
                packetWrapper2.write(Type.UUID, packetWrapper.read(Type.UUID));
                packetWrapper2.write(Type.STRING, packetWrapper.read(Type.STRING));
                int n3 = packetWrapper.read(Type.VAR_INT);
                packetWrapper2.write(Type.VAR_INT, n3);
                for (int j = 0; j < n3; ++j) {
                    packetWrapper2.write(Type.STRING, packetWrapper.read(Type.STRING));
                    packetWrapper2.write(Type.STRING, packetWrapper.read(Type.STRING));
                    packetWrapper2.write(Type.OPTIONAL_STRING, packetWrapper.read(Type.OPTIONAL_STRING));
                }
                if (bitSet.get(0) && packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                    packetWrapper.read(Type.UUID);
                    profileKey = packetWrapper.read(Type.PROFILE_KEY);
                } else {
                    profileKey = null;
                }
                int n4 = n2 = bitSet.get(1) ? packetWrapper.read(Type.VAR_INT) : 0;
                if (bitSet.get(0)) {
                    packetWrapper.read(Type.BOOLEAN);
                }
                int n5 = bitSet.get(1) ? packetWrapper.read(Type.VAR_INT) : 0;
                JsonElement jsonElement = bitSet.get(0) ? packetWrapper.read(Type.OPTIONAL_COMPONENT) : null;
                packetWrapper2.write(Type.VAR_INT, n2);
                packetWrapper2.write(Type.VAR_INT, n5);
                packetWrapper2.write(Type.OPTIONAL_COMPONENT, jsonElement);
                packetWrapper2.write(Type.OPTIONAL_PROFILE_KEY, profileKey);
            }
            packetWrapper2.send(Protocol1_19_1To1_19_3.class);
            return;
        }
        PlayerProfileUpdate[] playerProfileUpdateArray = new PlayerProfileUpdate[n];
        for (int i = 0; i < n; ++i) {
            UUID uUID = packetWrapper.read(Type.UUID);
            int n6 = 0;
            int n7 = 0;
            JsonElement jsonElement = null;
            block8: for (int n8 : PROFILE_ACTIONS) {
                if (!bitSet.get(n8)) continue;
                switch (n8) {
                    case 2: {
                        n6 = packetWrapper.read(Type.VAR_INT);
                        continue block8;
                    }
                    case 4: {
                        n7 = packetWrapper.read(Type.VAR_INT);
                        continue block8;
                    }
                    case 5: {
                        jsonElement = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                    }
                }
            }
            playerProfileUpdateArray[i] = new PlayerProfileUpdate(uUID, n6, n7, jsonElement, null);
        }
        if (bitSet.get(1)) {
            this.sendPlayerProfileUpdate(packetWrapper.user(), 1, playerProfileUpdateArray);
        } else if (bitSet.get(1)) {
            this.sendPlayerProfileUpdate(packetWrapper.user(), 2, playerProfileUpdateArray);
        } else if (bitSet.get(0)) {
            this.sendPlayerProfileUpdate(packetWrapper.user(), 3, playerProfileUpdateArray);
        }
    }

    private static final class PlayerProfileUpdate {
        private final UUID uuid;
        private final int gamemode;
        private final int latency;
        private final JsonElement displayName;

        private PlayerProfileUpdate(UUID uUID, int n, int n2, @Nullable JsonElement jsonElement) {
            this.uuid = uUID;
            this.gamemode = n;
            this.latency = n2;
            this.displayName = jsonElement;
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

        PlayerProfileUpdate(UUID uUID, int n, int n2, JsonElement jsonElement, 1 var5_5) {
            this(uUID, n, n2, jsonElement);
        }
    }
}

