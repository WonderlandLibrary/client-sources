package alphentus.mod.mods.visuals;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import org.lwjgl.input.Keyboard;

public class NoFov extends Mod {

    public NoFov() {
        super("NoFov", Keyboard.KEY_NONE, true, ModCategory.VISUALS);
    }
}