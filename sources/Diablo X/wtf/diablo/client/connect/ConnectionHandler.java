package wtf.diablo.client.connect;

import best.azura.eventbus.core.EventBus;
import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.play.server.S40PacketDisconnect;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.event.impl.network.ServerConnectEvent;
import wtf.diablo.client.notification.Notification;
import wtf.diablo.client.notification.NotificationType;

import java.util.ArrayList;
import java.util.List;

public final class ConnectionHandler {
    private final List<String> knownBannedIps = new ArrayList<>();

    public ConnectionHandler(final EventBus eventBus) {
        eventBus.register(this);
    }

    @EventHandler
    private final Listener<ServerConnectEvent> serverConnectEventListener = serverConnectEvent -> {
        final String currentIp = getComputerIpAdress();

        if (!serverConnectEvent.getIp().contains("hypixel.net"))
            return;

        if (this.knownBannedIps.contains(currentIp)) {
            serverConnectEvent.cancel("§7[§cDiablo§7] §rYour current IP address will get you security banned on hypixel\nPlease use a VPN/Proxy to connect to hypixel or reconnect to your vpn.");
            return;
        }
    };

    @EventHandler
    private final Listener<RecievePacketEvent> packetEventListener = recievePacketEvent -> {
        if (!GuiConnecting.LAST_IP.contains("hypixel.net"))
            return;

        String reason;

        if (recievePacketEvent.getPacket() instanceof S00PacketDisconnect) {
            final S00PacketDisconnect packet = (S00PacketDisconnect) recievePacketEvent.getPacket();
            reason = packet.func_149603_c().getUnformattedText();
        } else if (recievePacketEvent.getPacket() instanceof S40PacketDisconnect) {
            final S40PacketDisconnect packet = (S40PacketDisconnect) recievePacketEvent.getPacket();
            reason = packet.getReason().getUnformattedText();
        } else {
            return;
        }

        if (reason.contains("banned")) {
            final String computerIpAdress = getComputerIpAdress();
            if (this.knownBannedIps.contains(computerIpAdress)) {
                return;
            }
            this.knownBannedIps.add(computerIpAdress);

            final Notification notification = new Notification("IP Banned", "Marked your current IP address as banned", 5000L, NotificationType.ERROR);

            Diablo.getInstance().getNotificationManager().addNotification(notification);
        }
    };

    public static final String getComputerIpAdress() {
        final HttpResponse<String> response = Unirest.get("https://checkip.amazonaws.com/").asString();
        return response.getBody();
    }
}
