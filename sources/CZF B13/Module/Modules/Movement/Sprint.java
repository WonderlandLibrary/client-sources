package gq.vapu.czfclient.Module.Modules.Movement;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Blatant.Scaffold;

import java.awt.*;

public class Sprint extends Module {
    private final Option<Boolean> omni = new Option<Boolean>("All-Direction", "All-Direction", true);

    public Sprint() {
        super("Sprint", new String[]{"run"}, ModuleType.Movement);
        this.setColor(new Color(158, 205, 125).getRGB());
        this.addValues(this.omni);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(false);
        super.onDisable();
    }

    @EventHandler
    private void onUpdate(EventPreUpdate event) {
        if (mc.thePlayer.getFoodStats().getFoodLevel() > 6 && this.omni.getValue() != false
                ? mc.thePlayer.moving()
                : mc.thePlayer.moveForward > 0.0f) {
            if (!(ModuleManager.getModuleByClass(Scaffold.class).isEnabled() && mc.gameSettings.keyBindSprint.isKeyDown())) {
                mc.thePlayer.setSprinting(true);
            }
        }
    }
}