// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.render;

import net.minecraft.client.gui.GuiScreen;
import me.chrest.client.gui.click.ClickGui;
import me.chrest.utils.ClientUtils;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "Click Gui", keybind = 54, shown = false)
public class Gui extends Module
{
    @Option.Op(name = "Dark Theme")
    private boolean darkTheme;
    
    @Override
    public void enable() {
        ClientUtils.mc().displayGuiScreen(ClickGui.getInstance());
    }
    
    public boolean isDarkTheme() {
        return this.darkTheme;
    }
}
