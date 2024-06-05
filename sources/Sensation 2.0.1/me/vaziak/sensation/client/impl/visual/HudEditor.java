package me.vaziak.sensation.client.impl.visual;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;

/**
 * @author antja03
 */
public class HudEditor extends Module {

    public HudEditor() {
        super("Hud Editor", Category.VISUAL);
        setBind(Keyboard.KEY_F4);
    }

    public void onEnable() {
        Minecraft.getMinecraft().displayGuiScreen(Sensation.instance.hudEditor);
        setState(false, false);
    }

}
