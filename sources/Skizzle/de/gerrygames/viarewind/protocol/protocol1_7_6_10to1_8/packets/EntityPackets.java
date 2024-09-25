/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import java.util.List;
import java.util.UUID;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.entities.Entity1_10Types;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_8;
import us.myles.ViaVersion.packets.State;

public class EntityPackets {
    public static void register(Protocol protocol) {
        protocol.registerOutgoing(State.PLAY, 4, 4, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.SHORT);
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
                        if (packetWrapper.get(Type.SHORT, 0) > 4) {
                            packetWrapper.cancel();
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        if (packetWrapper.isCancelled()) {
                            return;
                        }
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        UUID uuid = tracker.getPlayerUUID(packetWrapper.get(Type.INT, 0));
                        if (uuid == null) {
                            return;
                        }
                        Item[] equipment = tracker.getPlayerEquipment(uuid);
                        if (equipment == null) {
                            equipment = new Item[5];
                            tracker.setPlayerEquipment(uuid, equipment);
                        }
                        equipment[packetWrapper.get(Type.SHORT, (int)0).shortValue()] = packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                        GameProfileStorage storage = packetWrapper.user().get(GameProfileStorage.class);
                        GameProfileStorage.GameProfile profile = storage.get(uuid);
                        if (profile != null && profile.gamemode == 3) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 10, 10, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        Position position = packetWrapper.read(Type.POSITION);
                        packetWrapper.write(Type.INT, position.getX());
                        packetWrapper.write(Type.UNSIGNED_BYTE, position.getY());
                        packetWrapper.write(Type.INT, position.getZ());
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 13, 13, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map((Type)Type.VAR_INT, Type.INT);
            }
        });
        protocol.registerOutgoing(State.PLAY, 18, 18, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
            }
        });
        protocol.registerOutgoing(State.PLAY, 19, 19, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int[] entityIds = packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        for (int entityId : entityIds) {
                            tracker.removeEntity(entityId);
                        }
                        while (entityIds.length > 127) {
                            int[] entityIds2 = new int[127];
                            System.arraycopy(entityIds, 0, entityIds2, 0, 127);
                            int[] temp = new int[entityIds.length - 127];
                            System.arraycopy(entityIds, 127, temp, 0, temp.length);
                            entityIds = temp;
                            PacketWrapper destroy = new PacketWrapper(19, null, packetWrapper.user());
                            destroy.write(Types1_7_6_10.INT_ARRAY, entityIds2);
                            PacketUtil.sendPacket(destroy, Protocol1_7_6_10TO1_8.class);
                        }
                        packetWrapper.write(Types1_7_6_10.INT_ARRAY, entityIds);
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 20, 20, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
            }
        });
        protocol.registerOutgoing(State.PLAY, 21, 21, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.BOOLEAN);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.INT, 0);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                        if (replacement != null) {
                            packetWrapper.cancel();
                            byte x = packetWrapper.get(Type.BYTE, 0);
                            byte y = packetWrapper.get(Type.BYTE, 1);
                            byte z = packetWrapper.get(Type.BYTE, 2);
                            replacement.relMove((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 22, 22, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.BOOLEAN);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.INT, 0);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                        if (replacement != null) {
                            packetWrapper.cancel();
                            byte yaw = packetWrapper.get(Type.BYTE, 0);
                            byte pitch = packetWrapper.get(Type.BYTE, 1);
                            replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 23, 23, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.BOOLEAN);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.INT, 0);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                        if (replacement != null) {
                            packetWrapper.cancel();
                            byte x = packetWrapper.get(Type.BYTE, 0);
                            byte y = packetWrapper.get(Type.BYTE, 1);
                            byte z = packetWrapper.get(Type.BYTE, 2);
                            byte yaw = packetWrapper.get(Type.BYTE, 3);
                            byte pitch = packetWrapper.get(Type.BYTE, 4);
                            replacement.relMove((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                            replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 24, 24, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.BOOLEAN);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.INT, 0);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        Entity1_10Types.EntityType type = tracker.getClientEntityTypes().get(entityId);
                        if (type == Entity1_10Types.EntityType.MINECART_ABSTRACT) {
                            int y = packetWrapper.get(Type.INT, 2);
                            packetWrapper.set(Type.INT, 2, y += 12);
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.INT, 0);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                        if (replacement != null) {
                            packetWrapper.cancel();
                            int x = packetWrapper.get(Type.INT, 1);
                            int y = packetWrapper.get(Type.INT, 2);
                            int z = packetWrapper.get(Type.INT, 3);
                            byte yaw = packetWrapper.get(Type.BYTE, 0);
                            byte pitch = packetWrapper.get(Type.BYTE, 1);
                            replacement.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                            replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 25, 25, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.INT, 0);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                        if (replacement != null) {
                            packetWrapper.cancel();
                            byte yaw = packetWrapper.get(Type.BYTE, 0);
                            replacement.setHeadYaw((float)yaw * 360.0f / 256.0f);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 27, 27, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        boolean leash = packetWrapper.get(Type.BOOLEAN, 0);
                        if (leash) {
                            return;
                        }
                        int passenger = packetWrapper.get(Type.INT, 0);
                        int vehicle = packetWrapper.get(Type.INT, 1);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        tracker.setPassenger(vehicle, passenger);
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 28, 28, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
                        int entityId = wrapper.get(Type.INT, 0);
                        EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                        if (tracker.getClientEntityTypes().containsKey(entityId)) {
                            EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                            if (replacement != null) {
                                wrapper.cancel();
                                replacement.updateMetadata(metadataList);
                            } else {
                                MetadataRewriter.transform(tracker.getClientEntityTypes().get(entityId), metadataList);
                                if (metadataList.isEmpty()) {
                                    wrapper.cancel();
                                }
                            }
                        } else {
                            tracker.addMetadataToBuffer(entityId, metadataList);
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 29, 29, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map((Type)Type.VAR_INT, Type.SHORT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.BYTE);
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 30, 30, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
            }
        });
        protocol.registerOutgoing(State.PLAY, 32, 32, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.INT, 0);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        if (tracker.getEntityReplacement(entityId) != null) {
                            packetWrapper.cancel();
                            return;
                        }
                        int amount = packetWrapper.passthrough(Type.INT);
                        for (int i = 0; i < amount; ++i) {
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.passthrough(Type.DOUBLE);
                            int modifierlength = packetWrapper.read(Type.VAR_INT);
                            packetWrapper.write(Type.SHORT, (short)modifierlength);
                            for (int j = 0; j < modifierlength; ++j) {
                                packetWrapper.passthrough(Type.UUID);
                                packetWrapper.passthrough(Type.DOUBLE);
                                packetWrapper.passthrough(Type.BYTE);
                            }
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 73, -1, new PacketRemapper(){

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
    }
}

