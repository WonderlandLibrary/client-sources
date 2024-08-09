/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata.MetadataRewriter1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;

public class EntityPackets {
    public static void register(Protocol1_13_1To1_13 protocol1_13_1To1_13) {
        MetadataRewriter1_13_1To1_13 metadataRewriter1_13_1To1_13 = protocol1_13_1To1_13.get(MetadataRewriter1_13_1To1_13.class);
        protocol1_13_1To1_13.registerClientbound(ClientboundPackets1_13.SPAWN_ENTITY, new PacketHandlers(protocol1_13_1To1_13){
            final Protocol1_13_1To1_13 val$protocol;
            {
                this.val$protocol = protocol1_13_1To1_13;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(arg_0 -> 1.lambda$register$0(this.val$protocol, arg_0));
            }

            private static void lambda$register$0(Protocol1_13_1To1_13 protocol1_13_1To1_13, PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                byte by = packetWrapper.get(Type.BYTE, 0);
                Entity1_13Types.EntityType entityType = Entity1_13Types.getTypeFromId(by, true);
                if (entityType != null) {
                    if (entityType.is((EntityType)Entity1_13Types.EntityType.FALLING_BLOCK)) {
                        int n2 = packetWrapper.get(Type.INT, 0);
                        packetWrapper.set(Type.INT, 0, protocol1_13_1To1_13.getMappingData().getNewBlockStateId(n2));
                    }
                    packetWrapper.user().getEntityTracker(Protocol1_13_1To1_13.class).addEntity(n, entityType);
                }
            }
        });
        protocol1_13_1To1_13.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketHandlers(metadataRewriter1_13_1To1_13){
            final MetadataRewriter1_13_1To1_13 val$metadataRewriter;
            {
                this.val$metadataRewriter = metadataRewriter1_13_1To1_13;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_13.METADATA_LIST);
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST));
            }
        });
        protocol1_13_1To1_13.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketHandlers(metadataRewriter1_13_1To1_13){
            final MetadataRewriter1_13_1To1_13 val$metadataRewriter;
            {
                this.val$metadataRewriter = metadataRewriter1_13_1To1_13;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_13.METADATA_LIST);
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        metadataRewriter1_13_1To1_13.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
        metadataRewriter1_13_1To1_13.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST);
    }
}

