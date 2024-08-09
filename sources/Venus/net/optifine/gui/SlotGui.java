/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

public abstract class SlotGui
extends FocusableGui
implements IRenderable {
    protected static final int NO_DRAG = -1;
    protected static final int DRAG_OUTSIDE = -2;
    protected final Minecraft minecraft;
    protected int width;
    protected int height;
    protected int y0;
    protected int y1;
    protected int x1;
    protected int x0;
    protected final int itemHeight;
    protected boolean centerListVertically = true;
    protected int yDrag = -2;
    protected double yo;
    protected boolean visible = true;
    protected boolean renderSelection = true;
    protected boolean renderHeader;
    protected int headerHeight;
    private boolean scrolling;

    public SlotGui(Minecraft minecraft, int n, int n2, int n3, int n4, int n5) {
        this.minecraft = minecraft;
        this.width = n;
        this.height = n2;
        this.y0 = n3;
        this.y1 = n4;
        this.itemHeight = n5;
        this.x0 = 0;
        this.x1 = n;
    }

    public void updateSize(int n, int n2, int n3, int n4) {
        this.width = n;
        this.height = n2;
        this.y0 = n3;
        this.y1 = n4;
        this.x0 = 0;
        this.x1 = n;
    }

    public void setRenderSelection(boolean bl) {
        this.renderSelection = bl;
    }

    protected void setRenderHeader(boolean bl, int n) {
        this.renderHeader = bl;
        this.headerHeight = n;
        if (!bl) {
            this.headerHeight = 0;
        }
    }

    public void setVisible(boolean bl) {
        this.visible = bl;
    }

    public boolean isVisible() {
        return this.visible;
    }

    protected abstract int getItemCount();

    @Override
    public List<? extends IGuiEventListener> getEventListeners() {
        return Collections.emptyList();
    }

    protected boolean selectItem(int n, int n2, double d, double d2) {
        return false;
    }

    protected abstract boolean isSelectedItem(int var1);

    protected int getMaxPosition() {
        return this.getItemCount() * this.itemHeight + this.headerHeight;
    }

    protected abstract void renderBackground();

    protected void updateItemPosition(int n, int n2, int n3, float f) {
    }

    protected abstract void renderItem(MatrixStack var1, int var2, int var3, int var4, int var5, int var6, int var7, float var8);

    protected void renderHeader(int n, int n2, Tessellator tessellator) {
    }

    protected void clickedHeader(int n, int n2) {
    }

    protected void renderDecorations(int n, int n2) {
    }

    public int getItemAtPosition(double d, double d2) {
        int n = this.x0 + this.width / 2 - this.getRowWidth() / 2;
        int n2 = this.x0 + this.width / 2 + this.getRowWidth() / 2;
        int n3 = MathHelper.floor(d2 - (double)this.y0) - this.headerHeight + (int)this.yo - 4;
        int n4 = n3 / this.itemHeight;
        return d < (double)this.getScrollbarPosition() && d >= (double)n && d <= (double)n2 && n4 >= 0 && n3 >= 0 && n4 < this.getItemCount() ? n4 : -1;
    }

    protected void capYPosition() {
        this.yo = MathHelper.clamp(this.yo, 0.0, (double)this.getMaxScroll());
    }

    public int getMaxScroll() {
        return Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4));
    }

    public void centerScrollOn(int n) {
        this.yo = n * this.itemHeight + this.itemHeight / 2 - (this.y1 - this.y0) / 2;
        this.capYPosition();
    }

    public int getScroll() {
        return (int)this.yo;
    }

    public boolean isMouseInList(double d, double d2) {
        return d2 >= (double)this.y0 && d2 <= (double)this.y1 && d >= (double)this.x0 && d <= (double)this.x1;
    }

    public int getScrollBottom() {
        return (int)this.yo - this.height - this.headerHeight;
    }

    public void scroll(int n) {
        this.yo += (double)n;
        this.capYPosition();
        this.yDrag = -2;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.visible) {
            this.renderBackground();
            int n3 = this.getScrollbarPosition();
            int n4 = n3 + 6;
            this.capYPosition();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            this.minecraft.getTextureManager().bindTexture(AbstractGui.BACKGROUND_LOCATION);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            float f2 = 32.0f;
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos(this.x0, this.y1, 0.0).tex((float)this.x0 / 32.0f, (float)(this.y1 + (int)this.yo) / 32.0f).color(32, 32, 32, 255).endVertex();
            bufferBuilder.pos(this.x1, this.y1, 0.0).tex((float)this.x1 / 32.0f, (float)(this.y1 + (int)this.yo) / 32.0f).color(32, 32, 32, 255).endVertex();
            bufferBuilder.pos(this.x1, this.y0, 0.0).tex((float)this.x1 / 32.0f, (float)(this.y0 + (int)this.yo) / 32.0f).color(32, 32, 32, 255).endVertex();
            bufferBuilder.pos(this.x0, this.y0, 0.0).tex((float)this.x0 / 32.0f, (float)(this.y0 + (int)this.yo) / 32.0f).color(32, 32, 32, 255).endVertex();
            tessellator.draw();
            int n5 = this.x0 + this.width / 2 - this.getRowWidth() / 2 + 2;
            int n6 = this.y0 + 4 - (int)this.yo;
            if (this.renderHeader) {
                this.renderHeader(n5, n6, tessellator);
            }
            this.renderList(matrixStack, n5, n6, n, n2, f);
            RenderSystem.disableDepthTest();
            this.renderHoleBackground(0, this.y0, 255, 255);
            this.renderHoleBackground(this.y1, this.height, 255, 255);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            RenderSystem.disableAlphaTest();
            RenderSystem.shadeModel(7425);
            RenderSystem.disableTexture();
            int n7 = 4;
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos(this.x0, this.y0 + 4, 0.0).tex(0.0f, 1.0f).color(0, 0, 0, 0).endVertex();
            bufferBuilder.pos(this.x1, this.y0 + 4, 0.0).tex(1.0f, 1.0f).color(0, 0, 0, 0).endVertex();
            bufferBuilder.pos(this.x1, this.y0, 0.0).tex(1.0f, 0.0f).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(this.x0, this.y0, 0.0).tex(0.0f, 0.0f).color(0, 0, 0, 255).endVertex();
            tessellator.draw();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos(this.x0, this.y1, 0.0).tex(0.0f, 1.0f).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(this.x1, this.y1, 0.0).tex(1.0f, 1.0f).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(this.x1, this.y1 - 4, 0.0).tex(1.0f, 0.0f).color(0, 0, 0, 0).endVertex();
            bufferBuilder.pos(this.x0, this.y1 - 4, 0.0).tex(0.0f, 0.0f).color(0, 0, 0, 0).endVertex();
            tessellator.draw();
            int n8 = this.getMaxScroll();
            if (n8 > 0) {
                int n9 = (int)((float)((this.y1 - this.y0) * (this.y1 - this.y0)) / (float)this.getMaxPosition());
                int n10 = (int)this.yo * (this.y1 - this.y0 - (n9 = MathHelper.clamp(n9, 32, this.y1 - this.y0 - 8))) / n8 + this.y0;
                if (n10 < this.y0) {
                    n10 = this.y0;
                }
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferBuilder.pos(n3, this.y1, 0.0).tex(0.0f, 1.0f).color(0, 0, 0, 255).endVertex();
                bufferBuilder.pos(n4, this.y1, 0.0).tex(1.0f, 1.0f).color(0, 0, 0, 255).endVertex();
                bufferBuilder.pos(n4, this.y0, 0.0).tex(1.0f, 0.0f).color(0, 0, 0, 255).endVertex();
                bufferBuilder.pos(n3, this.y0, 0.0).tex(0.0f, 0.0f).color(0, 0, 0, 255).endVertex();
                tessellator.draw();
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferBuilder.pos(n3, n10 + n9, 0.0).tex(0.0f, 1.0f).color(128, 128, 128, 255).endVertex();
                bufferBuilder.pos(n4, n10 + n9, 0.0).tex(1.0f, 1.0f).color(128, 128, 128, 255).endVertex();
                bufferBuilder.pos(n4, n10, 0.0).tex(1.0f, 0.0f).color(128, 128, 128, 255).endVertex();
                bufferBuilder.pos(n3, n10, 0.0).tex(0.0f, 0.0f).color(128, 128, 128, 255).endVertex();
                tessellator.draw();
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferBuilder.pos(n3, n10 + n9 - 1, 0.0).tex(0.0f, 1.0f).color(192, 192, 192, 255).endVertex();
                bufferBuilder.pos(n4 - 1, n10 + n9 - 1, 0.0).tex(1.0f, 1.0f).color(192, 192, 192, 255).endVertex();
                bufferBuilder.pos(n4 - 1, n10, 0.0).tex(1.0f, 0.0f).color(192, 192, 192, 255).endVertex();
                bufferBuilder.pos(n3, n10, 0.0).tex(0.0f, 0.0f).color(192, 192, 192, 255).endVertex();
                tessellator.draw();
            }
            this.renderDecorations(n, n2);
            RenderSystem.enableTexture();
            RenderSystem.shadeModel(7424);
            RenderSystem.enableAlphaTest();
            RenderSystem.disableBlend();
        }
    }

    protected void updateScrollingState(double d, double d2, int n) {
        this.scrolling = n == 0 && d >= (double)this.getScrollbarPosition() && d < (double)(this.getScrollbarPosition() + 6);
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        this.updateScrollingState(d, d2, n);
        if (this.isVisible() && this.isMouseInList(d, d2)) {
            int n2 = this.getItemAtPosition(d, d2);
            if (n2 == -1 && n == 0) {
                this.clickedHeader((int)(d - (double)(this.x0 + this.width / 2 - this.getRowWidth() / 2)), (int)(d2 - (double)this.y0) + (int)this.yo - 4);
                return false;
            }
            if (n2 != -1 && this.selectItem(n2, n, d, d2)) {
                if (this.getEventListeners().size() > n2) {
                    this.setListener(this.getEventListeners().get(n2));
                }
                this.setDragging(false);
                return false;
            }
            return this.scrolling;
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double d, double d2, int n) {
        if (this.getListener() != null) {
            this.getListener().mouseReleased(d, d2, n);
        }
        return true;
    }

    @Override
    public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        if (super.mouseDragged(d, d2, n, d3, d4)) {
            return false;
        }
        if (this.isVisible() && n == 0 && this.scrolling) {
            if (d2 < (double)this.y0) {
                this.yo = 0.0;
            } else if (d2 > (double)this.y1) {
                this.yo = this.getMaxScroll();
            } else {
                double d5 = this.getMaxScroll();
                if (d5 < 1.0) {
                    d5 = 1.0;
                }
                int n2 = (int)((float)((this.y1 - this.y0) * (this.y1 - this.y0)) / (float)this.getMaxPosition());
                double d6 = d5 / (double)(this.y1 - this.y0 - (n2 = MathHelper.clamp(n2, 32, this.y1 - this.y0 - 8)));
                if (d6 < 1.0) {
                    d6 = 1.0;
                }
                this.yo += d4 * d6;
                this.capYPosition();
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        if (!this.isVisible()) {
            return true;
        }
        this.yo -= d3 * (double)this.itemHeight / 2.0;
        return false;
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (!this.isVisible()) {
            return true;
        }
        if (super.keyPressed(n, n2, n3)) {
            return false;
        }
        if (n == 264) {
            this.moveSelection(1);
            return false;
        }
        if (n == 265) {
            this.moveSelection(-1);
            return false;
        }
        return true;
    }

    protected void moveSelection(int n) {
    }

    @Override
    public boolean charTyped(char c, int n) {
        return !this.isVisible() ? false : super.charTyped(c, n);
    }

    @Override
    public boolean isMouseOver(double d, double d2) {
        return this.isMouseInList(d, d2);
    }

    public int getRowWidth() {
        return 1;
    }

    protected void renderList(MatrixStack matrixStack, int n, int n2, int n3, int n4, float f) {
        int n5 = this.getItemCount();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        for (int i = 0; i < n5; ++i) {
            int n6 = n2 + i * this.itemHeight + this.headerHeight;
            int n7 = this.itemHeight - 4;
            if (n6 > this.y1 || n6 + n7 < this.y0) {
                this.updateItemPosition(i, n, n6, f);
            }
            if (this.renderSelection && this.isSelectedItem(i)) {
                int n8 = this.x0 + this.width / 2 - this.getRowWidth() / 2;
                int n9 = this.x0 + this.width / 2 + this.getRowWidth() / 2;
                RenderSystem.disableTexture();
                float f2 = this.isFocused() ? 1.0f : 0.5f;
                RenderSystem.color4f(f2, f2, f2, 1.0f);
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
                bufferBuilder.pos(n8, n6 + n7 + 2, 0.0).endVertex();
                bufferBuilder.pos(n9, n6 + n7 + 2, 0.0).endVertex();
                bufferBuilder.pos(n9, n6 - 2, 0.0).endVertex();
                bufferBuilder.pos(n8, n6 - 2, 0.0).endVertex();
                tessellator.draw();
                RenderSystem.color4f(0.0f, 0.0f, 0.0f, 1.0f);
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
                bufferBuilder.pos(n8 + 1, n6 + n7 + 1, 0.0).endVertex();
                bufferBuilder.pos(n9 - 1, n6 + n7 + 1, 0.0).endVertex();
                bufferBuilder.pos(n9 - 1, n6 - 1, 0.0).endVertex();
                bufferBuilder.pos(n8 + 1, n6 - 1, 0.0).endVertex();
                tessellator.draw();
                RenderSystem.enableTexture();
            }
            if (n6 + this.itemHeight < this.y0 || n6 > this.y1) continue;
            this.renderItem(matrixStack, i, n, n6, n7, n3, n4, f);
        }
    }

    protected boolean isFocused() {
        return true;
    }

    protected int getScrollbarPosition() {
        return this.width / 2 + 124;
    }

    protected void renderHoleBackground(int n, int n2, int n3, int n4) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        this.minecraft.getTextureManager().bindTexture(AbstractGui.BACKGROUND_LOCATION);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = 32.0f;
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos(this.x0, n2, 0.0).tex(0.0f, (float)n2 / 32.0f).color(64, 64, 64, n4).endVertex();
        bufferBuilder.pos(this.x0 + this.width, n2, 0.0).tex((float)this.width / 32.0f, (float)n2 / 32.0f).color(64, 64, 64, n4).endVertex();
        bufferBuilder.pos(this.x0 + this.width, n, 0.0).tex((float)this.width / 32.0f, (float)n / 32.0f).color(64, 64, 64, n3).endVertex();
        bufferBuilder.pos(this.x0, n, 0.0).tex(0.0f, (float)n / 32.0f).color(64, 64, 64, n3).endVertex();
        tessellator.draw();
    }

    public void setLeftPos(int n) {
        this.x0 = n;
        this.x1 = n + this.width;
    }

    public int getItemHeight() {
        return this.itemHeight;
    }
}

