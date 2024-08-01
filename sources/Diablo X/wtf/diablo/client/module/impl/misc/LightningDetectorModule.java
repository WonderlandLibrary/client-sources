package wtf.diablo.client.module.impl.misc;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.notification.Notification;
import wtf.diablo.client.notification.NotificationType;

@ModuleMetaData(name = "Lightning Detector", description = "Detects lightning", category = ModuleCategoryEnum.MISC)
public final class LightningDetectorModule extends AbstractModule {



    @EventHandler
    private final Listener<RecievePacketEvent> packetEventListener = e -> {
        if (e.getPacket() instanceof S29PacketSoundEffect) {
            S29PacketSoundEffect packet = (S29PacketSoundEffect) e.getPacket();
            if (packet.getSoundName().equals("ambient.weather.thunder")) {
                Diablo.getInstance().getNotificationManager().addNotification(new Notification("Lightning", String.format("Lightning detected at %s, %s, %s", packet.getX(), packet.getY(), packet.getZ()), 7500, NotificationType.INFORMATION));
            }
        }
    };
}