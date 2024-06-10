package xyz.gucciclient.modules.mods.render;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.utils.*;
import xyz.gucciclient.*;
import net.minecraft.client.gui.*;

public class G0ui extends Module
{
    public G0ui() {
        super(Modules.ClickGUI.name(), 54, Category.RENDER);
    }
    
    @Override
    public void onEnable() {
        if (Wrapper.getPlayer() != null && Wrapper.getMinecraft().currentScreen == null) {
            Wrapper.getMinecraft().displayGuiScreen((GuiScreen)Gucci.getClickGUI());
            this.toggle();
        }
    }
}
