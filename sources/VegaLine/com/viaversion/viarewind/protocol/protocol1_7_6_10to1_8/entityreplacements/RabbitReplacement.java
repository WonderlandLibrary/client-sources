/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.EntityReplacement1_7to1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.ArrayList;
import java.util.List;

public class RabbitReplacement
extends EntityReplacement1_7to1_8 {
    private final int entityId;
    private final List<Metadata> datawatcher = new ArrayList<Metadata>();
    private double locX;
    private double locY;
    private double locZ;
    private float yaw;
    private float pitch;
    private float headYaw;

    public RabbitReplacement(int entityId, UserConnection user) {
        super(user);
        this.entityId = entityId;
        this.spawn();
    }

    @Override
    public void setLocation(double x, double y, double z) {
        this.locX = x;
        this.locY = y;
        this.locZ = z;
        this.updateLocation();
    }

    @Override
    public void relMove(double x, double y, double z) {
        this.locX += x;
        this.locY += y;
        this.locZ += z;
        this.updateLocation();
    }

    @Override
    public void setYawPitch(float yaw, float pitch) {
        if (this.yaw != yaw || this.pitch != pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.updateLocation();
        }
    }

    @Override
    public void setHeadYaw(float yaw) {
        if (this.headYaw != yaw) {
            this.headYaw = yaw;
            this.updateLocation();
        }
    }

    @Override
    public void updateMetadata(List<Metadata> metadataList) {
        for (Metadata metadata : metadataList) {
            this.datawatcher.removeIf(m -> m.id() == metadata.id());
            this.datawatcher.add(metadata);
        }
        this.updateMetadata();
    }

    public void updateLocation() {
        this.sendTeleportWithHead(this.entityId, this.locX, this.locY, this.locZ, this.yaw, this.pitch, this.headYaw);
    }

    public void updateMetadata() {
        PacketWrapper metadataPacket = PacketWrapper.create(ClientboundPackets1_7.ENTITY_METADATA, null, this.user);
        metadataPacket.write(Type.INT, this.entityId);
        ArrayList<Metadata> metadataList = new ArrayList<Metadata>();
        for (Metadata metadata : this.datawatcher) {
            metadataList.add(new Metadata(metadata.id(), metadata.metaType(), metadata.getValue()));
        }
        MetadataRewriter.transform(Entity1_10Types.EntityType.CHICKEN, metadataList);
        metadataPacket.write(Types1_7_6_10.METADATA_LIST, metadataList);
        PacketUtil.sendPacket(metadataPacket, Protocol1_7_6_10To1_8.class, true, true);
    }

    @Override
    public void spawn() {
        this.sendSpawn(this.entityId, 93, this.locX, this.locY, this.locZ);
    }

    @Override
    public void despawn() {
        PacketWrapper despawn = PacketWrapper.create(ClientboundPackets1_7.DESTROY_ENTITIES, null, this.user);
        despawn.write(Types1_7_6_10.INT_ARRAY, new int[]{this.entityId});
        PacketUtil.sendPacket(despawn, Protocol1_7_6_10To1_8.class, true, true);
    }

    @Override
    public int getEntityId() {
        return this.entityId;
    }
}

