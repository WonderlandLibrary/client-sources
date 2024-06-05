package alphentus.mod.mods.combat;

import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 30.07.2020.
 */
public class Teams extends Mod {

    public Teams() {
        super("Teams", Keyboard.KEY_NONE, true, ModCategory.COMBAT);
    }
}