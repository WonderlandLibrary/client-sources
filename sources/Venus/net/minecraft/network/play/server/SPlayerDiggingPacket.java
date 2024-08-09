/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SPlayerDiggingPacket
implements IPacket<IClientPlayNetHandler> {
    private static final Logger LOGGER = LogManager.getLogger();
    private BlockPos pos;
    private BlockState state;
    CPlayerDiggingPacket.Action action;
    private boolean successful;

    public SPlayerDiggingPacket() {
    }

    public SPlayerDiggingPacket(BlockPos blockPos, BlockState blockState, CPlayerDiggingPacket.Action action, boolean bl, String string) {
        this.pos = blockPos.toImmutable();
        this.state = blockState;
        this.action = action;
        this.successful = bl;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.pos = packetBuffer.readBlockPos();
        this.state = Block.BLOCK_STATE_IDS.getByValue(packetBuffer.readVarInt());
        this.action = packetBuffer.readEnumValue(CPlayerDiggingPacket.Action.class);
        this.successful = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.pos);
        packetBuffer.writeVarInt(Block.getStateId(this.state));
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeBoolean(this.successful);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleAcknowledgePlayerDigging(this);
    }

    public BlockState getBlockState() {
        return this.state;
    }

    public BlockPos getPosition() {
        return this.pos;
    }

    public boolean wasSuccessful() {
        return this.successful;
    }

    public CPlayerDiggingPacket.Action getAction() {
        return this.action;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

