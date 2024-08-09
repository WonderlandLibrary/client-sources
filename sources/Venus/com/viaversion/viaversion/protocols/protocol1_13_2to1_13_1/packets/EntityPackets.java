/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;

public class EntityPackets {
    public static void register(Protocol protocol) {
        PacketHandler packetHandler = EntityPackets::lambda$register$0;
        protocol.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketHandlers(packetHandler){
            final PacketHandler val$metaTypeHandler;
            {
                this.val$metaTypeHandler = packetHandler;
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
                this.map(Types1_13.METADATA_LIST, Types1_13_2.METADATA_LIST);
                this.handler(this.val$metaTypeHandler);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketHandlers(packetHandler){
            final PacketHandler val$metaTypeHandler;
            {
                this.val$metaTypeHandler = packetHandler;
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
                this.map(Types1_13.METADATA_LIST, Types1_13_2.METADATA_LIST);
                this.handler(this.val$metaTypeHandler);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.ENTITY_METADATA, new PacketHandlers(packetHandler){
            final PacketHandler val$metaTypeHandler;
            {
                this.val$metaTypeHandler = packetHandler;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Types1_13.METADATA_LIST, Types1_13_2.METADATA_LIST);
                this.handler(this.val$metaTypeHandler);
            }
        });
    }

    private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
        for (Metadata metadata : packetWrapper.get(Types1_13_2.METADATA_LIST, 0)) {
            metadata.setMetaType(Types1_13_2.META_TYPES.byId(metadata.metaType().typeId()));
        }
    }
}

