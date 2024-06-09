package ca.commencal.ware.module.modules.render;

import ca.commencal.ware.module.Module;
import ca.commencal.ware.module.ModuleCategory;


public class FullBright extends Module {

    public FullBright() {
        super("Fullbright", ModuleCategory.RENDER);
    }
    private float originalgamma;

    @Override
    public void onEnable() {
        originalgamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 1.5999999E7F;
    }
    @Override
    public void onDisable()
    {
        mc.gameSettings.gammaSetting = originalgamma;
    }
}