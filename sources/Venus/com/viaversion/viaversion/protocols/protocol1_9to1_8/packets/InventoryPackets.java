/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.InventoryTracker;

public class InventoryPackets {
    public static void register(Protocol1_9To1_8 protocol1_9To1_8) {
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.WINDOW_PROPERTY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(1::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                short s2 = packetWrapper.get(Type.SHORT, 0);
                short s3 = packetWrapper.get(Type.SHORT, 1);
                InventoryTracker inventoryTracker = packetWrapper.user().get(InventoryTracker.class);
                if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equalsIgnoreCase("minecraft:enchanting_table") && s2 > 3 && s2 < 7) {
                    short s4 = (short)(s3 >> 8);
                    short s5 = (short)(s3 & 0xFF);
                    packetWrapper.create(packetWrapper.getId(), arg_0 -> 1.lambda$null$0(s, s2, s5, arg_0)).scheduleSend(Protocol1_9To1_8.class);
                    packetWrapper.set(Type.SHORT, 0, (short)(s2 + 3));
                    packetWrapper.set(Type.SHORT, 1, s4);
                }
            }

            private static void lambda$null$0(short s, short s2, short s3, PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.UNSIGNED_BYTE, s);
                packetWrapper.write(Type.SHORT, s2);
                packetWrapper.write(Type.SHORT, s3);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.OPEN_WINDOW, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(2::lambda$register$0);
                this.handler(2::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0);
                if (string.equals("minecraft:brewing_stand")) {
                    packetWrapper.set(Type.UNSIGNED_BYTE, 1, (short)(packetWrapper.get(Type.UNSIGNED_BYTE, 1) + 1));
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0);
                InventoryTracker inventoryTracker = packetWrapper.user().get(InventoryTracker.class);
                inventoryTracker.setInventory(string);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SET_SLOT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.ITEM);
                this.handler(3::lambda$register$0);
                this.handler(3::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                InventoryTracker inventoryTracker = packetWrapper.user().get(InventoryTracker.class);
                short s = packetWrapper.get(Type.SHORT, 0);
                if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand") && s >= 4) {
                    packetWrapper.set(Type.SHORT, 0, (short)(s + 1));
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                boolean bl;
                Item item = packetWrapper.get(Type.ITEM, 0);
                boolean bl2 = bl = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
                if (bl) {
                    InventoryTracker inventoryTracker = packetWrapper.user().get(InventoryTracker.class);
                    EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                    short s = packetWrapper.get(Type.SHORT, 0);
                    byte by = packetWrapper.get(Type.UNSIGNED_BYTE, 0).byteValue();
                    inventoryTracker.setItemId(by, s, item == null ? 0 : item.identifier());
                    entityTracker1_9.syncShieldWithSword();
                }
                ItemRewriter.toClient(item);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.WINDOW_ITEMS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.ITEM_ARRAY);
                this.handler(4::lambda$register$0);
                this.handler(4::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                InventoryTracker inventoryTracker = packetWrapper.user().get(InventoryTracker.class);
                if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand")) {
                    Item[] itemArray = packetWrapper.get(Type.ITEM_ARRAY, 0);
                    Item[] itemArray2 = new Item[itemArray.length + 1];
                    for (int i = 0; i < itemArray2.length; ++i) {
                        if (i > 4) {
                            itemArray2[i] = itemArray[i - 1];
                            continue;
                        }
                        if (i == 4) continue;
                        itemArray2[i] = itemArray[i];
                    }
                    packetWrapper.set(Type.ITEM_ARRAY, 0, itemArray2);
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Item[] itemArray = packetWrapper.get(Type.ITEM_ARRAY, 0);
                Short s = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                InventoryTracker inventoryTracker = packetWrapper.user().get(InventoryTracker.class);
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                boolean bl = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
                for (short s2 = 0; s2 < itemArray.length; s2 = (short)((short)(s2 + 1))) {
                    Item item = itemArray[s2];
                    if (bl) {
                        inventoryTracker.setItemId(s, s2, item == null ? 0 : item.identifier());
                    }
                    ItemRewriter.toClient(item);
                }
                if (bl) {
                    entityTracker1_9.syncShieldWithSword();
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.CLOSE_WINDOW, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                InventoryTracker inventoryTracker = packetWrapper.user().get(InventoryTracker.class);
                inventoryTracker.setInventory(null);
                inventoryTracker.resetInventory(packetWrapper.get(Type.UNSIGNED_BYTE, 0));
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.MAP_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(6::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, true);
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.CREATIVE_INVENTORY_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.SHORT);
                this.map(Type.ITEM);
                this.handler(7::lambda$register$0);
                this.handler(this::lambda$register$1);
            }

            private void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                boolean bl;
                short s = packetWrapper.get(Type.SHORT, 0);
                boolean bl2 = bl = s == 45;
                if (bl) {
                    packetWrapper.create(ClientboundPackets1_9.SET_SLOT, new PacketHandler(this, s){
                        final short val$slot;
                        final 7 this$0;
                        {
                            this.this$0 = var1_1;
                            this.val$slot = s;
                        }

                        @Override
                        public void handle(PacketWrapper packetWrapper) throws Exception {
                            packetWrapper.write(Type.UNSIGNED_BYTE, (short)0);
                            packetWrapper.write(Type.SHORT, this.val$slot);
                            packetWrapper.write(Type.ITEM, null);
                        }
                    }).send(Protocol1_9To1_8.class);
                    packetWrapper.set(Type.SHORT, 0, (short)-999);
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                boolean bl;
                Item item = packetWrapper.get(Type.ITEM, 0);
                boolean bl2 = bl = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
                if (bl) {
                    InventoryTracker inventoryTracker = packetWrapper.user().get(InventoryTracker.class);
                    EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                    short s = packetWrapper.get(Type.SHORT, 0);
                    inventoryTracker.setItemId((short)0, s, item == null ? 0 : item.identifier());
                    entityTracker1_9.syncShieldWithSword();
                }
                ItemRewriter.toServer(item);
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.CLICK_WINDOW, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map((Type)Type.VAR_INT, Type.BYTE);
                this.map(Type.ITEM);
                this.handler(8::lambda$register$0);
                this.handler(this::lambda$register$1);
            }

            private void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                short s2 = packetWrapper.get(Type.SHORT, 0);
                boolean bl = s2 == 45 && s == 0;
                InventoryTracker inventoryTracker = packetWrapper.user().get(InventoryTracker.class);
                if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand")) {
                    if (s2 == 4) {
                        bl = true;
                    }
                    if (s2 > 4) {
                        packetWrapper.set(Type.SHORT, 0, (short)(s2 - 1));
                    }
                }
                if (bl) {
                    packetWrapper.create(ClientboundPackets1_9.SET_SLOT, new PacketHandler(this, s, s2){
                        final short val$windowID;
                        final short val$slot;
                        final 8 this$0;
                        {
                            this.this$0 = var1_1;
                            this.val$windowID = s;
                            this.val$slot = s2;
                        }

                        @Override
                        public void handle(PacketWrapper packetWrapper) throws Exception {
                            packetWrapper.write(Type.UNSIGNED_BYTE, this.val$windowID);
                            packetWrapper.write(Type.SHORT, this.val$slot);
                            packetWrapper.write(Type.ITEM, null);
                        }
                    }).scheduleSend(Protocol1_9To1_8.class);
                    packetWrapper.set(Type.BYTE, 0, (byte)0);
                    packetWrapper.set(Type.BYTE, 1, (byte)0);
                    packetWrapper.set(Type.SHORT, 0, (short)-999);
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Item item = packetWrapper.get(Type.ITEM, 0);
                if (Via.getConfig().isShowShieldWhenSwordInHand()) {
                    Short s = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                    byte by = packetWrapper.get(Type.BYTE, 1);
                    short s2 = packetWrapper.get(Type.SHORT, 0);
                    byte by2 = packetWrapper.get(Type.BYTE, 0);
                    InventoryTracker inventoryTracker = packetWrapper.user().get(InventoryTracker.class);
                    inventoryTracker.handleWindowClick(packetWrapper.user(), s, by, s2, by2);
                }
                ItemRewriter.toServer(item);
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.CLOSE_WINDOW, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(9::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                InventoryTracker inventoryTracker = packetWrapper.user().get(InventoryTracker.class);
                inventoryTracker.setInventory(null);
                inventoryTracker.resetInventory(packetWrapper.get(Type.UNSIGNED_BYTE, 0));
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.HELD_ITEM_CHANGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.SHORT);
                this.handler(10::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                boolean bl = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                if (entityTracker1_9.isBlocking()) {
                    entityTracker1_9.setBlocking(true);
                    if (!bl) {
                        entityTracker1_9.setSecondHand(null);
                    }
                }
                if (bl) {
                    entityTracker1_9.setHeldItemSlot(packetWrapper.get(Type.SHORT, 0).shortValue());
                    entityTracker1_9.syncShieldWithSword();
                }
            }
        });
    }
}

