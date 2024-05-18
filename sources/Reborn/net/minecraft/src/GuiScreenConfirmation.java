package net.minecraft.src;

public class GuiScreenConfirmation extends GuiYesNo
{
    private String field_96288_n;
    
    public GuiScreenConfirmation(final GuiScreen par1GuiScreen, final String par2Str, final String par3Str, final String par4Str, final int par5) {
        super(par1GuiScreen, par2Str, par3Str, par5);
        this.field_96288_n = par4Str;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiSmallButton(0, this.width / 2 - 155, this.height / 6 + 112, this.buttonText1));
        this.buttonList.add(new GuiSmallButton(1, this.width / 2 - 155 + 160, this.height / 6 + 112, this.buttonText2));
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, this.field_96288_n, this.width / 2, 110, 16777215);
    }
}
