package alphentus.mod.mods.movement;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class WaterSpeed extends Mod {

    public WaterSpeed() {
        super("WaterSpeed", Keyboard.KEY_NONE, true, ModCategory.MOVEMENT);
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() != Type.TICKUPDATE)
            return;
        if (!getState())
            return;

        if (mc.thePlayer.isInWater() && !mc.thePlayer.isSneaking() && mc.thePlayer.isMoving() && !mc.thePlayer.isCollidedHorizontally && !mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.thePlayer.motionY = 0.0;
            mc.thePlayer.motionX *= 1.2;
            mc.thePlayer.motionZ *= 1.2;
        }
    }
}