/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.Protocol1_13_2To1_13_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;

public class InventoryPackets {
    public static void register(Protocol1_13_2To1_13_1 protocol1_13_2To1_13_1) {
        protocol1_13_2To1_13_1.registerClientbound(ClientboundPackets1_13.SET_SLOT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
            }
        });
        protocol1_13_2To1_13_1.registerClientbound(ClientboundPackets1_13.WINDOW_ITEMS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLAT_ITEM_ARRAY, Type.FLAT_VAR_INT_ITEM_ARRAY);
            }
        });
        protocol1_13_2To1_13_1.registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0);
                if (string.equals("minecraft:trader_list") || string.equals("trader_list")) {
                    packetWrapper.passthrough(Type.INT);
                    int n = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                    for (int i = 0; i < n; ++i) {
                        packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
                        packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
                        boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
                        if (bl) {
                            packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
                        }
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.passthrough(Type.INT);
                    }
                }
            }
        });
        protocol1_13_2To1_13_1.registerClientbound(ClientboundPackets1_13.ENTITY_EQUIPMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
            }
        });
        protocol1_13_2To1_13_1.registerClientbound(ClientboundPackets1_13.DECLARE_RECIPES, InventoryPackets::lambda$register$0);
        protocol1_13_2To1_13_1.registerServerbound(ServerboundPackets1_13.CLICK_WINDOW, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_VAR_INT_ITEM, Type.FLAT_ITEM);
            }
        });
        protocol1_13_2To1_13_1.registerServerbound(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.SHORT);
                this.map(Type.FLAT_VAR_INT_ITEM, Type.FLAT_ITEM);
            }
        });
    }

    private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            int n2;
            int n3;
            packetWrapper.passthrough(Type.STRING);
            String string = packetWrapper.passthrough(Type.STRING);
            if (string.equals("crafting_shapeless")) {
                packetWrapper.passthrough(Type.STRING);
                n3 = packetWrapper.passthrough(Type.VAR_INT);
                for (n2 = 0; n2 < n3; ++n2) {
                    packetWrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, packetWrapper.read(Type.FLAT_ITEM_ARRAY_VAR_INT));
                }
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
                continue;
            }
            if (string.equals("crafting_shaped")) {
                n3 = packetWrapper.passthrough(Type.VAR_INT) * packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.STRING);
                for (n2 = 0; n2 < n3; ++n2) {
                    packetWrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, packetWrapper.read(Type.FLAT_ITEM_ARRAY_VAR_INT));
                }
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
                continue;
            }
            if (!string.equals("smelting")) continue;
            packetWrapper.passthrough(Type.STRING);
            packetWrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, packetWrapper.read(Type.FLAT_ITEM_ARRAY_VAR_INT));
            packetWrapper.write(Type.FLAT_VAR_INT_ITEM, packetWrapper.read(Type.FLAT_ITEM));
            packetWrapper.passthrough(Type.FLOAT);
            packetWrapper.passthrough(Type.VAR_INT);
        }
    }
}

