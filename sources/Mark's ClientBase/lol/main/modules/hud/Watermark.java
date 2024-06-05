package lol.main.modules.hud;

import lol.base.BaseClient;
import lol.base.addons.CategoryAddon;
import lol.base.addons.ModuleAddon;
import lol.base.annotations.ModuleInfo;

@ModuleInfo(name = "Watermark", description = "Renders a watermark", category = CategoryAddon.HUD, enabled = true)
public class Watermark extends ModuleAddon {

    public void onRender() {
        mc.fontRendererObj.drawStringWithShadow(BaseClient.get().clientManager.name + " - " + BaseClient.get().clientManager.version, 5, 5, -1);
    }
}
