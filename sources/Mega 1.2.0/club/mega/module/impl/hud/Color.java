package club.mega.module.impl.hud;

import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.NumberSetting;

@Module.ModuleInfo(name = "Color", description = "Client color", category = Category.HUD)
public class Color extends Module {

    public final NumberSetting red = new NumberSetting("Red", this, 0, 255, 0, 1);
    public final NumberSetting green = new NumberSetting("Green", this, 0, 255, 140, 1);
    public final NumberSetting blue = new NumberSetting("Blue", this, 0, 255, 255, 1);

}
