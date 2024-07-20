/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public class BlockItemPackets1_15
extends ItemRewriter<ClientboundPackets1_15, ServerboundPackets1_14, Protocol1_14_4To1_15> {
    public BlockItemPackets1_15(Protocol1_14_4To1_15 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        BlockRewriter<ClientboundPackets1_15> blockRewriter = new BlockRewriter<ClientboundPackets1_15>(this.protocol, Type.POSITION1_14);
        new RecipeRewriter<ClientboundPackets1_15>(this.protocol).register(ClientboundPackets1_15.DECLARE_RECIPES);
        ((Protocol1_14_4To1_15)this.protocol).registerServerbound(ServerboundPackets1_14.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
        this.registerSetCooldown(ClientboundPackets1_15.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_15.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_15.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_15.TRADE_LIST);
        this.registerEntityEquipment(ClientboundPackets1_15.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_15.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_15.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_15.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_15.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_15.MULTI_BLOCK_CHANGE);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.CHUNK_DATA, wrapper -> {
            int j;
            Chunk chunk = wrapper.read(new Chunk1_15Type());
            wrapper.write(new Chunk1_14Type(), chunk);
            if (chunk.isFullChunk()) {
                int[] biomeData = chunk.getBiomeData();
                int[] newBiomeData = new int[256];
                for (int i = 0; i < 4; ++i) {
                    for (j = 0; j < 4; ++j) {
                        int x = j << 2;
                        int z = i << 2;
                        int newIndex = z << 4 | x;
                        int oldIndex = i << 2 | j;
                        int biome = biomeData[oldIndex];
                        for (int k = 0; k < 4; ++k) {
                            int offX = newIndex + (k << 4);
                            for (int l = 0; l < 4; ++l) {
                                newBiomeData[offX + l] = biome;
                            }
                        }
                    }
                }
                chunk.setBiomeData(newBiomeData);
            }
            for (int i = 0; i < chunk.getSections().length; ++i) {
                ChunkSection section = chunk.getSections()[i];
                if (section == null) continue;
                DataPalette palette = section.palette(PaletteType.BLOCKS);
                for (j = 0; j < palette.size(); ++j) {
                    int mappedBlockStateId = ((Protocol1_14_4To1_15)this.protocol).getMappingData().getNewBlockStateId(palette.idByIndex(j));
                    palette.setIdByIndex(j, mappedBlockStateId);
                }
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_15.EFFECT, 1010, 2001);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.SPAWN_PARTICLE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map((Type)Type.DOUBLE, Type.FLOAT);
                this.map((Type)Type.DOUBLE, Type.FLOAT);
                this.map((Type)Type.DOUBLE, Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Type.INT, 0);
                    if (id == 3 || id == 23) {
                        int data = wrapper.passthrough(Type.VAR_INT);
                        wrapper.set(Type.VAR_INT, 0, ((Protocol1_14_4To1_15)BlockItemPackets1_15.this.protocol).getMappingData().getNewBlockStateId(data));
                    } else if (id == 32) {
                        Item item = BlockItemPackets1_15.this.handleItemToClient(wrapper.read(Type.FLAT_VAR_INT_ITEM));
                        wrapper.write(Type.FLAT_VAR_INT_ITEM, item);
                    }
                    int mappedId = ((Protocol1_14_4To1_15)BlockItemPackets1_15.this.protocol).getMappingData().getNewParticleId(id);
                    if (id != mappedId) {
                        wrapper.set(Type.INT, 0, mappedId);
                    }
                });
            }
        });
    }
}

