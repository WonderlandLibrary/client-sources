// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.misc;

import net.minecraft.util.StringUtils;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Ambience", renderName = "Ambience", description = "Changes the world time.", category = ModuleCategory.MISC)
public final class AmbienceModule extends Module
{
    private final EnumProperty<Time> timeEnumProperty;
    private final EventListener<EventReceivePacket> sendPacketEventListener;
    private final EventListener<EventUpdate> updateEventListener;
    
    public AmbienceModule() {
        this.timeEnumProperty = new EnumProperty<Time>("Time", Time.NIGHT);
        this.sendPacketEventListener = (event -> {
            if (event.getPacket() instanceof S03PacketTimeUpdate) {
                event.cancel();
            }
            return;
        });
        this.updateEventListener = (event -> {
            this.setSuffix(StringUtils.upperSnakeCaseToPascal(this.timeEnumProperty.getValueAsString()));
            this.mc.theWorld.setWorldTime((this.timeEnumProperty.getValue() == Time.DAY) ? 3000L : 15000L);
        });
    }
    
    private enum Time
    {
        DAY, 
        NIGHT;
    }
}
