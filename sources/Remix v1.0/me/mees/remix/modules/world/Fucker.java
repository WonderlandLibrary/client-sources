package me.mees.remix.modules.world;

import me.satisfactory.base.utils.timer.*;
import me.satisfactory.base.module.*;
import me.satisfactory.base.events.*;
import net.minecraft.block.*;
import me.tojatta.api.utilities.utilities.angle.*;
import pw.stamina.causam.scan.method.model.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;

public class Fucker extends Module
{
    TimerUtil timer;
    
    public Fucker() {
        super("Fucker", 0, Category.WORLD);
        this.timer = new TimerUtil();
    }
    
    @Subscriber
    public void eventMotion(final EventMotion event) {
        for (int xOffset = -5; xOffset < 6; ++xOffset) {
            for (int zOffset = -5; zOffset < 6; ++zOffset) {
                for (int yOffset = 5; yOffset > -5; --yOffset) {
                    final double x = Fucker.mc.thePlayer.posX + xOffset;
                    final double y = Fucker.mc.thePlayer.posY + yOffset;
                    final double z = Fucker.mc.thePlayer.posZ + zOffset;
                    final BlockPos pos = new BlockPos(x, y, z);
                    final int id = Block.getIdFromBlock(Fucker.mc.theWorld.getBlockState(pos).getBlock());
                    if ((id == 26 || id == 92) && this.timer.hasTimeElapsed(50.0, true)) {
                        event.yaw = AngleUtility.getAngleBlockpos(pos)[0];
                        event.pitch = AngleUtility.getAngleBlockpos(pos)[1];
                        this.smashBlock(pos);
                    }
                }
            }
        }
    }
    
    public void smashBlock(final BlockPos pos) {
        Fucker.mc.thePlayer.sendQueue.sendPacketNoEvent(new C0APacketAnimation());
        Fucker.mc.thePlayer.sendQueue.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
        Fucker.mc.thePlayer.sendQueue.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
    }
}
