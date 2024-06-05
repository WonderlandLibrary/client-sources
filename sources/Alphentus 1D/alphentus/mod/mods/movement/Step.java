package alphentus.mod.mods.movement;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 04/08/2020.
 */
public class Step extends Mod {

    public Step () {
        super("Step", Keyboard.KEY_NONE, true, ModCategory.MOVEMENT);
    }

    @Override
    public void onDisable () {
        mc.thePlayer.stepHeight = 0.625F;
        super.onDisable();
    }

    @EventTarget
    public void event (Event event) {
        if (!getState())
            return;
        if (event.getType() != Type.TICKUPDATE)
            return;

        if (mc.thePlayer.isCollidedVertically) {
            mc.thePlayer.stepHeight = 255;
        }

    }

}
