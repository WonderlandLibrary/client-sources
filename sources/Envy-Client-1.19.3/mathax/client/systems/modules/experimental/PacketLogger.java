package mathax.client.systems.modules.experimental;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.util.Formatting;

public class PacketLogger extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> c2sPackets = sgGeneral.add(new BoolSetting.Builder()
        .name("C2S-packets")
        .description("Logs C2S packets (The packets you send to the server)")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> s2cPackets = sgGeneral.add(new BoolSetting.Builder()
        .name("S2C-packets")
        .description("Logs S2C packets (The packets you receive from the server)")
        .defaultValue(false)
        .build()
    );

    public PacketLogger() {
        super(Categories.Experimental, Items.AIR, "Packet-Logger", "Logs every packet you send or receive from the server.");
    }

    @EventHandler
    private void onPacketSent(PacketEvent.Sent event) {
        if (c2sPackets.get()) {
            Packet packet = event.packet;
            Class packetClass = packet.getClass();
            String packetName = packetClass.getCanonicalName();
            info(Formatting.WHITE + packetName);
        }
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive event) {
        if (s2cPackets.get()) {
            Packet packet = event.packet;
            Class packetClass = packet.getClass();
            String packetName = packetClass.getCanonicalName();
            info(Formatting.WHITE + packetName);
        }
    }
}
