/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class CPlayerDiggingPacket
implements IPacket<IServerPlayNetHandler> {
    private BlockPos position;
    private Direction facing;
    private Action action;

    public CPlayerDiggingPacket() {
    }

    public CPlayerDiggingPacket(Action action, BlockPos blockPos, Direction direction) {
        this.action = action;
        this.position = blockPos.toImmutable();
        this.facing = direction;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.action = packetBuffer.readEnumValue(Action.class);
        this.position = packetBuffer.readBlockPos();
        this.facing = Direction.byIndex(packetBuffer.readUnsignedByte());
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.facing.getIndex());
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processPlayerDigging(this);
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public Direction getFacing() {
        return this.facing;
    }

    public Action getAction() {
        return this.action;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }

    public static enum Action {
        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        DROP_ALL_ITEMS,
        DROP_ITEM,
        RELEASE_USE_ITEM,
        SWAP_ITEM_WITH_OFFHAND;

    }
}

