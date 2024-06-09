// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.andrewsnetwork.icarus.event.events.PostMotion;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

public class Sneak extends Module
{
    public Sneak() {
        super("Sneak", -16711936, Category.MOVEMENT);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PreMotion || e instanceof PostMotion) {
            final boolean sneaking = Sneak.mc.thePlayer.isSneaking();
            boolean moving = Sneak.mc.thePlayer.movementInput.moveForward != 0.0f;
            final boolean strafing = Sneak.mc.thePlayer.movementInput.moveStrafe != 0.0f;
            moving = (moving || strafing);
            if (moving && !sneaking) {
                this.sneak();
                if (e instanceof PreMotion) {
                    this.unsneak();
                }
            }
            else if (e instanceof PreMotion) {
                this.sneak();
            }
        }
    }
    
    private void sneak() {
        Sneak.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(Sneak.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
    }
    
    private void unsneak() {
        Sneak.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(Sneak.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
        if (Sneak.mc.thePlayer != null) {
            this.unsneak();
        }
    }
}
