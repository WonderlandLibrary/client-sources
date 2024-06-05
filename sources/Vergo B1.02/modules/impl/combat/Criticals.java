package xyz.vergoclient.modules.impl.combat;

import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventMove;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.util.main.ChatUtils;
import xyz.vergoclient.util.main.MovementUtils;

import java.util.Arrays;

public class Criticals extends Module implements OnEventInterface {

    public ModeSetting mode = new ModeSetting("Mode", "Watchdog", "Watchdog");

    @Override
    public void loadSettings() {
        mode.modes.addAll(Arrays.asList("Watchdog"));

        addSettings(mode);
    }

    public Criticals() {
        super("Criticals", Category.COMBAT);
    }

    @Override
    public void onEvent(Event e) {

        if(e instanceof EventTick) {
            if(!getInfo().equals("Watchdog")) {
                setInfo("Watchdog");
            }
        }

        if (KillAura.target != null && MovementUtils.isOnGround(0.001)) {
            if (KillAura.target.hurtTime > 20) {
                for (double offset : new double[]{0.06f, 0.01f}) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset + (Math.random() * 0.001), mc.thePlayer.posZ, false));
                    ChatUtils.addChatMessage("CRIT!");
                }
            }
        }
    }

}
