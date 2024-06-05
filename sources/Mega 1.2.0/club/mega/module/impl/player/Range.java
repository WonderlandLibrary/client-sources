package club.mega.module.impl.player;

import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.NumberSetting;

@Module.ModuleInfo(name = "Range", description = "More range", category = Category.PLAYER)
public class Range extends Module {

    public final NumberSetting range = new NumberSetting("Range", this, 3, 6, 3, 0.1);

}
