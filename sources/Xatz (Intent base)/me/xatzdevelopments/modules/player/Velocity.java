package me.xatzdevelopments.modules.player;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventReadPacket;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.ModeSetting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {
    public ModeSetting Mode = new ModeSetting("Mode", "Simple",  "Simple", "AAC", "Cubecraft" );
    
    public Velocity() {
        super("Velocity", Keyboard.KEY_NONE, Category.PLAYER, "Reduces your knockback");
        this.addSettings(Mode);
    }
    
    @Override
    public void onEvent(final Event e) {
       // if (ModulesUtils.GetModule("Speed").isEnabled() && ModulesUtils.GetModeSetting("Speed", "Mode").getMode() == "Velocity" && MoveUtils.isMoving()) {
       //     return;
       //}
        if (this.mc.isIntegratedServerRunning()) {
            return;
        }
        final String mode;
        switch (mode = this.Mode.getMode()) {
            case "Simple": {
                if (!(e instanceof EventReadPacket)) {
                    break;
                }
                final Packet p = ((EventReadPacket)e).getPacket();
                if ((p instanceof S27PacketExplosion || p instanceof S12PacketEntityVelocity) && this.mc.thePlayer.hurtTime > 7) {
                    e.setCancelled(true);
                    break;
                }
                break;
            }
            case "AAC": {
                if (e instanceof EventUpdate && this.mc.thePlayer.hurtTime > 7) {
                    final EntityPlayerSP thePlayer = this.mc.thePlayer;
                    thePlayer.motionX *= 0.7;
                    final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                    thePlayer2.motionZ *= 0.7;
                    break;
                }
                break;
            }
            case "Cubecraft": {
                if (!(e instanceof EventReadPacket)) {
                    break;
                }
                final Packet p = ((EventReadPacket)e).getPacket();
                if ((!(p instanceof S27PacketExplosion) && !(p instanceof S12PacketEntityVelocity)) || this.mc.thePlayer.hurtTime <= 7) {
                    break;
                }
                e.setCancelled(true);
                if (this.mc.thePlayer.motionY < 0.0 && this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.motionY = 0.01;
                    break;
                }
                break;
            }
            default:
                break;
        }
    }
}
