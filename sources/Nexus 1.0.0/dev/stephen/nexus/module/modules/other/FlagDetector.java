package dev.stephen.nexus.module.modules.other;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.network.EventPacket;
import dev.stephen.nexus.event.types.TransferOrder;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.utils.render.notifications.impl.Notification;
import dev.stephen.nexus.utils.render.notifications.impl.NotificationMoode;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public class FlagDetector extends Module {
    public FlagDetector() {
        super("FlagDetector", "Detects flags", 0, ModuleCategory.OTHER);
    }

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }
        if (event.getOrder() == TransferOrder.RECEIVE) {
            if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
                Client.INSTANCE.getNotificationManager().addNewNotification(new Notification("You flagged", 1000, NotificationMoode.WARNING));
            }
        }
    };
}
