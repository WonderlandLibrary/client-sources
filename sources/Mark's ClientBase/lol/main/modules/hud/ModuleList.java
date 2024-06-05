package lol.main.modules.hud;

import lol.base.BaseClient;
import lol.base.addons.CategoryAddon;
import lol.base.addons.ModuleAddon;
import lol.base.annotations.ModuleInfo;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@ModuleInfo(name = "ModuleList", description = "Renders a list of active modules", category = CategoryAddon.HUD, enabled = true)
public class ModuleList extends ModuleAddon {

    public void onRender() {
        float moduleOffset = 5 + mc.fontRendererObj.FONT_HEIGHT + 2;
        for (ModuleAddon moduleAddon : BaseClient.get().moduleManager.getByEnable()) {
            mc.fontRendererObj.drawStringWithShadow(" - " + moduleAddon.name + (moduleAddon.keyBind != 0 ? " [" + Keyboard.getKeyName(moduleAddon.keyBind) + "]" : ""), 5, moduleOffset, new Color(255,0,55).getRGB());
            moduleOffset += mc.fontRendererObj.FONT_HEIGHT + 2;
        }
    }
}
