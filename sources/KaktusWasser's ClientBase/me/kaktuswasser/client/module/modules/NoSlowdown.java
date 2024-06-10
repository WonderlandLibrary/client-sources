// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.item.EnumAction;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.PostMotion;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.module.Module;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class NoSlowdown extends Module
{
    public NoSlowdown() {
        super("NoSlowdown", -16765390, Category.MOVEMENT);
        this.setTag("No Slowdown");
    }
    
    private void block() {
        NoSlowdown.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(NoSlowdown.mc.thePlayer.inventory.getCurrentItem()));
    }
    
    private void unblock() {
        NoSlowdown.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
        if (NoSlowdown.mc.thePlayer != null) {
            this.unblock();
        }
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PreMotion || e instanceof PostMotion) {
            final HealingBot healingBot = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
            if (healingBot.isHealing()) {
                return;
            }
            final ItemStack currentItem = NoSlowdown.mc.thePlayer.getHeldItem();
            if (currentItem != null && currentItem.getItem().getItemUseAction(currentItem) == EnumAction.BLOCK) {
                if (NoSlowdown.mc.thePlayer.isBlocking()) {
                    boolean moving = NoSlowdown.mc.thePlayer.movementInput.moveForward != 0.0f;
                    final boolean strafing = NoSlowdown.mc.thePlayer.movementInput.moveStrafe != 0.0f;
                    moving = (moving || strafing);
                    this.block();
                    if (e instanceof PreMotion && moving) {
                        this.unblock();
                    }
                }
                else {
                    this.unblock();
                }
            }
        }
    }
}
