package alphentus.mod.mods.movement;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 31.07.2020.
 */
public class NoFall extends Mod {

    public NoFall() {
        super("NoFall", Keyboard.KEY_NONE, true, ModCategory.MOVEMENT);
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() != Type.TICKUPDATE)
            return;
        if (!getState())
            return;

        if (mc.thePlayer.onGround && mc.thePlayer.fallDistance <= 0 && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava())
            mc.thePlayer.motionY = -1000;
    }
}
