package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public abstract class SelectionListBase
{
    private final Minecraft field_96622_a;
    private final int field_96619_e;
    private final int field_96616_f;
    private final int field_96617_g;
    private final int field_96627_h;
    protected final int field_96620_b;
    protected int field_96621_c;
    protected int field_96618_d;
    private float field_96628_i;
    private float field_96625_j;
    private float field_96626_k;
    private int field_96623_l;
    private long field_96624_m;
    
    public SelectionListBase(final Minecraft par1Minecraft, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.field_96628_i = -2.0f;
        this.field_96623_l = -1;
        this.field_96624_m = 0L;
        this.field_96622_a = par1Minecraft;
        this.field_96616_f = par3;
        this.field_96627_h = par3 + par5;
        this.field_96620_b = par6;
        this.field_96619_e = par2;
        this.field_96617_g = par2 + par4;
    }
    
    protected abstract int func_96608_a();
    
    protected abstract void func_96615_a(final int p0, final boolean p1);
    
    protected abstract boolean func_96609_a(final int p0);
    
    protected int func_96613_b() {
        return this.func_96608_a() * this.field_96620_b;
    }
    
    protected abstract void func_96611_c();
    
    protected abstract void func_96610_a(final int p0, final int p1, final int p2, final int p3, final Tessellator p4);
    
    private void func_96614_f() {
        int var1 = this.func_96607_d();
        if (var1 < 0) {
            var1 = 0;
        }
        if (this.field_96626_k < 0.0f) {
            this.field_96626_k = 0.0f;
        }
        if (this.field_96626_k > var1) {
            this.field_96626_k = var1;
        }
    }
    
    public int func_96607_d() {
        return this.func_96613_b() - (this.field_96627_h - this.field_96616_f - 4);
    }
    
    public void func_96612_a(final int par1, final int par2, final float par3) {
        this.field_96621_c = par1;
        this.field_96618_d = par2;
        this.func_96611_c();
        final int var4 = this.func_96608_a();
        final int var5 = this.func_96606_e();
        final int var6 = var5 + 6;
        if (Mouse.isButtonDown(0)) {
            if (this.field_96628_i == -1.0f) {
                boolean var7 = true;
                if (par2 >= this.field_96616_f && par2 <= this.field_96627_h) {
                    final int var8 = this.field_96619_e + 2;
                    final int var9 = this.field_96617_g - 2;
                    final int var10 = par2 - this.field_96616_f + (int)this.field_96626_k - 4;
                    final int var11 = var10 / this.field_96620_b;
                    if (par1 >= var8 && par1 <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4) {
                        final boolean var12 = var11 == this.field_96623_l && Minecraft.getSystemTime() - this.field_96624_m < 250L;
                        this.func_96615_a(var11, var12);
                        this.field_96623_l = var11;
                        this.field_96624_m = Minecraft.getSystemTime();
                    }
                    else if (par1 >= var8 && par1 <= var9 && var10 < 0) {
                        var7 = false;
                    }
                    if (par1 >= var5 && par1 <= var6) {
                        this.field_96625_j = -1.0f;
                        int var13 = this.func_96607_d();
                        if (var13 < 1) {
                            var13 = 1;
                        }
                        int var14 = (this.field_96627_h - this.field_96616_f) * (this.field_96627_h - this.field_96616_f) / this.func_96613_b();
                        if (var14 < 32) {
                            var14 = 32;
                        }
                        if (var14 > this.field_96627_h - this.field_96616_f - 8) {
                            var14 = this.field_96627_h - this.field_96616_f - 8;
                        }
                        this.field_96625_j /= (this.field_96627_h - this.field_96616_f - var14) / var13;
                    }
                    else {
                        this.field_96625_j = 1.0f;
                    }
                    if (var7) {
                        this.field_96628_i = par2;
                    }
                    else {
                        this.field_96628_i = -2.0f;
                    }
                }
                else {
                    this.field_96628_i = -2.0f;
                }
            }
            else if (this.field_96628_i >= 0.0f) {
                this.field_96626_k -= (par2 - this.field_96628_i) * this.field_96625_j;
                this.field_96628_i = par2;
            }
        }
        else {
            while (!this.field_96622_a.gameSettings.touchscreen && Mouse.next()) {
                int var15 = Mouse.getEventDWheel();
                if (var15 != 0) {
                    if (var15 > 0) {
                        var15 = -1;
                    }
                    else if (var15 < 0) {
                        var15 = 1;
                    }
                    this.field_96626_k += var15 * this.field_96620_b / 2;
                }
            }
            this.field_96628_i = -1.0f;
        }
        this.func_96614_f();
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        final Tessellator var16 = Tessellator.instance;
        this.field_96622_a.renderEngine.bindTexture("/gui/background.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float var17 = 32.0f;
        var16.startDrawingQuads();
        var16.setColorOpaque_I(2105376);
        var16.addVertexWithUV(this.field_96619_e, this.field_96627_h, 0.0, this.field_96619_e / var17, (this.field_96627_h + (int)this.field_96626_k) / var17);
        var16.addVertexWithUV(this.field_96617_g, this.field_96627_h, 0.0, this.field_96617_g / var17, (this.field_96627_h + (int)this.field_96626_k) / var17);
        var16.addVertexWithUV(this.field_96617_g, this.field_96616_f, 0.0, this.field_96617_g / var17, (this.field_96616_f + (int)this.field_96626_k) / var17);
        var16.addVertexWithUV(this.field_96619_e, this.field_96616_f, 0.0, this.field_96619_e / var17, (this.field_96616_f + (int)this.field_96626_k) / var17);
        var16.draw();
        final int var9 = this.field_96619_e + 2;
        final int var10 = this.field_96616_f + 4 - (int)this.field_96626_k;
        for (int var11 = 0; var11 < var4; ++var11) {
            final int var13 = var10 + var11 * this.field_96620_b;
            final int var14 = this.field_96620_b - 4;
            if (var13 + this.field_96620_b <= this.field_96627_h && var13 - 4 >= this.field_96616_f) {
                if (this.func_96609_a(var11)) {
                    final int var18 = this.field_96619_e + 2;
                    final int var19 = this.field_96617_g - 2;
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
                this.func_96610_a(var11, var9, var13, var14, var16);
            }
        }
        GL11.glDisable(2929);
        final byte var20 = 4;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        var16.startDrawingQuads();
        var16.setColorRGBA_I(0, 0);
        var16.addVertexWithUV(this.field_96619_e, this.field_96616_f + var20, 0.0, 0.0, 1.0);
        var16.addVertexWithUV(this.field_96617_g, this.field_96616_f + var20, 0.0, 1.0, 1.0);
        var16.setColorRGBA_I(0, 255);
        var16.addVertexWithUV(this.field_96617_g, this.field_96616_f, 0.0, 1.0, 0.0);
        var16.addVertexWithUV(this.field_96619_e, this.field_96616_f, 0.0, 0.0, 0.0);
        var16.draw();
        var16.startDrawingQuads();
        var16.setColorRGBA_I(0, 255);
        var16.addVertexWithUV(this.field_96619_e, this.field_96627_h, 0.0, 0.0, 1.0);
        var16.addVertexWithUV(this.field_96617_g, this.field_96627_h, 0.0, 1.0, 1.0);
        var16.setColorRGBA_I(0, 0);
        var16.addVertexWithUV(this.field_96617_g, this.field_96627_h - var20, 0.0, 1.0, 0.0);
        var16.addVertexWithUV(this.field_96619_e, this.field_96627_h - var20, 0.0, 0.0, 0.0);
        var16.draw();
        int var13 = this.func_96607_d();
        if (var13 > 0) {
            int var14 = (this.field_96627_h - this.field_96616_f) * (this.field_96627_h - this.field_96616_f) / this.func_96613_b();
            if (var14 < 32) {
                var14 = 32;
            }
            if (var14 > this.field_96627_h - this.field_96616_f - 8) {
                var14 = this.field_96627_h - this.field_96616_f - 8;
            }
            int var18 = (int)this.field_96626_k * (this.field_96627_h - this.field_96616_f - var14) / var13 + this.field_96616_f;
            if (var18 < this.field_96616_f) {
                var18 = this.field_96616_f;
            }
            var16.startDrawingQuads();
            var16.setColorRGBA_I(0, 255);
            var16.addVertexWithUV(var5, this.field_96627_h, 0.0, 0.0, 1.0);
            var16.addVertexWithUV(var6, this.field_96627_h, 0.0, 1.0, 1.0);
            var16.addVertexWithUV(var6, this.field_96616_f, 0.0, 1.0, 0.0);
            var16.addVertexWithUV(var5, this.field_96616_f, 0.0, 0.0, 0.0);
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
        GL11.glEnable(3553);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GL11.glDisable(3042);
    }
    
    protected int func_96606_e() {
        return this.field_96617_g - 8;
    }
}
