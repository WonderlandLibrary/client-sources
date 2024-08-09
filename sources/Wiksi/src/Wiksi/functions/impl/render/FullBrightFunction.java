package src.Wiksi.functions.impl.render;


import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.ModeSetting;

@FunctionRegister(name = "FullBright", type = Category.Render)
public class FullBrightFunction extends Function {
    public ModeSetting fbType = new ModeSetting("Type", "Gamma");
    private float originalGamma;

    @Override
    public void onEnable() {
        super.onEnable();
        if (fbType.is("Gamma")) {
            originalGamma = (float) mc.gameSettings.gamma;
            mc.gameSettings.gamma = 100;
        }
    }

    @Override
    public void onDisable() {
        if (fbType.is("Gamma")) {
            mc.gameSettings.gamma = originalGamma;
        }
        super.onDisable();
    }
}
