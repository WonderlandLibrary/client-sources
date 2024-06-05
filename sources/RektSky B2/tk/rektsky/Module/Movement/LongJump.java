package tk.rektsky.Module.Movement;

import tk.rektsky.Module.*;
import net.minecraft.client.*;
import tk.rektsky.Event.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.network.play.server.*;

public class LongJump extends Module
{
    public LongJump() {
        super("LongJump", "RektSky go BRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR", Category.MOVEMENT);
    }
    
    @Override
    public void onEnable() {
        if (this.mc.thePlayer.onGround) {
            this.mc.thePlayer.jump();
        }
        ((Fly)ModulesManager.getModuleByClass(Fly.class)).disable = Minecraft.getSystemTime() - 3000L;
    }
    
    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof WorldTickEvent) {
            final double playerYaw = Math.toRadians(this.mc.thePlayer.rotationYaw);
            this.mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX + 8.956969696969 * -Math.sin(playerYaw), this.mc.thePlayer.posY + 1.69, this.mc.thePlayer.posZ + 8.956969696969 * Math.cos(playerYaw), false));
            this.mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 50.0, this.mc.thePlayer.posZ, false));
            this.rawSetToggled(false);
            this.mc.timer.timerSpeed = 1.0f;
        }
        if (event instanceof PacketSentEvent) {
            final Packet p = ((PacketSentEvent)event).getPacket();
            if (p instanceof C03PacketPlayer) {
                final C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)p;
            }
        }
        if (event instanceof PacketReceiveEvent && ((PacketReceiveEvent)event).getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)((PacketReceiveEvent)event).getPacket()).getEntityID() == this.mc.thePlayer.getEntityId()) {
            final S12PacketEntityVelocity packet = (S12PacketEntityVelocity)((PacketReceiveEvent)event).getPacket();
            Math.toRadians(this.mc.thePlayer.rotationYaw);
        }
    }
}
