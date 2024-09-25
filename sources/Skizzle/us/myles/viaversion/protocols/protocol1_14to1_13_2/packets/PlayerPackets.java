/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_14to1_13_2.packets;

import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.packets.InventoryPackets;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;

public class PlayerPackets {
    public static void register(Protocol protocol) {
        protocol.registerOutgoing(ClientboundPackets1_13.OPEN_SIGN_EDITOR, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        protocol.registerIncoming(ServerboundPackets1_14.QUERY_BLOCK_NBT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerIncoming(ServerboundPackets1_14.EDIT_BOOK, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item item = wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                        InventoryPackets.toServer(item);
                        if (Via.getConfig().isTruncate1_14Books()) {
                            if (item == null) {
                                return;
                            }
                            CompoundTag tag = item.getTag();
                            if (tag == null) {
                                return;
                            }
                            Object pages = tag.get("pages");
                            if (!(pages instanceof ListTag)) {
                                return;
                            }
                            ListTag listTag = (ListTag)pages;
                            if (listTag.size() <= 50) {
                                return;
                            }
                            listTag.setValue(listTag.getValue().subList(0, 50));
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(ServerboundPackets1_14.PLAYER_DIGGING, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.BYTE);
            }
        });
        protocol.registerIncoming(ServerboundPackets1_14.RECIPE_BOOK_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int type = wrapper.get(Type.VAR_INT, 0);
                        if (type == 0) {
                            wrapper.passthrough(Type.STRING);
                        } else if (type == 1) {
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.read(Type.BOOLEAN);
                            wrapper.read(Type.BOOLEAN);
                            wrapper.read(Type.BOOLEAN);
                            wrapper.read(Type.BOOLEAN);
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(ServerboundPackets1_14.UPDATE_COMMAND_BLOCK, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerIncoming(ServerboundPackets1_14.UPDATE_STRUCTURE_BLOCK, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerIncoming(ServerboundPackets1_14.UPDATE_SIGN, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerIncoming(ServerboundPackets1_14.PLAYER_BLOCK_PLACEMENT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int hand = wrapper.read(Type.VAR_INT);
                        Position position = wrapper.read(Type.POSITION1_14);
                        int face = wrapper.read(Type.VAR_INT);
                        float x = wrapper.read(Type.FLOAT).floatValue();
                        float y = wrapper.read(Type.FLOAT).floatValue();
                        float z = wrapper.read(Type.FLOAT).floatValue();
                        wrapper.read(Type.BOOLEAN);
                        wrapper.write(Type.POSITION, position);
                        wrapper.write(Type.VAR_INT, face);
                        wrapper.write(Type.VAR_INT, hand);
                        wrapper.write(Type.FLOAT, Float.valueOf(x));
                        wrapper.write(Type.FLOAT, Float.valueOf(y));
                        wrapper.write(Type.FLOAT, Float.valueOf(z));
                    }
                });
            }
        });
    }
}

