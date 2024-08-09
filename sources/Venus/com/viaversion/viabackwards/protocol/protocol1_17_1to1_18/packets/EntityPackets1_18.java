/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.Iterator;

public final class EntityPackets1_18
extends EntityRewriter<ClientboundPackets1_18, Protocol1_17_1To1_18> {
    public EntityPackets1_18(Protocol1_17_1To1_18 protocol1_17_1To1_18) {
        super(protocol1_17_1To1_18);
    }

    @Override
    protected void registerPackets() {
        this.registerMetadataRewriter(ClientboundPackets1_18.ENTITY_METADATA, Types1_18.METADATA_LIST, Types1_17.METADATA_LIST);
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_18.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets1_18 this$0;
            {
                this.this$0 = entityPackets1_18;
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
                this.map(Type.LONG);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.read(Type.VAR_INT);
                this.handler(this.this$0.worldDataTrackerHandler(1));
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("minecraft:worldgen/biome");
                ListTag listTag = (ListTag)compoundTag2.get("value");
                Iterator iterator2 = listTag.getValue().iterator();
                while (iterator2.hasNext()) {
                    Tag tag = (Tag)iterator2.next();
                    CompoundTag compoundTag3 = (CompoundTag)((CompoundTag)tag).get("element");
                    StringTag stringTag = (StringTag)compoundTag3.get("category");
                    if (stringTag.getValue().equals("mountain")) {
                        stringTag.setValue("extreme_hills");
                    }
                    compoundTag3.put("depth", new FloatTag(0.125f));
                    compoundTag3.put("scale", new FloatTag(0.05f));
                }
                this.this$0.tracker(packetWrapper.user()).setBiomesSent(listTag.size());
            }
        });
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_18.RESPAWN, new PacketHandlers(this){
            final EntityPackets1_18 this$0;
            {
                this.this$0 = entityPackets1_18;
            }

            @Override
            public void register() {
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.handler(this.this$0.worldDataTrackerHandler(0));
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler(this::lambda$registerRewrites$0);
        this.registerMetaTypeHandler(Types1_17.META_TYPES.itemType, null, null, null, Types1_17.META_TYPES.componentType, Types1_17.META_TYPES.optionalComponentType);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_17Types.getTypeFromId(n);
    }

    private void lambda$registerRewrites$0(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metadata.setMetaType(Types1_17.META_TYPES.byId(metadata.metaType().typeId()));
        MetaType metaType = metadata.metaType();
        if (metaType == Types1_17.META_TYPES.particleType) {
            Particle particle = (Particle)metadata.getValue();
            if (particle.getId() == 3) {
                Particle.ParticleData particleData = particle.getArguments().remove(0);
                int n = (Integer)particleData.getValue();
                if (n == 7786) {
                    particle.setId(3);
                } else {
                    particle.setId(2);
                }
                return;
            }
            this.rewriteParticle(particle);
        }
    }
}

