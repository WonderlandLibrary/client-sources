/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import de.gerrygames.viarewind.utils.ChatUtil;
import de.gerrygames.viarewind.utils.PacketUtil;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.UUID;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.Entity1_10Types;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_8;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueCreator;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_8;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;

public class PlayerPackets {
    public static void register(Protocol protocol) {
        protocol.registerOutgoing(State.PLAY, 6, 11);
        protocol.registerOutgoing(State.PLAY, 7, 55);
        protocol.registerOutgoing(State.PLAY, 12, -1, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                        UUID uuid = packetWrapper.read(Type.UUID);
                        int action = packetWrapper.read(Type.VAR_INT);
                        BossBarStorage bossBarStorage = packetWrapper.user().get(BossBarStorage.class);
                        if (action == 0) {
                            bossBarStorage.add(uuid, ChatUtil.jsonToLegacy(packetWrapper.read(Type.COMPONENT)), packetWrapper.read(Type.FLOAT).floatValue());
                            packetWrapper.read(Type.VAR_INT);
                            packetWrapper.read(Type.VAR_INT);
                            packetWrapper.read(Type.UNSIGNED_BYTE);
                        } else if (action == 1) {
                            bossBarStorage.remove(uuid);
                        } else if (action == 2) {
                            bossBarStorage.updateHealth(uuid, packetWrapper.read(Type.FLOAT).floatValue());
                        } else if (action == 3) {
                            String title = ChatUtil.jsonToLegacy(packetWrapper.read(Type.COMPONENT));
                            bossBarStorage.updateTitle(uuid, title);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 14, 58);
        protocol.registerOutgoing(State.PLAY, 15, 2);
        protocol.registerOutgoing(State.PLAY, 23, -1, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 24, 63, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        String channel = packetWrapper.get(Type.STRING, 0);
                        if (channel.equalsIgnoreCase("MC|TrList")) {
                            packetWrapper.passthrough(Type.INT);
                            int size = packetWrapper.isReadable(Type.BYTE, 0) ? packetWrapper.passthrough(Type.BYTE).byteValue() : packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                            for (int i = 0; i < size; ++i) {
                                packetWrapper.write(Type.ITEM, ItemRewriter.toClient(packetWrapper.read(Type.ITEM)));
                                packetWrapper.write(Type.ITEM, ItemRewriter.toClient(packetWrapper.read(Type.ITEM)));
                                boolean has3Items = packetWrapper.passthrough(Type.BOOLEAN);
                                if (has3Items) {
                                    packetWrapper.write(Type.ITEM, ItemRewriter.toClient(packetWrapper.read(Type.ITEM)));
                                }
                                packetWrapper.passthrough(Type.BOOLEAN);
                                packetWrapper.passthrough(Type.INT);
                                packetWrapper.passthrough(Type.INT);
                            }
                        } else if (channel.equalsIgnoreCase("MC|BOpen")) {
                            packetWrapper.read(Type.VAR_INT);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 26, 64);
        protocol.registerOutgoing(State.PLAY, 30, 43, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLOAT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short reason = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        if (reason == 3) {
                            packetWrapper.user().get(EntityTracker.class).setPlayerGamemode(packetWrapper.get(Type.FLOAT, 0).intValue());
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 35, 1, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        tracker.setPlayerId(packetWrapper.get(Type.INT, 0));
                        tracker.setPlayerGamemode(packetWrapper.get(Type.UNSIGNED_BYTE, 0).shortValue());
                        tracker.getClientEntityTypes().put(tracker.getPlayerId(), Entity1_10Types.EntityType.ENTITY_HUMAN);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        ClientWorld world = packetWrapper.user().get(ClientWorld.class);
                        world.setEnvironment(packetWrapper.get(Type.BYTE, 0).byteValue());
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 42, 54);
        protocol.registerOutgoing(State.PLAY, 43, 57);
        protocol.registerOutgoing(State.PLAY, 45, 56);
        protocol.registerOutgoing(State.PLAY, 46, 8, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        PlayerPosition pos = packetWrapper.user().get(PlayerPosition.class);
                        int teleportId = packetWrapper.read(Type.VAR_INT);
                        pos.setConfirmId(teleportId);
                        byte flags = packetWrapper.get(Type.BYTE, 0);
                        double x = packetWrapper.get(Type.DOUBLE, 0);
                        double y = packetWrapper.get(Type.DOUBLE, 1);
                        double z = packetWrapper.get(Type.DOUBLE, 2);
                        float yaw = packetWrapper.get(Type.FLOAT, 0).floatValue();
                        float pitch = packetWrapper.get(Type.FLOAT, 1).floatValue();
                        packetWrapper.set(Type.BYTE, 0, (byte)0);
                        if (flags != 0) {
                            if ((flags & 1) != 0) {
                                packetWrapper.set(Type.DOUBLE, 0, x += pos.getPosX());
                            }
                            if ((flags & 2) != 0) {
                                packetWrapper.set(Type.DOUBLE, 1, y += pos.getPosY());
                            }
                            if ((flags & 4) != 0) {
                                packetWrapper.set(Type.DOUBLE, 2, z += pos.getPosZ());
                            }
                            if ((flags & 8) != 0) {
                                packetWrapper.set(Type.FLOAT, 0, Float.valueOf(yaw += pos.getYaw()));
                            }
                            if ((flags & 0x10) != 0) {
                                packetWrapper.set(Type.FLOAT, 1, Float.valueOf(pitch += pos.getPitch()));
                            }
                        }
                        pos.setPos(x, y, z);
                        pos.setYaw(yaw);
                        pos.setPitch(pitch);
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 50, 72);
        protocol.registerOutgoing(State.PLAY, 51, 7, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.user().get(EntityTracker.class).setPlayerGamemode(packetWrapper.get(Type.UNSIGNED_BYTE, 1).shortValue());
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.user().get(BossBarStorage.class).updateLocation();
                        packetWrapper.user().get(BossBarStorage.class).changeWorld();
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        ClientWorld world = packetWrapper.user().get(ClientWorld.class);
                        world.setEnvironment(packetWrapper.get(Type.INT, 0));
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 54, 67);
        protocol.registerOutgoing(State.PLAY, 55, 9);
        protocol.registerOutgoing(State.PLAY, 61, 31);
        protocol.registerOutgoing(State.PLAY, 62, 6);
        protocol.registerOutgoing(State.PLAY, 67, 5);
        protocol.registerOutgoing(State.PLAY, 69, 69);
        protocol.registerOutgoing(State.PLAY, 72, 71);
        protocol.registerIncoming(State.PLAY, 2, 1, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        String msg = packetWrapper.get(Type.STRING, 0);
                        if (msg.toLowerCase().startsWith("/offhand")) {
                            packetWrapper.cancel();
                            PacketWrapper swapItems = new PacketWrapper(19, null, packetWrapper.user());
                            swapItems.write(Type.VAR_INT, 6);
                            swapItems.write(Type.POSITION, new Position(0, 0, 0));
                            swapItems.write(Type.BYTE, (byte)-1);
                            PacketUtil.sendToServer(swapItems, Protocol1_8TO1_9.class, true, true);
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 5, 15);
        protocol.registerIncoming(State.PLAY, 10, 2, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int type = packetWrapper.get(Type.VAR_INT, 1);
                        if (type == 2) {
                            packetWrapper.passthrough(Type.FLOAT);
                            packetWrapper.passthrough(Type.FLOAT);
                            packetWrapper.passthrough(Type.FLOAT);
                        }
                        if (type == 2 || type == 0) {
                            packetWrapper.write(Type.VAR_INT, 0);
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 15, 3, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int playerId;
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        if (tracker.isInsideVehicle(playerId = tracker.getPlayerId())) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 12, 4, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        PlayerPosition pos = packetWrapper.user().get(PlayerPosition.class);
                        if (pos.getConfirmId() != -1) {
                            return;
                        }
                        pos.setPos(packetWrapper.get(Type.DOUBLE, 0), packetWrapper.get(Type.DOUBLE, 1), packetWrapper.get(Type.DOUBLE, 2));
                        pos.setOnGround(packetWrapper.get(Type.BOOLEAN, 0));
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.user().get(BossBarStorage.class).updateLocation();
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 14, 5, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        PlayerPosition pos = packetWrapper.user().get(PlayerPosition.class);
                        if (pos.getConfirmId() != -1) {
                            return;
                        }
                        pos.setYaw(packetWrapper.get(Type.FLOAT, 0).floatValue());
                        pos.setPitch(packetWrapper.get(Type.FLOAT, 1).floatValue());
                        pos.setOnGround(packetWrapper.get(Type.BOOLEAN, 0));
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.user().get(BossBarStorage.class).updateLocation();
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 13, 6, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        double x = packetWrapper.get(Type.DOUBLE, 0);
                        double y = packetWrapper.get(Type.DOUBLE, 1);
                        double z = packetWrapper.get(Type.DOUBLE, 2);
                        float yaw = packetWrapper.get(Type.FLOAT, 0).floatValue();
                        float pitch = packetWrapper.get(Type.FLOAT, 1).floatValue();
                        boolean onGround = packetWrapper.get(Type.BOOLEAN, 0);
                        PlayerPosition pos = packetWrapper.user().get(PlayerPosition.class);
                        if (pos.getConfirmId() != -1) {
                            if (pos.getPosX() == x && pos.getPosY() == y && pos.getPosZ() == z && pos.getYaw() == yaw && pos.getPitch() == pitch) {
                                PacketWrapper confirmTeleport = packetWrapper.create(0);
                                confirmTeleport.write(Type.VAR_INT, pos.getConfirmId());
                                PacketUtil.sendToServer(confirmTeleport, Protocol1_8TO1_9.class, true, true);
                                pos.setConfirmId(-1);
                            }
                        } else {
                            pos.setPos(x, y, z);
                            pos.setYaw(yaw);
                            pos.setPitch(pitch);
                            pos.setOnGround(onGround);
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.user().get(BossBarStorage.class).updateLocation();
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 19, 7, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BYTE, Type.VAR_INT);
                this.map(Type.POSITION);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int state = packetWrapper.get(Type.VAR_INT, 0);
                        if (state == 0) {
                            packetWrapper.user().get(BlockPlaceDestroyTracker.class).setMining(true);
                        } else if (state == 2) {
                            BlockPlaceDestroyTracker tracker = packetWrapper.user().get(BlockPlaceDestroyTracker.class);
                            tracker.setMining(false);
                            tracker.setLastMining(System.currentTimeMillis() + 100L);
                            packetWrapper.user().get(Cooldown.class).setLastHit(0L);
                        } else if (state == 1) {
                            BlockPlaceDestroyTracker tracker = packetWrapper.user().get(BlockPlaceDestroyTracker.class);
                            tracker.setMining(false);
                            tracker.setLastMining(0L);
                            packetWrapper.user().get(Cooldown.class).hit();
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 28, 8, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.BYTE, Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.ITEM);
                    }
                });
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.VAR_INT, 0);
                    }
                });
                this.map(Type.BYTE, Type.UNSIGNED_BYTE);
                this.map(Type.BYTE, Type.UNSIGNED_BYTE);
                this.map(Type.BYTE, Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        if (packetWrapper.get(Type.VAR_INT, 0) == -1) {
                            packetWrapper.cancel();
                            PacketWrapper useItem = new PacketWrapper(29, null, packetWrapper.user());
                            useItem.write(Type.VAR_INT, 0);
                            PacketUtil.sendToServer(useItem, Protocol1_8TO1_9.class, true, true);
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        if (packetWrapper.get(Type.VAR_INT, 0) != -1) {
                            packetWrapper.user().get(BlockPlaceDestroyTracker.class).place();
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 23, 9, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.user().get(Cooldown.class).hit();
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 26, 10, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                        final PacketWrapper delayedPacket = new PacketWrapper(26, null, packetWrapper.user());
                        delayedPacket.write(Type.VAR_INT, 0);
                        Protocol1_8TO1_9.TIMER.schedule(new TimerTask(){

                            @Override
                            public void run() {
                                PacketUtil.sendToServer(delayedPacket, Protocol1_8TO1_9.class);
                            }
                        }, 5L);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.user().get(BlockPlaceDestroyTracker.class).updateMining();
                        packetWrapper.user().get(Cooldown.class).hit();
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 20, 11, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        PlayerPosition pos;
                        int action = packetWrapper.get(Type.VAR_INT, 1);
                        if (action == 6) {
                            packetWrapper.set(Type.VAR_INT, 1, 7);
                        } else if (action == 0 && !(pos = packetWrapper.user().get(PlayerPosition.class)).isOnGround()) {
                            PacketWrapper elytra = new PacketWrapper(20, null, packetWrapper.user());
                            elytra.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                            elytra.write(Type.VAR_INT, 8);
                            elytra.write(Type.VAR_INT, 0);
                            PacketUtil.sendToServer(elytra, Protocol1_8TO1_9.class, true, false);
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 21, 12, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int playerId;
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        int vehicle = tracker.getVehicle(playerId = tracker.getPlayerId());
                        if (vehicle != -1 && tracker.getClientEntityTypes().get(vehicle) == Entity1_10Types.EntityType.BOAT) {
                            PacketWrapper steerBoat = new PacketWrapper(17, null, packetWrapper.user());
                            float left = packetWrapper.get(Type.FLOAT, 0).floatValue();
                            float forward = packetWrapper.get(Type.FLOAT, 1).floatValue();
                            steerBoat.write(Type.BOOLEAN, forward != 0.0f || left < 0.0f);
                            steerBoat.write(Type.BOOLEAN, forward != 0.0f || left > 0.0f);
                            PacketUtil.sendToServer(steerBoat, Protocol1_8TO1_9.class);
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 25, 18, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        for (int i = 0; i < 4; ++i) {
                            packetWrapper.write(Type.STRING, ChatUtil.jsonToLegacy(packetWrapper.read(Type.COMPONENT)));
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 18, 19);
        protocol.registerIncoming(State.PLAY, 1, 20, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.BOOLEAN, false);
                    }
                });
                this.map(Type.OPTIONAL_POSITION);
            }
        });
        protocol.registerIncoming(State.PLAY, 4, 21, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.BYTE, Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.VAR_INT, 1);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short flags = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        PacketWrapper updateSkin = new PacketWrapper(28, null, packetWrapper.user());
                        updateSkin.write(Type.VAR_INT, packetWrapper.user().get(EntityTracker.class).getPlayerId());
                        ArrayList<Metadata> metadata = new ArrayList<Metadata>();
                        metadata.add(new Metadata(10, MetaType1_8.Byte, (byte)flags));
                        updateSkin.write(Types1_8.METADATA_LIST, metadata);
                        PacketUtil.sendPacket(updateSkin, Protocol1_8TO1_9.class);
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 3, 22);
        protocol.registerIncoming(State.PLAY, 9, 23, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        String channel = packetWrapper.get(Type.STRING, 0);
                        if (channel.equalsIgnoreCase("MC|BEdit") || channel.equalsIgnoreCase("MC|BSign")) {
                            Item book = packetWrapper.passthrough(Type.ITEM);
                            book.setIdentifier(386);
                            CompoundTag tag = book.getTag();
                            if (tag.contains("pages")) {
                                ListTag pages = (ListTag)tag.get("pages");
                                for (int i = 0; i < pages.size(); ++i) {
                                    StringTag page = (StringTag)pages.get(i);
                                    String value = page.getValue();
                                    value = ChatUtil.jsonToLegacy(value);
                                    page.setValue(value);
                                }
                            }
                        } else if (channel.equalsIgnoreCase("MC|AdvCdm")) {
                            channel = "MC|AdvCmd";
                            packetWrapper.set(Type.STRING, 0, "MC|AdvCmd");
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 27, 24);
        protocol.registerIncoming(State.PLAY, 22, 25);
    }
}

