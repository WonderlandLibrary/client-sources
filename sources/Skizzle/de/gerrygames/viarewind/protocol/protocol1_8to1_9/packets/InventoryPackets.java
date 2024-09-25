/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.util.GsonUtil;

public class InventoryPackets {
    public static void register(Protocol protocol) {
        protocol.registerOutgoing(State.PLAY, 17, 50);
        protocol.registerOutgoing(State.PLAY, 18, 46, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short windowsId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        packetWrapper.user().get(Windows.class).remove(windowsId);
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 19, 45, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.COMPONENT);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        String type = packetWrapper.get(Type.STRING, 0);
                        if (type.equals("EntityHorse")) {
                            packetWrapper.passthrough(Type.INT);
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short windowId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        String windowType = packetWrapper.get(Type.STRING, 0);
                        packetWrapper.user().get(Windows.class).put(windowId, windowType);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        String name;
                        String type = packetWrapper.get(Type.STRING, 0);
                        if (type.equalsIgnoreCase("minecraft:shulker_box")) {
                            type = "minecraft:container";
                            packetWrapper.set(Type.STRING, 0, "minecraft:container");
                        }
                        if ((name = packetWrapper.get(Type.COMPONENT, 0).toString()).equalsIgnoreCase("{\"translate\":\"container.shulkerBox\"}")) {
                            packetWrapper.set(Type.COMPONENT, 0, GsonUtil.getJsonParser().parse("{\"text\":\"Shulker Box\"}"));
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 20, 48, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short windowId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        Item[] items = packetWrapper.read(Type.ITEM_ARRAY);
                        for (int i = 0; i < items.length; ++i) {
                            items[i] = ItemRewriter.toClient(items[i]);
                        }
                        if (windowId == 0 && items.length == 46) {
                            Item[] old = items;
                            items = new Item[45];
                            System.arraycopy(old, 0, items, 0, 45);
                        } else {
                            String type = packetWrapper.user().get(Windows.class).get(windowId);
                            if (type != null && type.equalsIgnoreCase("minecraft:brewing_stand")) {
                                System.arraycopy(items, 0, packetWrapper.user().get(Windows.class).getBrewingItems(windowId), 0, 4);
                                Windows.updateBrewingStand(packetWrapper.user(), items[4], windowId);
                                Item[] old = items;
                                items = new Item[old.length - 1];
                                System.arraycopy(old, 0, items, 0, 4);
                                System.arraycopy(old, 5, items, 4, old.length - 5);
                            }
                        }
                        packetWrapper.write(Type.ITEM_ARRAY, items);
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 21, 49);
        protocol.registerOutgoing(State.PLAY, 22, 47, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.ITEM);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.ITEM, 0, ItemRewriter.toClient(packetWrapper.get(Type.ITEM, 0)));
                        byte windowId = packetWrapper.get(Type.BYTE, 0);
                        short slot = packetWrapper.get(Type.SHORT, 0);
                        if (windowId == 0 && slot == 45) {
                            packetWrapper.cancel();
                            return;
                        }
                        String type = packetWrapper.user().get(Windows.class).get(windowId);
                        if (type == null) {
                            return;
                        }
                        if (type.equalsIgnoreCase("minecraft:brewing_stand")) {
                            if (slot > 4) {
                                slot = (short)(slot - 1);
                                packetWrapper.set(Type.SHORT, 0, slot);
                            } else {
                                if (slot == 4) {
                                    packetWrapper.cancel();
                                    Windows.updateBrewingStand(packetWrapper.user(), packetWrapper.get(Type.ITEM, 0), windowId);
                                    return;
                                }
                                packetWrapper.user().get(Windows.class).getBrewingItems((short)((short)windowId))[slot] = packetWrapper.get(Type.ITEM, 0);
                            }
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 8, 13, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short windowsId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        packetWrapper.user().get(Windows.class).remove(windowsId);
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 7, 14, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE, Type.VAR_INT);
                this.map(Type.ITEM);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.ITEM, 0, ItemRewriter.toServer(packetWrapper.get(Type.ITEM, 0)));
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short slot;
                        short windowId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        Windows windows = packetWrapper.user().get(Windows.class);
                        String type = windows.get(windowId);
                        if (type == null) {
                            return;
                        }
                        if (type.equalsIgnoreCase("minecraft:brewing_stand") && (slot = packetWrapper.get(Type.SHORT, 0).shortValue()) > 3) {
                            slot = (short)(slot + 1);
                            packetWrapper.set(Type.SHORT, 0, slot);
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 24, 16, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.map(Type.ITEM);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.ITEM, 0, ItemRewriter.toServer(packetWrapper.get(Type.ITEM, 0)));
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 6, 17);
    }
}

