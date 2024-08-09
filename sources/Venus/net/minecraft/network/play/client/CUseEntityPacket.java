/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class CUseEntityPacket
implements IPacket<IServerPlayNetHandler> {
    private int entityId;
    private Action action;
    private Vector3d hitVec;
    private Hand hand;
    private boolean field_241791_e_;

    public CUseEntityPacket() {
    }

    public CUseEntityPacket(Entity entity2, boolean bl) {
        this.entityId = entity2.getEntityId();
        this.action = Action.ATTACK;
        this.field_241791_e_ = bl;
    }

    public CUseEntityPacket(Entity entity2, Hand hand, boolean bl) {
        this.entityId = entity2.getEntityId();
        this.action = Action.INTERACT;
        this.hand = hand;
        this.field_241791_e_ = bl;
    }

    public CUseEntityPacket(Entity entity2, Hand hand, Vector3d vector3d, boolean bl) {
        this.entityId = entity2.getEntityId();
        this.action = Action.INTERACT_AT;
        this.hand = hand;
        this.hitVec = vector3d;
        this.field_241791_e_ = bl;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
        this.action = packetBuffer.readEnumValue(Action.class);
        if (this.action == Action.INTERACT_AT) {
            this.hitVec = new Vector3d(packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readFloat());
        }
        if (this.action == Action.INTERACT || this.action == Action.INTERACT_AT) {
            this.hand = packetBuffer.readEnumValue(Hand.class);
        }
        this.field_241791_e_ = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
        packetBuffer.writeEnumValue(this.action);
        if (this.action == Action.INTERACT_AT) {
            packetBuffer.writeFloat((float)this.hitVec.x);
            packetBuffer.writeFloat((float)this.hitVec.y);
            packetBuffer.writeFloat((float)this.hitVec.z);
        }
        if (this.action == Action.INTERACT || this.action == Action.INTERACT_AT) {
            packetBuffer.writeEnumValue(this.hand);
        }
        packetBuffer.writeBoolean(this.field_241791_e_);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processUseEntity(this);
    }

    @Nullable
    public Entity getEntityFromWorld(World world) {
        return world.getEntityByID(this.entityId);
    }

    public Action getAction() {
        return this.action;
    }

    @Nullable
    public Hand getHand() {
        return this.hand;
    }

    public Vector3d getHitVec() {
        return this.hitVec;
    }

    public boolean func_241792_e_() {
        return this.field_241791_e_;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }

    public static enum Action {
        INTERACT,
        ATTACK,
        INTERACT_AT;

    }
}

