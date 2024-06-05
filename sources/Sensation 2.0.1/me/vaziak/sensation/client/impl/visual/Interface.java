package me.vaziak.sensation.client.impl.visual;

import org.lwjgl.input.Keyboard;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;

/**
 * @author antja03
 */
public class Interface extends Module {

    public Interface() {
        super("Interface", Category.MISC);
        setBind(Keyboard.KEY_RSHIFT);
    }

    public void onEnable() {
        mc.displayGuiScreen(Sensation.instance.userInterface);
        setState(false, false);
    }

}
