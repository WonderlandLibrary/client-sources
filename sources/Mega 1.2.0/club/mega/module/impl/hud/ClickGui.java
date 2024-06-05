package club.mega.module.impl.hud;

import club.mega.Mega;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.BooleanSetting;
import club.mega.module.setting.impl.NumberSetting;
import org.lwjgl.input.Keyboard;

@Module.ModuleInfo(name = "ClickGui", description = "ClickGui", category = Category.HUD)
public class ClickGui extends Module {

    public final NumberSetting animationSpeed = new NumberSetting("Animation speed", this, 0.1, 5, 1.5, 0.1);
    public final BooleanSetting blur = new BooleanSetting("Blur", this, false);
    public final BooleanSetting debug = new BooleanSetting("Debug", this, false);

    public ClickGui() {
        setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MC.displayGuiScreen(Mega.INSTANCE.getClickGUI());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
