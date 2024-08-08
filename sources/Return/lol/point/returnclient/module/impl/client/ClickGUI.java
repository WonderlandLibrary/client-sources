package lol.point.returnclient.module.impl.client;

import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.BooleanSetting;
import lol.point.returnclient.settings.impl.StringSetting;
import lol.point.returnclient.ui.cgui.box.BoxClickGUI;
import lol.point.returnclient.ui.cgui.dropdown.DropdownClickGUI;
import lol.point.returnclient.ui.cgui.hybrid.ModernClickGUI;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "ClickGUI",
        description = "draws a clicky gui",
        category = Category.CLIENT,
        hidden = true,
        key = Keyboard.KEY_RSHIFT
)
public class ClickGUI extends Module {
    private final StringSetting mode = new StringSetting("Design", new String[]{"Modern", "Box", "Dropdown"});
    public final BooleanSetting pauseSingleplayer = new BooleanSetting("Pause singleplayer", false);

    private ModernClickGUI modernClickGUI;
    private BoxClickGUI boxClickGUI;
    private DropdownClickGUI dropdownClickGUI;

    public ClickGUI() {
        addSettings(mode, pauseSingleplayer);
    }

    public String getSuffix() {
        return mode.value;
    }

    public void onEnable() {
        switch (mode.value) {
            case "Modern" -> {
                if (modernClickGUI == null) {
                    modernClickGUI = new ModernClickGUI();
                }
                mc.displayGuiScreen(modernClickGUI);
            }
            case "Box" -> {
                if (boxClickGUI == null) {
                    boxClickGUI = new BoxClickGUI();
                }
                mc.displayGuiScreen(boxClickGUI);
            }
            case "Dropdown" -> {
                if (dropdownClickGUI == null) {
                    dropdownClickGUI = new DropdownClickGUI();
                }
                mc.displayGuiScreen(dropdownClickGUI);
            }
        }
        setEnabled(false);
    }
}
