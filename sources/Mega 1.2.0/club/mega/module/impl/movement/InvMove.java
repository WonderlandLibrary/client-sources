package club.mega.module.impl.movement;

import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.BooleanSetting;

@Module.ModuleInfo(name = "InvMove", description = "Move in your inventory", category = Category.MOVEMENT)
public class InvMove extends Module {

    public final BooleanSetting clickGuiOnly = new BooleanSetting("ClickGui only", this, false);

}
