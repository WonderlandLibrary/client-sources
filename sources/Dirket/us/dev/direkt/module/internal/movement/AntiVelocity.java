package us.dev.direkt.module.internal.movement;

import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketResourcePackStatus.Action;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import us.dev.api.property.BoundedProperty;
import us.dev.api.property.Property;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventPreReceivePacket;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

@ModData(label = "Anti Velocity", aliases = {"knockback", "antikb", "velocity"}, category = ModCategory.MOVEMENT)
public class AntiVelocity extends ToggleableModule {

    @Exposed(description = "Should all velocity taken be reversed")
    private Property<Boolean> reverseVelocity = new Property<>("Reverse", false);

    @Exposed(description = "The percentage of velocity that should be retained")
    private Property<Integer> percentVelocity = new BoundedProperty<>("Percent", 0, 0, 100);

    @Listener
    protected Link<EventPreReceivePacket> onPreReceivePacket = new Link<>(event -> {
        if (event.getPacket() instanceof SPacketEntityVelocity) {
            SPacketEntityVelocity s12 = (SPacketEntityVelocity)event.getPacket();
            if (Wrapper.getPlayer() != null && Wrapper.getPlayer().getEntityId() == s12.getEntityID()) {
                event.setCancelled(true);
                double x = s12.getMotionX() * this.percentVelocity.getValue().doubleValue() / 100 / 8000;
                double y = s12.getMotionY() * this.percentVelocity.getValue().doubleValue() / 100 / 8000;
                double z = s12.getMotionZ() * this.percentVelocity.getValue().doubleValue() / 100 / 8000;
                Wrapper.getPlayer().motionX += (reverseVelocity.getValue() ? -x : x);
                Wrapper.getPlayer().motionY += y;
                Wrapper.getPlayer().motionZ += (reverseVelocity.getValue() ? -z : z);
            }
        } else if(event.getPacket() instanceof SPacketExplosion) {
            event.setCancelled(true);
        }
    }, new PacketFilter<>(SPacketEntityVelocity.class, SPacketExplosion.class));
	
}
