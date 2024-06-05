package club.mega.module.impl.player;

import club.mega.Mega;
import club.mega.event.impl.EventPreTick;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.impl.combat.Criticals;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "NoFall", description = "Remove fall damage", category = Category.PLAYER)
public class NoFall extends Module {

    @Handler
    public final void preTick(final EventPreTick event) {
        if (MC.thePlayer.fallDistance >= 2.9 && !Mega.INSTANCE.getModuleManager().isToggled(Criticals.class))
        event.setOnGround(true);
    }

}
