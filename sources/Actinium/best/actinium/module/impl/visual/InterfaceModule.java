package best.actinium.module.impl.visual;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.render.BloomEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.ui.CompactScreen;
import best.actinium.util.IAccess;
import net.minecraft.client.gui.Gui;
import org.lwjglx.input.Keyboard;

import java.awt.*;

@ModuleInfo(
        name = "Interface",
        description = "Allows you to manage modules in a user interface.",
        category = ModuleCategory.VISUAL,
        keyBind = Keyboard.KEY_RSHIFT
)
public class InterfaceModule extends Module {
    private final CompactScreen compactScreen;

    public InterfaceModule() {
        this.compactScreen = new CompactScreen();
    }

    @Override
    public void onEnable() {
        IAccess.mc.displayGuiScreen(compactScreen);
        setEnabled(false);
    }
}