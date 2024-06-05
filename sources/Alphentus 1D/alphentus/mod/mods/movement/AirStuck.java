package alphentus.mod.mods.movement;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 08.08.2020.
 */
public class AirStuck extends Mod {

    public AirStuck() {
        super("AirStuck", Keyboard.KEY_NONE, true, ModCategory.MOVEMENT);
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() != Type.TICKUPDATE)
            return;
        if (!getState())
            return;

        mc.thePlayer.motionY = 0;
        mc.thePlayer.isDead = true;
    }

    @Override
    public void onDisable() {
        mc.thePlayer.isDead = false;
        super.onDisable();
    }
}