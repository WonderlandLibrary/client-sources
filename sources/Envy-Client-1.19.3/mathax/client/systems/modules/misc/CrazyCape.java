package mathax.client.systems.modules.misc;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.Random;

public class CrazyCape extends Module {

    @EventHandler
    public void onTick(TickEvent.Post event) {
        Random random = new Random();
        int randomOffset = random.nextInt(5) - 1; // generates a random number between -1 and 1
        mc.player.capeX = mc.player.prevCapeX + 0.7 + randomOffset;
        mc.player.capeY = mc.player.prevCapeY + 0.5 + randomOffset;
        mc.player.capeZ = mc.player.prevCapeZ + 0.7 + randomOffset;
    }
    public CrazyCape() {
        super(Categories.Fun, Items.PAPER, "Crazy Cape", "Breaks the Cape Physics");
    }
}
