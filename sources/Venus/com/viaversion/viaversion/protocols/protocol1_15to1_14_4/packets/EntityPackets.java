/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata.MetadataRewriter1_15To1_14_4;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public final class EntityPackets {
    public static void register(Protocol1_15To1_14_4 protocol1_15To1_14_4) {
        MetadataRewriter1_15To1_14_4 metadataRewriter1_15To1_14_4 = protocol1_15To1_14_4.get(MetadataRewriter1_15To1_14_4.class);
        metadataRewriter1_15To1_14_4.registerTrackerWithData(ClientboundPackets1_14_4.SPAWN_ENTITY, Entity1_15Types.FALLING_BLOCK);
        protocol1_15To1_14_4.registerClientbound(ClientboundPackets1_14_4.SPAWN_MOB, new PacketHandlers(metadataRewriter1_15To1_14_4){
            final MetadataRewriter1_15To1_14_4 val$metadataRewriter;
            {
                this.val$metadataRewriter = metadataRewriter1_15To1_14_4;
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
                this.handler(this.val$metadataRewriter.trackerHandler());
                this.handler(arg_0 -> 1.lambda$register$0(this.val$metadataRewriter, arg_0));
            }

            private static void lambda$register$0(MetadataRewriter1_15To1_14_4 metadataRewriter1_15To1_14_4, PacketWrapper packetWrapper) throws Exception {
                EntityPackets.access$000(packetWrapper, packetWrapper.get(Type.VAR_INT, 0), metadataRewriter1_15To1_14_4);
            }
        });
        protocol1_15To1_14_4.registerClientbound(ClientboundPackets1_14_4.SPAWN_PLAYER, new PacketHandlers(metadataRewriter1_15To1_14_4){
            final MetadataRewriter1_15To1_14_4 val$metadataRewriter;
            {
                this.val$metadataRewriter = metadataRewriter1_15To1_14_4;
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
                this.handler(arg_0 -> 2.lambda$register$0(this.val$metadataRewriter, arg_0));
            }

            private static void lambda$register$0(MetadataRewriter1_15To1_14_4 metadataRewriter1_15To1_14_4, PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                packetWrapper.user().getEntityTracker(Protocol1_15To1_14_4.class).addEntity(n, Entity1_15Types.PLAYER);
                EntityPackets.access$000(packetWrapper, n, metadataRewriter1_15To1_14_4);
            }
        });
        protocol1_15To1_14_4.registerClientbound(ClientboundPackets1_14_4.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.LONG, 0L);
            }
        });
        protocol1_15To1_14_4.registerClientbound(ClientboundPackets1_14_4.JOIN_GAME, new PacketHandlers(metadataRewriter1_15To1_14_4){
            final MetadataRewriter1_15To1_14_4 val$metadataRewriter;
            {
                this.val$metadataRewriter = metadataRewriter1_15To1_14_4;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(this.val$metadataRewriter.playerTrackerHandler());
                this.handler(4::lambda$register$0);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.handler(4::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, !Via.getConfig().is1_15InstantRespawn());
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.LONG, 0L);
            }
        });
        metadataRewriter1_15To1_14_4.registerMetadataRewriter(ClientboundPackets1_14_4.ENTITY_METADATA, Types1_14.METADATA_LIST);
        metadataRewriter1_15To1_14_4.registerRemoveEntities(ClientboundPackets1_14_4.DESTROY_ENTITIES);
    }

    private static void sendMetadataPacket(PacketWrapper packetWrapper, int n, EntityRewriter<?, ?> entityRewriter) throws Exception {
        List<Metadata> list = packetWrapper.read(Types1_14.METADATA_LIST);
        if (list.isEmpty()) {
            return;
        }
        packetWrapper.send(Protocol1_15To1_14_4.class);
        packetWrapper.cancel();
        entityRewriter.handleMetadata(n, list, packetWrapper.user());
        PacketWrapper packetWrapper2 = PacketWrapper.create(ClientboundPackets1_15.ENTITY_METADATA, packetWrapper.user());
        packetWrapper2.write(Type.VAR_INT, n);
        packetWrapper2.write(Types1_14.METADATA_LIST, list);
        packetWrapper2.send(Protocol1_15To1_14_4.class);
    }

    public static int getNewEntityId(int n) {
        return n >= 4 ? n + 1 : n;
    }

    static void access$000(PacketWrapper packetWrapper, int n, EntityRewriter entityRewriter) throws Exception {
        EntityPackets.sendMetadataPacket(packetWrapper, n, entityRewriter);
    }
}

