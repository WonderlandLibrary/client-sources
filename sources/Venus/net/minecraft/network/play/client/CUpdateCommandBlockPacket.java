/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.util.math.BlockPos;

public class CUpdateCommandBlockPacket
implements IPacket<IServerPlayNetHandler> {
    private BlockPos pos;
    private String command;
    private boolean trackOutput;
    private boolean conditional;
    private boolean auto;
    private CommandBlockTileEntity.Mode mode;

    public CUpdateCommandBlockPacket() {
    }

    public CUpdateCommandBlockPacket(BlockPos blockPos, String string, CommandBlockTileEntity.Mode mode, boolean bl, boolean bl2, boolean bl3) {
        this.pos = blockPos;
        this.command = string;
        this.trackOutput = bl;
        this.conditional = bl2;
        this.auto = bl3;
        this.mode = mode;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.pos = packetBuffer.readBlockPos();
        this.command = packetBuffer.readString(Short.MAX_VALUE);
        this.mode = packetBuffer.readEnumValue(CommandBlockTileEntity.Mode.class);
        byte by = packetBuffer.readByte();
        this.trackOutput = (by & 1) != 0;
        this.conditional = (by & 2) != 0;
        this.auto = (by & 4) != 0;
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.pos);
        packetBuffer.writeString(this.command);
        packetBuffer.writeEnumValue(this.mode);
        int n = 0;
        if (this.trackOutput) {
            n |= 1;
        }
        if (this.conditional) {
            n |= 2;
        }
        if (this.auto) {
            n |= 4;
        }
        packetBuffer.writeByte(n);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processUpdateCommandBlock(this);
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public String getCommand() {
        return this.command;
    }

    public boolean shouldTrackOutput() {
        return this.trackOutput;
    }

    public boolean isConditional() {
        return this.conditional;
    }

    public boolean isAuto() {
        return this.auto;
    }

    public CommandBlockTileEntity.Mode getMode() {
        return this.mode;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

