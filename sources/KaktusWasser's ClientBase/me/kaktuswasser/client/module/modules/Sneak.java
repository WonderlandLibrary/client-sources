// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.network.Packet;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.PostMotion;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C0BPacketEntityAction;

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
