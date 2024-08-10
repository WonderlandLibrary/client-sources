// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.world;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.NumberValue;

@ModuleInfo(
        name = "FastPlace",
        category = Category.WORLD
)
public class FastPlace extends Module {
    public final NumberValue<Integer> placeDelay = new NumberValue<>("PlaceDelay", 1, 0, 4, 1);

    public FastPlace() {
        addSettings(placeDelay);
    }
}
