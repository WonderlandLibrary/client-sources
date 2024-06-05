package tk.rektsky.Module.RektSky;

import tk.rektsky.Module.*;
import tk.rektsky.Guis.*;
import net.minecraft.client.gui.*;

public class ClickGUI extends Module
{
    public ClickGUI() {
        super("ClickGUI", "Just clickGUI", 54, Category.REKTSKY);
    }
    
    @Override
    public void onEnable() {
        this.rawSetToggled(false);
        this.mc.displayGuiScreen(new ClickGui(this.mc.currentScreen));
    }
}
