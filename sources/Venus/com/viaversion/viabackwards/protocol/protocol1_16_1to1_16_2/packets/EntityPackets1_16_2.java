/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;

import com.google.common.collect.Sets;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage.BiomeStorage;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets;
import java.util.Set;

public class EntityPackets1_16_2
extends EntityRewriter<ClientboundPackets1_16_2, Protocol1_16_1To1_16_2> {
    private final Set<String> oldDimensions = Sets.newHashSet("minecraft:overworld", "minecraft:the_nether", "minecraft:the_end");
    private boolean warned;

    public EntityPackets1_16_2(Protocol1_16_1To1_16_2 protocol1_16_1To1_16_2) {
        super(protocol1_16_1To1_16_2);
    }

    @Override
    protected void registerPackets() {
        this.registerTrackerWithData(ClientboundPackets1_16_2.SPAWN_ENTITY, Entity1_16_2Types.FALLING_BLOCK);
        this.registerSpawnTracker(ClientboundPackets1_16_2.SPAWN_MOB);
        this.registerTracker(ClientboundPackets1_16_2.SPAWN_EXPERIENCE_ORB, Entity1_16_2Types.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_16_2.SPAWN_PAINTING, Entity1_16_2Types.PAINTING);
        this.registerTracker(ClientboundPackets1_16_2.SPAWN_PLAYER, Entity1_16_2Types.PLAYER);
        this.registerRemoveEntities(ClientboundPackets1_16_2.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_16_2.ENTITY_METADATA, Types1_16.METADATA_LIST);
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16_2.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets1_16_2 this$0;
            {
                this.this$0 = entityPackets1_16_2;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(1::lambda$register$0);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.handler(this::lambda$register$1);
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.handler(1::lambda$register$2);
                this.handler(EntityPackets1_16_2.access$000(this.this$0, Entity1_16_2Types.PLAYER, Type.INT));
            }

            private static void lambda$register$2(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                packetWrapper.write(Type.UNSIGNED_BYTE, (short)Math.min(n, 255));
            }

            private void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                CompoundTag compoundTag;
                CompoundTag compoundTag2 = packetWrapper.read(Type.NBT);
                if (packetWrapper.user().getProtocolInfo().getProtocolVersion() <= ProtocolVersion.v1_15_2.getVersion()) {
                    compoundTag = (CompoundTag)compoundTag2.get("minecraft:worldgen/biome");
                    ListTag listTag = (ListTag)compoundTag.get("value");
                    BiomeStorage biomeStorage = packetWrapper.user().get(BiomeStorage.class);
                    biomeStorage.clear();
                    for (Tag tag : listTag) {
                        CompoundTag compoundTag3 = (CompoundTag)tag;
                        StringTag stringTag = (StringTag)compoundTag3.get("name");
                        NumberTag numberTag = (NumberTag)compoundTag3.get("id");
                        biomeStorage.addBiome(stringTag.getValue(), numberTag.asInt());
                    }
                } else if (!EntityPackets1_16_2.access$100(this.this$0)) {
                    EntityPackets1_16_2.access$102(this.this$0, true);
                    ViaBackwards.getPlatform().getLogger().warning("1.16 and 1.16.1 clients are only partially supported and may have wrong biomes displayed.");
                }
                packetWrapper.write(Type.NBT, EntityPackets.DIMENSIONS_TAG);
                compoundTag = packetWrapper.read(Type.NBT);
                packetWrapper.write(Type.STRING, EntityPackets1_16_2.access$200(this.this$0, compoundTag));
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                boolean bl = packetWrapper.read(Type.BOOLEAN);
                short s = packetWrapper.read(Type.UNSIGNED_BYTE);
                if (bl) {
                    s = (short)(s | 8);
                }
                packetWrapper.write(Type.UNSIGNED_BYTE, s);
            }
        });
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16_2.RESPAWN, this::lambda$registerPackets$0);
    }

    private String getDimensionFromData(CompoundTag compoundTag) {
        StringTag stringTag = (StringTag)compoundTag.get("effects");
        return stringTag != null && this.oldDimensions.contains(stringTag.getValue()) ? stringTag.getValue() : "minecraft:overworld";
    }

    @Override
    protected void registerRewrites() {
        this.registerMetaTypeHandler(Types1_16.META_TYPES.itemType, Types1_16.META_TYPES.blockStateType, null, Types1_16.META_TYPES.particleType, Types1_16.META_TYPES.componentType, Types1_16.META_TYPES.optionalComponentType);
        this.mapTypes(Entity1_16_2Types.values(), Entity1_16Types.class);
        this.mapEntityTypeWithData(Entity1_16_2Types.PIGLIN_BRUTE, Entity1_16_2Types.PIGLIN).jsonName();
        this.filter().filterFamily(Entity1_16_2Types.ABSTRACT_PIGLIN).index(15).toIndex(16);
        this.filter().filterFamily(Entity1_16_2Types.ABSTRACT_PIGLIN).index(16).toIndex(15);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_16_2Types.getTypeFromId(n);
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        CompoundTag compoundTag = packetWrapper.read(Type.NBT);
        packetWrapper.write(Type.STRING, this.getDimensionFromData(compoundTag));
    }

    static PacketHandler access$000(EntityPackets1_16_2 entityPackets1_16_2, EntityType entityType, Type type) {
        return entityPackets1_16_2.getTrackerHandler(entityType, type);
    }

    static boolean access$100(EntityPackets1_16_2 entityPackets1_16_2) {
        return entityPackets1_16_2.warned;
    }

    static boolean access$102(EntityPackets1_16_2 entityPackets1_16_2, boolean bl) {
        entityPackets1_16_2.warned = bl;
        return entityPackets1_16_2.warned;
    }

    static String access$200(EntityPackets1_16_2 entityPackets1_16_2, CompoundTag compoundTag) {
        return entityPackets1_16_2.getDimensionFromData(compoundTag);
    }
}

