package cc.slack.features.modules.impl.utilties;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
        name = "AutoRespawn",
        category = Category.UTILITIES
)
public class AutoRespawn extends Module {

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate (UpdateEvent event) {
        if (!mc.thePlayer.isEntityAlive()) {
            mc.thePlayer.respawnPlayer();
        }
    }

}
