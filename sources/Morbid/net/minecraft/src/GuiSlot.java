package net.minecraft.src;

import net.minecraft.client.*;
import java.util.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public abstract class GuiSlot
{
    private final Minecraft mc;
    private int width;
    private int height;
    protected int top;
    protected int bottom;
    private int right;
    private int left;
    protected final int slotHeight;
    private int scrollUpButtonID;
    private int scrollDownButtonID;
    protected int mouseX;
    protected int mouseY;
    private float initialClickY;
    private float scrollMultiplier;
    private float amountScrolled;
    private int selectedElement;
    private long lastClicked;
    private boolean showSelectionBox;
    private boolean field_77243_s;
    private int field_77242_t;
    
    public GuiSlot(final Minecraft par1Minecraft, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.initialClickY = -2.0f;
        this.selectedElement = -1;
        this.lastClicked = 0L;
        this.showSelectionBox = true;
        this.mc = par1Minecraft;
        this.width = par2;
        this.height = par3;
        this.top = par4;
        this.bottom = par5;
        this.slotHeight = par6;
        this.left = 0;
        this.right = par2;
    }
    
    public void func_77207_a(final int par1, final int par2, final int par3, final int par4) {
        this.width = par1;
        this.height = par2;
        this.top = par3;
        this.bottom = par4;
        this.left = 0;
        this.right = par1;
    }
    
    public void setShowSelectionBox(final boolean par1) {
        this.showSelectionBox = par1;
    }
    
    protected void func_77223_a(final boolean par1, final int par2) {
        this.field_77243_s = par1;
        this.field_77242_t = par2;
        if (!par1) {
            this.field_77242_t = 0;
        }
    }
    
    protected abstract int getSize();
    
    protected abstract void elementClicked(final int p0, final boolean p1);
    
    protected abstract boolean isSelected(final int p0);
    
    protected int getContentHeight() {
        return this.getSize() * this.slotHeight + this.field_77242_t;
    }
    
    protected abstract void drawBackground();
    
    protected abstract void drawSlot(final int p0, final int p1, final int p2, final int p3, final Tessellator p4);
    
    protected void func_77222_a(final int par1, final int par2, final Tessellator par3Tessellator) {
    }
    
    protected void func_77224_a(final int par1, final int par2) {
    }
    
    protected void func_77215_b(final int par1, final int par2) {
    }
    
    public int func_77210_c(final int par1, final int par2) {
        final int var3 = this.width / 2 - 110;
        final int var4 = this.width / 2 + 110;
        final int var5 = par2 - this.top - this.field_77242_t + (int)this.amountScrolled - 4;
        final int var6 = var5 / this.slotHeight;
        return (par1 >= var3 && par1 <= var4 && var6 >= 0 && var5 >= 0 && var6 < this.getSize()) ? var6 : -1;
    }
    
    public void registerScrollButtons(final List par1List, final int par2, final int par3) {
        this.scrollUpButtonID = par2;
        this.scrollDownButtonID = par3;
    }
    
    private void bindAmountScrolled() {
        int var1 = this.func_77209_d();
        if (var1 < 0) {
            var1 /= 2;
        }
        if (this.amountScrolled < 0.0f) {
            this.amountScrolled = 0.0f;
        }
        if (this.amountScrolled > var1) {
            this.amountScrolled = var1;
        }
    }
    
    public int func_77209_d() {
        return this.getContentHeight() - (this.bottom - this.top - 4);
    }
    
    public void func_77208_b(final int par1) {
        this.amountScrolled += par1;
        this.bindAmountScrolled();
        this.initialClickY = -2.0f;
    }
    
    public void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == this.scrollUpButtonID) {
                this.amountScrolled -= this.slotHeight * 2 / 3;
                this.initialClickY = -2.0f;
                this.bindAmountScrolled();
            }
            else if (par1GuiButton.id == this.scrollDownButtonID) {
                this.amountScrolled += this.slotHeight * 2 / 3;
                this.initialClickY = -2.0f;
                this.bindAmountScrolled();
            }
        }
    }
    
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.mouseX = par1;
        this.mouseY = par2;
        this.drawBackground();
        final int var4 = this.getSize();
        final int var5 = this.getScrollBarX();
        final int var6 = var5 + 6;
        if (Mouse.isButtonDown(0)) {
            if (this.initialClickY == -1.0f) {
                boolean var7 = true;
                if (par2 >= this.top && par2 <= this.bottom) {
                    final int var8 = this.width / 2 - 110;
                    final int var9 = this.width / 2 + 110;
                    final int var10 = par2 - this.top - this.field_77242_t + (int)this.amountScrolled - 4;
                    final int var11 = var10 / this.slotHeight;
                    if (par1 >= var8 && par1 <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4) {
                        final boolean var12 = var11 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L;
                        this.elementClicked(var11, var12);
                        this.selectedElement = var11;
                        this.lastClicked = Minecraft.getSystemTime();
                    }
                    else if (par1 >= var8 && par1 <= var9 && var10 < 0) {
                        this.func_77224_a(par1 - var8, par2 - this.top + (int)this.amountScrolled - 4);
                        var7 = false;
                    }
                    if (par1 >= var5 && par1 <= var6) {
                        this.scrollMultiplier = -1.0f;
                        int var13 = this.func_77209_d();
                        if (var13 < 1) {
                            var13 = 1;
                        }
                        int var14 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
                        if (var14 < 32) {
                            var14 = 32;
                        }
                        if (var14 > this.bottom - this.top - 8) {
                            var14 = this.bottom - this.top - 8;
                        }
                        this.scrollMultiplier /= (this.bottom - this.top - var14) / var13;
                    }
                    else {
                        this.scrollMultiplier = 1.0f;
                    }
                    if (var7) {
                        this.initialClickY = par2;
                    }
                    else {
                        this.initialClickY = -2.0f;
                    }
                }
                else {
                    this.initialClickY = -2.0f;
                }
            }
            else if (this.initialClickY >= 0.0f) {
                this.amountScrolled -= (par2 - this.initialClickY) * this.scrollMultiplier;
                this.initialClickY = par2;
            }
        }
        else {
            while (!this.mc.gameSettings.touchscreen && Mouse.next()) {
                int var15 = Mouse.getEventDWheel();
                if (var15 != 0) {
                    if (var15 > 0) {
                        var15 = -1;
                    }
                    else if (var15 < 0) {
                        var15 = 1;
                    }
                    this.amountScrolled += var15 * this.slotHeight / 2;
                }
            }
            this.initialClickY = -1.0f;
        }
        this.bindAmountScrolled();
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        final Tessellator var16 = Tessellator.instance;
        this.mc.renderEngine.bindTexture("/gui/background.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float var17 = 32.0f;
        var16.startDrawingQuads();
        var16.setColorOpaque_I(2105376);
        var16.addVertexWithUV(this.left, this.bottom, 0.0, this.left / var17, (this.bottom + (int)this.amountScrolled) / var17);
        var16.addVertexWithUV(this.right, this.bottom, 0.0, this.right / var17, (this.bottom + (int)this.amountScrolled) / var17);
        var16.addVertexWithUV(this.right, this.top, 0.0, this.right / var17, (this.top + (int)this.amountScrolled) / var17);
        var16.addVertexWithUV(this.left, this.top, 0.0, this.left / var17, (this.top + (int)this.amountScrolled) / var17);
        var16.draw();
        final int var9 = this.width / 2 - 92 - 16;
        final int var10 = this.top + 4 - (int)this.amountScrolled;
        if (this.field_77243_s) {
            this.func_77222_a(var9, var10, var16);
        }
        for (int var11 = 0; var11 < var4; ++var11) {
            final int var13 = var10 + var11 * this.slotHeight + this.field_77242_t;
            final int var14 = this.slotHeight - 4;
            if (var13 <= this.bottom && var13 + var14 >= this.top) {
                if (this.showSelectionBox && this.isSelected(var11)) {
                    final int var18 = this.width / 2 - 110;
                    final int var19 = this.width / 2 + 110;
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
        this.overlayBackground(0, this.top, 255, 255);
        this.overlayBackground(this.bottom, this.height, 255, 255);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        var16.startDrawingQuads();
        var16.setColorRGBA_I(0, 0);
        var16.addVertexWithUV(this.left, this.top + var20, 0.0, 0.0, 1.0);
        var16.addVertexWithUV(this.right, this.top + var20, 0.0, 1.0, 1.0);
        var16.setColorRGBA_I(0, 255);
        var16.addVertexWithUV(this.right, this.top, 0.0, 1.0, 0.0);
        var16.addVertexWithUV(this.left, this.top, 0.0, 0.0, 0.0);
        var16.draw();
        var16.startDrawingQuads();
        var16.setColorRGBA_I(0, 255);
        var16.addVertexWithUV(this.left, this.bottom, 0.0, 0.0, 1.0);
        var16.addVertexWithUV(this.right, this.bottom, 0.0, 1.0, 1.0);
        var16.setColorRGBA_I(0, 0);
        var16.addVertexWithUV(this.right, this.bottom - var20, 0.0, 1.0, 0.0);
        var16.addVertexWithUV(this.left, this.bottom - var20, 0.0, 0.0, 0.0);
        var16.draw();
        int var13 = this.func_77209_d();
        if (var13 > 0) {
            int var14 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
            if (var14 < 32) {
                var14 = 32;
            }
            if (var14 > this.bottom - this.top - 8) {
                var14 = this.bottom - this.top - 8;
            }
            int var18 = (int)this.amountScrolled * (this.bottom - this.top - var14) / var13 + this.top;
            if (var18 < this.top) {
                var18 = this.top;
            }
            var16.startDrawingQuads();
            var16.setColorRGBA_I(0, 255);
            var16.addVertexWithUV(var5, this.bottom, 0.0, 0.0, 1.0);
            var16.addVertexWithUV(var6, this.bottom, 0.0, 1.0, 1.0);
            var16.addVertexWithUV(var6, this.top, 0.0, 1.0, 0.0);
            var16.addVertexWithUV(var5, this.top, 0.0, 0.0, 0.0);
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
        this.func_77215_b(par1, par2);
        GL11.glEnable(3553);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GL11.glDisable(3042);
    }
    
    protected int getScrollBarX() {
        return this.width / 2 + 124;
    }
    
    private void overlayBackground(final int par1, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        this.mc.renderEngine.bindTexture("/gui/background.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float var6 = 32.0f;
        var5.startDrawingQuads();
        var5.setColorRGBA_I(4210752, par4);
        var5.addVertexWithUV(0.0, par2, 0.0, 0.0, par2 / var6);
        var5.addVertexWithUV(this.width, par2, 0.0, this.width / var6, par2 / var6);
        var5.setColorRGBA_I(4210752, par3);
        var5.addVertexWithUV(this.width, par1, 0.0, this.width / var6, par1 / var6);
        var5.addVertexWithUV(0.0, par1, 0.0, 0.0, par1 / var6);
        var5.draw();
    }
}
