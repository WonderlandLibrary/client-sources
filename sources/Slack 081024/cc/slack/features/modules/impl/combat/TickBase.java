package cc.slack.features.modules.impl.combat;

import cc.slack.events.impl.game.TickEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.entity.player.EntityPlayer;

@ModuleInfo(
        name = "TickBase",
        category = Category.COMBAT
)
public class TickBase extends Module {


    private final NumberValue<Integer> cooldown = new NumberValue<>("Cooldown", 8, 1, 20, 1);
    private final NumberValue<Float> range = new NumberValue<>("Range", 3F, 1F, 10F, 0.05F);
    private final NumberValue<Integer> freeze = new NumberValue<>("Freeze Time", 10, 1, 20, 1);
    private final NumberValue<Float> timer = new NumberValue<>("Timer", 0.5F, 0.5F, 10F, 1F);

    public boolean ignoreUpdate = false;
    public static boolean publicFreeze = false;
    public int waitTicks = 0, delayTicks = 0;
    private final EntityPlayer target = null;


    public TickBase() {
        addSettings(cooldown, range, freeze, timer);
    }

    @Override
    public void onEnable() {
        clear();
    }

    @Override
    public void onDisable() {
        publicFreeze = false;
        clear();
    }

    @SuppressWarnings("unused")
    @Listen
    public void onTick(TickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null)
            return;

        publicFreeze = false;

        if (waitTicks == 0) {
            waitTicks--;
            ignoreUpdate = true;
            for (int i = 0; i < freeze.getValue() * timer.getValue(); i++) {
                mc.theWorld.updateEntities();
            }
            ignoreUpdate = false;
        }

        if (waitTicks > 0) {
            waitTicks--;
            publicFreeze = true;
        }

        if (delayTicks > 0) {
            delayTicks--;
        }

        if (target != null) {
            float distance = mc.thePlayer.getDistanceToEntity(target);
            if (distance < range.getValue() && distance > 3 && mc.gameSettings.keyBindForward.pressed) {
                if (delayTicks > 0) {
                } else {
                    waitTicks = freeze.getValue() * 2;
                    delayTicks = cooldown.getValue() * 20;
                }
            }

        } else {
            clear();
        }

    }

    private void clear() {
        publicFreeze = false;
        ignoreUpdate = false;
    }
    
}
