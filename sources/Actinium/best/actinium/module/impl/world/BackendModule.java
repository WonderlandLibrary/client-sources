package best.actinium.module.impl.world;

import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.util.io.BackendUtil;
import best.actinium.util.render.ChatUtil;
import net.minecraft.client.Minecraft;

import java.io.IOException;

@ModuleInfo(
        name = "Backend",
        description = "Used for testing the backend.",
        category = ModuleCategory.WORLD
)
public class BackendModule extends Module {
    @Override
    public void onEnable() {
        super.onEnable();
    }
}
