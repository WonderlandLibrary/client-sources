package us.dev.direkt.module.internal.player;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventPreReceivePacket;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Rotation Fix", aliases = {"NoRotation", "NoRotate"}, category = ModCategory.PLAYER)
public class RotationFix extends ToggleableModule {

    @Listener
    protected Link<EventPreReceivePacket> onPreReceivePacket = new Link<>(event -> {
        if (Wrapper.getPlayer() != null) {
            final SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
            event.setPacket(new SPacketPlayerPosLook(packet.getX(), packet.getY(), packet.getZ(), Wrapper.getPlayer().rotationYaw, Wrapper.getPlayer().rotationPitch, packet.getFlags(), packet.getTeleportId()));
        }
    }, new PacketFilter<>(SPacketPlayerPosLook.class));

}
