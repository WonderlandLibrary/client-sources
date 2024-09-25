/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Levitation;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.util.RelativeMoveUtil;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Pair;
import us.myles.ViaVersion.api.entities.Entity1_10Types;
import us.myles.ViaVersion.api.minecraft.Vector;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueCreator;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_8;
import us.myles.ViaVersion.api.type.types.version.Types1_9;
import us.myles.ViaVersion.packets.State;

public class EntityPackets {
    public static void register(Protocol protocol) {
        protocol.registerOutgoing(State.PLAY, 27, 26, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        byte status = packetWrapper.read(Type.BYTE);
                        if (status > 23) {
                            packetWrapper.cancel();
                            return;
                        }
                        packetWrapper.write(Type.BYTE, status);
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 37, 21, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.VAR_INT, 0);
                        short relX = packetWrapper.read(Type.SHORT);
                        short relY = packetWrapper.read(Type.SHORT);
                        short relZ = packetWrapper.read(Type.SHORT);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                        if (replacement != null) {
                            packetWrapper.cancel();
                            replacement.relMove((double)relX / 4096.0, (double)relY / 4096.0, (double)relZ / 4096.0);
                            return;
                        }
                        Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), entityId, relX, relY, relZ);
                        packetWrapper.write(Type.BYTE, (byte)moves[0].getBlockX());
                        packetWrapper.write(Type.BYTE, (byte)moves[0].getBlockY());
                        packetWrapper.write(Type.BYTE, (byte)moves[0].getBlockZ());
                        boolean onGround = packetWrapper.passthrough(Type.BOOLEAN);
                        if (moves.length > 1) {
                            PacketWrapper secondPacket = new PacketWrapper(21, null, packetWrapper.user());
                            secondPacket.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                            secondPacket.write(Type.BYTE, (byte)moves[1].getBlockX());
                            secondPacket.write(Type.BYTE, (byte)moves[1].getBlockY());
                            secondPacket.write(Type.BYTE, (byte)moves[1].getBlockZ());
                            secondPacket.write(Type.BOOLEAN, onGround);
                            PacketUtil.sendPacket(secondPacket, Protocol1_8TO1_9.class);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 38, 23, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.VAR_INT, 0);
                        short relX = packetWrapper.read(Type.SHORT);
                        short relY = packetWrapper.read(Type.SHORT);
                        short relZ = packetWrapper.read(Type.SHORT);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                        if (replacement != null) {
                            packetWrapper.cancel();
                            replacement.relMove((double)relX / 4096.0, (double)relY / 4096.0, (double)relZ / 4096.0);
                            replacement.setYawPitch((float)packetWrapper.read(Type.BYTE).byteValue() * 360.0f / 256.0f, (float)packetWrapper.read(Type.BYTE).byteValue() * 360.0f / 256.0f);
                            return;
                        }
                        Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), entityId, relX, relY, relZ);
                        packetWrapper.write(Type.BYTE, (byte)moves[0].getBlockX());
                        packetWrapper.write(Type.BYTE, (byte)moves[0].getBlockY());
                        packetWrapper.write(Type.BYTE, (byte)moves[0].getBlockZ());
                        byte yaw = packetWrapper.passthrough(Type.BYTE);
                        byte pitch = packetWrapper.passthrough(Type.BYTE);
                        boolean onGround = packetWrapper.passthrough(Type.BOOLEAN);
                        Entity1_10Types.EntityType type = packetWrapper.user().get(EntityTracker.class).getClientEntityTypes().get(entityId);
                        if (type == Entity1_10Types.EntityType.BOAT) {
                            yaw = (byte)(yaw - 64);
                            packetWrapper.set(Type.BYTE, 3, yaw);
                        }
                        if (moves.length > 1) {
                            PacketWrapper secondPacket = new PacketWrapper(23, null, packetWrapper.user());
                            secondPacket.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                            secondPacket.write(Type.BYTE, (byte)moves[1].getBlockX());
                            secondPacket.write(Type.BYTE, (byte)moves[1].getBlockY());
                            secondPacket.write(Type.BYTE, (byte)moves[1].getBlockZ());
                            secondPacket.write(Type.BYTE, yaw);
                            secondPacket.write(Type.BYTE, pitch);
                            secondPacket.write(Type.BOOLEAN, onGround);
                            PacketUtil.sendPacket(secondPacket, Protocol1_8TO1_9.class);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 39, 22, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.VAR_INT, 0);
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
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.VAR_INT, 0);
                        Entity1_10Types.EntityType type = packetWrapper.user().get(EntityTracker.class).getClientEntityTypes().get(entityId);
                        if (type == Entity1_10Types.EntityType.BOAT) {
                            byte yaw = packetWrapper.get(Type.BYTE, 0);
                            yaw = (byte)(yaw - 64);
                            packetWrapper.set(Type.BYTE, 0, yaw);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 40, 20);
        protocol.registerOutgoing(State.PLAY, 41, 24, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper packetWrapper) throws Exception {
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        int vehicle = tracker.getVehicle(tracker.getPlayerId());
                        if (vehicle == -1) {
                            packetWrapper.cancel();
                        }
                        packetWrapper.write(Type.VAR_INT, vehicle);
                    }
                });
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.FLOAT, Protocol1_8TO1_9.DEGREES_TO_ANGLE);
                this.map(Type.FLOAT, Protocol1_8TO1_9.DEGREES_TO_ANGLE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        if (packetWrapper.isCancelled()) {
                            return;
                        }
                        PlayerPosition position = packetWrapper.user().get(PlayerPosition.class);
                        double x = (double)packetWrapper.get(Type.INT, 0).intValue() / 32.0;
                        double y = (double)packetWrapper.get(Type.INT, 1).intValue() / 32.0;
                        double z = (double)packetWrapper.get(Type.INT, 2).intValue() / 32.0;
                        position.setPos(x, y, z);
                    }
                });
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.BOOLEAN, true);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.VAR_INT, 0);
                        Entity1_10Types.EntityType type = packetWrapper.user().get(EntityTracker.class).getClientEntityTypes().get(entityId);
                        if (type == Entity1_10Types.EntityType.BOAT) {
                            byte yaw = packetWrapper.get(Type.BYTE, 1);
                            yaw = (byte)(yaw - 64);
                            packetWrapper.set(Type.BYTE, 0, yaw);
                            int y = packetWrapper.get(Type.INT, 1);
                            packetWrapper.set(Type.INT, 1, y += 10);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 47, 10);
        protocol.registerOutgoing(State.PLAY, 48, 19, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        for (int entityId : packetWrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0)) {
                            tracker.removeEntity(entityId);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 49, 30, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        byte id = packetWrapper.get(Type.BYTE, 0);
                        if (id > 23) {
                            packetWrapper.cancel();
                        }
                        if (id == 25) {
                            if (packetWrapper.get(Type.VAR_INT, 0).intValue() != packetWrapper.user().get(EntityTracker.class).getPlayerId()) {
                                return;
                            }
                            Levitation levitation = packetWrapper.user().get(Levitation.class);
                            levitation.setActive(false);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 52, 25, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.VAR_INT, 0);
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
        protocol.registerOutgoing(State.PLAY, 57, 28, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = wrapper.get(Types1_8.METADATA_LIST, 0);
                        int entityId = wrapper.get(Type.VAR_INT, 0);
                        EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                        if (tracker.getClientEntityTypes().containsKey(entityId)) {
                            MetadataRewriter.transform(tracker.getClientEntityTypes().get(entityId), metadataList);
                            if (metadataList.isEmpty()) {
                                wrapper.cancel();
                            }
                        } else {
                            tracker.addMetadataToBuffer(entityId, metadataList);
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 58, 27, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.BOOLEAN, true);
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 59, 18);
        protocol.registerOutgoing(State.PLAY, 60, 4, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int slot = packetWrapper.read(Type.VAR_INT);
                        if (slot == 1) {
                            packetWrapper.cancel();
                        } else if (slot > 1) {
                            --slot;
                        }
                        packetWrapper.write(Type.SHORT, (short)slot);
                    }
                });
                this.map(Type.ITEM);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.ITEM, 0, ItemRewriter.toClient(packetWrapper.get(Type.ITEM, 0)));
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 64, 27, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                        EntityTracker entityTracker = packetWrapper.user().get(EntityTracker.class);
                        int vehicle = packetWrapper.read(Type.VAR_INT);
                        int count = packetWrapper.read(Type.VAR_INT);
                        ArrayList<Integer> passengers = new ArrayList<Integer>();
                        for (int i = 0; i < count; ++i) {
                            passengers.add(packetWrapper.read(Type.VAR_INT));
                        }
                        List<Integer> oldPassengers = entityTracker.getPassengers(vehicle);
                        entityTracker.setPassengers(vehicle, passengers);
                        if (!oldPassengers.isEmpty()) {
                            for (Integer passenger : oldPassengers) {
                                PacketWrapper detach = new PacketWrapper(27, null, packetWrapper.user());
                                detach.write(Type.INT, passenger);
                                detach.write(Type.INT, -1);
                                detach.write(Type.BOOLEAN, false);
                                PacketUtil.sendPacket(detach, Protocol1_8TO1_9.class);
                            }
                        }
                        for (int i = 0; i < count; ++i) {
                            int v = i == 0 ? vehicle : passengers.get(i - 1);
                            int p = passengers.get(i);
                            PacketWrapper attach = new PacketWrapper(27, null, packetWrapper.user());
                            attach.write(Type.INT, p);
                            attach.write(Type.INT, v);
                            attach.write(Type.BOOLEAN, false);
                            PacketUtil.sendPacket(attach, Protocol1_8TO1_9.class);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 73, 13);
        protocol.registerOutgoing(State.PLAY, 74, 24, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.VAR_INT, 0);
                        Entity1_10Types.EntityType type = packetWrapper.user().get(EntityTracker.class).getClientEntityTypes().get(entityId);
                        if (type == Entity1_10Types.EntityType.BOAT) {
                            byte yaw = packetWrapper.get(Type.BYTE, 1);
                            yaw = (byte)(yaw - 64);
                            packetWrapper.set(Type.BYTE, 0, yaw);
                            int y = packetWrapper.get(Type.INT, 1);
                            packetWrapper.set(Type.INT, 1, y += 10);
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.VAR_INT, 0);
                        packetWrapper.user().get(EntityTracker.class).resetEntityOffset(entityId);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.get(Type.VAR_INT, 0);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                        if (replacement != null) {
                            packetWrapper.cancel();
                            int x = packetWrapper.get(Type.INT, 0);
                            int y = packetWrapper.get(Type.INT, 1);
                            int z = packetWrapper.get(Type.INT, 2);
                            byte yaw = packetWrapper.get(Type.BYTE, 0);
                            byte pitch = packetWrapper.get(Type.BYTE, 1);
                            replacement.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                            replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 75, 32, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        boolean player = packetWrapper.get(Type.VAR_INT, 0).intValue() == packetWrapper.user().get(EntityTracker.class).getPlayerId();
                        int size = packetWrapper.get(Type.INT, 0);
                        int removed = 0;
                        for (int i = 0; i < size; ++i) {
                            String key = packetWrapper.read(Type.STRING);
                            boolean skip = !Protocol1_8TO1_9.VALID_ATTRIBUTES.contains((Object)key);
                            double value = packetWrapper.read(Type.DOUBLE);
                            int modifiersize = packetWrapper.read(Type.VAR_INT);
                            if (!skip) {
                                packetWrapper.write(Type.STRING, key);
                                packetWrapper.write(Type.DOUBLE, value);
                                packetWrapper.write(Type.VAR_INT, modifiersize);
                            } else {
                                ++removed;
                            }
                            ArrayList<Pair<Byte, Double>> modifiers = new ArrayList<Pair<Byte, Double>>();
                            for (int j = 0; j < modifiersize; ++j) {
                                UUID uuid = packetWrapper.read(Type.UUID);
                                double amount = packetWrapper.read(Type.DOUBLE);
                                byte operation = packetWrapper.read(Type.BYTE);
                                modifiers.add(new Pair<Byte, Double>(operation, amount));
                                if (skip) continue;
                                packetWrapper.write(Type.UUID, uuid);
                                packetWrapper.write(Type.DOUBLE, amount);
                                packetWrapper.write(Type.BYTE, operation);
                            }
                            if (!player || !key.equals("generic.attackSpeed")) continue;
                            packetWrapper.user().get(Cooldown.class).setAttackSpeed(value, modifiers);
                        }
                        packetWrapper.set(Type.INT, 0, size - removed);
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 76, 29, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        byte id = packetWrapper.get(Type.BYTE, 0);
                        if (id > 23) {
                            packetWrapper.cancel();
                        }
                        if (id == 25) {
                            if (packetWrapper.get(Type.VAR_INT, 0).intValue() != packetWrapper.user().get(EntityTracker.class).getPlayerId()) {
                                return;
                            }
                            Levitation levitation = packetWrapper.user().get(Levitation.class);
                            levitation.setActive(true);
                            levitation.setAmplifier(packetWrapper.get(Type.BYTE, 1).byteValue());
                        }
                    }
                });
            }
        });
    }
}

