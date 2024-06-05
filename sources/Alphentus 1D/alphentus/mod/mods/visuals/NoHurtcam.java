package alphentus.mod.mods.visuals;

import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import org.lwjgl.input.Keyboard;

public class NoHurtcam extends Mod {

    public NoHurtcam() {
        super("NoHurtcam", Keyboard.KEY_NONE, true, ModCategory.VISUALS);
    }
}