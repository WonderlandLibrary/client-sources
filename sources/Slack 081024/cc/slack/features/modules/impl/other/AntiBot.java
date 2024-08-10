// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.other;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import net.minecraft.entity.EntityLivingBase;

@ModuleInfo(
        name = "AntiBot",
        category = Category.OTHER
)
public class AntiBot extends Module {


    public final BooleanValue colored = new BooleanValue("Colored name", true);


    public AntiBot() {
        super();
        addSettings(colored);
    }

    public boolean isBot(EntityLivingBase e) {
        if (colored.getValue() && e.getCustomNameTag().contains("\u00A7")) return true;

        return false;
    }
}
