package com.client.glowclient;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.tileentity.*;

public class CC extends gA
{
    public CC() {
        super();
    }
    
    @Override
    public boolean M(final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final World world2, final BlockPos blockPos2) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        final TileEntity tileEntity2 = world2.getTileEntity(blockPos2);
        if (tileEntity instanceof TileEntityCommandBlock && tileEntity2 instanceof TileEntityCommandBlock) {
            final CommandBlockBaseLogic commandBlockLogic = ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic();
            final CommandBlockBaseLogic commandBlockLogic2 = ((TileEntityCommandBlock)tileEntity2).getCommandBlockLogic();
            if (!commandBlockLogic.getCommand().equals(commandBlockLogic2.getCommand())) {
                final PacketBuffer packetBuffer;
                (packetBuffer = new PacketBuffer(Unpooled.buffer())).writeByte(commandBlockLogic2.getCommandBlockType());
                final CommandBlockBaseLogic commandBlockBaseLogic = commandBlockLogic;
                final CommandBlockBaseLogic commandBlockBaseLogic2 = commandBlockLogic2;
                final PacketBuffer packetBuffer2 = packetBuffer;
                commandBlockBaseLogic2.fillInInfo((ByteBuf)packetBuffer2);
                packetBuffer2.writeString(commandBlockBaseLogic.getCommand());
                packetBuffer.writeBoolean(commandBlockLogic2.shouldTrackOutput());
                return this.M((net.minecraft.network.Packet<INetHandler>)new CPacketCustomPayload("MC|AdvCdm", packetBuffer));
            }
        }
        return false;
    }
}
