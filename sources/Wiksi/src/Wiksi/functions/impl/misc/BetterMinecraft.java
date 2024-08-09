package src.Wiksi.functions.impl.misc;

import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.BooleanSetting;

@FunctionRegister(name = "BetterMinecraft", type = Category.Render)
public class BetterMinecraft extends Function {

    public final BooleanSetting smoothCamera = new BooleanSetting("Плавная камера", true);
    public final BooleanSetting betterTab = new BooleanSetting("Улучшенный таб", true);
    public final BooleanSetting gui = new BooleanSetting("Гуи блюр", true);
    public final BooleanSetting blur = new BooleanSetting("Инвентори блюр", true);
    public final BooleanSetting button = new BooleanSetting("Улучшенные кнопки", true);
    public final BooleanSetting RGBBlock = new BooleanSetting("Ргб обводка", true);
    public final BooleanSetting RGBFog = new BooleanSetting("Охуенный туманчик", true);
    public BetterMinecraft() {
        addSettings(smoothCamera, betterTab, gui, blur, button, RGBBlock, RGBFog);
    }

}
