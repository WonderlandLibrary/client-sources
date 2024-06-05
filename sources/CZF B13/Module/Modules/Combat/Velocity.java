package gq.vapu.czfclient.Module.Modules.Combat;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketReceive;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

import java.awt.*;

public class Velocity extends Module {
    private final Numbers<Double> percentage = new Numbers<Double>("Percent", "Percent", 0.0, 0.0, 100.0, 5.0);

    public Velocity() {
        super("Velocity", new String[]{"antivelocity", "antiknockback", "antikb"}, ModuleType.Combat);
        this.addValues(this.percentage);
        this.setColor(new Color(191, 191, 191).getRGB());
    }

    @EventHandler
    private void onPacket(EventPacketReceive e) {
        if (e.getPacket() instanceof S12PacketEntityVelocity || e.getPacket() instanceof S27PacketExplosion) {
            if (this.percentage.getValue().equals(0.0)) {
                e.setCancelled(true);
            } else {
                S12PacketEntityVelocity packet = (S12PacketEntityVelocity) e.getPacket();
                packet.motionX = (int) (this.percentage.getValue() / 100.0);
                packet.motionY = (int) (this.percentage.getValue() / 100.0);
                packet.motionZ = (int) (this.percentage.getValue() / 100.0);
            }
        }
    }
}
