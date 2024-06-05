package club.mega.module.impl.hud;

import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.BooleanSetting;

@Module.ModuleInfo(name = "TabGui", description = "TabGui", category = Category.HUD)
public class TabGui extends Module {

    public final BooleanSetting blur = new BooleanSetting("Blur", this, true);

}
