package net.minecraft.src;

import java.util.*;

public class GuiScreenDisconnectedOnline extends GuiScreen
{
    private String field_98113_a;
    private String field_98111_b;
    private Object[] field_98112_c;
    private List field_98110_d;
    private final GuiScreen field_98114_n;
    
    public GuiScreenDisconnectedOnline(final GuiScreen par1GuiScreen, final String par2Str, final String par3Str, final Object... par4ArrayOfObj) {
        final StringTranslate var5 = StringTranslate.getInstance();
        this.field_98114_n = par1GuiScreen;
        this.field_98113_a = var5.translateKey(par2Str);
        this.field_98111_b = par3Str;
        this.field_98112_c = par4ArrayOfObj;
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.back")));
        if (this.field_98112_c != null) {
            this.field_98110_d = this.fontRenderer.listFormattedStringToWidth(var1.translateKeyFormat(this.field_98111_b, this.field_98112_c), this.width - 50);
        }
        else {
            this.field_98110_d = this.fontRenderer.listFormattedStringToWidth(var1.translateKey(this.field_98111_b), this.width - 50);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.id == 0) {
            this.mc.displayGuiScreen(this.field_98114_n);
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.field_98113_a, this.width / 2, this.height / 2 - 50, 11184810);
        int var4 = this.height / 2 - 30;
        if (this.field_98110_d != null) {
            for (final String var6 : this.field_98110_d) {
                this.drawCenteredString(this.fontRenderer, var6, this.width / 2, var4, 16777215);
                var4 += this.fontRenderer.FONT_HEIGHT;
            }
        }
        super.drawScreen(par1, par2, par3);
    }
}
