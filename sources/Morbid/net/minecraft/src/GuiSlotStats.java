package net.minecraft.src;

import org.lwjgl.input.*;
import java.util.*;

abstract class GuiSlotStats extends GuiSlot
{
    protected int field_77262_g;
    protected List field_77266_h;
    protected Comparator field_77267_i;
    protected int field_77264_j;
    protected int field_77265_k;
    final GuiStats statsGui;
    
    protected GuiSlotStats(final GuiStats par1GuiStats) {
        super(GuiStats.getMinecraft1(par1GuiStats), par1GuiStats.width, par1GuiStats.height, 32, par1GuiStats.height - 64, 20);
        this.statsGui = par1GuiStats;
        this.field_77262_g = -1;
        this.field_77264_j = -1;
        this.field_77265_k = 0;
        this.setShowSelectionBox(false);
        this.func_77223_a(true, 20);
    }
    
    @Override
    protected void elementClicked(final int par1, final boolean par2) {
    }
    
    @Override
    protected boolean isSelected(final int par1) {
        return false;
    }
    
    @Override
    protected void drawBackground() {
        this.statsGui.drawDefaultBackground();
    }
    
    @Override
    protected void func_77222_a(final int par1, final int par2, final Tessellator par3Tessellator) {
        if (!Mouse.isButtonDown(0)) {
            this.field_77262_g = -1;
        }
        if (this.field_77262_g == 0) {
            GuiStats.drawSprite(this.statsGui, par1 + 115 - 18, par2 + 1, 0, 0);
        }
        else {
            GuiStats.drawSprite(this.statsGui, par1 + 115 - 18, par2 + 1, 0, 18);
        }
        if (this.field_77262_g == 1) {
            GuiStats.drawSprite(this.statsGui, par1 + 165 - 18, par2 + 1, 0, 0);
        }
        else {
            GuiStats.drawSprite(this.statsGui, par1 + 165 - 18, par2 + 1, 0, 18);
        }
        if (this.field_77262_g == 2) {
            GuiStats.drawSprite(this.statsGui, par1 + 215 - 18, par2 + 1, 0, 0);
        }
        else {
            GuiStats.drawSprite(this.statsGui, par1 + 215 - 18, par2 + 1, 0, 18);
        }
        if (this.field_77264_j != -1) {
            short var4 = 79;
            byte var5 = 18;
            if (this.field_77264_j == 1) {
                var4 = 129;
            }
            else if (this.field_77264_j == 2) {
                var4 = 179;
            }
            if (this.field_77265_k == 1) {
                var5 = 36;
            }
            GuiStats.drawSprite(this.statsGui, par1 + var4, par2 + 1, var5, 0);
        }
    }
    
    @Override
    protected void func_77224_a(final int par1, final int par2) {
        this.field_77262_g = -1;
        if (par1 >= 79 && par1 < 115) {
            this.field_77262_g = 0;
        }
        else if (par1 >= 129 && par1 < 165) {
            this.field_77262_g = 1;
        }
        else if (par1 >= 179 && par1 < 215) {
            this.field_77262_g = 2;
        }
        if (this.field_77262_g >= 0) {
            this.func_77261_e(this.field_77262_g);
            GuiStats.getMinecraft2(this.statsGui).sndManager.playSoundFX("random.click", 1.0f, 1.0f);
        }
    }
    
    @Override
    protected final int getSize() {
        return this.field_77266_h.size();
    }
    
    protected final StatCrafting func_77257_d(final int par1) {
        return this.field_77266_h.get(par1);
    }
    
    protected abstract String func_77258_c(final int p0);
    
    protected void func_77260_a(final StatCrafting par1StatCrafting, final int par2, final int par3, final boolean par4) {
        if (par1StatCrafting != null) {
            final String var5 = par1StatCrafting.func_75968_a(GuiStats.getStatsFileWriter(this.statsGui).writeStat(par1StatCrafting));
            this.statsGui.drawString(GuiStats.getFontRenderer4(this.statsGui), var5, par2 - GuiStats.getFontRenderer5(this.statsGui).getStringWidth(var5), par3 + 5, par4 ? 16777215 : 9474192);
        }
        else {
            final String var5 = "-";
            this.statsGui.drawString(GuiStats.getFontRenderer6(this.statsGui), var5, par2 - GuiStats.getFontRenderer7(this.statsGui).getStringWidth(var5), par3 + 5, par4 ? 16777215 : 9474192);
        }
    }
    
    @Override
    protected void func_77215_b(final int par1, final int par2) {
        if (par2 >= this.top && par2 <= this.bottom) {
            final int var3 = this.func_77210_c(par1, par2);
            final int var4 = this.statsGui.width / 2 - 92 - 16;
            if (var3 >= 0) {
                if (par1 < var4 + 40 || par1 > var4 + 40 + 20) {
                    return;
                }
                final StatCrafting var5 = this.func_77257_d(var3);
                this.func_77259_a(var5, par1, par2);
            }
            else {
                String var6 = "";
                if (par1 >= var4 + 115 - 18 && par1 <= var4 + 115) {
                    var6 = this.func_77258_c(0);
                }
                else if (par1 >= var4 + 165 - 18 && par1 <= var4 + 165) {
                    var6 = this.func_77258_c(1);
                }
                else {
                    if (par1 < var4 + 215 - 18 || par1 > var4 + 215) {
                        return;
                    }
                    var6 = this.func_77258_c(2);
                }
                var6 = new StringBuilder().append(StringTranslate.getInstance().translateKey(var6)).toString().trim();
                if (var6.length() > 0) {
                    final int var7 = par1 + 12;
                    final int var8 = par2 - 12;
                    final int var9 = GuiStats.getFontRenderer8(this.statsGui).getStringWidth(var6);
                    GuiStats.drawGradientRect(this.statsGui, var7 - 3, var8 - 3, var7 + var9 + 3, var8 + 8 + 3, -1073741824, -1073741824);
                    GuiStats.getFontRenderer9(this.statsGui).drawStringWithShadow(var6, var7, var8, -1);
                }
            }
        }
    }
    
    protected void func_77259_a(final StatCrafting par1StatCrafting, final int par2, final int par3) {
        if (par1StatCrafting != null) {
            final Item var4 = Item.itemsList[par1StatCrafting.getItemID()];
            final String var5 = new StringBuilder().append(StringTranslate.getInstance().translateNamedKey(var4.getUnlocalizedName())).toString().trim();
            if (var5.length() > 0) {
                final int var6 = par2 + 12;
                final int var7 = par3 - 12;
                final int var8 = GuiStats.getFontRenderer10(this.statsGui).getStringWidth(var5);
                GuiStats.drawGradientRect1(this.statsGui, var6 - 3, var7 - 3, var6 + var8 + 3, var7 + 8 + 3, -1073741824, -1073741824);
                GuiStats.getFontRenderer11(this.statsGui).drawStringWithShadow(var5, var6, var7, -1);
            }
        }
    }
    
    protected void func_77261_e(final int par1) {
        if (par1 != this.field_77264_j) {
            this.field_77264_j = par1;
            this.field_77265_k = -1;
        }
        else if (this.field_77265_k == -1) {
            this.field_77265_k = 1;
        }
        else {
            this.field_77264_j = -1;
            this.field_77265_k = 0;
        }
        Collections.sort((List<Object>)this.field_77266_h, this.field_77267_i);
    }
}
