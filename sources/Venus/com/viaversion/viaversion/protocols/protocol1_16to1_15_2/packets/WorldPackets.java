/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import java.util.Map;
import java.util.UUID;

public class WorldPackets {
    public static void register(Protocol1_16To1_15_2 protocol1_16To1_15_2) {
        BlockRewriter<ClientboundPackets1_15> blockRewriter = new BlockRewriter<ClientboundPackets1_15>(protocol1_16To1_15_2, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_15.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_15.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_15.MULTI_BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_15.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.UPDATE_LIGHT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, true);
            }
        });
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.CHUNK_DATA, arg_0 -> WorldPackets.lambda$register$2(protocol1_16To1_15_2, arg_0));
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.BLOCK_ENTITY_DATA, arg_0 -> WorldPackets.lambda$register$3(protocol1_16To1_15_2, arg_0));
        blockRewriter.registerEffect(ClientboundPackets1_15.EFFECT, 1010, 2001);
    }

    private static void handleBlockEntity(Protocol1_16To1_15_2 protocol1_16To1_15_2, CompoundTag compoundTag) {
        StringTag stringTag;
        Object t;
        Object t2;
        StringTag stringTag2 = (StringTag)compoundTag.get("id");
        if (stringTag2 == null) {
            return;
        }
        String string = stringTag2.getValue();
        if (string.equals("minecraft:conduit")) {
            Object t3 = compoundTag.remove("target_uuid");
            if (!(t3 instanceof StringTag)) {
                return;
            }
            UUID uUID = UUID.fromString((String)((Tag)t3).getValue());
            compoundTag.put("Target", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(uUID)));
        } else if (string.equals("minecraft:skull") && compoundTag.get("Owner") instanceof CompoundTag) {
            Object object;
            CompoundTag compoundTag2 = (CompoundTag)compoundTag.remove("Owner");
            StringTag stringTag3 = (StringTag)compoundTag2.remove("Id");
            if (stringTag3 != null) {
                object = UUID.fromString(stringTag3.getValue());
                compoundTag2.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray((UUID)object)));
            }
            object = new CompoundTag();
            for (Map.Entry<String, Tag> entry : compoundTag2.entrySet()) {
                ((CompoundTag)object).put(entry.getKey(), entry.getValue());
            }
            compoundTag.put("SkullOwner", object);
        } else if (string.equals("minecraft:sign")) {
            for (int i = 1; i <= 4; ++i) {
                Object t4 = compoundTag.get("Text" + i);
                if (!(t4 instanceof StringTag)) continue;
                JsonElement jsonElement = protocol1_16To1_15_2.getComponentRewriter().processText(((StringTag)t4).getValue());
                compoundTag.put("Text" + i, new StringTag(jsonElement.toString()));
            }
        } else if (string.equals("minecraft:mob_spawner") && (t2 = compoundTag.get("SpawnData")) instanceof CompoundTag && (t = ((CompoundTag)t2).get("id")) instanceof StringTag && (stringTag = (StringTag)t).getValue().equals("minecraft:zombie_pigman")) {
            stringTag.setValue("minecraft:zombified_piglin");
        }
    }

    private static void lambda$register$3(Protocol1_16To1_15_2 protocol1_16To1_15_2, PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.POSITION1_14);
        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        CompoundTag compoundTag = packetWrapper.passthrough(Type.NBT);
        WorldPackets.handleBlockEntity(protocol1_16To1_15_2, compoundTag);
    }

    private static void lambda$register$2(Protocol1_16To1_15_2 protocol1_16To1_15_2, PacketWrapper packetWrapper) throws Exception {
        Chunk chunk = packetWrapper.read(new Chunk1_15Type());
        packetWrapper.write(new Chunk1_16Type(), chunk);
        chunk.setIgnoreOldLightData(chunk.isFullChunk());
        for (int i = 0; i < chunk.getSections().length; ++i) {
            ChunkSection chunkSection = chunk.getSections()[i];
            if (chunkSection == null) continue;
            DataPalette object = chunkSection.palette(PaletteType.BLOCKS);
            for (int j = 0; j < object.size(); ++j) {
                int n = protocol1_16To1_15_2.getMappingData().getNewBlockStateId(object.idByIndex(j));
                object.setIdByIndex(j, n);
            }
        }
        CompoundTag compoundTag = chunk.getHeightMap();
        for (Tag tag : compoundTag.values()) {
            LongArrayTag longArrayTag = (LongArrayTag)tag;
            int[] nArray = new int[256];
            CompactArrayUtil.iterateCompactArray(9, nArray.length, longArrayTag.getValue(), (arg_0, arg_1) -> WorldPackets.lambda$null$0(nArray, arg_0, arg_1));
            longArrayTag.setValue(CompactArrayUtil.createCompactArrayWithPadding(9, nArray.length, arg_0 -> WorldPackets.lambda$null$1(nArray, arg_0)));
        }
        if (chunk.getBlockEntities() == null) {
            return;
        }
        for (CompoundTag compoundTag2 : chunk.getBlockEntities()) {
            WorldPackets.handleBlockEntity(protocol1_16To1_15_2, compoundTag2);
        }
    }

    private static long lambda$null$1(int[] nArray, int n) {
        return nArray[n];
    }

    private static void lambda$null$0(int[] nArray, int n, int n2) {
        nArray[n] = n2;
    }
}

