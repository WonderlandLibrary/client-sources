package lol.point.returnclient.module.impl.misc;

import lol.point.Return;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;

@ModuleInfo(
        name = "Blink",
        description = "simulates net loss",
        category = Category.MISC
)
public class Blink extends Module {

    public void onEnable() {
        Return.INSTANCE.moduleManager.blinkManager.enable();
    }

    public void onDisable() {
        Return.INSTANCE.moduleManager.blinkManager.disable();
    }

}
