package ez.cloudclient.module.modules.render;

import ez.cloudclient.module.Module;

public class FullBright extends Module {

    private float startGamma;

    public FullBright() {
        super("FullBright", Category.RENDER, "Increase client brightness");
    }

    @Override
    protected void onEnable() {
        if (mc.player != null) {
            startGamma = mc.gameSettings.gammaSetting;
        }
    }

    @Override
    protected void onDisable() {
        if (mc.player != null) {
            mc.gameSettings.gammaSetting = startGamma;
        }
    }

    @Override
    public void onTick() {
        if (mc.gameSettings.gammaSetting < 16) {
            mc.gameSettings.gammaSetting++;
        }
    }
}
