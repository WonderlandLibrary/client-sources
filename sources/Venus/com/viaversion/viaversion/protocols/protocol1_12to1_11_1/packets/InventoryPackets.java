/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class InventoryPackets
extends ItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_12, Protocol1_12To1_11_1> {
    public InventoryPackets(Protocol1_12To1_11_1 protocol1_12To1_11_1) {
        super(protocol1_12To1_11_1);
    }

    @Override
    public void registerPackets() {
        this.registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
        this.registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
        this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.get(Type.STRING, 0).equalsIgnoreCase("MC|TrList")) {
                    packetWrapper.passthrough(Type.INT);
                    int n = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                    for (int i = 0; i < n; ++i) {
                        this.this$0.handleItemToClient(packetWrapper.passthrough(Type.ITEM));
                        this.this$0.handleItemToClient(packetWrapper.passthrough(Type.ITEM));
                        boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
                        if (bl) {
                            this.this$0.handleItemToClient(packetWrapper.passthrough(Type.ITEM));
                        }
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.passthrough(Type.INT);
                    }
                }
            }
        });
        ((Protocol1_12To1_11_1)this.protocol).registerServerbound(ServerboundPackets1_12.CLICK_WINDOW, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Item item = packetWrapper.get(Type.ITEM, 0);
                if (!Via.getConfig().is1_12QuickMoveActionFix()) {
                    this.this$0.handleItemToServer(item);
                    return;
                }
                byte by = packetWrapper.get(Type.BYTE, 0);
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 1 && by == 0 && item == null) {
                    short s = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                    short s2 = packetWrapper.get(Type.SHORT, 0);
                    short s3 = packetWrapper.get(Type.SHORT, 1);
                    InventoryQuickMoveProvider inventoryQuickMoveProvider = Via.getManager().getProviders().get(InventoryQuickMoveProvider.class);
                    boolean bl = inventoryQuickMoveProvider.registerQuickMoveAction(s, s2, s3, packetWrapper.user());
                    if (bl) {
                        packetWrapper.cancel();
                    }
                } else {
                    this.this$0.handleItemToServer(item);
                }
            }
        });
        this.registerCreativeInvAction(ServerboundPackets1_12.CREATIVE_INVENTORY_ACTION, Type.ITEM);
    }

    @Override
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        if (item.identifier() == 355) {
            item.setData((short)0);
        }
        boolean bl = item.identifier() >= 235 && item.identifier() <= 252;
        if (bl |= item.identifier() == 453) {
            item.setIdentifier(1);
            item.setData((short)0);
        }
        return item;
    }

    @Override
    public @Nullable Item handleItemToClient(@Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (item.identifier() == 355) {
            item.setData((short)14);
        }
        return item;
    }
}

