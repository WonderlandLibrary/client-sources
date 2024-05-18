package pw.latematt.xiv.mod.mods.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

/**
 * @author TehNeon
 */
public class NoFall extends Mod implements Listener<SendPacketEvent> {
    public NoFall() {
        super("NoFall", ModType.PLAYER, Keyboard.KEY_N, 0xFFF5B8CD);
    }

    public void onEventCalled(SendPacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            if (mc.thePlayer.fallDistance < 3)
                return;
            C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
            packet.setOnGround(true);
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
