package rip.helium.cheat.impl.visual;

import org.lwjgl.input.Keyboard;

import rip.helium.Helium;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;

/**
 * @author antja03
 */
public class Interface extends Cheat {

    public Interface() {
        super("Interface", "Opens the interface when toggled.", CheatCategory.MISC);
        setBind(Keyboard.KEY_RSHIFT);
    }

    public void onEnable() {
        getMc().displayGuiScreen(Helium.instance.userInterface);
        setState(false, false);
    }

}
