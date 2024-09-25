/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.google.common.base.Charsets;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.ArmorStandReplacement;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider.TitleRenderProvider;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerAbilities;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerPosition;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Windows;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.ChatUtil;
import de.gerrygames.viarewind.utils.PacketUtil;
import de.gerrygames.viarewind.utils.Utils;
import de.gerrygames.viarewind.utils.math.AABB;
import de.gerrygames.viarewind.utils.math.Ray3d;
import de.gerrygames.viarewind.utils.math.RayTracing;
import de.gerrygames.viarewind.utils.math.Vector3d;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.List;
import java.util.UUID;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.entities.Entity1_10Types;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueCreator;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.CustomByteType;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;

public class PlayerPackets {
    public static void register(Protocol protocol) {
        protocol.registerOutgoing(State.PLAY, 1, 1, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        if (!ViaRewind.getConfig().isReplaceAdventureMode()) {
                            return;
                        }
                        if (packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 2) {
                            packetWrapper.set(Type.UNSIGNED_BYTE, 0, (short)0);
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.BOOLEAN);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        tracker.setGamemode(packetWrapper.get(Type.UNSIGNED_BYTE, 0).shortValue());
                        tracker.setPlayerId(packetWrapper.get(Type.INT, 0));
                        tracker.getClientEntityTypes().put(tracker.getPlayerId(), Entity1_10Types.EntityType.ENTITY_HUMAN);
                        tracker.setDimension(packetWrapper.get(Type.BYTE, 0).byteValue());
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
        protocol.registerOutgoing(State.PLAY, 2, 2, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.COMPONENT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        byte position = packetWrapper.read(Type.BYTE);
                        if (position == 2) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 5, 5, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        Position position = packetWrapper.read(Type.POSITION);
                        packetWrapper.write(Type.INT, position.getX());
                        packetWrapper.write(Type.INT, Integer.valueOf(position.getY()));
                        packetWrapper.write(Type.INT, position.getZ());
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 6, 6, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map((Type)Type.VAR_INT, Type.SHORT);
                this.map(Type.FLOAT);
            }
        });
        protocol.registerOutgoing(State.PLAY, 7, 7, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        if (!ViaRewind.getConfig().isReplaceAdventureMode()) {
                            return;
                        }
                        if (packetWrapper.get(Type.UNSIGNED_BYTE, 1) == 2) {
                            packetWrapper.set(Type.UNSIGNED_BYTE, 1, (short)0);
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        tracker.setGamemode(packetWrapper.get(Type.UNSIGNED_BYTE, 1).shortValue());
                        if (tracker.getDimension() != packetWrapper.get(Type.INT, 0).intValue()) {
                            tracker.setDimension(packetWrapper.get(Type.INT, 0));
                            tracker.clearEntities();
                            tracker.getClientEntityTypes().put(tracker.getPlayerId(), Entity1_10Types.EntityType.ENTITY_HUMAN);
                        }
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
        protocol.registerOutgoing(State.PLAY, 8, 8, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        PlayerPosition playerPosition = packetWrapper.user().get(PlayerPosition.class);
                        playerPosition.setPositionPacketReceived(true);
                        byte flags = packetWrapper.read(Type.BYTE);
                        if ((flags & 1) == 1) {
                            double x = packetWrapper.get(Type.DOUBLE, 0);
                            packetWrapper.set(Type.DOUBLE, 0, x += playerPosition.getPosX());
                        }
                        double y = packetWrapper.get(Type.DOUBLE, 1);
                        if ((flags & 2) == 2) {
                            y += playerPosition.getPosY();
                        }
                        playerPosition.setReceivedPosY(y);
                        packetWrapper.set(Type.DOUBLE, 1, y += (double)1.62f);
                        if ((flags & 4) == 4) {
                            double z = packetWrapper.get(Type.DOUBLE, 2);
                            packetWrapper.set(Type.DOUBLE, 2, z += playerPosition.getPosZ());
                        }
                        if ((flags & 8) == 8) {
                            float yaw = packetWrapper.get(Type.FLOAT, 0).floatValue();
                            packetWrapper.set(Type.FLOAT, 0, Float.valueOf(yaw += playerPosition.getYaw()));
                        }
                        if ((flags & 0x10) == 16) {
                            float pitch = packetWrapper.get(Type.FLOAT, 1).floatValue();
                            packetWrapper.set(Type.FLOAT, 1, Float.valueOf(pitch += playerPosition.getPitch()));
                        }
                    }
                });
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper packetWrapper) throws Exception {
                        PlayerPosition playerPosition = packetWrapper.user().get(PlayerPosition.class);
                        packetWrapper.write(Type.BOOLEAN, playerPosition.isOnGround());
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        if (tracker.getSpectating() != tracker.getPlayerId()) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 31, 31, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map((Type)Type.VAR_INT, Type.SHORT);
                this.map((Type)Type.VAR_INT, Type.SHORT);
            }
        });
        protocol.registerOutgoing(State.PLAY, 43, 43, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLOAT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short mode = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        if (mode != 3) {
                            return;
                        }
                        int gamemode = packetWrapper.get(Type.FLOAT, 0).intValue();
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        if (gamemode == 3 || tracker.getGamemode() == 3) {
                            Item[] equipment;
                            UUID uuid = packetWrapper.user().get(ProtocolInfo.class).getUuid();
                            if (gamemode == 3) {
                                GameProfileStorage.GameProfile profile = packetWrapper.user().get(GameProfileStorage.class).get(uuid);
                                equipment = new Item[5];
                                equipment[4] = profile.getSkull();
                            } else {
                                equipment = tracker.getPlayerEquipment(uuid);
                                if (equipment == null) {
                                    equipment = new Item[5];
                                }
                            }
                            for (int i = 1; i < 5; ++i) {
                                PacketWrapper setSlot = new PacketWrapper(47, null, packetWrapper.user());
                                setSlot.write(Type.BYTE, (byte)0);
                                setSlot.write(Type.SHORT, (short)(9 - i));
                                setSlot.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, equipment[i]);
                                PacketUtil.sendPacket(setSlot, Protocol1_7_6_10TO1_8.class);
                            }
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short mode = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        if (mode == 3) {
                            int gamemode = packetWrapper.get(Type.FLOAT, 0).intValue();
                            if (gamemode == 2 && ViaRewind.getConfig().isReplaceAdventureMode()) {
                                gamemode = 0;
                                packetWrapper.set(Type.FLOAT, 0, Float.valueOf(0.0f));
                            }
                            packetWrapper.user().get(EntityTracker.class).setGamemode(gamemode);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 54, 54, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        Position position = packetWrapper.read(Type.POSITION);
                        packetWrapper.write(Type.INT, position.getX());
                        packetWrapper.write(Type.INT, Integer.valueOf(position.getY()));
                        packetWrapper.write(Type.INT, position.getZ());
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 56, 56, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                        int action = packetWrapper.read(Type.VAR_INT);
                        int count = packetWrapper.read(Type.VAR_INT);
                        GameProfileStorage gameProfileStorage = packetWrapper.user().get(GameProfileStorage.class);
                        for (int i = 0; i < count; ++i) {
                            GameProfileStorage.GameProfile gameProfile;
                            GameProfileStorage.GameProfile gameProfile2;
                            UUID uuid = packetWrapper.read(Type.UUID);
                            if (action == 0) {
                                int ping;
                                String name = packetWrapper.read(Type.STRING);
                                gameProfile2 = gameProfileStorage.get(uuid);
                                if (gameProfile2 == null) {
                                    gameProfile2 = gameProfileStorage.put(uuid, name);
                                }
                                int propertyCount = packetWrapper.read(Type.VAR_INT);
                                while (propertyCount-- > 0) {
                                    gameProfile2.properties.add(new GameProfileStorage.Property(packetWrapper.read(Type.STRING), packetWrapper.read(Type.STRING), packetWrapper.read(Type.BOOLEAN) != false ? packetWrapper.read(Type.STRING) : null));
                                }
                                int gamemode = packetWrapper.read(Type.VAR_INT);
                                gameProfile2.ping = ping = packetWrapper.read(Type.VAR_INT).intValue();
                                gameProfile2.gamemode = gamemode;
                                if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                                    gameProfile2.setDisplayName(packetWrapper.read(Type.STRING));
                                }
                                PacketWrapper packet = new PacketWrapper(56, null, packetWrapper.user());
                                packet.write(Type.STRING, gameProfile2.name);
                                packet.write(Type.BOOLEAN, true);
                                packet.write(Type.SHORT, (short)ping);
                                PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
                                continue;
                            }
                            if (action == 1) {
                                EntityTracker tracker;
                                int entityId;
                                int gamemode = packetWrapper.read(Type.VAR_INT);
                                gameProfile2 = gameProfileStorage.get(uuid);
                                if (gameProfile2 == null || gameProfile2.gamemode == gamemode) continue;
                                if ((gamemode == 3 || gameProfile2.gamemode == 3) && (entityId = (tracker = packetWrapper.user().get(EntityTracker.class)).getPlayerEntityId(uuid)) != -1) {
                                    Item[] equipment;
                                    if (gamemode == 3) {
                                        equipment = new Item[5];
                                        equipment[4] = gameProfile2.getSkull();
                                    } else {
                                        equipment = tracker.getPlayerEquipment(uuid);
                                        if (equipment == null) {
                                            equipment = new Item[5];
                                        }
                                    }
                                    for (short slot = 0; slot < 5; slot = (short)(slot + 1)) {
                                        PacketWrapper equipmentPacket = new PacketWrapper(4, null, packetWrapper.user());
                                        equipmentPacket.write(Type.INT, entityId);
                                        equipmentPacket.write(Type.SHORT, slot);
                                        equipmentPacket.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, equipment[slot]);
                                        PacketUtil.sendPacket(equipmentPacket, Protocol1_7_6_10TO1_8.class);
                                    }
                                }
                                gameProfile2.gamemode = gamemode;
                                continue;
                            }
                            if (action == 2) {
                                int ping = packetWrapper.read(Type.VAR_INT);
                                gameProfile2 = gameProfileStorage.get(uuid);
                                if (gameProfile2 == null) continue;
                                gameProfile2.ping = ping;
                                PacketWrapper packet = new PacketWrapper(56, null, packetWrapper.user());
                                packet.write(Type.STRING, gameProfile2.name);
                                packet.write(Type.BOOLEAN, true);
                                packet.write(Type.SHORT, (short)ping);
                                PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
                                continue;
                            }
                            if (action == 3) {
                                String displayName = packetWrapper.read(Type.BOOLEAN) != false ? packetWrapper.read(Type.STRING) : null;
                                gameProfile2 = gameProfileStorage.get(uuid);
                                if (gameProfile2 == null || gameProfile2.displayName == null && displayName == null || !(gameProfile2.displayName == null && displayName != null || gameProfile2.displayName != null && displayName == null) && gameProfile2.displayName.equals(displayName)) continue;
                                gameProfile2.setDisplayName(displayName);
                                continue;
                            }
                            if (action != 4 || (gameProfile = gameProfileStorage.remove(uuid)) == null) continue;
                            PacketWrapper packet = new PacketWrapper(56, null, packetWrapper.user());
                            packet.write(Type.STRING, gameProfile.name);
                            packet.write(Type.BOOLEAN, false);
                            packet.write(Type.SHORT, (short)gameProfile.ping);
                            PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 57, 57, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        byte flags = packetWrapper.get(Type.BYTE, 0);
                        float flySpeed = packetWrapper.get(Type.FLOAT, 0).floatValue();
                        float walkSpeed = packetWrapper.get(Type.FLOAT, 1).floatValue();
                        PlayerAbilities abilities = packetWrapper.user().get(PlayerAbilities.class);
                        abilities.setInvincible((flags & 8) == 8);
                        abilities.setAllowFly((flags & 4) == 4);
                        abilities.setFlying((flags & 2) == 2);
                        abilities.setCreative((flags & 1) == 1);
                        abilities.setFlySpeed(flySpeed);
                        abilities.setWalkSpeed(walkSpeed);
                        if (abilities.isSprinting() && abilities.isFlying()) {
                            packetWrapper.set(Type.FLOAT, 0, Float.valueOf(abilities.getFlySpeed() * 2.0f));
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 63, 63, new PacketRemapper(){

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
                                Item item = ItemRewriter.toClient(packetWrapper.read(Type.ITEM));
                                packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
                                item = ItemRewriter.toClient(packetWrapper.read(Type.ITEM));
                                packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
                                boolean has3Items = packetWrapper.passthrough(Type.BOOLEAN);
                                if (has3Items) {
                                    item = ItemRewriter.toClient(packetWrapper.read(Type.ITEM));
                                    packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
                                }
                                packetWrapper.passthrough(Type.BOOLEAN);
                                packetWrapper.read(Type.INT);
                                packetWrapper.read(Type.INT);
                            }
                        } else if (channel.equalsIgnoreCase("MC|Brand")) {
                            packetWrapper.write(Type.REMAINING_BYTES, packetWrapper.read(Type.STRING).getBytes(Charsets.UTF_8));
                        }
                        packetWrapper.cancel();
                        packetWrapper.setId(-1);
                        ByteBuf newPacketBuf = Unpooled.buffer();
                        packetWrapper.writeToBuffer(newPacketBuf);
                        PacketWrapper newWrapper = new PacketWrapper(63, newPacketBuf, packetWrapper.user());
                        newWrapper.passthrough(Type.STRING);
                        if (newPacketBuf.readableBytes() <= 32767) {
                            newWrapper.write(Type.SHORT, (short)newPacketBuf.readableBytes());
                            newWrapper.send(Protocol1_7_6_10TO1_8.class, true, true);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 67, -1, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        int entityId = packetWrapper.read(Type.VAR_INT);
                        int spectating = tracker.getSpectating();
                        if (spectating != entityId) {
                            tracker.setSpectating(entityId);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 69, -1, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                        TitleRenderProvider titleRenderProvider = Via.getManager().getProviders().get(TitleRenderProvider.class);
                        if (titleRenderProvider == null) {
                            return;
                        }
                        int action = packetWrapper.read(Type.VAR_INT);
                        UUID uuid = Utils.getUUID(packetWrapper.user());
                        switch (action) {
                            case 0: {
                                titleRenderProvider.setTitle(uuid, packetWrapper.read(Type.STRING));
                                break;
                            }
                            case 1: {
                                titleRenderProvider.setSubTitle(uuid, packetWrapper.read(Type.STRING));
                                break;
                            }
                            case 2: {
                                titleRenderProvider.setTimings(uuid, packetWrapper.read(Type.INT), packetWrapper.read(Type.INT), packetWrapper.read(Type.INT));
                                break;
                            }
                            case 3: {
                                titleRenderProvider.clear(uuid);
                                break;
                            }
                            case 4: {
                                titleRenderProvider.reset(uuid);
                            }
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 71, -1, new PacketRemapper(){

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
        protocol.registerOutgoing(State.PLAY, 72, -1, new PacketRemapper(){

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
        protocol.registerIncoming(State.PLAY, 1, 1, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        String msg = packetWrapper.get(Type.STRING, 0);
                        int gamemode = packetWrapper.user().get(EntityTracker.class).getGamemode();
                        if (gamemode == 3 && msg.toLowerCase().startsWith("/stp ")) {
                            String username = msg.split(" ")[1];
                            GameProfileStorage storage = packetWrapper.user().get(GameProfileStorage.class);
                            GameProfileStorage.GameProfile profile = storage.get(username, true);
                            if (profile != null && profile.uuid != null) {
                                packetWrapper.cancel();
                                PacketWrapper teleportPacket = new PacketWrapper(24, null, packetWrapper.user());
                                teleportPacket.write(Type.UUID, profile.uuid);
                                PacketUtil.sendToServer(teleportPacket, Protocol1_7_6_10TO1_8.class, true, true);
                            }
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 2, 2, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT, Type.VAR_INT);
                this.map(Type.BYTE, Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int mode = packetWrapper.get(Type.VAR_INT, 1);
                        if (mode != 0) {
                            return;
                        }
                        int entityId = packetWrapper.get(Type.VAR_INT, 0);
                        EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                        EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                        if (!(replacement instanceof ArmorStandReplacement)) {
                            return;
                        }
                        ArmorStandReplacement armorStand = (ArmorStandReplacement)replacement;
                        AABB boundingBox = armorStand.getBoundingBox();
                        PlayerPosition playerPosition = packetWrapper.user().get(PlayerPosition.class);
                        Vector3d pos = new Vector3d(playerPosition.getPosX(), playerPosition.getPosY() + 1.8, playerPosition.getPosZ());
                        double yaw = Math.toRadians(playerPosition.getYaw());
                        double pitch = Math.toRadians(playerPosition.getPitch());
                        Vector3d dir = new Vector3d(-Math.cos(pitch) * Math.sin(yaw), -Math.sin(pitch), Math.cos(pitch) * Math.cos(yaw));
                        Ray3d ray = new Ray3d(pos, dir);
                        Vector3d intersection = RayTracing.trace(ray, boundingBox, 5.0);
                        if (intersection == null) {
                            return;
                        }
                        intersection.substract(boundingBox.getMin());
                        mode = 2;
                        packetWrapper.set(Type.VAR_INT, 1, mode);
                        packetWrapper.write(Type.FLOAT, Float.valueOf((float)intersection.getX()));
                        packetWrapper.write(Type.FLOAT, Float.valueOf((float)intersection.getY()));
                        packetWrapper.write(Type.FLOAT, Float.valueOf((float)intersection.getZ()));
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 3, 3, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        PlayerPosition playerPosition = packetWrapper.user().get(PlayerPosition.class);
                        playerPosition.setOnGround(packetWrapper.get(Type.BOOLEAN, 0));
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 4, 4, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.DOUBLE);
                    }
                });
                this.map(Type.DOUBLE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        double x = packetWrapper.get(Type.DOUBLE, 0);
                        double feetY = packetWrapper.get(Type.DOUBLE, 1);
                        double z = packetWrapper.get(Type.DOUBLE, 2);
                        PlayerPosition playerPosition = packetWrapper.user().get(PlayerPosition.class);
                        if (playerPosition.isPositionPacketReceived()) {
                            playerPosition.setPositionPacketReceived(false);
                            packetWrapper.set(Type.DOUBLE, 1, feetY -= 0.01);
                        }
                        playerPosition.setOnGround(packetWrapper.get(Type.BOOLEAN, 0));
                        playerPosition.setPos(x, feetY, z);
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 5, 5, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        PlayerPosition playerPosition = packetWrapper.user().get(PlayerPosition.class);
                        playerPosition.setYaw(packetWrapper.get(Type.FLOAT, 0).floatValue());
                        playerPosition.setPitch(packetWrapper.get(Type.FLOAT, 1).floatValue());
                        playerPosition.setOnGround(packetWrapper.get(Type.BOOLEAN, 0));
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 6, 6, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.DOUBLE);
                    }
                });
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        double x = packetWrapper.get(Type.DOUBLE, 0);
                        double feetY = packetWrapper.get(Type.DOUBLE, 1);
                        double z = packetWrapper.get(Type.DOUBLE, 2);
                        float yaw = packetWrapper.get(Type.FLOAT, 0).floatValue();
                        float pitch = packetWrapper.get(Type.FLOAT, 1).floatValue();
                        PlayerPosition playerPosition = packetWrapper.user().get(PlayerPosition.class);
                        if (playerPosition.isPositionPacketReceived()) {
                            playerPosition.setPositionPacketReceived(false);
                            feetY = playerPosition.getReceivedPosY();
                            packetWrapper.set(Type.DOUBLE, 1, feetY);
                        }
                        playerPosition.setOnGround(packetWrapper.get(Type.BOOLEAN, 0));
                        playerPosition.setPos(x, feetY, z);
                        playerPosition.setYaw(yaw);
                        playerPosition.setPitch(pitch);
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 7, 7, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int x = packetWrapper.read(Type.INT);
                        short y = packetWrapper.read(Type.UNSIGNED_BYTE);
                        int z = packetWrapper.read(Type.INT);
                        packetWrapper.write(Type.POSITION, new Position(x, y, z));
                    }
                });
                this.map(Type.BYTE);
            }
        });
        protocol.registerIncoming(State.PLAY, 8, 8, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int x = packetWrapper.read(Type.INT);
                        short y = packetWrapper.read(Type.UNSIGNED_BYTE);
                        int z = packetWrapper.read(Type.INT);
                        packetWrapper.write(Type.POSITION, new Position(x, y, z));
                        packetWrapper.passthrough(Type.BYTE);
                        Item item = packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
                        item = ItemRewriter.toServer(item);
                        packetWrapper.write(Type.ITEM, item);
                        for (int i = 0; i < 3; ++i) {
                            packetWrapper.passthrough(Type.BYTE);
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 10, 10, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int entityId = packetWrapper.read(Type.INT);
                        int animation = packetWrapper.read(Type.BYTE).byteValue();
                        if (animation == 1) {
                            return;
                        }
                        packetWrapper.cancel();
                        switch (animation) {
                            case 104: {
                                animation = 0;
                                break;
                            }
                            case 105: {
                                animation = 1;
                                break;
                            }
                            case 3: {
                                animation = 2;
                                break;
                            }
                            default: {
                                return;
                            }
                        }
                        PacketWrapper entityAction = new PacketWrapper(11, null, packetWrapper.user());
                        entityAction.write(Type.VAR_INT, entityId);
                        entityAction.write(Type.VAR_INT, animation);
                        entityAction.write(Type.VAR_INT, 0);
                        PacketUtil.sendPacket(entityAction, Protocol1_7_6_10TO1_8.class, true, true);
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 11, 11, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT, Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.BYTE) - 1);
                    }
                });
                this.map(Type.INT, Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int action = packetWrapper.get(Type.VAR_INT, 1);
                        if (action == 3 || action == 4) {
                            PlayerAbilities abilities = packetWrapper.user().get(PlayerAbilities.class);
                            abilities.setSprinting(action == 3);
                            PacketWrapper abilitiesPacket = new PacketWrapper(57, null, packetWrapper.user());
                            abilitiesPacket.write(Type.BYTE, abilities.getFlags());
                            abilitiesPacket.write(Type.FLOAT, Float.valueOf(abilities.isSprinting() ? abilities.getFlySpeed() * 2.0f : abilities.getFlySpeed()));
                            abilitiesPacket.write(Type.FLOAT, Float.valueOf(abilities.getWalkSpeed()));
                            PacketUtil.sendPacket(abilitiesPacket, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 12, 12, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        EntityTracker tracker;
                        boolean jump = packetWrapper.read(Type.BOOLEAN);
                        boolean unmount = packetWrapper.read(Type.BOOLEAN);
                        short flags = 0;
                        if (jump) {
                            flags = (short)(flags + 1);
                        }
                        if (unmount) {
                            flags = (short)(flags + 2);
                        }
                        packetWrapper.write(Type.UNSIGNED_BYTE, flags);
                        if (unmount && (tracker = packetWrapper.user().get(EntityTracker.class)).getSpectating() != tracker.getPlayerId()) {
                            PacketWrapper sneakPacket = new PacketWrapper(11, null, packetWrapper.user());
                            sneakPacket.write(Type.VAR_INT, tracker.getPlayerId());
                            sneakPacket.write(Type.VAR_INT, 0);
                            sneakPacket.write(Type.VAR_INT, 0);
                            PacketWrapper unsneakPacket = new PacketWrapper(11, null, packetWrapper.user());
                            unsneakPacket.write(Type.VAR_INT, tracker.getPlayerId());
                            unsneakPacket.write(Type.VAR_INT, 1);
                            unsneakPacket.write(Type.VAR_INT, 0);
                            PacketUtil.sendToServer(sneakPacket, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 18, 18, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int x = packetWrapper.read(Type.INT);
                        short y = packetWrapper.read(Type.SHORT);
                        int z = packetWrapper.read(Type.INT);
                        packetWrapper.write(Type.POSITION, new Position(x, y, z));
                        for (int i = 0; i < 4; ++i) {
                            String line = packetWrapper.read(Type.STRING);
                            line = ChatUtil.legacyToJson(line);
                            packetWrapper.write(Type.COMPONENT, GsonUtil.getJsonParser().parse(line));
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 19, 19, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        byte flags = packetWrapper.get(Type.BYTE, 0);
                        PlayerAbilities abilities = packetWrapper.user().get(PlayerAbilities.class);
                        abilities.setAllowFly((flags & 4) == 4);
                        abilities.setFlying((flags & 2) == 2);
                        packetWrapper.set(Type.FLOAT, 0, Float.valueOf(abilities.getFlySpeed()));
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 20, 20, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.OPTIONAL_POSITION, null);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        String msg = packetWrapper.get(Type.STRING, 0);
                        if (msg.toLowerCase().startsWith("/stp ")) {
                            packetWrapper.cancel();
                            String[] args = msg.split(" ");
                            if (args.length <= 2) {
                                String prefix = args.length == 1 ? "" : args[1];
                                GameProfileStorage storage = packetWrapper.user().get(GameProfileStorage.class);
                                List<GameProfileStorage.GameProfile> profiles = storage.getAllWithPrefix(prefix, true);
                                PacketWrapper tabComplete = new PacketWrapper(58, null, packetWrapper.user());
                                tabComplete.write(Type.VAR_INT, profiles.size());
                                for (GameProfileStorage.GameProfile profile : profiles) {
                                    tabComplete.write(Type.STRING, profile.name);
                                }
                                PacketUtil.sendPacket(tabComplete, Protocol1_7_6_10TO1_8.class);
                            }
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 21, 21, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.BYTE);
                        boolean cape = packetWrapper.read(Type.BOOLEAN);
                        packetWrapper.write(Type.UNSIGNED_BYTE, (short)(cape ? 127 : 126));
                    }
                });
            }
        });
        protocol.registerIncoming(State.PLAY, 23, 23, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        String channel = packetWrapper.get(Type.STRING, 0);
                        short length = packetWrapper.read(Type.SHORT);
                        if (channel.equalsIgnoreCase("MC|ItemName")) {
                            CustomByteType customByteType = new CustomByteType(Integer.valueOf(length));
                            byte[] data = packetWrapper.read(customByteType);
                            String name = new String(data, Charsets.UTF_8);
                            ByteBuf buf = packetWrapper.user().getChannel().alloc().buffer();
                            Type.STRING.write(buf, name);
                            data = new byte[buf.readableBytes()];
                            buf.readBytes(data);
                            buf.release();
                            packetWrapper.write(Type.REMAINING_BYTES, data);
                            Windows windows = packetWrapper.user().get(Windows.class);
                            PacketWrapper updateCost = new PacketWrapper(49, null, packetWrapper.user());
                            updateCost.write(Type.UNSIGNED_BYTE, windows.anvilId);
                            updateCost.write(Type.SHORT, (short)0);
                            updateCost.write(Type.SHORT, windows.levelCost);
                            PacketUtil.sendPacket(updateCost, Protocol1_7_6_10TO1_8.class, true, true);
                        } else if (channel.equalsIgnoreCase("MC|BEdit") || channel.equalsIgnoreCase("MC|BSign")) {
                            Item book = packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
                            CompoundTag tag = book.getTag();
                            if (tag != null && tag.contains("pages")) {
                                ListTag pages = (ListTag)tag.get("pages");
                                for (int i = 0; i < pages.size(); ++i) {
                                    StringTag page = (StringTag)pages.get(i);
                                    String value = page.getValue();
                                    value = ChatUtil.legacyToJson(value);
                                    page.setValue(value);
                                }
                            }
                            packetWrapper.write(Type.ITEM, book);
                        } else if (channel.equalsIgnoreCase("MC|Brand")) {
                            packetWrapper.write(Type.VAR_INT, Integer.valueOf(length));
                        }
                    }
                });
            }
        });
    }
}

