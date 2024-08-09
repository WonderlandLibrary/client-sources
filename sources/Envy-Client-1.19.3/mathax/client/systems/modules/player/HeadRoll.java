package mathax.client.systems.modules.player;

import mathax.client.eventbus.EventHandler;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;

public class HeadRoll extends Module {
    public HeadRoll() {
        super(Categories.Player, Items.PLAYER_HEAD, "Head Roll", "CRYSTAL || Rolls the players head lol.");
    }

    @EventHandler
    public void onTick() {
        float timer = 0;
        if (mc.player != null) {
            timer = mc.player.age % 20 / 10F;
        }
        float pitch = MathHelper.sin(timer * (float)Math.PI) * 90F;

        if (mc.player != null) {
            mc.player.networkHandler.sendPacket(
                new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(), pitch,
                    mc.player.isOnGround()));
        }
    }
}
