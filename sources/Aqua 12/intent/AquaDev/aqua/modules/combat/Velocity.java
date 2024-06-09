// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.combat;

import net.minecraft.network.Packet;
import intent.AquaDev.aqua.utils.PlayerUtil;
import events.listeners.EventUpdate;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import events.listeners.EventReceivedPacket;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.TimeUtil;
import intent.AquaDev.aqua.modules.Module;

public class Velocity extends Module
{
    TimeUtil timeUtil;
    
    public Velocity() {
        super("Velocity", Type.Combat, "Velocity", 0, Category.Combat);
        this.timeUtil = new TimeUtil();
        Aqua.setmgr.register(new Setting("MatrixValue", this, 0.1, 0.0, 0.8, false));
        Aqua.setmgr.register(new Setting("Mode", this, "Cancel", new String[] { "CancelLongjump", "Matrix", "Cancel" }));
    }
    
    @Override
    public void setup() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventReceivedPacket) {
            if (Aqua.setmgr.getSetting("VelocityMode").getCurrentMode().equalsIgnoreCase("CancelLongjump")) {
                final Packet p = EventReceivedPacket.INSTANCE.getPacket();
                if (p instanceof S12PacketEntityVelocity) {
                    final S12PacketEntityVelocity packet = (S12PacketEntityVelocity)p;
                    if (packet.getEntityID() == Velocity.mc.thePlayer.getEntityId() && Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Gamster") && !Aqua.moduleManager.getModuleByName("Longjump").isToggled()) {
                        EventReceivedPacket.INSTANCE.setCancelled(true);
                    }
                }
                if (p instanceof S27PacketExplosion) {
                    EventReceivedPacket.INSTANCE.setCancelled(true);
                }
            }
            if (Aqua.setmgr.getSetting("VelocityMode").getCurrentMode().equalsIgnoreCase("Cancel")) {
                final Packet p = EventReceivedPacket.INSTANCE.getPacket();
                if (p instanceof S12PacketEntityVelocity) {
                    final S12PacketEntityVelocity packet = (S12PacketEntityVelocity)p;
                    if (packet.getEntityID() == Velocity.mc.thePlayer.getEntityId()) {
                        EventReceivedPacket.INSTANCE.setCancelled(true);
                    }
                }
                if (p instanceof S27PacketExplosion) {
                    EventReceivedPacket.INSTANCE.setCancelled(true);
                }
            }
        }
        if (event instanceof EventUpdate && Aqua.setmgr.getSetting("VelocityMode").getCurrentMode().equalsIgnoreCase("Matrix") && !Aqua.moduleManager.getModuleByName("Longjump").isToggled() && Velocity.mc.thePlayer.hurtTime != 0 && !Velocity.mc.gameSettings.keyBindJump.pressed) {
            PlayerUtil.setSpeed((float)Aqua.setmgr.getSetting("VelocityMatrixValue").getCurrentNumber());
        }
    }
}
