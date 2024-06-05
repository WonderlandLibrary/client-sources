package gq.vapu.czfclient.Module.Modules.Movement;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventMove;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.PlayerUtil;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class VanillaZoomFly extends Module {
    int i = 0;
    double movespeed = 0.0;
    private final TimerUtil timer = new TimerUtil();

    public VanillaZoomFly() {
        super("VanillaZoomFly", new String[]{"VanillaZoomFly"}, ModuleType.Movement);
    }

    public void onEnable() {
        damagePlayer();
    }

    public void damagePlayer() {
        mc.thePlayer.motionY = 0.1D;
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                mc.thePlayer.posY + 3.100001D, mc.thePlayer.posZ, true));
        i = 1;
    }


    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        if (mc.thePlayer.motionY < 0) {
            mc.thePlayer.onGround = true;
        }
        if (!mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.thePlayer.motionY = 0.0;
        } else {
            mc.thePlayer.onGround = true;
        }
    }

    @EventHandler
    private void onUpdate(EventMove e) {
        mc.thePlayer.setMoveSpeed(e, 0);
        if (PlayerUtil.isMoving2() == true && i == 1) {
            mc.thePlayer.setSprinting(true);
            if (movespeed == 0) {
                movespeed = 10;
            } else if (movespeed > 9 && movespeed < 10) {
                movespeed = 1.5;
            } else if (movespeed > 0.3) {
                movespeed = movespeed - 0.005;
            } else {
                movespeed = 0.3;
            }
            mc.thePlayer.setMoveSpeed(e, movespeed);
        } else {
            movespeed = 0.3;
        }
    }

    public void onDisable() {
        i = 0;
        movespeed = 0.0;
        mc.thePlayer.motionX *= 0.0D;
        mc.thePlayer.motionZ *= 0.0D;
    }
}
