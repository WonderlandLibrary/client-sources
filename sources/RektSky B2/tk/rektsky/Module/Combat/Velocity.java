package tk.rektsky.Module.Combat;

import net.minecraft.client.*;
import tk.rektsky.Event.*;
import tk.rektsky.Module.Movement.*;
import tk.rektsky.Module.*;
import net.minecraft.network.play.server.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Velocity extends Module
{
    public static boolean shouldEnable;
    boolean velHurt;
    long atkTimer;
    long velTimer;
    
    public Velocity() {
        super("Velocity", "Lower knockback : )", 0, Category.COMBAT);
        this.velHurt = false;
        this.atkTimer = 0L;
        this.velTimer = this.getTime();
    }
    
    public long getTime() {
        return Minecraft.getSystemTime();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof PacketReceiveEvent && Minecraft.getSystemTime() - ((Fly)ModulesManager.getModuleByClass(Fly.class)).disable > 5000L && Velocity.shouldEnable) {
            final Packet packet = ((PacketReceiveEvent)event).getPacket();
            if (packet instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)packet).getMotionY() > 2500 && ((S12PacketEntityVelocity)packet).getMotionY() < 4400) {
                ((PacketReceiveEvent)event).setCanceled(true);
            }
        }
        if (event instanceof PacketSentEvent && ((PacketSentEvent)event).getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity)((PacketSentEvent)event).getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK) {
            this.atkTimer = this.getTime();
        }
    }
    
    static {
        Velocity.shouldEnable = true;
    }
}
