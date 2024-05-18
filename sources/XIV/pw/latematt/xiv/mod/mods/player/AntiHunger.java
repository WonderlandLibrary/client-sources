package pw.latematt.xiv.mod.mods.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

/**
 * @author Jack
 */
public class AntiHunger extends Mod implements Listener<SendPacketEvent> {
    public AntiHunger() {
        super("AntiHunger", ModType.PLAYER, Keyboard.KEY_NONE, 0xFFBC832F);
    }

    @Override
    public void onEventCalled(SendPacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer player = (C03PacketPlayer) event.getPacket();
            if (mc.thePlayer.posY == mc.thePlayer.lastTickPosY && !mc.playerController.isHittingBlock)
                player.setOnGround(false);
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}
