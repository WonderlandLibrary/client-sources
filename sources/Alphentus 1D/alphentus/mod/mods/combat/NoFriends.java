package alphentus.mod.mods.combat;

import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 13.08.2020.
 */
public class NoFriends extends Mod {

    public NoFriends() {
        super("NoFriends", Keyboard.KEY_NONE, true, ModCategory.COMBAT);
    }
}