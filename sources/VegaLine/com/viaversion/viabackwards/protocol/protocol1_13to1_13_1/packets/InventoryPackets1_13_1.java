/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.rewriter.ItemRewriter;

public class InventoryPackets1_13_1
extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_13, Protocol1_13To1_13_1> {
    public InventoryPackets1_13_1(Protocol1_13To1_13_1 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_13.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_13.WINDOW_ITEMS, Type.FLAT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_13.SET_SLOT, Type.FLAT_ITEM);
        ((Protocol1_13To1_13_1)this.protocol).registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, wrapper -> {
            String channel = wrapper.passthrough(Type.STRING);
            if (channel.equals("minecraft:trader_list")) {
                wrapper.passthrough(Type.INT);
                int size = wrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                for (int i = 0; i < size; ++i) {
                    Item input = wrapper.passthrough(Type.FLAT_ITEM);
                    this.handleItemToClient(input);
                    Item output = wrapper.passthrough(Type.FLAT_ITEM);
                    this.handleItemToClient(output);
                    boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                    if (secondItem) {
                        Item second = wrapper.passthrough(Type.FLAT_ITEM);
                        this.handleItemToClient(second);
                    }
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.INT);
                    wrapper.passthrough(Type.INT);
                }
            }
        });
        this.registerEntityEquipment(ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.FLAT_ITEM);
        this.registerClickWindow(ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_ITEM);
        this.registerSpawnParticle(ClientboundPackets1_13.SPAWN_PARTICLE, Type.FLAT_ITEM, Type.FLOAT);
    }
}

