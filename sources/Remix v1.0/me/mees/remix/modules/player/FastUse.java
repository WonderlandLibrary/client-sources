package me.mees.remix.modules.player;

import me.satisfactory.base.module.*;
import me.satisfactory.base.events.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import pw.stamina.causam.scan.method.model.*;

public class FastUse extends Module
{
    public FastUse() {
        super("FastUse", 0, Category.PLAYER);
    }
    
    @Override
    public void onEnable() {
        FastUse.mc.rightClickDelayTimer = 1;
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        FastUse.mc.rightClickDelayTimer = 4;
        super.onDisable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        if (FastUse.mc.thePlayer.getItemInUseDuration() == 1.0 && !(FastUse.mc.thePlayer.getItemInUse().getItem() instanceof ItemBow) && !(FastUse.mc.thePlayer.getItemInUse().getItem() instanceof ItemSword) && Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed) {
            for (int i = 1; i < 20; ++i) {
                FastUse.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(FastUse.mc.thePlayer.rotationYaw, FastUse.mc.thePlayer.rotationPitch, FastUse.mc.thePlayer.onGround));
                if (FastUse.mc.thePlayer.ticksExisted % 5 == 0) {
                    FastUse.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(FastUse.mc.thePlayer.posX, FastUse.mc.thePlayer.posY - 1.0, FastUse.mc.thePlayer.posZ, false));
                }
            }
        }
        FastUse.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    }
}
