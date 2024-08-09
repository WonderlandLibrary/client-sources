/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public class BlockItemPackets1_16_2
extends ItemRewriter<ClientboundPackets1_16_2, ServerboundPackets1_16, Protocol1_16_1To1_16_2> {
    public BlockItemPackets1_16_2(Protocol1_16_1To1_16_2 protocol1_16_1To1_16_2) {
        super(protocol1_16_1To1_16_2);
    }

    @Override
    protected void registerPackets() {
        BlockRewriter<ClientboundPackets1_16_2> blockRewriter = new BlockRewriter<ClientboundPackets1_16_2>(this.protocol, Type.POSITION1_14);
        new RecipeRewriter<ClientboundPackets1_16_2>(this.protocol).register(ClientboundPackets1_16_2.DECLARE_RECIPES);
        this.registerSetCooldown(ClientboundPackets1_16_2.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_16_2.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_16_2.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_16_2.ENTITY_EQUIPMENT);
        this.registerTradeList(ClientboundPackets1_16_2.TRADE_LIST);
        this.registerAdvancements(ClientboundPackets1_16_2.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16_2.UNLOCK_RECIPES, BlockItemPackets1_16_2::lambda$registerPackets$0);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_16_2.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16_2.BLOCK_CHANGE);
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16_2.CHUNK_DATA, this::lambda$registerPackets$1);
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16_2.BLOCK_ENTITY_DATA, new PacketHandlers(this){
            final BlockItemPackets1_16_2 this$0;
            {
                this.this$0 = blockItemPackets1_16_2;
            }

            @Override
            public void register() {
                this.map(Type.POSITION1_14);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                BlockItemPackets1_16_2.access$000(this.this$0, packetWrapper.passthrough(Type.NBT));
            }
        });
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE, this::lambda$registerPackets$2);
        blockRewriter.registerEffect(ClientboundPackets1_16_2.EFFECT, 1010, 2001);
        this.registerSpawnParticle(ClientboundPackets1_16_2.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        this.registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_1To1_16_2)this.protocol).registerServerbound(ServerboundPackets1_16.EDIT_BOOK, this::lambda$registerPackets$3);
    }

    private void handleBlockEntity(CompoundTag compoundTag) {
        StringTag stringTag = (StringTag)compoundTag.get("id");
        if (stringTag == null) {
            return;
        }
        if (stringTag.getValue().equals("minecraft:skull")) {
            CompoundTag compoundTag2;
            Object t = compoundTag.get("SkullOwner");
            if (!(t instanceof CompoundTag)) {
                return;
            }
            CompoundTag compoundTag3 = (CompoundTag)t;
            if (!compoundTag3.contains("Id")) {
                return;
            }
            CompoundTag compoundTag4 = (CompoundTag)compoundTag3.get("Properties");
            if (compoundTag4 == null) {
                return;
            }
            ListTag listTag = (ListTag)compoundTag4.get("textures");
            if (listTag == null) {
                return;
            }
            CompoundTag compoundTag5 = compoundTag2 = listTag.size() > 0 ? (CompoundTag)listTag.get(0) : null;
            if (compoundTag2 == null) {
                return;
            }
            int n = ((Tag)compoundTag2.get("Value")).getValue().hashCode();
            int[] nArray = new int[]{n, 0, 0, 0};
            compoundTag3.put("Id", new IntArrayTag(nArray));
        }
    }

    private void lambda$registerPackets$3(PacketWrapper packetWrapper) throws Exception {
        this.handleItemToServer(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    private void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        long l = packetWrapper.read(Type.LONG);
        packetWrapper.read(Type.BOOLEAN);
        int n = (int)(l >> 42);
        int n2 = (int)(l << 44 >> 44);
        int n3 = (int)(l << 22 >> 42);
        packetWrapper.write(Type.INT, n);
        packetWrapper.write(Type.INT, n3);
        BlockChangeRecord[] blockChangeRecordArray = packetWrapper.read(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
        packetWrapper.write(Type.BLOCK_CHANGE_RECORD_ARRAY, blockChangeRecordArray);
        for (int i = 0; i < blockChangeRecordArray.length; ++i) {
            BlockChangeRecord blockChangeRecord = blockChangeRecordArray[i];
            int n4 = ((Protocol1_16_1To1_16_2)this.protocol).getMappingData().getNewBlockStateId(blockChangeRecord.getBlockId());
            blockChangeRecordArray[i] = new BlockChangeRecord1_8(blockChangeRecord.getSectionX(), blockChangeRecord.getY(n2), blockChangeRecord.getSectionZ(), n4);
        }
    }

    private void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        Chunk chunk = packetWrapper.read(new Chunk1_16_2Type());
        packetWrapper.write(new Chunk1_16Type(), chunk);
        chunk.setIgnoreOldLightData(true);
        for (int i = 0; i < chunk.getSections().length; ++i) {
            ChunkSection object = chunk.getSections()[i];
            if (object == null) continue;
            DataPalette dataPalette = object.palette(PaletteType.BLOCKS);
            for (int j = 0; j < dataPalette.size(); ++j) {
                int n = ((Protocol1_16_1To1_16_2)this.protocol).getMappingData().getNewBlockStateId(dataPalette.idByIndex(j));
                dataPalette.setIdByIndex(j, n);
            }
        }
        for (CompoundTag compoundTag : chunk.getBlockEntities()) {
            if (compoundTag == null) continue;
            this.handleBlockEntity(compoundTag);
        }
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.passthrough(Type.BOOLEAN);
        packetWrapper.passthrough(Type.BOOLEAN);
        packetWrapper.passthrough(Type.BOOLEAN);
        packetWrapper.passthrough(Type.BOOLEAN);
        packetWrapper.read(Type.BOOLEAN);
        packetWrapper.read(Type.BOOLEAN);
        packetWrapper.read(Type.BOOLEAN);
        packetWrapper.read(Type.BOOLEAN);
    }

    static void access$000(BlockItemPackets1_16_2 blockItemPackets1_16_2, CompoundTag compoundTag) {
        blockItemPackets1_16_2.handleBlockEntity(compoundTag);
    }
}

