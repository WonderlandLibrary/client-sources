package host.kix.uzi.module.modules.render;

import host.kix.uzi.module.Module;
import host.kix.uzi.ui.console.GuiConsole;
import org.lwjgl.input.Keyboard;

/**
 * Created by myche on 2/25/2017.
 */
public class Console extends Module {

    public Console() {
        super("Console", Keyboard.KEY_GRAVE, Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(new GuiConsole());
        this.setEnabled(false);
    }
}
