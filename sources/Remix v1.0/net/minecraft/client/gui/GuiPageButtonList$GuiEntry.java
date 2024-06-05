package net.minecraft.client.gui;

import net.minecraft.client.*;

public static class GuiEntry implements IGuiListEntry
{
    private final Minecraft field_178031_a;
    private final Gui field_178029_b;
    private final Gui field_178030_c;
    private Gui field_178028_d;
    
    public GuiEntry(final Gui p_i45533_1_, final Gui p_i45533_2_) {
        this.field_178031_a = Minecraft.getMinecraft();
        this.field_178029_b = p_i45533_1_;
        this.field_178030_c = p_i45533_2_;
    }
    
    public Gui func_178022_a() {
        return this.field_178029_b;
    }
    
    public Gui func_178021_b() {
        return this.field_178030_c;
    }
    
    @Override
    public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
        this.func_178017_a(this.field_178029_b, y, mouseX, mouseY, false);
        this.func_178017_a(this.field_178030_c, y, mouseX, mouseY, false);
    }
    
    private void func_178017_a(final Gui p_178017_1_, final int p_178017_2_, final int p_178017_3_, final int p_178017_4_, final boolean p_178017_5_) {
        if (p_178017_1_ != null) {
            if (p_178017_1_ instanceof GuiButton) {
                this.func_178024_a((GuiButton)p_178017_1_, p_178017_2_, p_178017_3_, p_178017_4_, p_178017_5_);
            }
            else if (p_178017_1_ instanceof GuiTextField) {
                this.func_178027_a((GuiTextField)p_178017_1_, p_178017_2_, p_178017_5_);
            }
            else if (p_178017_1_ instanceof GuiLabel) {
                this.func_178025_a((GuiLabel)p_178017_1_, p_178017_2_, p_178017_3_, p_178017_4_, p_178017_5_);
            }
        }
    }
    
    private void func_178024_a(final GuiButton p_178024_1_, final int p_178024_2_, final int p_178024_3_, final int p_178024_4_, final boolean p_178024_5_) {
        p_178024_1_.yPosition = p_178024_2_;
        if (!p_178024_5_) {
            p_178024_1_.drawButton(this.field_178031_a, p_178024_3_, p_178024_4_);
        }
    }
    
    private void func_178027_a(final GuiTextField p_178027_1_, final int p_178027_2_, final boolean p_178027_3_) {
        p_178027_1_.yPosition = p_178027_2_;
        if (!p_178027_3_) {
            p_178027_1_.drawTextBox();
        }
    }
    
    private void func_178025_a(final GuiLabel p_178025_1_, final int p_178025_2_, final int p_178025_3_, final int p_178025_4_, final boolean p_178025_5_) {
        p_178025_1_.field_146174_h = p_178025_2_;
        if (!p_178025_5_) {
            p_178025_1_.drawLabel(this.field_178031_a, p_178025_3_, p_178025_4_);
        }
    }
    
    @Override
    public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
        this.func_178017_a(this.field_178029_b, p_178011_3_, 0, 0, true);
        this.func_178017_a(this.field_178030_c, p_178011_3_, 0, 0, true);
    }
    
    @Override
    public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        final boolean var7 = this.func_178026_a(this.field_178029_b, p_148278_2_, p_148278_3_, p_148278_4_);
        final boolean var8 = this.func_178026_a(this.field_178030_c, p_148278_2_, p_148278_3_, p_148278_4_);
        return var7 || var8;
    }
    
    private boolean func_178026_a(final Gui p_178026_1_, final int p_178026_2_, final int p_178026_3_, final int p_178026_4_) {
        if (p_178026_1_ == null) {
            return false;
        }
        if (p_178026_1_ instanceof GuiButton) {
            return this.func_178023_a((GuiButton)p_178026_1_, p_178026_2_, p_178026_3_, p_178026_4_);
        }
        if (p_178026_1_ instanceof GuiTextField) {
            this.func_178018_a((GuiTextField)p_178026_1_, p_178026_2_, p_178026_3_, p_178026_4_);
        }
        return false;
    }
    
    private boolean func_178023_a(final GuiButton p_178023_1_, final int p_178023_2_, final int p_178023_3_, final int p_178023_4_) {
        final boolean var5 = p_178023_1_.mousePressed(this.field_178031_a, p_178023_2_, p_178023_3_);
        if (var5) {
            this.field_178028_d = p_178023_1_;
        }
        return var5;
    }
    
    private void func_178018_a(final GuiTextField p_178018_1_, final int p_178018_2_, final int p_178018_3_, final int p_178018_4_) {
        p_178018_1_.mouseClicked(p_178018_2_, p_178018_3_, p_178018_4_);
        if (p_178018_1_.isFocused()) {
            this.field_178028_d = p_178018_1_;
        }
    }
    
    @Override
    public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
        this.func_178016_b(this.field_178029_b, x, y, mouseEvent);
        this.func_178016_b(this.field_178030_c, x, y, mouseEvent);
    }
    
    private void func_178016_b(final Gui p_178016_1_, final int p_178016_2_, final int p_178016_3_, final int p_178016_4_) {
        if (p_178016_1_ != null && p_178016_1_ instanceof GuiButton) {
            this.func_178019_b((GuiButton)p_178016_1_, p_178016_2_, p_178016_3_, p_178016_4_);
        }
    }
    
    private void func_178019_b(final GuiButton p_178019_1_, final int p_178019_2_, final int p_178019_3_, final int p_178019_4_) {
        p_178019_1_.mouseReleased(p_178019_2_, p_178019_3_);
    }
}
