package lol.point.returnclient.module.impl.player;

import lol.point.returnclient.events.impl.player.EventMotion;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.StringSetting;
import lol.point.returnclient.util.minecraft.MoveUtil;
import me.zero.alpine.event.EventPhase;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(
        name = "NoFall",
        description = "minimizes fall damage",
        category = Category.PLAYER
)
public class NoFall extends Module {

    private final StringSetting mode = new StringSetting("Mode", new String[]{"Vanilla", "Packet", "Invalid", "Vercan", "No ground", "Matrix"});

    public NoFall() {
        addSettings(mode);
    }

    public String getSuffix() {
        return mode.value;
    }

    @Subscribe
    private final Listener<EventMotion> onMotion = new Listener<>(eventMotion -> {
        if (eventMotion.getEventPhase() == EventPhase.PRE) {
            if (mc.thePlayer.fallDistance > 3.0) {
                switch (mode.value) {
                    case "Vanilla" -> eventMotion.onGround = true;
                    case "Packet" -> {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
                        mc.thePlayer.fallDistance = 0;
                    }
                    case "Invalid" -> {
                        mc.thePlayer.onGround = true;
                        mc.thePlayer.motionY = -9999;
                        mc.thePlayer.fallDistance = 0;
                    }
                    case "Vercan" -> { // works on vulcan & verus
                        mc.thePlayer.onGround = true;
                        mc.thePlayer.motionY = 0.0;
                        mc.thePlayer.fallDistance = 0;
                    }
                    case "No ground" -> eventMotion.onGround = false;
                    case "Matrix" -> {
                        float distance = mc.thePlayer.fallDistance;

                        if (MoveUtil.isBlockUnder()) {
                            if (distance > 2) {
                                MoveUtil.strafe(0.19);
                            }

                            if (distance > 3 && MoveUtil.speed() < 0.2) {
                                eventMotion.onGround = true;
                                distance = 0;
                            }
                        }

                        mc.thePlayer.fallDistance = distance;
                    }
                }
            }
        }
    });

}
