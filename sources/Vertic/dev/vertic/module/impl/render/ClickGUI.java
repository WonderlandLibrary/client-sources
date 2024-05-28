package dev.vertic.module.impl.render;

import dev.vertic.Client;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.ModeSetting;
import dev.vertic.setting.impl.NumberSetting;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {

    public final ModeSetting style = new ModeSetting("Style", "DropDown", "Dropdown", "Haha no other modes noob.");
    public final NumberSetting bgOpacity = new NumberSetting("Opacity", 175, 50, 255, 5);

    public ClickGUI() {
        super("ClickGUI", "Displays a GUI to edit modules and settings.", Category.RENDER);
        this.addSettings(style, bgOpacity);
        this.setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    protected void onEnable() {
        mc.displayGuiScreen(Client.instance.getDropDownUI());
    }

}
