package gq.vapu.czfclient.Module.Modules.Blatant;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketSend;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.optifine.MathUtils;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PingSpoof extends Module {
    private final List<Packet> packetList = new CopyOnWriteArrayList<Packet>();
    private final TimerUtil timer = new TimerUtil();

    public PingSpoof() {
        super("FakePing", new String[]{"spoofping", "ping"}, ModuleType.Blatant);
        this.setColor(new Color(117, 52, 203).getRGB());
    }

    @EventHandler
    private void onPacketSend(EventPacketSend e) {
        if (EventPacketSend.getPacket() instanceof C00PacketKeepAlive && mc.thePlayer.isEntityAlive()) {
            this.packetList.add(EventPacketSend.getPacket());
            e.setCancelled(true);
        }
        if (this.timer.hasReached(750.0)) {
            if (!this.packetList.isEmpty()) {
                int i = 0;
                double totalPackets = MathUtils.getIncremental(Math.random() * 10.0, 1.0);
                for (Packet packet : this.packetList) {
                    if ((double) i >= totalPackets)
                        continue;
                    ++i;
                    mc.getNetHandler().getNetworkManager().sendPacket(packet);
                    this.packetList.remove(packet);
                }
            }
            mc.getNetHandler().getNetworkManager().sendPacket(new C00PacketKeepAlive(10000));
            this.timer.reset();
        }
    }
}
