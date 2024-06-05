package tk.rektsky.Module.Player;

import tk.rektsky.Event.*;
import net.minecraft.network.play.server.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.util.*;
import tk.rektsky.Module.Movement.*;
import tk.rektsky.Module.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class AntiVoid extends Module
{
    int ticks;
    int startY;
    public boolean isVoid;
    
    public AntiVoid() {
        super("AntiVoid", "You won't die to void any more", Category.PLAYER);
        this.isVoid = true;
    }
    
    @Override
    public void onEnable() {
        this.ticks = 0;
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof PacketReceiveEvent) {
            final Packet p = ((PacketReceiveEvent)event).getPacket();
            if (p instanceof S08PacketPlayerPosLook) {
                final S08PacketPlayerPosLook s08PacketPlayerPosLook = (S08PacketPlayerPosLook)((PacketReceiveEvent)event).getPacket();
            }
        }
        if (event instanceof WorldTickEvent) {
            ++this.ticks;
            this.isVoid = true;
            for (int y = this.mc.thePlayer.getPosition().getY(); y >= 0; --y) {
                if (!this.mc.theWorld.isAirBlock(new BlockPos(this.mc.thePlayer.getPosition().getX(), y, this.mc.thePlayer.getPosition().getZ()))) {
                    this.isVoid = false;
                    return;
                }
            }
            if (this.mc.thePlayer.fallDistance >= 5.0f && this.mc.thePlayer.motionY < -0.2 && this.isVoid && this.ticks > 40) {
                this.mc.thePlayer.fallDistance = 0.0f;
                final double playerYaw = Math.toRadians(this.mc.thePlayer.rotationYaw);
                this.ticks = 0;
                ((Fly)ModulesManager.getModuleByClass(Fly.class)).disable = Minecraft.getSystemTime() - 3000L;
                this.mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.5, this.mc.thePlayer.posZ, false));
                this.mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 50.0, this.mc.thePlayer.posZ, false));
            }
        }
    }
}
