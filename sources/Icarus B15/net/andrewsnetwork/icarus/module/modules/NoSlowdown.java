// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.item.EnumAction;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.event.events.PostMotion;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.andrewsnetwork.icarus.module.Module;

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
        Label_0040: {
            if (e instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (e instanceof PreMotion || e instanceof PostMotion) {
            final HealingBot healingBot = (HealingBot)Icarus.getModuleManager().getModuleByName("healingbot");
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
