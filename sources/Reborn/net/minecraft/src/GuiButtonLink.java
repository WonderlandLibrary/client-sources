package net.minecraft.src;

import java.net.*;

public class GuiButtonLink extends GuiButton
{
    public GuiButtonLink(final int par1, final int par2, final int par3, final int par4, final int par5, final String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
    }
    
    public void func_96135_a(final String par1Str) {
        try {
            final URI var2 = new URI(par1Str);
            final Class var3 = Class.forName("java.awt.Desktop");
            final Object var4 = var3.getMethod("getDesktop", (Class[])new Class[0]).invoke(null, new Object[0]);
            var3.getMethod("browse", URI.class).invoke(var4, var2);
        }
        catch (Throwable var5) {
            var5.printStackTrace();
        }
    }
}
