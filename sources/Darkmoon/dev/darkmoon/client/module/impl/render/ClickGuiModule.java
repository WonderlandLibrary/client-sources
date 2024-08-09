package dev.darkmoon.client.module.impl.render;

import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import org.lwjgl.input.Keyboard;

@ModuleAnnotation(name = "ClickGui", category = Category.RENDER)
public class ClickGuiModule extends Module {
    public static BooleanSetting blur = new BooleanSetting("Blur", true);
    public static NumberSetting blurRadius = new NumberSetting("Blur Iterations", 5, 1, 15, 1, blur::get);

    public ClickGuiModule() {
        bind = Keyboard.KEY_RSHIFT;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(DarkMoon.getInstance().getCsGui());
        DarkMoon.getInstance().getModuleManager().getModule(ClickGuiModule.class).setToggled(false);
    }
}
