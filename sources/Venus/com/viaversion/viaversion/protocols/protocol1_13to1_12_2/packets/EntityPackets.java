/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata.MetadataRewriter1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

public class EntityPackets {
    public static void register(Protocol1_13To1_12_2 protocol1_13To1_12_2) {
        MetadataRewriter1_13To1_12_2 metadataRewriter1_13To1_12_2 = protocol1_13To1_12_2.get(MetadataRewriter1_13To1_12_2.class);
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.SPAWN_ENTITY, new PacketHandlers(){

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
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n;
                int n2 = packetWrapper.get(Type.VAR_INT, 0);
                byte by = packetWrapper.get(Type.BYTE, 0);
                Entity1_13Types.EntityType entityType = Entity1_13Types.getTypeFromId(by, true);
                if (entityType == null) {
                    return;
                }
                packetWrapper.user().getEntityTracker(Protocol1_13To1_12_2.class).addEntity(n2, entityType);
                if (entityType.is((EntityType)Entity1_13Types.EntityType.FALLING_BLOCK)) {
                    n = packetWrapper.get(Type.INT, 0);
                    int n3 = (n & 0xFFF) << 4 | n >> 12 & 0xF;
                    packetWrapper.set(Type.INT, 0, WorldPackets.toNewId(n3));
                }
                if (entityType.is((EntityType)Entity1_13Types.EntityType.ITEM_FRAME)) {
                    n = packetWrapper.get(Type.INT, 0);
                    switch (n) {
                        case 0: {
                            n = 3;
                            break;
                        }
                        case 1: {
                            n = 4;
                            break;
                        }
                        case 3: {
                            n = 5;
                        }
                    }
                    packetWrapper.set(Type.INT, 0, n);
                }
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.SPAWN_MOB, new PacketHandlers(metadataRewriter1_13To1_12_2){
            final MetadataRewriter1_13To1_12_2 val$metadataRewriter;
            {
                this.val$metadataRewriter = metadataRewriter1_13To1_12_2;
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
                this.map(Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST));
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.SPAWN_PLAYER, new PacketHandlers(metadataRewriter1_13To1_12_2){
            final MetadataRewriter1_13To1_12_2 val$metadataRewriter;
            {
                this.val$metadataRewriter = metadataRewriter1_13To1_12_2;
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
                this.map(Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.JOIN_GAME, new PacketHandlers(metadataRewriter1_13To1_12_2){
            final MetadataRewriter1_13To1_12_2 val$metadataRewriter;
            {
                this.val$metadataRewriter = metadataRewriter1_13To1_12_2;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(4::lambda$register$0);
                this.handler(this.val$metadataRewriter.playerTrackerHandler());
                this.handler(Protocol1_13To1_12_2.SEND_DECLARE_COMMANDS_AND_TAGS);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 1);
                clientWorld.setEnvironment(n);
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.ENTITY_EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by = packetWrapper.read(Type.BYTE);
                if (Via.getConfig().isNewEffectIndicator()) {
                    by = (byte)(by | 4);
                }
                packetWrapper.write(Type.BYTE, by);
            }
        });
        metadataRewriter1_13To1_12_2.registerRemoveEntities(ClientboundPackets1_12_1.DESTROY_ENTITIES);
        metadataRewriter1_13To1_12_2.registerMetadataRewriter(ClientboundPackets1_12_1.ENTITY_METADATA, Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
    }
}

