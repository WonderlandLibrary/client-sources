package wtf.diablo.client.module.impl.misc.sessioninfo;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S40PacketDisconnect;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.client.renderering.OverlayEvent;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.notification.Notification;
import wtf.diablo.client.notification.NotificationType;

@ModuleMetaData(name = "Session Info", description = "Displays information about your current session", category = ModuleCategoryEnum.MISC)
public final class SessionInfoModule extends AbstractModule {
    private ServerSession serverSession;

    private boolean shouldChange;

    @Override
    protected void onEnable() {
        super.onEnable();
        Diablo.getInstance().getNotificationManager().addNotification(new Notification("Session Info", "Session Info will not work until you reconnect to a server", 7500L, NotificationType.WARNING));
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        this.serverSession = null;
        this.shouldChange = true;
    }

    @EventHandler
    private final Listener<RecievePacketEvent> recievePacketEventListener = e -> {
        if (e.getPacket() instanceof S00PacketDisconnect || e.getPacket() instanceof S40PacketDisconnect) {
            this.serverSession = null;
            this.shouldChange = true;
        }

        if (e.getPacket() instanceof S01PacketJoinGame && this.shouldChange) {
            this.serverSession = new ServerSession();
            this.shouldChange = false;
        }
    };

    @EventHandler
    private final Listener<OverlayEvent> overlayEventListener = e -> {
        final String sessionText = serverSession.getFormattedTime();
        final int width = this.mc.fontRendererObj.getStringWidth(sessionText);

        mc.fontRendererObj.drawStringWithShadow(sessionText, e.getScaledResolution().getScaledWidth() / 2.0F - width / 2.0F, 25, -1);
    };
}
