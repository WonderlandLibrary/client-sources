package alphentus.mod.mods.player;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 30.07.2020.
 */
public class FastPlace extends Mod {

    public FastPlace() {
        super("FastPlace", Keyboard.KEY_NONE, true, ModCategory.PLAYER);
    }

    @Override
    public void onDisable() {
        mc.rightClickDelayTimer = 4;
        super.onDisable();
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() != Type.RENDER2D)
            return;
        if (!getState())
            return;

        mc.rightClickDelayTimer = 0;
    }
}