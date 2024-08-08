package me.xatzdevelopments.modules.player;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventReadPacket;
import me.xatzdevelopments.modules.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Module
{
    public NoRotate() {
        super("NoRotate", 0, Category.PLAYER, "Prevents the server from rotating your head.");
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventReadPacket) {
            if (this.mc.isSingleplayer()) {
                return;
            }
            final Packet p = ((EventReadPacket)e).getPacket();
            if (p instanceof S08PacketPlayerPosLook) {
                final S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)p;
                packet.field_148936_d = this.mc.thePlayer.rotationYaw;
                packet.field_148937_e = this.mc.thePlayer.rotationPitch;
            }
        }
    }
}
