/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage.ChunkLightStorage;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;

public final class EntityPackets
extends EntityRewriter<ClientboundPackets1_17_1, Protocol1_18To1_17_1> {
    public EntityPackets(Protocol1_18To1_17_1 protocol1_18To1_17_1) {
        super(protocol1_18To1_17_1);
    }

    @Override
    public void registerPackets() {
        this.registerMetadataRewriter(ClientboundPackets1_17_1.ENTITY_METADATA, Types1_17.METADATA_LIST, Types1_18.METADATA_LIST);
        ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_17_1.JOIN_GAME, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
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
                this.handler(1::lambda$register$0);
                this.handler(this.this$0.worldDataTrackerHandler(1));
                this.handler(this.this$0.biomeSizeTracker());
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.write(Type.VAR_INT, n);
            }
        });
        ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_17_1.RESPAWN, new PacketHandlers(this){
            final EntityPackets this$0;
            {
                this.this$0 = entityPackets;
            }

            @Override
            public void register() {
                this.map(Type.NBT);
                this.map(Type.STRING);
                this.handler(this::lambda$register$0);
                this.handler(this.this$0.worldDataTrackerHandler(0));
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Object e;
                String string = packetWrapper.get(Type.STRING, 0);
                if (!string.equals((e = this.this$0.tracker(packetWrapper.user())).currentWorld())) {
                    packetWrapper.user().get(ChunkLightStorage.class).clear();
                }
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler(this::lambda$registerRewrites$0);
        this.registerMetaTypeHandler(Types1_18.META_TYPES.itemType, null, null, null);
    }

    @Override
    public EntityType typeFromId(int n) {
        return Entity1_17Types.getTypeFromId(n);
    }

    private void lambda$registerRewrites$0(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        metadata.setMetaType(Types1_18.META_TYPES.byId(metadata.metaType().typeId()));
        if (metadata.metaType() == Types1_18.META_TYPES.particleType) {
            Particle particle = (Particle)metadata.getValue();
            if (particle.getId() == 2) {
                particle.setId(3);
                particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, 7754));
            } else if (particle.getId() == 3) {
                particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, 7786));
            } else {
                this.rewriteParticle(particle);
            }
        }
    }
}

