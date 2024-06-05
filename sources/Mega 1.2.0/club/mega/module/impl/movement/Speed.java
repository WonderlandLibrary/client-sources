package club.mega.module.impl.movement;

import club.mega.event.impl.EventMovePlayer;
import club.mega.event.impl.EventTick;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.ListSetting;
import club.mega.module.setting.impl.NumberSetting;
import club.mega.util.MovementUtil;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "Speed", description = "Speed", category = Category.MOVEMENT)
public class Speed extends Module {

    public final ListSetting mode = new ListSetting("Mode", this, new String[]{"Vanilla", "Test"});
    public final NumberSetting speed = new NumberSetting("Speed", this, 0.3D, 5, 1, 0.1D, () -> mode.is("vanilla"));

    @Handler
    public final void movePlayer(final EventMovePlayer event) {
        MovementUtil.setSpeed(event, speed.getAsDouble(), MC.thePlayer.moveForward, MC.thePlayer.moveStrafing, MC.thePlayer.rotationYaw);
    }

    @Handler
    public final void tick(final EventTick event) {
        MovementUtil.jump();
    }

}
