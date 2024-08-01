package wtf.diablo.client.module.impl.render;

import org.lwjgl.input.Keyboard;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.gui.clickgui.dropdown.impl.DropdownClickGui;
import wtf.diablo.client.gui.clickgui.material.MaterialClickGUI;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ModeSetting;

import static wtf.diablo.client.util.render.ColorUtil.AMBIENT_COLOR;

@ModuleMetaData(
        name = "Click GUI",
        description = "Opens the ClickGui",
        category = ModuleCategoryEnum.RENDER,
        key = Keyboard.KEY_RSHIFT
)
public final class ClickGuiModule extends AbstractModule {
    private final ModeSetting<ClickGUIMode> clickGUIModeModeSetting = new ModeSetting<>("ClickGUIMode", ClickGUIMode.DROPDOWN);
    private final BooleanSetting outline = new BooleanSetting("Outline", false);

    private DropdownClickGui clickGui;

    public ClickGuiModule() {
        this.registerSettings(AMBIENT_COLOR, clickGUIModeModeSetting, this.outline);
    }

    @Override
    public void onEnable() {
        if (this.clickGui == null) {
            this.clickGui = new DropdownClickGui(Diablo.getInstance().getModuleRepository());
        }

        this.clickGui.setSearchText("");

        if (clickGUIModeModeSetting.getValue().equals(ClickGUIMode.DROPDOWN)) {
            this.mc.displayGuiScreen(clickGui);
        } else {
            this.mc.displayGuiScreen(new MaterialClickGUI());
        }
        this.toggle();
    }

    private enum ClickGUIMode implements IMode {
        DROPDOWN("Dropdown"),
        MATERIAL("Material")
        ;

        private final String name;

        ClickGUIMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public BooleanSetting getOutline() {
        return outline;
    }
}
