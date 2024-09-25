/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Windows;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.utils.ChatUtil;
import java.util.UUID;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import us.myles.viaversion.libs.gson.JsonElement;

public class InventoryPackets {
    public static void register(Protocol protocol) {
        protocol.registerOutgoing(State.PLAY, 45, 45, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short windowId = packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                        String windowType = packetWrapper.read(Type.STRING);
                        short windowtypeId = (short)Windows.getInventoryType(windowType);
                        packetWrapper.user().get(Windows.class).types.put(windowId, windowtypeId);
                        packetWrapper.write(Type.UNSIGNED_BYTE, windowtypeId);
                        JsonElement titleComponent = packetWrapper.read(Type.COMPONENT);
                        String title = ChatUtil.jsonToLegacy(titleComponent);
                        title = ChatUtil.removeUnusedColor(title, '8');
                        if (title.length() > 32) {
                            title = title.substring(0, 32);
                        }
                        packetWrapper.write(Type.STRING, title);
                        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                        packetWrapper.write(Type.BOOLEAN, true);
                        if (windowtypeId == 11) {
                            packetWrapper.passthrough(Type.INT);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 46, 46, new PacketRemapper(){

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
        protocol.registerOutgoing(State.PLAY, 47, 47, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short windowId = packetWrapper.read(Type.BYTE).byteValue();
                        short windowType = packetWrapper.user().get(Windows.class).get(windowId);
                        packetWrapper.write(Type.BYTE, (byte)windowId);
                        short slot = packetWrapper.read(Type.SHORT);
                        if (windowType == 4) {
                            if (slot == 1) {
                                packetWrapper.cancel();
                                return;
                            }
                            if (slot >= 2) {
                                slot = (short)(slot - 1);
                            }
                        }
                        packetWrapper.write(Type.SHORT, slot);
                    }
                });
                this.map(Type.ITEM, Types1_7_6_10.COMPRESSED_NBT_ITEM);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        Item item = packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                        ItemRewriter.toClient(item);
                        packetWrapper.set(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0, item);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        UUID uuid;
                        short windowId = packetWrapper.get(Type.BYTE, 0).byteValue();
                        if (windowId != 0) {
                            return;
                        }
                        short slot = packetWrapper.get(Type.SHORT, 0);
                        if (slot < 5 || slot > 8) {
                            return;
                        }
                        Item item = packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        Item[] equipment = tracker.getPlayerEquipment(uuid = packetWrapper.user().get(ProtocolInfo.class).getUuid());
                        if (equipment == null) {
                            equipment = new Item[5];
                            tracker.setPlayerEquipment(uuid, equipment);
                        }
                        equipment[9 - slot] = item;
                        if (tracker.getGamemode() == 3) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 48, 48, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short windowId = packetWrapper.read(Type.UNSIGNED_BYTE);
                        short windowType = packetWrapper.user().get(Windows.class).get(windowId);
                        packetWrapper.write(Type.UNSIGNED_BYTE, windowId);
                        Item[] items = packetWrapper.read(Type.ITEM_ARRAY);
                        if (windowType == 4) {
                            Item[] old = items;
                            items = new Item[old.length - 1];
                            items[0] = old[0];
                            System.arraycopy(old, 2, items, 1, old.length - 3);
                        }
                        for (int i = 0; i < items.length; ++i) {
                            items[i] = ItemRewriter.toClient(items[i]);
                        }
                        packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM_ARRAY, items);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        GameProfileStorage.GameProfile profile;
                        UUID uuid;
                        short windowId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        if (windowId != 0) {
                            return;
                        }
                        Item[] items = packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM_ARRAY, 0);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        Item[] equipment = tracker.getPlayerEquipment(uuid = packetWrapper.user().get(ProtocolInfo.class).getUuid());
                        if (equipment == null) {
                            equipment = new Item[5];
                            tracker.setPlayerEquipment(uuid, equipment);
                        }
                        for (int i = 5; i < 9; ++i) {
                            equipment[9 - i] = items[i];
                            if (tracker.getGamemode() != 3) continue;
                            items[i] = null;
                        }
                        if (tracker.getGamemode() == 3 && (profile = packetWrapper.user().get(GameProfileStorage.class).get(uuid)) != null) {
                            items[5] = profile.getSkull();
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 49, 49, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short windowId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        Windows windows = packetWrapper.user().get(Windows.class);
                        short windowType = windows.get(windowId);
                        short property = packetWrapper.get(Type.SHORT, 0);
                        short value = packetWrapper.get(Type.SHORT, 1);
                        if (windowType == -1) {
                            return;
                        }
                        if (windowType == 2) {
                            Windows.Furnace furnace = windows.furnace.computeIfAbsent(windowId, x -> new Windows.Furnace());
                            if (property == 0 || property == 1) {
                                if (property == 0) {
                                    furnace.setFuelLeft(value);
                                } else {
                                    furnace.setMaxFuel(value);
                                }
                                if (furnace.getMaxFuel() == 0) {
                                    packetWrapper.cancel();
                                    return;
                                }
                                value = (short)(200 * furnace.getFuelLeft() / furnace.getMaxFuel());
                                packetWrapper.set(Type.SHORT, 0, (short)1);
                                packetWrapper.set(Type.SHORT, 1, value);
                            } else if (property == 2 || property == 3) {
                                if (property == 2) {
                                    furnace.setProgress(value);
                                } else {
                                    furnace.setMaxProgress(value);
                                }
                                if (furnace.getMaxProgress() == 0) {
                                    packetWrapper.cancel();
                                    return;
                                }
                                value = (short)(200 * furnace.getProgress() / furnace.getMaxProgress());
                                packetWrapper.set(Type.SHORT, 0, (short)0);
                                packetWrapper.set(Type.SHORT, 1, value);
                            }
                        } else if (windowType == 4) {
                            if (property > 2) {
                                packetWrapper.cancel();
                                return;
                            }
                        } else if (windowType == 8) {
                            windows.levelCost = value;
                            windows.anvilId = windowId;
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 13, 13, new PacketRemapper(){

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
        protocol.registerIncoming(State.PLAY, 14, 14, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short windowId = packetWrapper.read(Type.BYTE).byteValue();
                        packetWrapper.write(Type.UNSIGNED_BYTE, windowId);
                        short windowType = packetWrapper.user().get(Windows.class).get(windowId);
                        short slot = packetWrapper.read(Type.SHORT);
                        if (windowType == 4 && slot > 0) {
                            slot = (short)(slot + 1);
                        }
                        packetWrapper.write(Type.SHORT, slot);
                    }
                });
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Types1_7_6_10.COMPRESSED_NBT_ITEM, Type.ITEM);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        Item item = packetWrapper.get(Type.ITEM, 0);
                        ItemRewriter.toServer(item);
                        packetWrapper.set(Type.ITEM, 0, item);
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 15, 15, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short action = packetWrapper.get(Type.SHORT, 0);
                        if (action == -89) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 16, 16, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.map(Types1_7_6_10.COMPRESSED_NBT_ITEM, Type.ITEM);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        Item item = packetWrapper.get(Type.ITEM, 0);
                        ItemRewriter.toServer(item);
                        packetWrapper.set(Type.ITEM, 0, item);
                    }
                });
            }
        });
    }
}

