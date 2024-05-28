package arsenic.module.impl.visual;

import arsenic.gui.click.ClickGuiScreen;
import arsenic.main.Nexus;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;
import arsenic.utils.minecraft.PlayerUtils;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "ClickGUI", category = ModuleCategory.Visual, hidden = true, keybind = Keyboard.KEY_RSHIFT)
public class ClickGui extends Module {
    public final BooleanProperty customFont = new BooleanProperty("Custom Font", false);

    private ClickGuiScreen screen = Nexus.getNexus().getClickGuiScreen();

    @Override
    protected void postApplyConfig() {
        screen.init(this);
    }

    @Override
    protected void onEnable() {
        mc.displayGuiScreen(screen);
        setEnabled(false);
    }

    public final ClickGuiScreen getScreen() {
        return screen;
    }
}
