/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.Protocol1_19_1To1_19_3;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ServerboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public final class BlockItemPackets1_19_3
extends ItemRewriter<ClientboundPackets1_19_3, ServerboundPackets1_19_1, Protocol1_19_1To1_19_3> {
    public BlockItemPackets1_19_3(Protocol1_19_1To1_19_3 protocol1_19_1To1_19_3) {
        super(protocol1_19_1To1_19_3);
    }

    @Override
    protected void registerPackets() {
        BlockRewriter<ClientboundPackets1_19_3> blockRewriter = new BlockRewriter<ClientboundPackets1_19_3>(this.protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_19_3.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_19_3.BLOCK_CHANGE);
        blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_19_3.MULTI_BLOCK_CHANGE);
        blockRewriter.registerEffect(ClientboundPackets1_19_3.EFFECT, 1010, 2001);
        blockRewriter.registerChunkData1_19(ClientboundPackets1_19_3.CHUNK_DATA, Chunk1_18Type::new);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_19_3.BLOCK_ENTITY_DATA);
        this.registerSetCooldown(ClientboundPackets1_19_3.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_19_3.WINDOW_ITEMS);
        this.registerSetSlot1_17_1(ClientboundPackets1_19_3.SET_SLOT);
        this.registerEntityEquipmentArray(ClientboundPackets1_19_3.ENTITY_EQUIPMENT);
        this.registerAdvancements(ClientboundPackets1_19_3.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerClickWindow1_17_1(ServerboundPackets1_19_1.CLICK_WINDOW);
        this.registerTradeList1_19(ClientboundPackets1_19_3.TRADE_LIST);
        this.registerCreativeInvAction(ServerboundPackets1_19_1.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_19_3.WINDOW_PROPERTY);
        this.registerSpawnParticle1_19(ClientboundPackets1_19_3.SPAWN_PARTICLE);
        ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.EXPLOSION, new PacketHandlers(this){
            final BlockItemPackets1_19_3 this$0;
            {
                this.this$0 = blockItemPackets1_19_3;
            }

            @Override
            public void register() {
                this.map((Type)Type.DOUBLE, Type.FLOAT);
                this.map((Type)Type.DOUBLE, Type.FLOAT);
                this.map((Type)Type.DOUBLE, Type.FLOAT);
            }
        });
        RecipeRewriter recipeRewriter = new RecipeRewriter(this.protocol);
        ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_3.DECLARE_RECIPES, arg_0 -> this.lambda$registerPackets$0(recipeRewriter, arg_0));
    }

    private void lambda$registerPackets$0(RecipeRewriter recipeRewriter, PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        block27: for (int i = 0; i < n; ++i) {
            String string = Key.stripMinecraftNamespace(packetWrapper.passthrough(Type.STRING));
            packetWrapper.passthrough(Type.STRING);
            switch (string) {
                case "crafting_shapeless": {
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.read(Type.VAR_INT);
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
                    packetWrapper.read(Type.VAR_INT);
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
                    packetWrapper.read(Type.VAR_INT);
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
                    packetWrapper.read(Type.VAR_INT);
                    continue block27;
                }
                default: {
                    recipeRewriter.handleRecipeType(packetWrapper, string);
                }
            }
        }
    }
}

