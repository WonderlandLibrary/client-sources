/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.Protocol1_19_3To1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public final class InventoryPackets
extends ItemRewriter<ClientboundPackets1_19_1, ServerboundPackets1_19_3, Protocol1_19_3To1_19_1> {
    private static final int MISC_CRAFTING_BOOK_CATEGORY = 0;

    public InventoryPackets(Protocol1_19_3To1_19_1 protocol1_19_3To1_19_1) {
        super(protocol1_19_3To1_19_1);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_19_1> blockRewriter = new BlockRewriter<ClientboundPackets1_19_1>(this.protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_19_1.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_19_1.BLOCK_CHANGE);
        blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_19_1.MULTI_BLOCK_CHANGE);
        blockRewriter.registerEffect(ClientboundPackets1_19_1.EFFECT, 1010, 2001);
        blockRewriter.registerChunkData1_19(ClientboundPackets1_19_1.CHUNK_DATA, Chunk1_18Type::new);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_19_1.BLOCK_ENTITY_DATA);
        this.registerSetCooldown(ClientboundPackets1_19_1.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_19_1.WINDOW_ITEMS);
        this.registerSetSlot1_17_1(ClientboundPackets1_19_1.SET_SLOT);
        this.registerAdvancements(ClientboundPackets1_19_1.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_19_1.ENTITY_EQUIPMENT);
        this.registerClickWindow1_17_1(ServerboundPackets1_19_3.CLICK_WINDOW);
        this.registerTradeList1_19(ClientboundPackets1_19_1.TRADE_LIST);
        this.registerCreativeInvAction(ServerboundPackets1_19_3.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_19_1.WINDOW_PROPERTY);
        this.registerSpawnParticle1_19(ClientboundPackets1_19_1.SPAWN_PARTICLE);
        RecipeRewriter recipeRewriter = new RecipeRewriter(this.protocol);
        ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_1.DECLARE_RECIPES, arg_0 -> this.lambda$registerPackets$0(recipeRewriter, arg_0));
        ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_1.EXPLOSION, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map((Type)Type.FLOAT, Type.DOUBLE);
                this.map((Type)Type.FLOAT, Type.DOUBLE);
                this.map((Type)Type.FLOAT, Type.DOUBLE);
            }
        });
    }

    private void lambda$registerPackets$0(RecipeRewriter recipeRewriter, PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        block27: for (int i = 0; i < n; ++i) {
            String string = Key.stripMinecraftNamespace(packetWrapper.passthrough(Type.STRING));
            packetWrapper.passthrough(Type.STRING);
            switch (string) {
                case "crafting_shapeless": {
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.write(Type.VAR_INT, 0);
                    int n2 = packetWrapper.passthrough(Type.VAR_INT);
                    for (int j = 0; j < n2; ++j) {
                        Item[] itemArray;
                        for (Item item : itemArray = packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT)) {
                            this.handleItemToClient(item);
                        }
                    }
                    this.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                    continue block27;
                }
                case "crafting_shaped": {
                    int n3 = packetWrapper.passthrough(Type.VAR_INT) * packetWrapper.passthrough(Type.VAR_INT);
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.write(Type.VAR_INT, 0);
                    for (int j = 0; j < n3; ++j) {
                        Item[] itemArray;
                        for (Item item : itemArray = packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT)) {
                            this.handleItemToClient(item);
                        }
                    }
                    this.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                    continue block27;
                }
                case "smelting": 
                case "campfire_cooking": 
                case "blasting": 
                case "smoking": {
                    Item[] itemArray;
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.write(Type.VAR_INT, 0);
                    for (Item item : itemArray = packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT)) {
                        this.handleItemToClient(item);
                    }
                    this.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.VAR_INT);
                    continue block27;
                }
                case "crafting_special_armordye": 
                case "crafting_special_bookcloning": 
                case "crafting_special_mapcloning": 
                case "crafting_special_mapextending": 
                case "crafting_special_firework_rocket": 
                case "crafting_special_firework_star": 
                case "crafting_special_firework_star_fade": 
                case "crafting_special_tippedarrow": 
                case "crafting_special_bannerduplicate": 
                case "crafting_special_shielddecoration": 
                case "crafting_special_shulkerboxcoloring": 
                case "crafting_special_suspiciousstew": 
                case "crafting_special_repairitem": {
                    packetWrapper.write(Type.VAR_INT, 0);
                    continue block27;
                }
                default: {
                    recipeRewriter.handleRecipeType(packetWrapper, string);
                }
            }
        }
    }
}

