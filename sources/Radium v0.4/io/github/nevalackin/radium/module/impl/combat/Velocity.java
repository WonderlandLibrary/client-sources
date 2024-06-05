package io.github.nevalackin.radium.module.impl.combat;

import io.github.nevalackin.radium.event.impl.packet.PacketReceiveEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.Representation;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

@ModuleInfo(label = "Velocity", category = ModuleCategory.COMBAT)
public final class Velocity extends Module {

    private final DoubleProperty horizontalPercentProperty = new DoubleProperty("Horizontal", 0, 0,
            100, 0.5, Representation.PERCENTAGE);
    private final DoubleProperty verticalPercentProperty = new DoubleProperty("Vertical", 0, 0,
            100, 0.5, Representation.PERCENTAGE);

    @Listener
    private void onPacketReceiveEvent(PacketReceiveEvent e) {
        Packet<?> packet = e.getPacket();
        if (packet instanceof S12PacketEntityVelocity) {
            int verticalPerc = verticalPercentProperty.getValue().intValue();
            int horizontalPerc = horizontalPercentProperty.getValue().intValue();
            boolean cancel = verticalPerc == 0 && horizontalPerc == 0;
            if (cancel) {
                e.setCancelled();
                return;
            }
            S12PacketEntityVelocity velocityPacket = (S12PacketEntityVelocity) packet;
            velocityPacket.motionX *= (horizontalPercentProperty.getValue() / 100);
            velocityPacket.motionY *= (verticalPercentProperty.getValue() / 100);
            velocityPacket.motionZ *= (horizontalPercentProperty.getValue() / 100);
        } else if (packet instanceof S27PacketExplosion) {
            int verticalPerc = verticalPercentProperty.getValue().intValue();
            int horizontalPerc = horizontalPercentProperty.getValue().intValue();
            boolean cancel = verticalPerc == 0 && horizontalPerc == 0;
            if (cancel) {
                e.setCancelled();
                return;
            }
            S27PacketExplosion packetExplosion = (S27PacketExplosion) packet;
            packetExplosion.motionX *= (horizontalPercentProperty.getValue() / 100);
            packetExplosion.motionY *= (verticalPercentProperty.getValue() / 100);
            packetExplosion.motionZ *= (horizontalPercentProperty.getValue() / 100);
        }
    }
}
