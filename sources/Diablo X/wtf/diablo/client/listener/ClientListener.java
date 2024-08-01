package wtf.diablo.client.listener;

import best.azura.eventbus.core.EventBus;
import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S01PacketJoinGame;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.module.impl.combat.killaura.KillAuraModule;
import wtf.diablo.client.notification.Notification;
import wtf.diablo.client.notification.NotificationManager;
import wtf.diablo.client.notification.NotificationType;

public final class ClientListener {
    private final Minecraft mc = Minecraft.getMinecraft();

    public ClientListener(final EventBus eventBus) {
        eventBus.register(this);
    }

    @EventHandler
    private final Listener<RecievePacketEvent> recievePacketEventListener = e -> {
        final NotificationManager notificationManager = Diablo.getInstance().getNotificationManager();
        if (e.getPacket() instanceof S01PacketJoinGame) {
            final KillAuraModule killAuraModule = Diablo.getInstance().getModuleRepository().getModuleInstance(KillAuraModule.class);

            if (killAuraModule.isEnabled()) {
                notificationManager.addNotification(new Notification("Info", "KillAura has been disabled due to a server change", 5000, NotificationType.INFORMATION));
                killAuraModule.toggle(false);
            }

            if (mc.isSingleplayer())
                notificationManager.addNotification(new Notification("Warning!", "Some modules may not function as intended in singleplayer", 10000, NotificationType.WARNING));
        }
    };
}
