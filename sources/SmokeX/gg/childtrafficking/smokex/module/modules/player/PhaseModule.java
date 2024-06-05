// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.player;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Phase", renderName = "Phase", description = "Allows you to phase through blocks", category = ModuleCategory.PLAYER)
public final class PhaseModule extends Module
{
    private double x;
    private double y;
    private double z;
    EnumProperty<Mode> modeEnumProperty;
    private EventListener<EventReceivePacket> eventReceivePacketEventListener;
    private EventListener<EventUpdate> eventUpdateEventListener;
    
    public PhaseModule() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.modeEnumProperty = new EnumProperty<Mode>("Mode", Mode.SKYWARS);
        this.eventReceivePacketEventListener = (event -> {
            switch (this.modeEnumProperty.getValue()) {
                case SKYWARS: {
                    if (event.getPacket() instanceof S08PacketPlayerPosLook && this.mc.thePlayer != null && this.mc.thePlayer.ticksExisted < 30) {
                        final S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)event.getPacket();
                        this.x = packet.getX();
                        this.y = packet.getY();
                        this.z = packet.getZ();
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        this.eventUpdateEventListener = (event -> {
            switch (this.modeEnumProperty.getValue()) {
                case SKYWARS: {
                    if (this.mc.thePlayer.ticksExisted < 30) {
                        this.mc.thePlayer.setPosition(this.x, this.y - 4.0, this.z);
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
        });
    }
    
    private enum Mode
    {
        SKYWARS;
    }
}
