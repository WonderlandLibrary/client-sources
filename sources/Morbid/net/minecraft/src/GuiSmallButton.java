package net.minecraft.src;

public class GuiSmallButton extends GuiButton
{
    private final EnumOptions enumOptions;
    
    public GuiSmallButton(final int par1, final int par2, final int par3, final String par4Str) {
        this(par1, par2, par3, null, par4Str);
    }
    
    public GuiSmallButton(final int par1, final int par2, final int par3, final int par4, final int par5, final String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
        this.enumOptions = null;
    }
    
    public GuiSmallButton(final int par1, final int par2, final int par3, final EnumOptions par4EnumOptions, final String par5Str) {
        super(par1, par2, par3, 150, 20, par5Str);
        this.enumOptions = par4EnumOptions;
    }
    
    public EnumOptions returnEnumOptions() {
        return this.enumOptions;
    }
}
