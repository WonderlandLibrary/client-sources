package wtf.diablo.client.module.impl.misc;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.impl.movement.speed.SpeedModule;
import wtf.diablo.client.notification.Notification;
import wtf.diablo.client.notification.NotificationType;

@ModuleMetaData(name = "Auto Disable", description = "Automatically disables modules when certain conditions are met", category = ModuleCategoryEnum.MISC)
public final class AutoDisableModule extends AbstractModule {
    @EventHandler
    private final Listener<RecievePacketEvent> packetEventListener = e -> {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            final SpeedModule speedModule = Diablo.getInstance().getModuleRepository().getModuleInstance(SpeedModule.class);

            if (speedModule.isEnabled()) {
                speedModule.toggle();
                Diablo.getInstance().getNotificationManager().addNotification(new Notification("Flag", "Disabled various modules due to flag", 5000, NotificationType.ERROR));
            }
        }
    };
}
