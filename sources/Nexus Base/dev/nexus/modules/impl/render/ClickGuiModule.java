package dev.nexus.modules.impl.render;

import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import dev.nexus.utils.ui.clickgui.ClickGuiScreen;
import org.lwjgl.input.Keyboard;

public class ClickGuiModule extends Module {
    public ClickGuiModule() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, ModuleCategory.RENDER);
    }

    @Override
    public void onEnable() {
        if (mc.currentScreen == null) {
            mc.displayGuiScreen(new ClickGuiScreen());
        }
        toggle();
        super.onEnable();
    }
}
