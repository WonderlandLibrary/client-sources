package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public abstract class GuiScreenSelectLocation
{
    private final Minecraft field_104092_f;
    private int field_104093_g;
    private int field_104105_h;
    protected int field_104098_a;
    protected int field_104096_b;
    private int field_104106_i;
    private int field_104103_j;
    protected final int field_104097_c;
    private int field_104104_k;
    private int field_104101_l;
    protected int field_104094_d;
    protected int field_104095_e;
    private float field_104102_m;
    private float field_104099_n;
    private float field_104100_o;
    private int field_104111_p;
    private long field_104110_q;
    private boolean field_104109_r;
    private boolean field_104108_s;
    private int field_104107_t;
    
    public GuiScreenSelectLocation(final Minecraft par1Minecraft, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.field_104102_m = -2.0f;
        this.field_104111_p = -1;
        this.field_104110_q = 0L;
        this.field_104109_r = true;
        this.field_104092_f = par1Minecraft;
        this.field_104093_g = par2;
        this.field_104105_h = par3;
        this.field_104098_a = par4;
        this.field_104096_b = par5;
        this.field_104097_c = par6;
        this.field_104103_j = 0;
        this.field_104106_i = par2;
    }
    
    public void func_104084_a(final int par1, final int par2, final int par3, final int par4) {
        this.field_104093_g = par1;
        this.field_104105_h = par2;
        this.field_104098_a = par3;
        this.field_104096_b = par4;
        this.field_104103_j = 0;
        this.field_104106_i = par1;
    }
    
    protected abstract int getSize();
    
    protected abstract void elementClicked(final int p0, final boolean p1);
    
    protected abstract boolean isSelected(final int p0);
    
    protected abstract boolean func_104086_b(final int p0);
    
    protected int getContentHeight() {
        return this.getSize() * this.field_104097_c + this.field_104107_t;
    }
    
    protected abstract void drawBackground();
    
    protected abstract void drawSlot(final int p0, final int p1, final int p2, final int p3, final Tessellator p4);
    
    protected void func_104088_a(final int par1, final int par2, final Tessellator par3Tessellator) {
    }
    
    protected void func_104089_a(final int par1, final int par2) {
    }
    
    protected void func_104087_b(final int par1, final int par2) {
    }
    
    private void func_104091_h() {
        int var1 = this.func_104085_d();
        if (var1 < 0) {
            var1 /= 2;
        }
        if (this.field_104100_o < 0.0f) {
            this.field_104100_o = 0.0f;
        }
        if (this.field_104100_o > var1) {
            this.field_104100_o = var1;
        }
    }
    
    public int func_104085_d() {
        return this.getContentHeight() - (this.field_104096_b - this.field_104098_a - 4);
    }
    
    public void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == this.field_104104_k) {
                this.field_104100_o -= this.field_104097_c * 2 / 3;
                this.field_104102_m = -2.0f;
                this.func_104091_h();
            }
            else if (par1GuiButton.id == this.field_104101_l) {
                this.field_104100_o += this.field_104097_c * 2 / 3;
                this.field_104102_m = -2.0f;
                this.func_104091_h();
            }
        }
    }
    
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.field_104094_d = par1;
        this.field_104095_e = par2;
        this.drawBackground();
        final int var4 = this.getSize();
        final int var5 = this.func_104090_g();
        final int var6 = var5 + 6;
        if (Mouse.isButtonDown(0)) {
            if (this.field_104102_m == -1.0f) {
                boolean var7 = true;
                if (par2 >= this.field_104098_a && par2 <= this.field_104096_b) {
                    final int var8 = this.field_104093_g / 2 - 110;
                    final int var9 = this.field_104093_g / 2 + 110;
                    final int var10 = par2 - this.field_104098_a - this.field_104107_t + (int)this.field_104100_o - 4;
                    final int var11 = var10 / this.field_104097_c;
                    if (par1 >= var8 && par1 <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4) {
                        final boolean var12 = var11 == this.field_104111_p && Minecraft.getSystemTime() - this.field_104110_q < 250L;
                        this.elementClicked(var11, var12);
                        this.field_104111_p = var11;
                        this.field_104110_q = Minecraft.getSystemTime();
                    }
                    else if (par1 >= var8 && par1 <= var9 && var10 < 0) {
                        this.func_104089_a(par1 - var8, par2 - this.field_104098_a + (int)this.field_104100_o - 4);
                        var7 = false;
                    }
                    if (par1 >= var5 && par1 <= var6) {
                        this.field_104099_n = -1.0f;
                        int var13 = this.func_104085_d();
                        if (var13 < 1) {
                            var13 = 1;
                        }
                        int var14 = (this.field_104096_b - this.field_104098_a) * (this.field_104096_b - this.field_104098_a) / this.getContentHeight();
                        if (var14 < 32) {
                            var14 = 32;
                        }
                        if (var14 > this.field_104096_b - this.field_104098_a - 8) {
                            var14 = this.field_104096_b - this.field_104098_a - 8;
                        }
                        this.field_104099_n /= (this.field_104096_b - this.field_104098_a - var14) / var13;
                    }
                    else {
                        this.field_104099_n = 1.0f;
                    }
                    if (var7) {
                        this.field_104102_m = par2;
                    }
                    else {
                        this.field_104102_m = -2.0f;
                    }
                }
                else {
                    this.field_104102_m = -2.0f;
                }
            }
            else if (this.field_104102_m >= 0.0f) {
                this.field_104100_o -= (par2 - this.field_104102_m) * this.field_104099_n;
                this.field_104102_m = par2;
            }
        }
        else {
            while (!this.field_104092_f.gameSettings.touchscreen && Mouse.next()) {
                int var15 = Mouse.getEventDWheel();
                if (var15 != 0) {
                    if (var15 > 0) {
                        var15 = -1;
                    }
                    else if (var15 < 0) {
                        var15 = 1;
                    }
                    this.field_104100_o += var15 * this.field_104097_c / 2;
                }
            }
            this.field_104102_m = -1.0f;
        }
        this.func_104091_h();
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        final Tessellator var16 = Tessellator.instance;
        this.field_104092_f.renderEngine.bindTexture("/gui/background.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float var17 = 32.0f;
        var16.startDrawingQuads();
        var16.setColorOpaque_I(2105376);
        var16.addVertexWithUV(this.field_104103_j, this.field_104096_b, 0.0, this.field_104103_j / var17, (this.field_104096_b + (int)this.field_104100_o) / var17);
        var16.addVertexWithUV(this.field_104106_i, this.field_104096_b, 0.0, this.field_104106_i / var17, (this.field_104096_b + (int)this.field_104100_o) / var17);
        var16.addVertexWithUV(this.field_104106_i, this.field_104098_a, 0.0, this.field_104106_i / var17, (this.field_104098_a + (int)this.field_104100_o) / var17);
        var16.addVertexWithUV(this.field_104103_j, this.field_104098_a, 0.0, this.field_104103_j / var17, (this.field_104098_a + (int)this.field_104100_o) / var17);
        var16.draw();
        final int var9 = this.field_104093_g / 2 - 92 - 16;
        final int var10 = this.field_104098_a + 4 - (int)this.field_104100_o;
        if (this.field_104108_s) {
            this.func_104088_a(var9, var10, var16);
        }
        for (int var11 = 0; var11 < var4; ++var11) {
            final int var13 = var10 + var11 * this.field_104097_c + this.field_104107_t;
            final int var14 = this.field_104097_c - 4;
            if (var13 <= this.field_104096_b && var13 + var14 >= this.field_104098_a) {
                if (this.field_104109_r && this.func_104086_b(var11)) {
                    final int var18 = this.field_104093_g / 2 - 110;
                    final int var19 = this.field_104093_g / 2 + 110;
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glDisable(3553);
                    var16.startDrawingQuads();
                    var16.setColorOpaque_I(0);
                    var16.addVertexWithUV(var18, var13 + var14 + 2, 0.0, 0.0, 1.0);
                    var16.addVertexWithUV(var19, var13 + var14 + 2, 0.0, 1.0, 1.0);
                    var16.addVertexWithUV(var19, var13 - 2, 0.0, 1.0, 0.0);
                    var16.addVertexWithUV(var18, var13 - 2, 0.0, 0.0, 0.0);
                    var16.draw();
                    GL11.glEnable(3553);
                }
                if (this.field_104109_r && this.isSelected(var11)) {
                    final int var18 = this.field_104093_g / 2 - 110;
                    final int var19 = this.field_104093_g / 2 + 110;
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glDisable(3553);
                    var16.startDrawingQuads();
                    var16.setColorOpaque_I(8421504);
                    var16.addVertexWithUV(var18, var13 + var14 + 2, 0.0, 0.0, 1.0);
                    var16.addVertexWithUV(var19, var13 + var14 + 2, 0.0, 1.0, 1.0);
                    var16.addVertexWithUV(var19, var13 - 2, 0.0, 1.0, 0.0);
                    var16.addVertexWithUV(var18, var13 - 2, 0.0, 0.0, 0.0);
                    var16.setColorOpaque_I(0);
                    var16.addVertexWithUV(var18 + 1, var13 + var14 + 1, 0.0, 0.0, 1.0);
                    var16.addVertexWithUV(var19 - 1, var13 + var14 + 1, 0.0, 1.0, 1.0);
                    var16.addVertexWithUV(var19 - 1, var13 - 1, 0.0, 1.0, 0.0);
                    var16.addVertexWithUV(var18 + 1, var13 - 1, 0.0, 0.0, 0.0);
                    var16.draw();
                    GL11.glEnable(3553);
                }
                this.drawSlot(var11, var9, var13, var14, var16);
            }
        }
        GL11.glDisable(2929);
        final byte var20 = 4;
        this.func_104083_b(0, this.field_104098_a, 255, 255);
        this.func_104083_b(this.field_104096_b, this.field_104105_h, 255, 255);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        var16.startDrawingQuads();
        var16.setColorRGBA_I(0, 0);
        var16.addVertexWithUV(this.field_104103_j, this.field_104098_a + var20, 0.0, 0.0, 1.0);
        var16.addVertexWithUV(this.field_104106_i, this.field_104098_a + var20, 0.0, 1.0, 1.0);
        var16.setColorRGBA_I(0, 255);
        var16.addVertexWithUV(this.field_104106_i, this.field_104098_a, 0.0, 1.0, 0.0);
        var16.addVertexWithUV(this.field_104103_j, this.field_104098_a, 0.0, 0.0, 0.0);
        var16.draw();
        var16.startDrawingQuads();
        var16.setColorRGBA_I(0, 255);
        var16.addVertexWithUV(this.field_104103_j, this.field_104096_b, 0.0, 0.0, 1.0);
        var16.addVertexWithUV(this.field_104106_i, this.field_104096_b, 0.0, 1.0, 1.0);
        var16.setColorRGBA_I(0, 0);
        var16.addVertexWithUV(this.field_104106_i, this.field_104096_b - var20, 0.0, 1.0, 0.0);
        var16.addVertexWithUV(this.field_104103_j, this.field_104096_b - var20, 0.0, 0.0, 0.0);
        var16.draw();
        int var13 = this.func_104085_d();
        if (var13 > 0) {
            int var14 = (this.field_104096_b - this.field_104098_a) * (this.field_104096_b - this.field_104098_a) / this.getContentHeight();
            if (var14 < 32) {
                var14 = 32;
            }
            if (var14 > this.field_104096_b - this.field_104098_a - 8) {
                var14 = this.field_104096_b - this.field_104098_a - 8;
            }
            int var18 = (int)this.field_104100_o * (this.field_104096_b - this.field_104098_a - var14) / var13 + this.field_104098_a;
            if (var18 < this.field_104098_a) {
                var18 = this.field_104098_a;
            }
            var16.startDrawingQuads();
            var16.setColorRGBA_I(0, 255);
            var16.addVertexWithUV(var5, this.field_104096_b, 0.0, 0.0, 1.0);
            var16.addVertexWithUV(var6, this.field_104096_b, 0.0, 1.0, 1.0);
            var16.addVertexWithUV(var6, this.field_104098_a, 0.0, 1.0, 0.0);
            var16.addVertexWithUV(var5, this.field_104098_a, 0.0, 0.0, 0.0);
            var16.draw();
            var16.startDrawingQuads();
            var16.setColorRGBA_I(8421504, 255);
            var16.addVertexWithUV(var5, var18 + var14, 0.0, 0.0, 1.0);
            var16.addVertexWithUV(var6, var18 + var14, 0.0, 1.0, 1.0);
            var16.addVertexWithUV(var6, var18, 0.0, 1.0, 0.0);
            var16.addVertexWithUV(var5, var18, 0.0, 0.0, 0.0);
            var16.draw();
            var16.startDrawingQuads();
            var16.setColorRGBA_I(12632256, 255);
            var16.addVertexWithUV(var5, var18 + var14 - 1, 0.0, 0.0, 1.0);
            var16.addVertexWithUV(var6 - 1, var18 + var14 - 1, 0.0, 1.0, 1.0);
            var16.addVertexWithUV(var6 - 1, var18, 0.0, 1.0, 0.0);
            var16.addVertexWithUV(var5, var18, 0.0, 0.0, 0.0);
            var16.draw();
        }
        this.func_104087_b(par1, par2);
        GL11.glEnable(3553);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GL11.glDisable(3042);
    }
    
    protected int func_104090_g() {
        return this.field_104093_g / 2 + 124;
    }
    
    private void func_104083_b(final int par1, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        this.field_104092_f.renderEngine.bindTexture("/gui/background.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float var6 = 32.0f;
        var5.startDrawingQuads();
        var5.setColorRGBA_I(4210752, par4);
        var5.addVertexWithUV(0.0, par2, 0.0, 0.0, par2 / var6);
        var5.addVertexWithUV(this.field_104093_g, par2, 0.0, this.field_104093_g / var6, par2 / var6);
        var5.setColorRGBA_I(4210752, par3);
        var5.addVertexWithUV(this.field_104093_g, par1, 0.0, this.field_104093_g / var6, par1 / var6);
        var5.addVertexWithUV(0.0, par1, 0.0, 0.0, par1 / var6);
        var5.draw();
    }
}
