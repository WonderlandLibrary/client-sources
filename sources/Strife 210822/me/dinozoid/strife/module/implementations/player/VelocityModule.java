package me.dinozoid.strife.module.implementations.player;

import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.network.PacketInboundEvent;
import me.dinozoid.strife.event.implementations.network.PacketOutboundEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.util.player.PlayerUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S27PacketExplosion;

@ModuleInfo(name = "Velocity", renderName = "Velocity", aliases = "Velo", category = Category.PLAYER)
public class VelocityModule extends Module {

    private DoubleProperty horizontal = new DoubleProperty("Horizontal", 0, 0, 100, 5, Property.Representation.INT);
    private DoubleProperty vertical = new DoubleProperty("Vertical", 0, 0, 100, 5, Property.Representation.INT);

    @Override
    public void init() {
        super.init();
        setSuffix(horizontal.value().intValue() + "% " + vertical.value().intValue() + "%");
        horizontal.addValueChange((oldValue, value) -> setSuffix(value.intValue() + "% " + vertical.value().intValue() + "%"));
        vertical.addValueChange((oldValue, value) -> setSuffix(value.intValue() + "% " + horizontal.value().intValue() + "%"));
    }

    @EventHandler
    private final Listener<PacketInboundEvent> packetInboundEvent = new Listener<>(event -> {
       if(event.packet() instanceof S12PacketEntityVelocity) {
           S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.packet();
           if(mc.theWorld != null && mc.thePlayer != null && packet.getEntityID() == mc.thePlayer.getEntityId()) {
               if(horizontal.value() == 0 && vertical.value() == 0) event.cancel();
               packet.setMotionX((int) (packet.getMotionX() * horizontal.value() / 100));
               packet.setMotionY((int) (packet.getMotionY() * vertical.value() / 100));
               packet.setMotionZ((int) (packet.getMotionZ() * horizontal.value() / 100));
           }
       }
       if(event.packet() instanceof S27PacketExplosion) {
           S27PacketExplosion packet = (S27PacketExplosion) event.packet();
           if(mc.theWorld != null && mc.thePlayer != null) {
               if(horizontal.value() == 0 && vertical.value() == 0) event.cancel();
               packet.setX(packet.getX() / 100 * horizontal.value());
               packet.setY(packet.getY() / 100 * vertical.value());
               packet.setZ(packet.getZ() / 100 * horizontal.value());
           }
       }
    });

}
