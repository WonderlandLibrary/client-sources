/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CEntityActionPacket
implements IPacket<IServerPlayNetHandler> {
    private int entityID;
    private Action action;
    private int auxData;
    public static boolean lastUpdatedSprint;

    public CEntityActionPacket() {
    }

    public CEntityActionPacket(Entity entity2, Action action) {
        this(entity2, action, 0);
        if (action == Action.START_SPRINTING) {
            lastUpdatedSprint = true;
        } else if (action == Action.STOP_SPRINTING) {
            lastUpdatedSprint = false;
        }
    }

    public CEntityActionPacket(Entity entity2, Action action, int n) {
        this.entityID = entity2.getEntityId();
        this.action = action;
        this.auxData = n;
        if (action == Action.START_SPRINTING) {
            lastUpdatedSprint = true;
        } else if (action == Action.STOP_SPRINTING) {
            lastUpdatedSprint = false;
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarInt();
        this.action = packetBuffer.readEnumValue(Action.class);
        this.auxData = packetBuffer.readVarInt();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityID);
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeVarInt(this.auxData);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processEntityAction(this);
    }

    public Action getAction() {
        return this.action;
    }

    public int getAuxData() {
        return this.auxData;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }

    public static enum Action {
        PRESS_SHIFT_KEY,
        RELEASE_SHIFT_KEY,
        STOP_SLEEPING,
        START_SPRINTING,
        STOP_SPRINTING,
        START_RIDING_JUMP,
        STOP_RIDING_JUMP,
        OPEN_INVENTORY,
        START_FALL_FLYING;

    }
}

