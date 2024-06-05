package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventTick;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;

import java.awt.*;

public class FullBright extends Module {
    private float old;

    public FullBright() {
        super("FullBright", new String[]{"fbright", "brightness", "bright"}, ModuleType.Render);
        this.setColor(new Color(244, 255, 149).getRGB());
    }

    @Override
    public void onEnable() {
        this.old = mc.gameSettings.gammaSetting;
    }

    @EventHandler
    private void onTick(EventTick e) {
        mc.gameSettings.gammaSetting = 1.5999999E7f;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = this.old;
    }
}
