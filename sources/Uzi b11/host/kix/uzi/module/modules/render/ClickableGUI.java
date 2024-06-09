package host.kix.uzi.module.modules.render;

import host.kix.uzi.ui.menu.GuiMenu;
import host.kix.uzi.utilities.value.Value;
import host.kix.uzi.module.Module;
import host.kix.uzi.ui.click.impl.GuiClick;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

/**
 * Created by myche on 2/3/2017.
 */
public class ClickableGUI extends Module {
    GuiClick click;

    public ClickableGUI() {
        super("ClickableGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
        this.setHidden(true);
    }

    @Override
    public void onEnable() {
        if (this.click == null) {
            this.click = new GuiClick();
            click.setup();
        }
        Minecraft.getMinecraft().displayGuiScreen(this.click);
        setEnabled(false);
        super.onEnable();
    }

}
