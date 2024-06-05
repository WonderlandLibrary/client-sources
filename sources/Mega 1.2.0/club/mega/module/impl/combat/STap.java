package club.mega.module.impl.combat;

import club.mega.event.impl.EventTick;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.util.AuraUtil;
import club.mega.util.RandomUtil;
import club.mega.util.TimeUtil;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "STap", description = "Get more combos", category = Category.COMBAT)
public class STap extends Module {

    private final TimeUtil timeUtil = new TimeUtil();

    @Handler
    public final void tick(final EventTick event) {
        if (KillAura.getInstance().isToggled() && AuraUtil.getTarget() != null && AuraUtil.getTarget().hurtTime == 10 && MC.thePlayer.hurtTime < 10) {
            MC.gameSettings.keyBindBack.pressed = true;
            timeUtil.reset();
        }

        if (timeUtil.hasTimePassed(200))
            MC.gameSettings.keyBindBack.pressed = false;
    }

}
