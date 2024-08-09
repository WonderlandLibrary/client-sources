/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;

public final class EntityPackets1_17
extends EntityRewriter<ClientboundPackets1_17, Protocol1_16_4To1_17> {
    private boolean warned;

    public EntityPackets1_17(Protocol1_16_4To1_17 protocol1_16_4To1_17) {
        super(protocol1_16_4To1_17);
    }

    @Override
    protected void registerPackets() {
        this.registerTrackerWithData(ClientboundPackets1_17.SPAWN_ENTITY, Entity1_17Types.FALLING_BLOCK);
        this.registerSpawnTracker(ClientboundPackets1_17.SPAWN_MOB);
        this.registerTracker(ClientboundPackets1_17.SPAWN_EXPERIENCE_ORB, Entity1_17Types.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_17.SPAWN_PAINTING, Entity1_17Types.PAINTING);
        this.registerTracker(ClientboundPackets1_17.SPAWN_PLAYER, Entity1_17Types.PLAYER);
        this.registerMetadataRewriter(ClientboundPackets1_17.ENTITY_METADATA, Types1_17.METADATA_LIST, Types1_16.METADATA_LIST);
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.REMOVE_ENTITY, ClientboundPackets1_16_2.DESTROY_ENTITIES, this::lambda$registerPackets$0);
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets1_17 this$0;
            {
                this.this$0 = entityPackets1_17;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.handler(1::lambda$register$0);
                this.handler(EntityPackets1_17.access$000(this.this$0, Entity1_17Types.PLAYER, Type.INT));
                this.handler(this.this$0.worldDataTrackerHandler(1));
                this.handler(this::lambda$register$1);
            }

            private void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                Tag tag;
                Object object;
                Tag tag22;
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("minecraft:worldgen/biome");
                ListTag listTag = (ListTag)compoundTag2.get("value");
                for (Tag tag22 : listTag) {
                    object = (CompoundTag)((CompoundTag)tag22).get("element");
                    tag = (StringTag)((CompoundTag)object).get("category");
                    if (!((StringTag)tag).getValue().equalsIgnoreCase("underground")) continue;
                    ((StringTag)tag).setValue("none");
                }
                CompoundTag compoundTag3 = (CompoundTag)compoundTag.get("minecraft:dimension_type");
                tag22 = (ListTag)compoundTag3.get("value");
                object = ((ListTag)tag22).iterator();
                while (object.hasNext()) {
                    tag = (Tag)object.next();
                    CompoundTag compoundTag4 = (CompoundTag)((CompoundTag)tag).get("element");
                    EntityPackets1_17.access$100(this.this$0, compoundTag4, false);
                }
                EntityPackets1_17.access$100(this.this$0, packetWrapper.get(Type.NBT, 1), true);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by = packetWrapper.get(Type.BYTE, 0);
                if (by == -1) {
                    packetWrapper.set(Type.BYTE, 0, (byte)0);
                }
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.RESPAWN, new PacketHandlers(this){
            final EntityPackets1_17 this$0;
            {
                this.this$0 = entityPackets1_17;
            }

            @Override
            public void register() {
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.handler(this.this$0.worldDataTrackerHandler(0));
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                EntityPackets1_17.access$100(this.this$0, packetWrapper.get(Type.NBT, 0), true);
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.PLAYER_POSITION, new PacketHandlers(this){
            final EntityPackets1_17 this$0;
            {
                this.this$0 = entityPackets1_17;
            }

            @Override
            public void register() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.read(Type.BOOLEAN);
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.ENTITY_PROPERTIES, new PacketHandlers(this){
            final EntityPackets1_17 this$0;
            {
                this.this$0 = entityPackets1_17;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.INT, packetWrapper.read(Type.VAR_INT));
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_ENTER, ClientboundPackets1_16_2.COMBAT_EVENT, 0);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_END, ClientboundPackets1_16_2.COMBAT_EVENT, 1);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_KILL, ClientboundPackets1_16_2.COMBAT_EVENT, 2);
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler(this::lambda$registerRewrites$1);
        this.registerMetaTypeHandler(Types1_16.META_TYPES.itemType, Types1_16.META_TYPES.blockStateType, null, null, Types1_16.META_TYPES.componentType, Types1_16.META_TYPES.optionalComponentType);
        this.mapTypes(Entity1_17Types.values(), Entity1_16_2Types.class);
        this.filter().type(Entity1_17Types.AXOLOTL).cancel(17);
        this.filter().type(Entity1_17Types.AXOLOTL).cancel(18);
        this.filter().type(Entity1_17Types.AXOLOTL).cancel(19);
        this.filter().type(Entity1_17Types.GLOW_SQUID).cancel(16);
        this.filter().type(Entity1_17Types.GOAT).cancel(17);
        this.mapEntityTypeWithData(Entity1_17Types.AXOLOTL, Entity1_17Types.TROPICAL_FISH).jsonName();
        this.mapEntityTypeWithData(Entity1_17Types.GOAT, Entity1_17Types.SHEEP).jsonName();
        this.mapEntityTypeWithData(Entity1_17Types.GLOW_SQUID, Entity1_17Types.SQUID).jsonName();
        this.mapEntityTypeWithData(Entity1_17Types.GLOW_ITEM_FRAME, Entity1_17Types.ITEM_FRAME);
        this.filter().type(Entity1_17Types.SHULKER).addIndex(17);
        this.filter().removeIndex(7);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_17Types.getTypeFromId(n);
    }

    private void reduceExtendedHeight(CompoundTag compoundTag, boolean bl) {
        IntTag intTag = (IntTag)compoundTag.get("min_y");
        IntTag intTag2 = (IntTag)compoundTag.get("height");
        NumberTag numberTag = (NumberTag)compoundTag.get("logical_height");
        if (intTag.asInt() != 0 || intTag2.asInt() > 256 || numberTag.asInt() > 256) {
            if (bl && !this.warned) {
                ViaBackwards.getPlatform().getLogger().warning("Custom worlds heights are NOT SUPPORTED for 1.16 players and older and may lead to errors!");
                ViaBackwards.getPlatform().getLogger().warning("You have min/max set to " + intTag.asInt() + "/" + intTag2.asInt());
                this.warned = true;
            }
            intTag2.setValue(Math.min(256, intTag2.asInt()));
            if (numberTag instanceof ByteTag) {
                ((ByteTag)numberTag).setValue((byte)Math.min(256, numberTag.asInt()));
            } else {
                ((IntTag)numberTag).setValue(Math.min(256, numberTag.asInt()));
            }
        }
    }

    private void lambda$registerRewrites$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metadata.setMetaType(Types1_16.META_TYPES.byId(metadata.metaType().typeId()));
        MetaType metaType = metadata.metaType();
        if (metaType == Types1_16.META_TYPES.particleType) {
            Particle particle = (Particle)metadata.getValue();
            if (particle.getId() == 16) {
                particle.getArguments().subList(4, 7).clear();
            } else if (particle.getId() == 37) {
                particle.setId(0);
                particle.getArguments().clear();
                return;
            }
            this.rewriteParticle(particle);
        } else if (metaType == Types1_16.META_TYPES.poseType) {
            int n = (Integer)metadata.value();
            if (n == 6) {
                metadata.setValue(1);
            } else if (n > 6) {
                metadata.setValue(n - 1);
            }
        }
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        this.tracker(packetWrapper.user()).removeEntity(n);
        int[] nArray = new int[]{n};
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, nArray);
    }

    static PacketHandler access$000(EntityPackets1_17 entityPackets1_17, EntityType entityType, Type type) {
        return entityPackets1_17.getTrackerHandler(entityType, type);
    }

    static void access$100(EntityPackets1_17 entityPackets1_17, CompoundTag compoundTag, boolean bl) {
        entityPackets1_17.reduceExtendedHeight(compoundTag, bl);
    }
}

