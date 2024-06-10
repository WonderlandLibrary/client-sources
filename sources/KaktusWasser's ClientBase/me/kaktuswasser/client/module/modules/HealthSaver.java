// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.network.play.client.C03PacketPlayer;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.SentPacket;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.BlockHelper;
import net.minecraft.entity.player.EntityPlayer;

public class HealthSaver extends Module
{
    public HealthSaver() {
        super("HealthSaver", -65408, Category.MISC);
        this.setTag("Health Saver");
    }
    
    public boolean canSaveHealth() {
        return (BlockHelper.isInLiquid() || BlockHelper.isInsideBlock(HealthSaver.mc.thePlayer)) && !HealthSaver.mc.thePlayer.isUsingItem() && !HealthSaver.mc.thePlayer.isSwingInProgress && HealthSaver.mc.thePlayer.motionX == 0.0 && HealthSaver.mc.thePlayer.isCollidedVertically && !HealthSaver.mc.gameSettings.keyBindJump.getIsKeyPressed() && HealthSaver.mc.thePlayer.motionZ == 0.0;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof SentPacket) {
            final SentPacket event = (SentPacket)e;
            if (event.getPacket() instanceof C03PacketPlayer && this.canSaveHealth()) {
                HealthSaver.mc.thePlayer.setAir(HealthSaver.mc.thePlayer.getAir() + 1);
                event.setCancelled(true);
            }
        }
    }
}
