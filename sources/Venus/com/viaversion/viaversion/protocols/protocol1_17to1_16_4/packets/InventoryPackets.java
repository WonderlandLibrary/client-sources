/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.storage.InventoryAcknowledgements;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public final class InventoryPackets
extends ItemRewriter<ClientboundPackets1_16_2, ServerboundPackets1_17, Protocol1_17To1_16_4> {
    public InventoryPackets(Protocol1_17To1_16_4 protocol1_17To1_16_4) {
        super(protocol1_17To1_16_4);
    }

    @Override
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_16_2.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_16_2.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerTradeList(ClientboundPackets1_16_2.TRADE_LIST);
        this.registerSetSlot(ClientboundPackets1_16_2.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_16_2.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_16_2.ENTITY_EQUIPMENT);
        this.registerSpawnParticle(ClientboundPackets1_16_2.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        new RecipeRewriter<ClientboundPackets1_16_2>(this.protocol).register(ClientboundPackets1_16_2.DECLARE_RECIPES);
        this.registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_17To1_16_4)this.protocol).registerServerbound(ServerboundPackets1_17.EDIT_BOOK, this::lambda$registerPackets$0);
        ((Protocol1_17To1_16_4)this.protocol).registerServerbound(ServerboundPackets1_17.CLICK_WINDOW, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.handler(1::lambda$register$0);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$1);
            }

            private void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                for (int i = 0; i < n; ++i) {
                    packetWrapper.read(Type.SHORT);
                    packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                }
                Item item = packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                int n2 = packetWrapper.get(Type.VAR_INT, 0);
                if (n2 == 5 || n2 == 1) {
                    item = null;
                } else {
                    this.this$0.handleItemToServer(item);
                }
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, item);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.SHORT, (short)0);
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.WINDOW_CONFIRMATION, null, InventoryPackets::lambda$registerPackets$1);
        ((Protocol1_17To1_16_4)this.protocol).registerServerbound(ServerboundPackets1_17.PONG, null, InventoryPackets::lambda$registerPackets$2);
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.INT);
        if ((n & 0x40000000) != 0 && packetWrapper.user().get(InventoryAcknowledgements.class).removeId(n)) {
            short s = (short)(n >> 16 & 0xFF);
            short s2 = (short)(n & 0xFFFF);
            PacketWrapper packetWrapper2 = packetWrapper.create(ServerboundPackets1_16_2.WINDOW_CONFIRMATION);
            packetWrapper2.write(Type.UNSIGNED_BYTE, s);
            packetWrapper2.write(Type.SHORT, s2);
            packetWrapper2.write(Type.BOOLEAN, true);
            packetWrapper2.sendToServer(Protocol1_17To1_16_4.class);
        }
        packetWrapper.cancel();
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        short s = packetWrapper.read(Type.UNSIGNED_BYTE);
        short s2 = packetWrapper.read(Type.SHORT);
        boolean bl = packetWrapper.read(Type.BOOLEAN);
        if (!bl) {
            int n = 0x40000000 | s << 16 | s2 & 0xFFFF;
            packetWrapper.user().get(InventoryAcknowledgements.class).addId(n);
            PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_17.PING);
            packetWrapper2.write(Type.INT, n);
            packetWrapper2.send(Protocol1_17To1_16_4.class);
        }
        packetWrapper.cancel();
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        this.handleItemToServer(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }
}

