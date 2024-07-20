/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_8to1_9.entityreplacement;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.entityreplacement.EntityReplacement1_8to1_9;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.ArrayList;
import java.util.List;

public class ShulkerBulletReplacement
extends EntityReplacement1_8to1_9 {
    private final int entityId;
    private final List<Metadata> datawatcher = new ArrayList<Metadata>();
    private double locX;
    private double locY;
    private double locZ;
    private float yaw;
    private float pitch;
    private float headYaw;

    public ShulkerBulletReplacement(int entityId, UserConnection user) {
        super(user);
        this.entityId = entityId;
        this.spawn();
    }

    @Override
    public void setLocation(double x, double y, double z) {
        if (x != this.locX || y != this.locY || z != this.locZ) {
            this.locX = x;
            this.locY = y;
            this.locZ = z;
            this.updateLocation();
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
        this.updateLocation();
    }

    @Override
    public void setYawPitch(float yaw, float pitch) {
        if (this.yaw != yaw && this.pitch != pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.updateLocation();
        }
    }

    @Override
    public void setHeadYaw(float yaw) {
        this.headYaw = yaw;
    }

    @Override
    public void updateMetadata(List<Metadata> metadataList) {
    }

    public void updateLocation() {
        this.sendTeleportWithHead(this.entityId, this.locX, this.locY, this.locZ, this.yaw, this.pitch, this.headYaw);
    }

    @Override
    public void spawn() {
        this.sendSpawnEntity(this.entityId, 66);
    }

    @Override
    public void despawn() {
        PacketWrapper despawn = PacketWrapper.create(ClientboundPackets1_8.DESTROY_ENTITIES, null, this.user);
        despawn.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{this.entityId});
        PacketUtil.sendPacket(despawn, Protocol1_8To1_9.class, true, true);
    }

    @Override
    public int getEntityId() {
        return this.entityId;
    }
}

