// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.combat;

import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Velocity", renderName = "Velocity", description = "Changes how much you get knocked back", aliases = { "velo", "kb", "knockback", "antikb" }, category = ModuleCategory.COMBAT)
public final class VelocityModule extends Module
{
    private final NumberProperty<Float> horizontalProperty;
    private final NumberProperty<Float> verticalProperty;
    private final EventListener<EventUpdate> eventUpdate;
    private final EventListener<EventReceivePacket> eventReceivePacket;
    
    public VelocityModule() {
        this.horizontalProperty = new NumberProperty<Float>("Horizontal", 0.0f, 0.0f, 100.0f, 1.0f);
        this.verticalProperty = new NumberProperty<Float>("Vertical", 0.0f, 0.0f, 100.0f, 1.0f);
        this.eventUpdate = (event -> this.setSuffix(this.horizontalProperty.getValue() + "%, " + this.verticalProperty.getValue() + "%"));
        this.eventReceivePacket = (event -> {
            if (event.getPacket() instanceof S12PacketEntityVelocity) {
                if (this.horizontalProperty.getValue() == 0.0f && this.verticalProperty.getValue() == 0.0f) {
                    event.cancel();
                }
                final S12PacketEntityVelocity packet = (S12PacketEntityVelocity)event.getPacket();
                if (packet.getEntityID() == this.mc.thePlayer.getEntityId()) {
                    packet.setMotionX(-1 * (int)(packet.getMotionX() * (this.horizontalProperty.getValue() / 100.0f)));
                    packet.setMotionY(-1 * (int)(packet.getMotionY() * (this.verticalProperty.getValue() / 100.0f)));
                    packet.setMotionZ(-1 * (int)(packet.getMotionZ() * (this.horizontalProperty.getValue() / 100.0f)));
                }
            }
            if (event.getPacket() instanceof S27PacketExplosion) {
                if (this.horizontalProperty.getValue() == 0.0f && this.verticalProperty.getValue() == 0.0f) {
                    event.cancel();
                }
                final S27PacketExplosion packet2 = (S27PacketExplosion)event.getPacket();
                packet2.setMotionX(-1.0f * (packet2.getMotionX() * (this.horizontalProperty.getValue() / 100.0f)));
                packet2.setMotionY(-1.0f * (packet2.getMotionY() * (this.verticalProperty.getValue() / 100.0f)));
                packet2.setMotionZ(-1.0f * (packet2.getMotionZ() * (this.horizontalProperty.getValue() / 100.0f)));
            }
        });
    }
    
    @Override
    public void init() {
        super.init();
    }
}
