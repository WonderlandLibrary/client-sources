/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.Protocol1_13_1To1_13_2;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;

public class EntityPackets1_13_2 {
    public static void register(Protocol1_13_1To1_13_2 protocol) {
        protocol.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketHandlers(){

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
                this.map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
                this.handler(wrapper -> {
                    for (Metadata metadata : wrapper.get(Types1_13.METADATA_LIST, 0)) {
                        metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
                this.handler(wrapper -> {
                    for (Metadata metadata : wrapper.get(Types1_13.METADATA_LIST, 0)) {
                        metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.ENTITY_METADATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
                this.handler(wrapper -> {
                    for (Metadata metadata : wrapper.get(Types1_13.METADATA_LIST, 0)) {
                        metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
                    }
                });
            }
        });
    }
}

