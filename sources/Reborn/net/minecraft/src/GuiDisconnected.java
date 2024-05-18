package net.minecraft.src;

import java.util.*;

public class GuiDisconnected extends GuiScreen
{
    private String errorMessage;
    private String errorDetail;
    private Object[] field_74247_c;
    private List field_74245_d;
    private final GuiScreen field_98095_n;
    
    public GuiDisconnected(final GuiScreen par1GuiScreen, final String par2Str, final String par3Str, final Object... par4ArrayOfObj) {
        final StringTranslate var5 = StringTranslate.getInstance();
        this.field_98095_n = par1GuiScreen;
        this.errorMessage = var5.translateKey(par2Str);
        this.errorDetail = par3Str;
        this.field_74247_c = par4ArrayOfObj;
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.toMenu")));
        if (this.field_74247_c != null) {
            this.field_74245_d = this.fontRenderer.listFormattedStringToWidth(var1.translateKeyFormat(this.errorDetail, this.field_74247_c), this.width - 50);
        }
        else {
            this.field_74245_d = this.fontRenderer.listFormattedStringToWidth(var1.translateKey(this.errorDetail), this.width - 50);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.id == 0) {
            this.mc.displayGuiScreen(this.field_98095_n);
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.errorMessage, this.width / 2, this.height / 2 - 50, 11184810);
        int var4 = this.height / 2 - 30;
        if (this.field_74245_d != null) {
            for (final String var6 : this.field_74245_d) {
                this.drawCenteredString(this.fontRenderer, var6, this.width / 2, var4, 16777215);
                var4 += this.fontRenderer.FONT_HEIGHT;
            }
        }
        super.drawScreen(par1, par2, par3);
    }
}
