package alphentus.mod.mods.player;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 12.08.2020.
 */
public class SafeWalk extends Mod {
    public SafeWalk() {
        super("SafeWalk", Keyboard.KEY_NONE, true, ModCategory.PLAYER);
    }
}