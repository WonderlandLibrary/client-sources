package alphentus.mod.mods.visuals;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.input.Keyboard;

public class NoBob extends Mod {

    public NoBob() {
        super("NoBob", Keyboard.KEY_NONE, true, ModCategory.VISUALS);
    }
}