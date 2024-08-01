package wtf.diablo.client.discord;

import best.azura.eventbus.core.EventBus;
import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S40PacketDisconnect;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.notification.Notification;
import wtf.diablo.client.notification.NotificationManager;
import wtf.diablo.client.notification.NotificationType;

public final class DiscordController {
    private final long startTimestamp = System.currentTimeMillis();

    private final NotificationManager notificationManager;
    private final long applicationId;

    public DiscordController(final long applicationId, final NotificationManager notificationManager, final EventBus eventBus) {
        this.applicationId = applicationId;
        this.notificationManager = notificationManager;

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        new DiscordListener(this, eventBus);
    }

    public void initialize() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
                .setReadyEventHandler(user -> notificationManager.addNotification(new Notification("Discord RPC", "Connected to Discord as " + user.username + "#" + user.discriminator, 5000, NotificationType.SUCCESS)))
                .setErroredEventHandler((code, message) -> notificationManager.addNotification(new Notification("Discord RPC", "Error: " + code + " " + message, 5000, NotificationType.ERROR)))
                .setDisconnectedEventHandler((code, message) -> notificationManager.addNotification(new Notification("Discord RPC", "Disconnected: " + code + " " + message, 5000, NotificationType.ERROR)))
                .build();

        DiscordRPC.discordInitialize(String.valueOf(applicationId), handlers, true);
    }

    public void updatePresence(final String text) {
        final Diablo diablo = Diablo.getInstance();

        final DiscordRichPresence presence = new DiscordRichPresence.Builder("Build: " + diablo.getClientInformation().getVersion() + " | UID: " + diablo.getDiabloSession().getUid())
                .setStartTimestamps(startTimestamp)
                .setBigImage("logo", "v" + diablo.getClientInformation().getBuild())
                .setDetails(text)
                .build();

        DiscordRPC.discordUpdatePresence(presence);
    }

    public void shutdown() {
        DiscordRPC.discordShutdown();
    }

    private final static class DiscordListener {
        private final static Minecraft mc = Minecraft.getMinecraft();

        private final DiscordController discordController;

        private DiscordListener(final DiscordController discordController, final EventBus eventBus) {
            this.discordController = discordController;

            eventBus.register(this);
        }

        @EventHandler
        private final Listener<RecievePacketEvent> packetEventListener = e -> {
            final DiscordController discordController = this.getDiscordController();

            if (e.getPacket() instanceof S01PacketJoinGame) {
                final String text = mc.isSingleplayer() ? "Playing a Singleplayer World" : "Playing on " + mc.getCurrentServerData().serverIP;
                discordController.updatePresence(text);
            }

            if (e.getPacket() instanceof S40PacketDisconnect || e.getPacket() instanceof S00PacketDisconnect) {
                discordController.updatePresence("In the Main Menu");
            }
        };

        private final DiscordController getDiscordController() {
            return this.discordController;
        }
    }
}
