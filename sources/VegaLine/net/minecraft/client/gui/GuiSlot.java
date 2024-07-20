/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;
import ru.govno.client.Client;
import ru.govno.client.module.modules.ComfortUi;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public abstract class GuiSlot {
    protected final Minecraft mc;
    protected int width;
    protected int height;
    protected int top;
    protected int bottom;
    protected int right;
    protected int left;
    protected final int slotHeight;
    private int scrollUpButtonID;
    private int scrollDownButtonID;
    protected int mouseX;
    protected int mouseY;
    protected boolean centerListVertically = true;
    protected int initialClickY = -2;
    protected float scrollMultiplier;
    protected float amountScrolled;
    protected int selectedElement = -1;
    protected long lastClicked;
    protected boolean visible = true;
    protected boolean showSelectionBox = true;
    protected boolean hasListHeader;
    protected int headerPadding;
    private boolean enabled = true;
    private final long initTime = System.currentTimeMillis();
    public static float smoothScroll;

    public GuiSlot(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
        this.mc = mcIn;
        this.width = width;
        this.height = height;
        this.top = topIn;
        this.bottom = bottomIn;
        this.slotHeight = slotHeightIn;
        this.left = 0;
        this.right = width;
    }

    public void setDimensions(int widthIn, int heightIn, int topIn, int bottomIn) {
        this.width = widthIn;
        this.height = heightIn;
        this.top = topIn;
        this.bottom = bottomIn;
        this.left = 0;
        this.right = widthIn;
    }

    public void func_193651_b(boolean p_193651_1_) {
        this.showSelectionBox = p_193651_1_;
    }

    protected void setHasListHeader(boolean hasListHeaderIn, int headerPaddingIn) {
        this.hasListHeader = hasListHeaderIn;
        this.headerPadding = headerPaddingIn;
        if (!hasListHeaderIn) {
            this.headerPadding = 0;
        }
    }

    protected abstract int getSize();

    protected abstract void elementClicked(int var1, boolean var2, int var3, int var4);

    protected abstract boolean isSelected(int var1);

    protected int getContentHeight() {
        return this.getSize() * this.slotHeight + this.headerPadding;
    }

    protected abstract void drawBackground();

    protected void func_192639_a(int p_192639_1_, int p_192639_2_, int p_192639_3_, float p_192639_4_) {
    }

    protected abstract void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7);

    protected void drawListHeader(int insideLeft, int insideTop, Tessellator tessellatorIn) {
    }

    protected void clickedHeader(int p_148132_1_, int p_148132_2_) {
    }

    protected void renderDecorations(int mouseXIn, int mouseYIn) {
    }

    public int getSlotIndexFromScreenCoords(int posX, int posY) {
        int i = this.left + this.width / 2 - this.getListWidth() / 2;
        int j = this.left + this.width / 2 + this.getListWidth() / 2;
        int k = posY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
        int l = k / this.slotHeight;
        return posX < this.getScrollBarX() && posX >= i && posX <= j && l >= 0 && k >= 0 && l < this.getSize() ? l : -1;
    }

    public void registerScrollButtons(int scrollUpButtonIDIn, int scrollDownButtonIDIn) {
        this.scrollUpButtonID = scrollUpButtonIDIn;
        this.scrollDownButtonID = scrollDownButtonIDIn;
    }

    protected void bindAmountScrolled() {
        smoothScroll = MathHelper.clamp(this.amountScrolled, 0.0f, (float)this.getMaxScroll());
        this.amountScrolled = Panic.stop ? smoothScroll : MathUtils.lerp(this.amountScrolled, smoothScroll, (float)Minecraft.frameTime * 0.0025f);
    }

    public int getMaxScroll() {
        return Math.max(0, this.getContentHeight() - (this.bottom - this.top - 4));
    }

    public float getAmountScrolled() {
        return this.amountScrolled;
    }

    public boolean isMouseYWithinSlotBounds(int p_148141_1_) {
        return p_148141_1_ >= this.top && p_148141_1_ <= this.bottom && this.mouseX >= this.left && this.mouseX <= this.right;
    }

    public void scrollBy(int amount) {
        this.amountScrolled += (float)amount;
        this.bindAmountScrolled();
        this.initialClickY = -2;
    }

    public void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == this.scrollUpButtonID) {
                this.amountScrolled -= (float)(this.slotHeight * 2 / 3);
                this.initialClickY = -2;
                this.bindAmountScrolled();
            } else if (button.id == this.scrollDownButtonID) {
                this.amountScrolled += (float)(this.slotHeight * 2 / 3);
                this.initialClickY = -2;
                this.bindAmountScrolled();
            }
        }
    }

    public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        if (this.visible) {
            int j1;
            this.mouseX = mouseXIn;
            this.mouseY = mouseYIn;
            if (Panic.stop || Client.moduleManager == null || !ComfortUi.get.isScreensDarking() || this.mc.world == null) {
                this.drawBackground();
            }
            int i = this.getScrollBarX();
            int j = i + 6;
            this.bindAmountScrolled();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            if (Panic.stop || Client.moduleManager == null || !ComfortUi.get.isScreensDarking() || this.mc.world == null) {
                this.drawContainerBackground(tessellator);
            }
            int k = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
            int l = this.top + 4 - (int)this.amountScrolled;
            if ((this.mc.currentScreen instanceof GuiMultiplayer || this.mc.currentScreen instanceof GuiWorldSelection || this.mc.currentScreen instanceof GuiConnecting) && !Panic.stop) {
                RenderUtils.drawScreenShaderBackground(sr, this.mouseX, this.mouseY);
            }
            if ((this.mc.currentScreen instanceof GuiMultiplayer || this.mc.currentScreen instanceof GuiWorldSelection) && !Panic.stop) {
                int bgColor = ColorUtils.getColor(0, 0, 0, 140);
                RenderUtils.drawAlphedRect(sr.getScaledWidth() / 2 - 170, 0.0, sr.getScaledWidth() / 2 + 170, sr.getScaledHeight(), bgColor);
                RenderUtils.drawAlphedSideways(sr.getScaledWidth() / 2 - 200, 0.0, sr.getScaledWidth() / 2 - 170, sr.getScaledHeight(), 0, bgColor);
                RenderUtils.drawAlphedSideways(sr.getScaledWidth() / 2 + 170, 0.0, sr.getScaledWidth() / 2 + 200, sr.getScaledHeight(), bgColor, 0);
            }
            if (this.hasListHeader) {
                this.drawListHeader(k, l, tessellator);
            }
            this.func_192638_a(k, l, mouseXIn, mouseYIn, partialTicks);
            GlStateManager.disableDepth();
            if (!(this.mc.currentScreen instanceof GuiMultiplayer) && !(this.mc.currentScreen instanceof GuiWorldSelection) || Panic.stop) {
                if (Panic.stop || Client.moduleManager == null || !ComfortUi.get.isScreensDarking() || this.mc.world == null) {
                    this.overlayBackground(0, this.top, 255, 255);
                    this.overlayBackground(this.bottom, this.height, 255, 255);
                }
            } else {
                int bgColor2 = ColorUtils.getColor(0, 0, 0);
                RenderUtils.drawAlphedRect(sr.getScaledWidth() / 2 - 170, sr.getScaledHeight() - 50, sr.getScaledWidth() / 2 + 170, sr.getScaledHeight(), bgColor2);
                RenderUtils.drawAlphedGradient(sr.getScaledWidth() / 2 - 170, sr.getScaledHeight() - 110, sr.getScaledWidth() / 2 + 170, sr.getScaledHeight() - 50, 0, bgColor2);
                RenderUtils.drawAlphedRect(sr.getScaledWidth() / 2 - 170, 0.0, sr.getScaledWidth() / 2 + 170, 30.0, bgColor2);
                RenderUtils.drawShadowRect(sr.getScaledWidth() / 2 - 170, 0.0, sr.getScaledWidth() / 2 + 170, 30.0, 5);
                RenderUtils.drawShadowRect(sr.getScaledWidth() / 2 - 170, 0.0, sr.getScaledWidth() / 2 + 170, 30.0, 15);
                RenderUtils.drawAlphedGradient(sr.getScaledWidth() / 2 - 170, 30.0, sr.getScaledWidth() / 2 + 170, 70.0, bgColor2, 0);
                RenderUtils.drawShadowRect(sr.getScaledWidth() / 2 - 170, sr.getScaledHeight() - 50, sr.getScaledWidth() / 2 + 170, sr.getScaledHeight(), 5);
                RenderUtils.drawShadowRect(sr.getScaledWidth() / 2 - 170, sr.getScaledHeight() - 50, sr.getScaledWidth() / 2 + 170, sr.getScaledHeight(), 15);
            }
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();
            int i1 = 4;
            if (!(this.mc.currentScreen instanceof GuiMultiplayer) && !(this.mc.currentScreen instanceof GuiWorldSelection)) {
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos(this.left, this.top + 4, 0.0).tex(0.0, 1.0).color(0, 0, 0, 0).endVertex();
                bufferbuilder.pos(this.right, this.top + 4, 0.0).tex(1.0, 1.0).color(0, 0, 0, 0).endVertex();
                bufferbuilder.pos(this.right, this.top, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(this.left, this.top, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
                tessellator.draw();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos(this.left, this.bottom, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(this.right, this.bottom, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(this.right, this.bottom - 4, 0.0).tex(1.0, 0.0).color(0, 0, 0, 0).endVertex();
                bufferbuilder.pos(this.left, this.bottom - 4, 0.0).tex(0.0, 0.0).color(0, 0, 0, 0).endVertex();
                tessellator.draw();
            }
            if ((j1 = this.getMaxScroll()) > 0) {
                int k1 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
                int l1 = (int)this.amountScrolled * (this.bottom - this.top - (k1 = MathHelper.clamp(k1, 32, this.bottom - this.top - 8))) / j1 + this.top;
                if (l1 < this.top) {
                    l1 = this.top;
                }
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos(i, this.bottom, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(j, this.bottom, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(j, this.top, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(i, this.top, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
                tessellator.draw();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos(i, l1 + k1, 0.0).tex(0.0, 1.0).color(128, 128, 128, 255).endVertex();
                bufferbuilder.pos(j, l1 + k1, 0.0).tex(1.0, 1.0).color(128, 128, 128, 255).endVertex();
                bufferbuilder.pos(j, l1, 0.0).tex(1.0, 0.0).color(128, 128, 128, 255).endVertex();
                bufferbuilder.pos(i, l1, 0.0).tex(0.0, 0.0).color(128, 128, 128, 255).endVertex();
                tessellator.draw();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos(i, l1 + k1 - 1, 0.0).tex(0.0, 1.0).color(192, 192, 192, 255).endVertex();
                bufferbuilder.pos(j - 1, l1 + k1 - 1, 0.0).tex(1.0, 1.0).color(192, 192, 192, 255).endVertex();
                bufferbuilder.pos(j - 1, l1, 0.0).tex(1.0, 0.0).color(192, 192, 192, 255).endVertex();
                bufferbuilder.pos(i, l1, 0.0).tex(0.0, 0.0).color(192, 192, 192, 255).endVertex();
                tessellator.draw();
            }
            this.renderDecorations(mouseXIn, mouseYIn);
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }

    public void handleMouseInput() {
        if (this.isMouseYWithinSlotBounds(this.mouseY)) {
            int i2;
            if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
                int i = (this.width - this.getListWidth()) / 2;
                int j = (this.width + this.getListWidth()) / 2;
                int k = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
                int l = k / this.slotHeight;
                if (l < this.getSize() && this.mouseX >= i && this.mouseX <= j && l >= 0 && k >= 0) {
                    this.elementClicked(l, false, this.mouseX, this.mouseY);
                    this.selectedElement = l;
                } else if (this.mouseX >= i && this.mouseX <= j && k < 0) {
                    this.clickedHeader(this.mouseX - i, this.mouseY - this.top + (int)this.amountScrolled - 4);
                }
            }
            if (Mouse.isButtonDown(0) && this.getEnabled()) {
                if (this.initialClickY != -1) {
                    if (this.initialClickY >= 0) {
                        this.amountScrolled -= (float)(this.mouseY - this.initialClickY) * this.scrollMultiplier;
                        this.initialClickY = this.mouseY;
                    }
                } else {
                    boolean flag1 = true;
                    if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
                        int j2 = (this.width - this.getListWidth()) / 2;
                        int k2 = (this.width + this.getListWidth()) / 2;
                        int l2 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
                        int i1 = l2 / this.slotHeight;
                        if (i1 < this.getSize() && this.mouseX >= j2 && this.mouseX <= k2 && i1 >= 0 && l2 >= 0) {
                            boolean flag = i1 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L;
                            this.elementClicked(i1, flag, this.mouseX, this.mouseY);
                            this.selectedElement = i1;
                            this.lastClicked = Minecraft.getSystemTime();
                        } else if (this.mouseX >= j2 && this.mouseX <= k2 && l2 < 0) {
                            this.clickedHeader(this.mouseX - j2, this.mouseY - this.top + (int)this.amountScrolled - 4);
                            flag1 = false;
                        }
                        int i3 = this.getScrollBarX();
                        int j1 = i3 + 6;
                        if (this.mouseX >= i3 && this.mouseX <= j1) {
                            this.scrollMultiplier = -1.0f;
                            int k1 = this.getMaxScroll();
                            if (k1 < 1) {
                                k1 = 1;
                            }
                            int l1 = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getContentHeight());
                            l1 = MathHelper.clamp(l1, 32, this.bottom - this.top - 8);
                            this.scrollMultiplier /= (float)(this.bottom - this.top - l1) / (float)k1;
                        } else {
                            this.scrollMultiplier = 1.0f;
                        }
                        this.initialClickY = flag1 ? this.mouseY : -2;
                    } else {
                        this.initialClickY = -2;
                    }
                }
            } else {
                this.initialClickY = -1;
            }
            if ((i2 = Mouse.getEventDWheel()) != 0) {
                if (i2 > 0) {
                    i2 = -1;
                } else if (i2 < 0) {
                    i2 = 1;
                }
                this.amountScrolled += (float)(i2 * this.slotHeight / 2);
            }
        }
    }

    public void setEnabled(boolean enabledIn) {
        this.enabled = enabledIn;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public int getListWidth() {
        return 220;
    }

    protected void func_192638_a(int p_192638_1_, int p_192638_2_, int p_192638_3_, int p_192638_4_, float p_192638_5_) {
        int i = this.getSize();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        for (int j = 0; j < i; ++j) {
            int k = p_192638_2_ + j * this.slotHeight + this.headerPadding;
            int l = this.slotHeight - 4;
            if (k > this.bottom || k + l < this.top) {
                this.func_192639_a(j, p_192638_1_, k, p_192638_5_);
            }
            if (this.showSelectionBox && this.isSelected(j)) {
                int i1 = this.left + (this.width / 2 - this.getListWidth() / 2);
                int j1 = this.left + this.width / 2 + this.getListWidth() / 2;
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.disableTexture2D();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos(i1, k + l + 2, 0.0).tex(0.0, 1.0).color(128, 128, 128, 255).endVertex();
                bufferbuilder.pos(j1, k + l + 2, 0.0).tex(1.0, 1.0).color(128, 128, 128, 255).endVertex();
                bufferbuilder.pos(j1, k - 2, 0.0).tex(1.0, 0.0).color(128, 128, 128, 255).endVertex();
                bufferbuilder.pos(i1, k - 2, 0.0).tex(0.0, 0.0).color(128, 128, 128, 255).endVertex();
                bufferbuilder.pos(i1 + 1, k + l + 1, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(j1 - 1, k + l + 1, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(j1 - 1, k - 1, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
                bufferbuilder.pos(i1 + 1, k - 1, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
                tessellator.draw();
                GlStateManager.enableTexture2D();
            }
            if (k < this.top - this.slotHeight || k > this.bottom) continue;
            this.func_192637_a(j, p_192638_1_, k, l, p_192638_3_, p_192638_4_, p_192638_5_);
        }
    }

    protected int getScrollBarX() {
        return this.width / 2 + 124;
    }

    protected void overlayBackground(int startY, int endY, int startAlpha, int endAlpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        this.mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        float f = 32.0f;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(this.left, endY, 0.0).tex(0.0, (float)endY / 32.0f).color(64, 64, 64, endAlpha).endVertex();
        bufferbuilder.pos(this.left + this.width, endY, 0.0).tex((float)this.width / 32.0f, (float)endY / 32.0f).color(64, 64, 64, endAlpha).endVertex();
        bufferbuilder.pos(this.left + this.width, startY, 0.0).tex((float)this.width / 32.0f, (float)startY / 32.0f).color(64, 64, 64, startAlpha).endVertex();
        bufferbuilder.pos(this.left, startY, 0.0).tex(0.0, (float)startY / 32.0f).color(64, 64, 64, startAlpha).endVertex();
        tessellator.draw();
    }

    public void setSlotXBoundsFromLeft(int leftIn) {
        this.left = leftIn;
        this.right = leftIn + this.width;
    }

    public int getSlotHeight() {
        return (int)((float)this.slotHeight + (smoothScroll - this.amountScrolled));
    }

    protected void drawContainerBackground(Tessellator p_drawContainerBackground_1_) {
        BufferBuilder bufferbuilder = p_drawContainerBackground_1_.getBuffer();
        this.mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        float f = 32.0f;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(this.left, this.bottom, 0.0).tex((float)this.left / 32.0f, (float)(this.bottom + (int)this.amountScrolled) / 32.0f).color(32, 32, 32, 255).endVertex();
        bufferbuilder.pos(this.right, this.bottom, 0.0).tex((float)this.right / 32.0f, (float)(this.bottom + (int)this.amountScrolled) / 32.0f).color(32, 32, 32, 255).endVertex();
        bufferbuilder.pos(this.right, this.top, 0.0).tex((float)this.right / 32.0f, (float)(this.top + (int)this.amountScrolled) / 32.0f).color(32, 32, 32, 255).endVertex();
        bufferbuilder.pos(this.left, this.top, 0.0).tex((float)this.left / 32.0f, (float)(this.top + (int)this.amountScrolled) / 32.0f).color(32, 32, 32, 255).endVertex();
        p_drawContainerBackground_1_.draw();
    }
}

