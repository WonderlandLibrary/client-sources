package xyz.mathax.mathaxclient.systems.modules.misc;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;

public class ColorSigns extends Module {

    public ColorSigns(Category category) {
        super(category, "Color Signs", "Allows you to use colors on signs on NON-PAPER servers. (use \"&\" for color symbols)");
    }

    @Override
    public void onEnable() {
        String brand = mc.player.getServerBrand();
        if (brand == null) {
            return;
        }

        if (brand.contains("Paper")) {
            warning("You are on a paper server. Color signs won't work here.");
        }
    }

    @EventHandler
    private void onPacketSend(PacketEvent.Send event) {
        if (!(event.packet instanceof UpdateSignC2SPacket)) {
            return;
        }

        UpdateSignC2SPacket packet = (UpdateSignC2SPacket)event.packet;
        for (int l = 0; l < packet.getText().length; l++) {
            String newText = packet.getText()[l].replaceAll("(?i)\u00a7|&([0-9A-FK-OR])", "\u00a7\u00a7$1$1");
            packet.getText()[l] = newText;
        }

        event.packet = packet;
    }
}