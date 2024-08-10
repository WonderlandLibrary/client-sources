// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement;

import cc.slack.start.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.impl.world.Scaffold;
import io.github.nevalackin.radbus.Listen;


@ModuleInfo(
        name = "Sprint",
        category = Category.MOVEMENT
)
public class Sprint extends Module {

    private final BooleanValue omniSprint = new BooleanValue("OmniSprint", false);

    public Sprint() {
        addSettings(omniSprint);
    }


    @Listen
    public void onUpdate(UpdateEvent e) {
        if (Slack.getInstance().getModuleManager().getInstance(Scaffold.class).isToggle()) return;
        mc.thePlayer.setSprinting(omniSprint.getValue());
    }

}
