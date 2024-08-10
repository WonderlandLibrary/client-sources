// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.player.PlayerUtil;
import cc.slack.utils.other.TimeUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.settings.GameSettings;

@ModuleInfo(
        name = "LegitScaffold",
        category = Category.GHOST
)
public class LegitScaffold extends Module {

    private final NumberValue<Integer> sneakTime = new NumberValue<>("Sneak Time", 60, 0, 300, 20);    
    private final BooleanValue onlyGround = new BooleanValue("Only Ground", true);
    private final BooleanValue holdSneak = new BooleanValue("Hold Sneak", false);

    private boolean shouldSneak = false;
    private final TimeUtil sneakTimer = new TimeUtil();

    public LegitScaffold() {
        addSettings(sneakTime, onlyGround, holdSneak);
    }

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSneak.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindSneak);
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate (UpdateEvent event) {
        if (mc.currentScreen != null) return;
        shouldSneak = !sneakTimer.hasReached((long) sneakTime.getValue());
        if (PlayerUtil.isOverAir() && (!onlyGround.getValue() || mc.thePlayer.onGround) && mc.thePlayer.motionY < 0.1) {
            shouldSneak = true;
        }

        if (holdSneak.getValue()) {
            mc.gameSettings.keyBindSneak.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindSneak) && shouldSneak;
        } else {
            mc.gameSettings.keyBindSneak.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindSneak) || shouldSneak;
        }
    }
}
