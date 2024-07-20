/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.EntityReplacement1_7to1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.MetaType1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viarewind.utils.math.AABB;
import com.viaversion.viarewind.utils.math.Vector3d;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.ArrayList;
import java.util.List;

public class ArmorStandReplacement
extends EntityReplacement1_7to1_8 {
    private final int entityId;
    private final List<Metadata> datawatcher = new ArrayList<Metadata>();
    private int[] entityIds = null;
    private double locX;
    private double locY;
    private double locZ;
    private State currentState = null;
    private boolean invisible = false;
    private boolean nameTagVisible = false;
    private String name = null;
    private float yaw;
    private float pitch;
    private float headYaw;
    private boolean small = false;
    private boolean marker = false;

    @Override
    public int getEntityId() {
        return this.entityId;
    }

    public ArmorStandReplacement(int entityId, UserConnection user) {
        super(user);
        this.entityId = entityId;
    }

    @Override
    public void setLocation(double x, double y, double z) {
        if (x != this.locX || y != this.locY || z != this.locZ) {
            this.locX = x;
            this.locY = y;
            this.locZ = z;
            this.updateLocation(false);
        }
    }

    @Override
    public void relMove(double x, double y, double z) {
        if (x == 0.0 && y == 0.0 && z == 0.0) {
            return;
        }
        this.locX += x;
        this.locY += y;
        this.locZ += z;
        this.updateLocation(false);
    }

    @Override
    public void setYawPitch(float yaw, float pitch) {
        if (this.yaw != yaw && this.pitch != pitch || this.headYaw != yaw) {
            this.yaw = yaw;
            this.headYaw = yaw;
            this.pitch = pitch;
            this.updateLocation(false);
        }
    }

    @Override
    public void setHeadYaw(float yaw) {
        if (this.headYaw != yaw) {
            this.headYaw = yaw;
            this.updateLocation(false);
        }
    }

    @Override
    public void updateMetadata(List<Metadata> metadataList) {
        for (Metadata metadata : metadataList) {
            this.datawatcher.removeIf(m -> m.id() == metadata.id());
            this.datawatcher.add(metadata);
        }
        this.updateState();
    }

    public void updateState() {
        int flags = 0;
        int armorStandFlags = 0;
        for (Metadata metadata : this.datawatcher) {
            if (metadata.id() == 0 && metadata.metaType() == MetaType1_8.Byte) {
                flags = ((Number)metadata.getValue()).byteValue();
                continue;
            }
            if (metadata.id() == 2 && metadata.metaType() == MetaType1_8.String) {
                this.name = metadata.getValue().toString();
                if (this.name == null || !this.name.equals("")) continue;
                this.name = null;
                continue;
            }
            if (metadata.id() == 10 && metadata.metaType() == MetaType1_8.Byte) {
                armorStandFlags = ((Number)metadata.getValue()).byteValue();
                continue;
            }
            if (metadata.id() != 3 || metadata.metaType() != MetaType1_8.Byte) continue;
            this.nameTagVisible = ((Number)metadata.getValue()).byteValue() != 0;
        }
        this.invisible = (flags & 0x20) != 0;
        this.small = armorStandFlags & true;
        this.marker = (armorStandFlags & 0x10) != 0;
        State prevState = this.currentState;
        this.currentState = this.invisible && this.marker ? State.HOLOGRAM : State.ZOMBIE;
        if (this.currentState != prevState) {
            this.despawn();
            this.spawn();
        } else {
            this.updateMetadata();
            this.updateLocation(false);
        }
    }

    public void updateLocation(boolean remount) {
        if (this.entityIds == null) {
            return;
        }
        if (this.currentState == State.ZOMBIE) {
            this.updateZombieLocation();
        } else if (this.currentState == State.HOLOGRAM) {
            this.updateHologramLocation(remount);
        }
    }

    private void updateZombieLocation() {
        this.sendTeleportWithHead(this.entityId, this.locX, this.locY, this.locZ, this.yaw, this.pitch, this.headYaw);
    }

    private void updateHologramLocation(boolean remount) {
        if (remount) {
            PacketWrapper detach = PacketWrapper.create(ClientboundPackets1_7.ATTACH_ENTITY, null, this.user);
            detach.write(Type.INT, this.entityIds[1]);
            detach.write(Type.INT, -1);
            detach.write(Type.BOOLEAN, false);
            PacketUtil.sendPacket(detach, Protocol1_7_6_10To1_8.class, true, true);
        }
        this.sendTeleport(this.entityIds[0], this.locX, this.locY + (this.marker ? 54.85 : (this.small ? 56.0 : 57.0)), this.locZ, 0.0f, 0.0f);
        if (remount) {
            this.sendTeleport(this.entityIds[1], this.locX, this.locY + 56.75, this.locZ, 0.0f, 0.0f);
            PacketWrapper attach = PacketWrapper.create(ClientboundPackets1_7.ATTACH_ENTITY, null, this.user);
            attach.write(Type.INT, this.entityIds[1]);
            attach.write(Type.INT, this.entityIds[0]);
            attach.write(Type.BOOLEAN, false);
            PacketUtil.sendPacket(attach, Protocol1_7_6_10To1_8.class, true, true);
        }
    }

    public void updateMetadata() {
        if (this.entityIds == null) {
            return;
        }
        PacketWrapper metadataPacket = PacketWrapper.create(ClientboundPackets1_7.ENTITY_METADATA, null, this.user);
        if (this.currentState == State.ZOMBIE) {
            this.writeZombieMeta(metadataPacket);
        } else if (this.currentState == State.HOLOGRAM) {
            this.writeHologramMeta(metadataPacket);
        } else {
            return;
        }
        PacketUtil.sendPacket(metadataPacket, Protocol1_7_6_10To1_8.class, true, true);
    }

    private void writeZombieMeta(PacketWrapper metadataPacket) {
        metadataPacket.write(Type.INT, this.entityIds[0]);
        ArrayList<Metadata> metadataList = new ArrayList<Metadata>();
        for (Metadata metadata : this.datawatcher) {
            if (metadata.id() < 0 || metadata.id() > 9) continue;
            metadataList.add(new Metadata(metadata.id(), metadata.metaType(), metadata.getValue()));
        }
        if (this.small) {
            metadataList.add(new Metadata(12, MetaType1_8.Byte, (byte)1));
        }
        MetadataRewriter.transform(Entity1_10Types.EntityType.ZOMBIE, metadataList);
        metadataPacket.write(Types1_7_6_10.METADATA_LIST, metadataList);
    }

    private void writeHologramMeta(PacketWrapper metadataPacket) {
        metadataPacket.write(Type.INT, this.entityIds[1]);
        ArrayList<Metadata> metadataList = new ArrayList<Metadata>();
        metadataList.add(new Metadata(12, MetaType1_7_6_10.Int, -1700000));
        metadataList.add(new Metadata(10, MetaType1_7_6_10.String, this.name));
        metadataList.add(new Metadata(11, MetaType1_7_6_10.Byte, (byte)1));
        metadataPacket.write(Types1_7_6_10.METADATA_LIST, metadataList);
    }

    @Override
    public void spawn() {
        if (this.entityIds != null) {
            this.despawn();
        }
        if (this.currentState == State.ZOMBIE) {
            this.spawnZombie();
        } else if (this.currentState == State.HOLOGRAM) {
            this.spawnHologram();
        }
        this.updateMetadata();
        this.updateLocation(true);
    }

    private void spawnZombie() {
        this.sendSpawn(this.entityId, 54, this.locX, this.locY, this.locZ);
        this.entityIds = new int[]{this.entityId};
    }

    private void spawnHologram() {
        int[] entityIds = new int[]{this.entityId, this.additionalEntityId()};
        PacketWrapper spawnSkull = PacketWrapper.create(ClientboundPackets1_7.SPAWN_ENTITY, null, this.user);
        spawnSkull.write(Type.VAR_INT, entityIds[0]);
        spawnSkull.write(Type.BYTE, (byte)66);
        spawnSkull.write(Type.INT, (int)(this.locX * 32.0));
        spawnSkull.write(Type.INT, (int)(this.locY * 32.0));
        spawnSkull.write(Type.INT, (int)(this.locZ * 32.0));
        spawnSkull.write(Type.BYTE, (byte)0);
        spawnSkull.write(Type.BYTE, (byte)0);
        spawnSkull.write(Type.INT, 0);
        PacketUtil.sendPacket(spawnSkull, Protocol1_7_6_10To1_8.class, true, true);
        this.sendSpawn(entityIds[1], 100, this.locX, this.locY, this.locZ);
        this.entityIds = entityIds;
    }

    private int additionalEntityId() {
        return 2147467647 - this.entityId;
    }

    public AABB getBoundingBox() {
        double w = this.small ? 0.25 : 0.5;
        double h = this.small ? 0.9875 : 1.975;
        Vector3d min = new Vector3d(this.locX - w / 2.0, this.locY, this.locZ - w / 2.0);
        Vector3d max = new Vector3d(this.locX + w / 2.0, this.locY + h, this.locZ + w / 2.0);
        return new AABB(min, max);
    }

    @Override
    public void despawn() {
        if (this.entityIds == null) {
            return;
        }
        PacketWrapper despawn = PacketWrapper.create(ClientboundPackets1_7.DESTROY_ENTITIES, null, this.user);
        despawn.write(Type.BYTE, (byte)this.entityIds.length);
        for (int id : this.entityIds) {
            despawn.write(Type.INT, id);
        }
        this.entityIds = null;
        PacketUtil.sendPacket(despawn, Protocol1_7_6_10To1_8.class, true, true);
    }

    private static enum State {
        HOLOGRAM,
        ZOMBIE;

    }
}

