package im.expensive.functions.impl.render;

import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;

@FunctionRegister(
        name = "Gamma",
        type = Category.Render
)
public class Gamma extends Function {
    @Override
    public boolean onEnable() {
        mc.gameSettings.gamma = 100;
        return super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gamma = 0;
    }
}
