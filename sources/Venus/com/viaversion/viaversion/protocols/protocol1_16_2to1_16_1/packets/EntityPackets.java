/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.metadata.MetadataRewriter1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;

public class EntityPackets {
    public static void register(Protocol1_16_2To1_16_1 protocol1_16_2To1_16_1) {
        MetadataRewriter1_16_2To1_16_1 metadataRewriter1_16_2To1_16_1 = protocol1_16_2To1_16_1.get(MetadataRewriter1_16_2To1_16_1.class);
        metadataRewriter1_16_2To1_16_1.registerTrackerWithData(ClientboundPackets1_16.SPAWN_ENTITY, Entity1_16_2Types.FALLING_BLOCK);
        metadataRewriter1_16_2To1_16_1.registerTracker(ClientboundPackets1_16.SPAWN_MOB);
        metadataRewriter1_16_2To1_16_1.registerTracker(ClientboundPackets1_16.SPAWN_PLAYER, Entity1_16_2Types.PLAYER);
        metadataRewriter1_16_2To1_16_1.registerMetadataRewriter(ClientboundPackets1_16.ENTITY_METADATA, Types1_16.METADATA_LIST);
        metadataRewriter1_16_2To1_16_1.registerRemoveEntities(ClientboundPackets1_16.DESTROY_ENTITIES);
        protocol1_16_2To1_16_1.registerClientbound(ClientboundPackets1_16.JOIN_GAME, new PacketHandlers(protocol1_16_2To1_16_1, metadataRewriter1_16_2To1_16_1){
            final Protocol1_16_2To1_16_1 val$protocol;
            final MetadataRewriter1_16_2To1_16_1 val$metadataRewriter;
            {
                this.val$protocol = protocol1_16_2To1_16_1;
                this.val$metadataRewriter = metadataRewriter1_16_2To1_16_1;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(1::lambda$register$0);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.handler(arg_0 -> 1.lambda$register$1(this.val$protocol, arg_0));
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map((Type)Type.UNSIGNED_BYTE, Type.VAR_INT);
                this.handler(this.val$metadataRewriter.playerTrackerHandler());
            }

            private static void lambda$register$1(Protocol1_16_2To1_16_1 protocol1_16_2To1_16_1, PacketWrapper packetWrapper) throws Exception {
                packetWrapper.read(Type.NBT);
                packetWrapper.write(Type.NBT, protocol1_16_2To1_16_1.getMappingData().getDimensionRegistry());
                String string = packetWrapper.read(Type.STRING);
                packetWrapper.write(Type.NBT, EntityPackets.getDimensionData(string));
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.read(Type.UNSIGNED_BYTE);
                packetWrapper.write(Type.BOOLEAN, (s & 8) != 0);
                s = (short)(s & 0xFFFFFFF7);
                packetWrapper.write(Type.UNSIGNED_BYTE, s);
            }
        });
        protocol1_16_2To1_16_1.registerClientbound(ClientboundPackets1_16.RESPAWN, EntityPackets::lambda$register$0);
    }

    public static CompoundTag getDimensionData(String string) {
        CompoundTag compoundTag = Protocol1_16_2To1_16_1.MAPPINGS.getDimensionDataMap().get(string);
        if (compoundTag == null) {
            Via.getPlatform().getLogger().severe("Could not get dimension data of " + string);
            throw new NullPointerException("Dimension data for " + string + " is null!");
        }
        return compoundTag.clone();
    }

    private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
        String string = packetWrapper.read(Type.STRING);
        packetWrapper.write(Type.NBT, EntityPackets.getDimensionData(string));
    }
}

