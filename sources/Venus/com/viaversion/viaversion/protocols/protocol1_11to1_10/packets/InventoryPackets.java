/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_11to1_10.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ItemRewriter;

public class InventoryPackets
extends ItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_11To1_10> {
    public InventoryPackets(Protocol1_11To1_10 protocol1_11To1_10) {
        super(protocol1_11To1_10);
    }

    @Override
    public void registerPackets() {
        this.registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
        this.registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
        this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.get(Type.STRING, 0).equalsIgnoreCase("MC|TrList")) {
                    packetWrapper.passthrough(Type.INT);
                    int n = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                    for (int i = 0; i < n; ++i) {
                        EntityIdRewriter.toClientItem(packetWrapper.passthrough(Type.ITEM));
                        EntityIdRewriter.toClientItem(packetWrapper.passthrough(Type.ITEM));
                        boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
                        if (bl) {
                            EntityIdRewriter.toClientItem(packetWrapper.passthrough(Type.ITEM));
                        }
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.passthrough(Type.INT);
                    }
                }
            }
        });
        this.registerClickWindow(ServerboundPackets1_9_3.CLICK_WINDOW, Type.ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
    }

    @Override
    public Item handleItemToClient(Item item) {
        EntityIdRewriter.toClientItem(item);
        return item;
    }

    @Override
    public Item handleItemToServer(Item item) {
        EntityIdRewriter.toServerItem(item);
        if (item == null) {
            return null;
        }
        boolean bl = item.identifier() >= 218 && item.identifier() <= 234;
        if (bl |= item.identifier() == 449 || item.identifier() == 450) {
            item.setIdentifier(1);
            item.setData((short)0);
        }
        return item;
    }
}

