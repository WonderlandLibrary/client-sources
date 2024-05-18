/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.auto;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.utils.Timer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@Module.Mod(displayName="AutoBedBreaker")
public class AutoBedBreaker
extends Module {
    private Minecraft mc = Minecraft.getMinecraft();
    public Timer delay = new Timer();
    private int xOffset;
    private int zOffset;
    private int yOffset;

    @EventTarget
    public void onUpdate(TickEvent event) {
        if (Minecraft.theWorld == null) {
            return;
        }
        this.xOffset = -5;
        while (this.xOffset < 6) {
            this.zOffset = -5;
            while (this.zOffset < 6) {
                this.yOffset = 5;
                while (this.yOffset > -5) {
                    double x = Minecraft.thePlayer.posX + (double)this.xOffset;
                    double y = Minecraft.thePlayer.posY + (double)this.yOffset;
                    double z = Minecraft.thePlayer.posZ + (double)this.zOffset;
                    int id = Block.getIdFromBlock(Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock());
                    if (id == 26) {
                        this.smashBlock(new BlockPos(x, y, z));
                        break;
                    }
                    --this.yOffset;
                }
                ++this.zOffset;
            }
            ++this.xOffset;
        }
    }

    public void smashBlock(BlockPos pos) {
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
    }
}

