/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.packets;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.rewriter.RecipeRewriter1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.Protocol1_19_4To1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;

public final class InventoryPackets
extends ItemRewriter<ClientboundPackets1_19_3, ServerboundPackets1_19_4, Protocol1_19_4To1_19_3> {
    public InventoryPackets(Protocol1_19_4To1_19_3 protocol1_19_4To1_19_3) {
        super(protocol1_19_4To1_19_3);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_19_3> blockRewriter = new BlockRewriter<ClientboundPackets1_19_3>(this.protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_19_3.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_19_3.BLOCK_CHANGE);
        blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_19_3.MULTI_BLOCK_CHANGE);
        blockRewriter.registerChunkData1_19(ClientboundPackets1_19_3.CHUNK_DATA, Chunk1_18Type::new);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_19_3.BLOCK_ENTITY_DATA);
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.EFFECT, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION1_14);
                this.map(Type.INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                int n2 = packetWrapper.get(Type.INT, 1);
                if (n == 1010) {
                    if (n2 >= 1092 && n2 <= 1106) {
                        packetWrapper.set(Type.INT, 1, ((Protocol1_19_4To1_19_3)InventoryPackets.access$000(this.this$0)).getMappingData().getNewItemId(n2));
                    } else {
                        packetWrapper.set(Type.INT, 0, 1011);
                        packetWrapper.set(Type.INT, 1, 0);
                    }
                } else if (n == 2001) {
                    packetWrapper.set(Type.INT, 1, ((Protocol1_19_4To1_19_3)InventoryPackets.access$100(this.this$0)).getMappingData().getNewBlockStateId(n2));
                }
            }
        });
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.OPEN_WINDOW, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.COMPONENT);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 1);
                if (n >= 21) {
                    packetWrapper.set(Type.VAR_INT, 1, n + 1);
                }
            }
        });
        this.registerSetCooldown(ClientboundPackets1_19_3.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_19_3.WINDOW_ITEMS);
        this.registerSetSlot1_17_1(ClientboundPackets1_19_3.SET_SLOT);
        this.registerAdvancements(ClientboundPackets1_19_3.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_19_3.ENTITY_EQUIPMENT);
        this.registerTradeList1_19(ClientboundPackets1_19_3.TRADE_LIST);
        this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_19_3.WINDOW_PROPERTY);
        this.registerSpawnParticle1_19(ClientboundPackets1_19_3.SPAWN_PARTICLE);
        this.registerCreativeInvAction(ServerboundPackets1_19_4.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        this.registerClickWindow1_17_1(ServerboundPackets1_19_4.CLICK_WINDOW);
        new RecipeRewriter1_19_3<ClientboundPackets1_19_3>(this, this.protocol){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
                super(protocol);
            }

            @Override
            public void handleCraftingShaped(PacketWrapper packetWrapper) throws Exception {
                super.handleCraftingShaped(packetWrapper);
                packetWrapper.write(Type.BOOLEAN, true);
            }
        }.register(ClientboundPackets1_19_3.DECLARE_RECIPES);
    }

    static Protocol access$000(InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }

    static Protocol access$100(InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }
}

