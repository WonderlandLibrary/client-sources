package me.mees.remix.modules.render;

import me.satisfactory.base.module.*;
import me.mees.remix.ui.gui.*;
import net.minecraft.client.gui.*;

public class CSGOGui extends Module
{
    public CSGOGui() {
        super("CSGOGui", 0, Category.RENDER);
    }
    
    @Override
    public void onEnable() {
        CSGOGuiScreen.currentCategory = CSGOGuiScreen.lastCategory;
        CSGOGui.mc.displayGuiScreen(new CSGOGuiScreen());
    }
    
    @Override
    public void onDisable() {
        super.toggle();
    }
}
