package im.expensive.functions.impl.misc;

import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;

@FunctionRegister(name = "BetterMinecraft", type = Category.Miscellaneous)
public class BetterMinecraft extends Function {

    public final BooleanSetting smoothCamera = new BooleanSetting("Плавная камера", true);
    public final BooleanSetting betterTab = new BooleanSetting("Улучшенный таб", true);

    public BetterMinecraft() {
        addSettings(smoothCamera, betterTab);
    }
}
