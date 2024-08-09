/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import java.util.Collections;

public class PlayerPackets {
    public static void register(Protocol1_14To1_13_2 protocol1_14To1_13_2) {
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.OPEN_SIGN_EDITOR, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        protocol1_14To1_13_2.registerServerbound(ServerboundPackets1_14.QUERY_BLOCK_NBT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol1_14To1_13_2.registerServerbound(ServerboundPackets1_14.EDIT_BOOK, arg_0 -> PlayerPackets.lambda$register$0(protocol1_14To1_13_2, arg_0));
        protocol1_14To1_13_2.registerServerbound(ServerboundPackets1_14.PLAYER_DIGGING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol1_14To1_13_2.registerServerbound(ServerboundPackets1_14.RECIPE_BOOK_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 0) {
                    packetWrapper.passthrough(Type.STRING);
                } else if (n == 1) {
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.read(Type.BOOLEAN);
                    packetWrapper.read(Type.BOOLEAN);
                    packetWrapper.read(Type.BOOLEAN);
                    packetWrapper.read(Type.BOOLEAN);
                }
            }
        });
        protocol1_14To1_13_2.registerServerbound(ServerboundPackets1_14.UPDATE_COMMAND_BLOCK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol1_14To1_13_2.registerServerbound(ServerboundPackets1_14.UPDATE_STRUCTURE_BLOCK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol1_14To1_13_2.registerServerbound(ServerboundPackets1_14.UPDATE_SIGN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol1_14To1_13_2.registerServerbound(ServerboundPackets1_14.PLAYER_BLOCK_PLACEMENT, PlayerPackets::lambda$register$1);
    }

    private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        Position position = packetWrapper.read(Type.POSITION1_14);
        int n2 = packetWrapper.read(Type.VAR_INT);
        float f = packetWrapper.read(Type.FLOAT).floatValue();
        float f2 = packetWrapper.read(Type.FLOAT).floatValue();
        float f3 = packetWrapper.read(Type.FLOAT).floatValue();
        packetWrapper.read(Type.BOOLEAN);
        packetWrapper.write(Type.POSITION, position);
        packetWrapper.write(Type.VAR_INT, n2);
        packetWrapper.write(Type.VAR_INT, n);
        packetWrapper.write(Type.FLOAT, Float.valueOf(f));
        packetWrapper.write(Type.FLOAT, Float.valueOf(f2));
        packetWrapper.write(Type.FLOAT, Float.valueOf(f3));
    }

    private static void lambda$register$0(Protocol1_14To1_13_2 protocol1_14To1_13_2, PacketWrapper packetWrapper) throws Exception {
        ListTag listTag;
        Item item = packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
        protocol1_14To1_13_2.getItemRewriter().handleItemToServer(item);
        if (item == null) {
            return;
        }
        CompoundTag compoundTag = item.tag();
        if (compoundTag == null) {
            return;
        }
        Object t = compoundTag.get("pages");
        if (t == null) {
            compoundTag.put("pages", new ListTag(Collections.singletonList(new StringTag())));
        }
        if (Via.getConfig().isTruncate1_14Books() && t instanceof ListTag && (listTag = (ListTag)t).size() > 50) {
            listTag.setValue(listTag.getValue().subList(0, 50));
        }
    }
}

