/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public class InventoryPackets
extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_13, Protocol1_13_1To1_13> {
    public InventoryPackets(Protocol1_13_1To1_13 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.registerSetSlot(ClientboundPackets1_13.SET_SLOT, Type.FLAT_ITEM);
        this.registerWindowItems(ClientboundPackets1_13.WINDOW_ITEMS, Type.FLAT_ITEM_ARRAY);
        this.registerAdvancements(ClientboundPackets1_13.ADVANCEMENTS, Type.FLAT_ITEM);
        this.registerSetCooldown(ClientboundPackets1_13.COOLDOWN);
        ((Protocol1_13_1To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(wrapper -> {
                    String channel = wrapper.get(Type.STRING, 0);
                    if (channel.equals("minecraft:trader_list") || channel.equals("trader_list")) {
                        wrapper.passthrough(Type.INT);
                        int size = wrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                        for (int i = 0; i < size; ++i) {
                            InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.FLAT_ITEM));
                            InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.FLAT_ITEM));
                            boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                            if (secondItem) {
                                InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.FLAT_ITEM));
                            }
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                        }
                    }
                });
            }
        });
        this.registerEntityEquipment(ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.FLAT_ITEM);
        RecipeRewriter recipeRewriter = new RecipeRewriter(this.protocol);
        ((Protocol1_13_1To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.DECLARE_RECIPES, wrapper -> {
            int size = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Type.STRING);
                String type2 = wrapper.passthrough(Type.STRING).replace("minecraft:", "");
                recipeRewriter.handleRecipeType(wrapper, type2);
            }
        });
        this.registerClickWindow(ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_ITEM);
        this.registerSpawnParticle(ClientboundPackets1_13.SPAWN_PARTICLE, Type.FLAT_ITEM, Type.FLOAT);
    }
}

