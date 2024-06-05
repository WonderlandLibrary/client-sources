package koks.modules.impl.player;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 22:38
 */
public class AntiFire extends Module {

    public AntiFire() {
        super("AntiFire", "You dont burn", Category.PLAYER);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (mc.thePlayer.isBurning()) {
                for (int i = 0; i < 10; i++) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
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