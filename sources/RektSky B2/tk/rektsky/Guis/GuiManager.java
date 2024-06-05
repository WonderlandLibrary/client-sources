package tk.rektsky.Guis;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;

public class GuiManager
{
    public static final Class<? extends GuiScreen>[] BLUR_GUI;
    
    static {
        BLUR_GUI = new Class[] { GuiKeybind.class, GuiContainer.class, GuiIngameMenu.class, GuiOptions.class, ClickGui.class };
    }
}
