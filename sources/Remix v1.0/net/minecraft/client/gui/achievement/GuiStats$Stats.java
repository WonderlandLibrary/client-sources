package net.minecraft.client.gui.achievement;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.stats.*;
import net.minecraft.client.resources.*;
import net.minecraft.item.*;
import java.util.*;

abstract class Stats extends GuiSlot
{
    protected int field_148218_l;
    protected List statsHolder;
    protected Comparator statSorter;
    protected int field_148217_o;
    protected int field_148215_p;
    
    protected Stats(final Minecraft mcIn) {
        super(mcIn, GuiStats.width, GuiStats.height, 32, GuiStats.height - 64, 20);
        this.field_148218_l = -1;
        this.field_148217_o = -1;
        this.setShowSelectionBox(false);
        this.setHasListHeader(true, 20);
    }
    
    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
    }
    
    @Override
    protected boolean isSelected(final int slotIndex) {
        return false;
    }
    
    @Override
    protected void drawBackground() {
        GuiStats.this.drawDefaultBackground();
    }
    
    @Override
    protected void drawListHeader(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
        if (!Mouse.isButtonDown(0)) {
            this.field_148218_l = -1;
        }
        if (this.field_148218_l == 0) {
            GuiStats.access$000(GuiStats.this, p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 0);
        }
        else {
            GuiStats.access$000(GuiStats.this, p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 18);
        }
        if (this.field_148218_l == 1) {
            GuiStats.access$000(GuiStats.this, p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 0);
        }
        else {
            GuiStats.access$000(GuiStats.this, p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 18);
        }
        if (this.field_148218_l == 2) {
            GuiStats.access$000(GuiStats.this, p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 0);
        }
        else {
            GuiStats.access$000(GuiStats.this, p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 18);
        }
        if (this.field_148217_o != -1) {
            short var4 = 79;
            byte var5 = 18;
            if (this.field_148217_o == 1) {
                var4 = 129;
            }
            else if (this.field_148217_o == 2) {
                var4 = 179;
            }
            if (this.field_148215_p == 1) {
                var5 = 36;
            }
            GuiStats.access$000(GuiStats.this, p_148129_1_ + var4, p_148129_2_ + 1, var5, 0);
        }
    }
    
    @Override
    protected void func_148132_a(final int p_148132_1_, final int p_148132_2_) {
        this.field_148218_l = -1;
        if (p_148132_1_ >= 79 && p_148132_1_ < 115) {
            this.field_148218_l = 0;
        }
        else if (p_148132_1_ >= 129 && p_148132_1_ < 165) {
            this.field_148218_l = 1;
        }
        else if (p_148132_1_ >= 179 && p_148132_1_ < 215) {
            this.field_148218_l = 2;
        }
        if (this.field_148218_l >= 0) {
            this.func_148212_h(this.field_148218_l);
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0f));
        }
    }
    
    @Override
    protected final int getSize() {
        return this.statsHolder.size();
    }
    
    protected final StatCrafting func_148211_c(final int p_148211_1_) {
        return this.statsHolder.get(p_148211_1_);
    }
    
    protected abstract String func_148210_b(final int p0);
    
    protected void func_148209_a(final StatBase p_148209_1_, final int p_148209_2_, final int p_148209_3_, final boolean p_148209_4_) {
        if (p_148209_1_ != null) {
            final String var5 = p_148209_1_.func_75968_a(GuiStats.access$100(GuiStats.this).writeStat(p_148209_1_));
            GuiStats.this.drawString(GuiStats.access$200(GuiStats.this), var5, p_148209_2_ - GuiStats.access$300(GuiStats.this).getStringWidth(var5), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
        }
        else {
            final String var5 = "-";
            GuiStats.this.drawString(GuiStats.access$400(GuiStats.this), var5, p_148209_2_ - GuiStats.access$500(GuiStats.this).getStringWidth(var5), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
        }
    }
    
    @Override
    protected void func_148142_b(final int p_148142_1_, final int p_148142_2_) {
        if (p_148142_2_ >= this.top && p_148142_2_ <= this.bottom) {
            final int var3 = this.getSlotIndexFromScreenCoords(p_148142_1_, p_148142_2_);
            final int var4 = this.width / 2 - 92 - 16;
            if (var3 >= 0) {
                if (p_148142_1_ < var4 + 40 || p_148142_1_ > var4 + 40 + 20) {
                    return;
                }
                final StatCrafting var5 = this.func_148211_c(var3);
                this.func_148213_a(var5, p_148142_1_, p_148142_2_);
            }
            else {
                String var6 = "";
                if (p_148142_1_ >= var4 + 115 - 18 && p_148142_1_ <= var4 + 115) {
                    var6 = this.func_148210_b(0);
                }
                else if (p_148142_1_ >= var4 + 165 - 18 && p_148142_1_ <= var4 + 165) {
                    var6 = this.func_148210_b(1);
                }
                else {
                    if (p_148142_1_ < var4 + 215 - 18 || p_148142_1_ > var4 + 215) {
                        return;
                    }
                    var6 = this.func_148210_b(2);
                }
                var6 = ("" + I18n.format(var6, new Object[0])).trim();
                if (var6.length() > 0) {
                    final int var7 = p_148142_1_ + 12;
                    final int var8 = p_148142_2_ - 12;
                    final int var9 = GuiStats.access$600(GuiStats.this).getStringWidth(var6);
                    GuiStats.this.drawGradientRect(var7 - 3, var8 - 3, var7 + var9 + 3, var8 + 8 + 3, -1073741824, -1073741824);
                    GuiStats.access$700(GuiStats.this).func_175063_a(var6, (float)var7, (float)var8, -1);
                }
            }
        }
    }
    
    protected void func_148213_a(final StatCrafting p_148213_1_, final int p_148213_2_, final int p_148213_3_) {
        if (p_148213_1_ != null) {
            final Item var4 = p_148213_1_.func_150959_a();
            final ItemStack var5 = new ItemStack(var4);
            final String var6 = var5.getUnlocalizedName();
            final String var7 = ("" + I18n.format(var6 + ".name", new Object[0])).trim();
            if (var7.length() > 0) {
                final int var8 = p_148213_2_ + 12;
                final int var9 = p_148213_3_ - 12;
                final int var10 = GuiStats.access$800(GuiStats.this).getStringWidth(var7);
                GuiStats.this.drawGradientRect(var8 - 3, var9 - 3, var8 + var10 + 3, var9 + 8 + 3, -1073741824, -1073741824);
                GuiStats.access$900(GuiStats.this).func_175063_a(var7, (float)var8, (float)var9, -1);
            }
        }
    }
    
    protected void func_148212_h(final int p_148212_1_) {
        if (p_148212_1_ != this.field_148217_o) {
            this.field_148217_o = p_148212_1_;
            this.field_148215_p = -1;
        }
        else if (this.field_148215_p == -1) {
            this.field_148215_p = 1;
        }
        else {
            this.field_148217_o = -1;
            this.field_148215_p = 0;
        }
        Collections.sort((List<Object>)this.statsHolder, this.statSorter);
    }
}
