package ez.cloudclient.module.modules.player;

import ez.cloudclient.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Category.PLAYER, "Prevent Taking Fall Damage");
    }

    @Override
    protected void onEnable() {
        if (mc.player != null) {
            mc.player.fallDistance = 0;
        }
    }

    @Override
    protected void onDisable() {
        if (mc.player != null) {
            mc.player.fallDistance = 0;
        }
    }

    @Override
    public void onTick() {
        if (mc.player.fallDistance != 0) {
            mc.player.connection.sendPacket(new CPacketPlayer(true));
        }
    }
}
