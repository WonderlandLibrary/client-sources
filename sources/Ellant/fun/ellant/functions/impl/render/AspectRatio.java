package fun.ellant.functions.impl.render;

import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.SliderSetting;

@FunctionRegister(name = "AspectRatio", type = Category.RENDER,desc = "Растягивает экран")
public class AspectRatio extends Function {
    public SliderSetting width = new SliderSetting("Ширина", 1, 0.6f, 2.5f, 0.1f);
    public AspectRatio() {
        addSettings(width);
    }
    @Override
    public boolean onEnable() {
        super.onEnable();
        return false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}