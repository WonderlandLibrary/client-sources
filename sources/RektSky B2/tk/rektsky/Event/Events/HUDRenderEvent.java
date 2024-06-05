package tk.rektsky.Event.Events;

import tk.rektsky.Event.*;
import net.minecraft.client.gui.*;

public class HUDRenderEvent extends Event
{
    private GuiIngame gui;
    
    public HUDRenderEvent(final GuiIngame gui) {
        this.gui = gui;
    }
    
    public GuiIngame getGui() {
        return this.gui;
    }
}
