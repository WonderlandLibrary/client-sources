// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.andrewsnetwork.icarus.event.events.SentPacket;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.values.ConstrainedValue;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.module.Module;

public class AimStep extends Module
{
    private final Value<Integer> stepVal;
    
    public AimStep() {
        super("AimStep", -15724289, Category.PLAYER);
        this.stepVal = (Value<Integer>)new ConstrainedValue("aimstep_Step Value", "step_value", 30, 10, 180, this);
        this.setTag("Aim Step");
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PreMotion) {
            final PreMotion event = (PreMotion)e;
            if (event.getYaw() > AimStep.mc.thePlayer.rotationYaw) {
                event.setYaw(event.getYaw() - 5.0f);
            }
        }
        else if (e instanceof SentPacket) {
            final SentPacket event2 = (SentPacket)e;
            if (event2.getPacket() instanceof C03PacketPlayer) {
                final C03PacketPlayer player = (C03PacketPlayer)event2.getPacket();
                if (player.rotating && player.yaw > AimStep.mc.thePlayer.rotationYaw) {
                    final C03PacketPlayer c03PacketPlayer = player;
                    c03PacketPlayer.yaw -= 5.0f;
                }
            }
        }
    }
}
