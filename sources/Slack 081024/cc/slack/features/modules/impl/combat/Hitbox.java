// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.combat;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;

@ModuleInfo(
        name = "Hitbox",
        category = Category.COMBAT
)
public class Hitbox extends Module {

    public final NumberValue<Float> hitboxSize = new NumberValue<>("Expand", 0.1f, 0f, 1f, 0.01f);
    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});


    public Hitbox() {
        super();
        addSettings(hitboxSize, displayMode);
    }


    @Override
    public String getMode() {
        switch (displayMode.getValue()) {
            case "Simple":
                return hitboxSize.getValue().toString();
        }
        return null;
    }
}
