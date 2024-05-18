package com.minimap.gui;

import net.minecraft.client.gui.*;
import com.minimap.settings.*;

public class MySmallButton extends GuiButton
{
    private final ModOptions modOptions;
    
    public MySmallButton(final int par1, final int par2, final int par3, final String par4Str) {
        this(par1, par2, par3, null, par4Str);
    }
    
    public MySmallButton(final int par1, final int par2, final int par3, final int par4, final int par5, final String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
        this.modOptions = null;
    }
    
    public MySmallButton(final int par1, final int par2, final int par3, final ModOptions par4EnumOptions, final String par5Str) {
        super(par1, par2, par3, 150, 20, par5Str);
        this.modOptions = par4EnumOptions;
    }
    
    public ModOptions returnModOptions() {
        return this.modOptions;
    }
}
