package me.mees.remix.modules.movement;

import me.satisfactory.base.module.*;
import me.satisfactory.base.events.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;

public class NoSlowDown extends Module
{
    public NoSlowDown() {
        super("NoSlowDown", 0, Category.MOVE);
    }
    
    public void onPacket(final EventSendPacket event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            final C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
            if (packet.func_149466_j() && NoSlowDown.mc.thePlayer.isUsingItem()) {
                NoSlowDown.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(NoSlowDown.mc.thePlayer.inventory.getCurrentItem()));
                NoSlowDown.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
                final double spood = 0.999 - Math.random() * 7.96E-4;
                final EntityPlayerSP thePlayer = NoSlowDown.mc.thePlayer;
                thePlayer.motionZ *= spood;
                final EntityPlayerSP thePlayer2 = NoSlowDown.mc.thePlayer;
                thePlayer2.motionX *= spood;
            }
        }
    }
}
