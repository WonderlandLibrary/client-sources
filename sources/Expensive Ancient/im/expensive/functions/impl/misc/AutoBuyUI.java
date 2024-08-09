package im.expensive.functions.impl.misc;

import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BindSetting;

@FunctionRegister(name = "AutoBuyUI", type = Category.Misc)
public class AutoBuyUI extends Function {

    public BindSetting setting = new BindSetting("Кнопка открытия", -1);

    public AutoBuyUI() {
        addSettings(setting);
    }
}
