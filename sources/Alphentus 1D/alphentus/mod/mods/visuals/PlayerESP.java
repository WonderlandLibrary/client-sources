package alphentus.mod.mods.visuals;

import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 30.07.2020.
 */
public class PlayerESP extends Mod {

    public PlayerESP() {
        super("PlayerESP", Keyboard.KEY_NONE, true, ModCategory.VISUALS);
    }
}