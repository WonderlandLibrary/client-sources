package koks.modules.impl.player;

import koks.event.Event;
import koks.event.impl.PacketEvent;
import koks.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 13:29
 */
public class NoRotate extends Module {

    public NoRotate() {
        super("NoRotate", "Your client dosnt rotate", Category.PLAYER);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof PacketEvent) {
            if (((PacketEvent) event).getType() == PacketEvent.Type.RECIVE) {
                if (((PacketEvent) event).getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }


}
