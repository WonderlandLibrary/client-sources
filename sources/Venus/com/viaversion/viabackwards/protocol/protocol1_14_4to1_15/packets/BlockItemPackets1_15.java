/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
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
    public BlockItemPackets1_15(Protocol1_14_4To1_15 protocol1_14_4To1_15) {
        super(protocol1_14_4To1_15);
    }

    @Override
    protected void registerPackets() {
        BlockRewriter<ClientboundPackets1_15> blockRewriter = new BlockRewriter<ClientboundPackets1_15>(this.protocol, Type.POSITION1_14);
        new RecipeRewriter<ClientboundPackets1_15>(this.protocol).register(ClientboundPackets1_15.DECLARE_RECIPES);
        ((Protocol1_14_4To1_15)this.protocol).registerServerbound(ServerboundPackets1_14.EDIT_BOOK, this::lambda$registerPackets$0);
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
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.CHUNK_DATA, this::lambda$registerPackets$1);
        blockRewriter.registerEffect(ClientboundPackets1_15.EFFECT, 1010, 2001);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.SPAWN_PARTICLE, new PacketHandlers(this){
            final BlockItemPackets1_15 this$0;
            {
                this.this$0 = blockItemPackets1_15;
            }

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
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n;
                int n2 = packetWrapper.get(Type.INT, 0);
                if (n2 == 3 || n2 == 23) {
                    n = packetWrapper.passthrough(Type.VAR_INT);
                    packetWrapper.set(Type.VAR_INT, 0, ((Protocol1_14_4To1_15)BlockItemPackets1_15.access$000(this.this$0)).getMappingData().getNewBlockStateId(n));
                } else if (n2 == 32) {
                    Item item = this.this$0.handleItemToClient(packetWrapper.read(Type.FLAT_VAR_INT_ITEM));
                    packetWrapper.write(Type.FLAT_VAR_INT_ITEM, item);
                }
                n = ((Protocol1_14_4To1_15)BlockItemPackets1_15.access$100(this.this$0)).getMappingData().getNewParticleId(n2);
                if (n2 != n) {
                    packetWrapper.set(Type.INT, 0, n);
                }
            }
        });
    }

    private void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2;
        Object object;
        Chunk chunk = packetWrapper.read(new Chunk1_15Type());
        packetWrapper.write(new Chunk1_14Type(), chunk);
        if (chunk.isFullChunk()) {
            int[] nArray = chunk.getBiomeData();
            object = new int[256];
            for (int i = 0; i < 4; ++i) {
                for (n2 = 0; n2 < 4; ++n2) {
                    n = n2 << 2;
                    int n3 = i << 2;
                    int n4 = n3 << 4 | n;
                    int n5 = i << 2 | n2;
                    int n6 = nArray[n5];
                    for (int j = 0; j < 4; ++j) {
                        int n7 = n4 + (j << 4);
                        for (int k = 0; k < 4; ++k) {
                            object[n7 + k] = n6;
                        }
                    }
                }
            }
            chunk.setBiomeData((int[])object);
        }
        for (int i = 0; i < chunk.getSections().length; ++i) {
            object = chunk.getSections()[i];
            if (object == null) continue;
            DataPalette dataPalette = object.palette(PaletteType.BLOCKS);
            for (n2 = 0; n2 < dataPalette.size(); ++n2) {
                n = ((Protocol1_14_4To1_15)this.protocol).getMappingData().getNewBlockStateId(dataPalette.idByIndex(n2));
                dataPalette.setIdByIndex(n2, n);
            }
        }
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        this.handleItemToServer(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    static Protocol access$000(BlockItemPackets1_15 blockItemPackets1_15) {
        return blockItemPackets1_15.protocol;
    }

    static Protocol access$100(BlockItemPackets1_15 blockItemPackets1_15) {
        return blockItemPackets1_15.protocol;
    }
}

