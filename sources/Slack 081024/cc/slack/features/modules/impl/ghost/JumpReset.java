// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.player.WorldEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;
import java.util.Random;

@ModuleInfo(
        name = "JumpReset",
        category = Category.GHOST
)
public class JumpReset extends Module {

    public final NumberValue<Double> chance = new NumberValue<>("Chance", 1D, 0D, 1D, 0.01D);
    
    boolean enable;

    public JumpReset() {
        addSettings(chance);
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate (UpdateEvent event) {
        if (mc.getCurrentScreen() != null) return;
        if (mc.thePlayer.hurtTime == 10) {
            enable = MathHelper.getRandomDoubleInRange(new Random(), 0, 1) <= chance.getValue();
        }
        if (!enable) return;
        if (mc.thePlayer.hurtTime >= 8) {
            mc.gameSettings.keyBindJump.pressed = true;
        }
        if (mc.thePlayer.hurtTime >= 7) {
            mc.gameSettings.keyBindForward.pressed = true;
        } else if (mc.thePlayer.hurtTime >= 4) {
            mc.gameSettings.keyBindJump.pressed = false;
            mc.gameSettings.keyBindForward.pressed = false;
        } else if (mc.thePlayer.hurtTime > 1){
            mc.gameSettings.keyBindForward.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindForward);
            mc.gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindJump);
        }
    }

    @Listen
    public void onWorld (WorldEvent event) {
        mc.gameSettings.keyBindJump.pressed = false;
        mc.gameSettings.keyBindForward.pressed = false;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindJump.pressed = false;
        mc.gameSettings.keyBindForward.pressed = false;
    }
}
