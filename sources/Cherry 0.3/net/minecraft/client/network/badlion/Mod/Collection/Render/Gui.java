// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Render;

import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

import net.minecraft.Badlion;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.Mod;

public class Gui extends Mod
{
    public Gui() {
        super("GUI", Category.RENDER);
        this.setBind(26);
    }
    
    @Override
    public void onEnable() {
        this.mc.displayGuiScreen(Badlion.getWinter().getGui());
        this.setEnabled(false);
    }
}
