package client.metaware.impl.module.render;

import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Fullbright", renderName = "Fullbright", category = Category.VISUALS, keybind = Keyboard.KEY_NONE)
public class Fullbright extends Module {

    private float old;

    @Override
    public void onEnable() {
        super.onEnable();
        old = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 1000000;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (mc.gameSettings.gammaSetting == old) {
            mc.gameSettings.gammaSetting = 0.5f;
        }else {
            mc.gameSettings.gammaSetting = old;
        }
    }

}
