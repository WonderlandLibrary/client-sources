package gq.vapu.czfclient.Module.Modules.Movement;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.WaitTimer;

import java.awt.*;

public class Spider extends Module {

    WaitTimer timer = new WaitTimer();
    boolean jumped = false;

    public Spider() {
        super("Spider", new String[]{"Spider"}, ModuleType.Movement);
        this.setColor(new Color(235, 194, 138).getRGB());
    }

    @Override
    public void onDisable() {

        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventHandler
    public void onUpdate() {
        if (!mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isOnLadder()) {
            timer.reset();
        }
        if (mc.thePlayer.isCollidedHorizontally) {
            mc.thePlayer.motionY = 0.33;
        }
        onUpdate();
    }
}