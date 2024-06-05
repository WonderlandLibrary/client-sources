package club.mega.module.impl.movement;

import club.mega.event.impl.EventTick;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.impl.player.Scaffold;
import club.mega.util.MovementUtil;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "Sprint", description = "Auto. Sprint", category = Category.MOVEMENT)
public class Sprint extends Module {

    @Handler
    public final void tick(final EventTick event) {
        if (!Scaffold.getInstance().isToggled())
        MC.gameSettings.keyBindSprint.pressed = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MC.gameSettings.keyBindSprint.pressed = false;
        MC.thePlayer.setSprinting(false);
    }

}
