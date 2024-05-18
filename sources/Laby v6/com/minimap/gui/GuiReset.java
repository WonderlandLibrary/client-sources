package com.minimap.gui;

import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import com.minimap.minimap.*;
import com.minimap.settings.*;
import com.minimap.*;
import java.io.*;

public class GuiReset extends GuiYesNo
{
    GuiScreen parent;
    
    public GuiReset(final GuiScreen p) {
        super(null, I18n.format("gui.xaero_reset_message", new Object[0]), I18n.format("gui.xaero_reset_message2", new Object[0]), 0);
        this.parent = p;
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                Minimap.resetImage();
                XaeroMinimap.settings = new ModSettings();
            }
            case 1: {
                this.mc.displayGuiScreen(new GuiMinimap3(XaeroMinimap.getSettings()));
                break;
            }
        }
    }
}
