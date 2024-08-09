package mathax.client.systems.modules.chat;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;

public class AutoL extends Module {

    private GameMessageS2CPacket packet;

    public AutoL() {
        super(Categories.Chat, Items.PAPER, "AutoL", "Automatically says L when people are salty.");
    }

    @EventHandler
    public void onTick(PacketEvent.Receive event) {
        if (event.packet instanceof TitleS2CPacket) {
            packet = (GameMessageS2CPacket) event.packet;
            if (packet.toString().contains("hacker") || packet.toString().contains("Hacker") || packet.toString().contains("HACKER") || packet.toString().contains("Hacks") || packet.toString().contains("hacks") || packet.toString().contains("HACKS") || packet.toString().contains("Lag") || packet.toString().contains("lag") || packet.toString().contains("LAG") || packet.toString().contains("cheater") || packet.toString().contains("Cheater") || packet.toString().contains("CHEATER") || packet.toString().contains("cheat") || packet.toString().contains("Cheat") || packet.toString().contains("CHEAT") || packet.toString().contains("hack") || packet.toString().contains("Hack") || packet.toString().contains("HACK") || packet.toString().contains("hacking") || packet.toString().contains("Hacking") || packet.toString().contains("HACKING") || packet.toString().contains("cheating") || packet.toString().contains("Cheating") || packet.toString().contains("CHEATING") || packet.toString().contains("cheats") || packet.toString().contains("Cheats") || packet.toString().contains("CHEATS") || packet.toString().contains("hacks") || packet.toString().contains("Hacks") || packet.toString().contains("HACKS") || packet.toString().contains("hacked") || packet.toString().contains("Hacked") || packet.toString().contains("HACKED") || packet.toString().contains("hackers") || packet.toString().contains("Hackers") || packet.toString().contains("HACKERS") || packet.toString().contains("cheaters") || packet.toString().contains("Cheaters") || packet.toString().contains("CHEATERS") || packet.toString().contains("cheats") || packet.toString().contains("Cheats") || packet.toString().contains("CHEATS") || packet.toString().contains("hacks") || packet.toString().contains("Hacks") || packet.toString().contains("HACKS") || packet.toString().contains("hacked") || packet.toString().contains("Hacked") || packet.toString().contains("HACKED")) {
                mc.player.networkHandler.sendChatMessage("L");
            }
        }
    }
}

