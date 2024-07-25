package club.bluezenith.module.modules.movement;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.UpdateEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.player.MovementUtil;

@SuppressWarnings("unused")
public class Sprint extends Module {
    public Sprint() {
        super("Sprint", ModuleCategory.MOVEMENT, "Sprint");
    }

    private final MillisTimer timer = new MillisTimer();
    private final ModeValue mode = new ModeValue("Mode", "Legit", true, null, "Legit", "Omni").setIndex(1);;

    @Listener
    public void onUpdate(UpdateEvent e) {
        if (mode.get().equals("Omni") ? MovementUtil.areMovementKeysPressed() : mc.thePlayer.movementInput.moveForward > 0f) {
            if (!mc.thePlayer.isCollidedHorizontally && (!mc.thePlayer.isUsingItem() || BlueZenith.getBlueZenith().getModuleManager().getAndCast(NoSlowDown.class).getState()) && !mc.thePlayer.isSneaking()) {
                mc.thePlayer.setSprinting(true);
            }
        }
    }
}





