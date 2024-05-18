package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;

public abstract class GuiSlot
{
    protected int bottom;
    protected int selectedElement;
    private int scrollDownButtonID;
    protected int initialClickY;
    protected final int slotHeight;
    protected int left;
    protected int mouseX;
    protected int right;
    protected boolean hasListHeader;
    protected float scrollMultiplier;
    protected int height;
    protected final Minecraft mc;
    protected boolean showSelectionBox;
    private int scrollUpButtonID;
    protected int width;
    protected boolean field_148163_i;
    protected boolean field_178041_q;
    private boolean enabled;
    protected int top;
    protected int mouseY;
    protected float amountScrolled;
    protected int headerPadding;
    protected long lastClicked;
    
    public GuiSlot(final Minecraft mc, final int n, final int height, final int top, final int bottom, final int slotHeight) {
        this.field_148163_i = (" ".length() != 0);
        this.initialClickY = -"  ".length();
        this.selectedElement = -" ".length();
        this.field_178041_q = (" ".length() != 0);
        this.showSelectionBox = (" ".length() != 0);
        this.enabled = (" ".length() != 0);
        this.mc = mc;
        this.width = n;
        this.height = height;
        this.top = top;
        this.bottom = bottom;
        this.slotHeight = slotHeight;
        this.left = "".length();
        this.right = n;
    }
    
    protected abstract int getSize();
    
    protected void drawListHeader(final int n, final int n2, final Tessellator tessellator) {
    }
    
    public boolean getEnabled() {
        return this.enabled;
    }
    
    protected void drawSelectionBox(final int n, final int n2, final int n3, final int n4) {
        final int size = this.getSize();
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        int i = "".length();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (i < size) {
            final int n5 = n2 + i * this.slotHeight + this.headerPadding;
            final int n6 = this.slotHeight - (0x45 ^ 0x41);
            if (n5 > this.bottom || n5 + n6 < this.top) {
                this.func_178040_a(i, n, n5);
            }
            if (this.showSelectionBox && this.isSelected(i)) {
                final int n7 = this.left + (this.width / "  ".length() - this.getListWidth() / "  ".length());
                final int n8 = this.left + this.width / "  ".length() + this.getListWidth() / "  ".length();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.disableTexture2D();
                worldRenderer.begin(0x62 ^ 0x65, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(n7, n5 + n6 + "  ".length(), 0.0).tex(0.0, 1.0).color(73 + 123 - 136 + 68, 13 + 99 - 17 + 33, 103 + 58 - 67 + 34, 50 + 62 - 97 + 240).endVertex();
                worldRenderer.pos(n8, n5 + n6 + "  ".length(), 0.0).tex(1.0, 1.0).color(123 + 5 - 50 + 50, 91 + 119 - 207 + 125, 118 + 103 - 133 + 40, 63 + 174 - 189 + 207).endVertex();
                worldRenderer.pos(n8, n5 - "  ".length(), 0.0).tex(1.0, 0.0).color(116 + 65 - 103 + 50, 117 + 103 - 111 + 19, 56 + 94 - 57 + 35, 84 + 19 + 11 + 141).endVertex();
                worldRenderer.pos(n7, n5 - "  ".length(), 0.0).tex(0.0, 0.0).color(74 + 55 - 27 + 26, 121 + 10 - 60 + 57, 77 + 6 - 72 + 117, 191 + 99 - 133 + 98).endVertex();
                worldRenderer.pos(n7 + " ".length(), n5 + n6 + " ".length(), 0.0).tex(0.0, 1.0).color("".length(), "".length(), "".length(), 203 + 93 - 50 + 9).endVertex();
                worldRenderer.pos(n8 - " ".length(), n5 + n6 + " ".length(), 0.0).tex(1.0, 1.0).color("".length(), "".length(), "".length(), 128 + 171 - 180 + 136).endVertex();
                worldRenderer.pos(n8 - " ".length(), n5 - " ".length(), 0.0).tex(1.0, 0.0).color("".length(), "".length(), "".length(), 126 + 45 + 56 + 28).endVertex();
                worldRenderer.pos(n7 + " ".length(), n5 - " ".length(), 0.0).tex(0.0, 0.0).color("".length(), "".length(), "".length(), 24 + 98 - 115 + 248).endVertex();
                instance.draw();
                GlStateManager.enableTexture2D();
            }
            this.drawSlot(i, n, n5, n6, n3, n4);
            ++i;
        }
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    protected int getContentHeight() {
        return this.getSize() * this.slotHeight + this.headerPadding;
    }
    
    public int getSlotIndexFromScreenCoords(final int n, final int n2) {
        final int n3 = this.left + this.width / "  ".length() - this.getListWidth() / "  ".length();
        final int n4 = this.left + this.width / "  ".length() + this.getListWidth() / "  ".length();
        final int n5 = n2 - this.top - this.headerPadding + (int)this.amountScrolled - (0x8E ^ 0x8A);
        final int n6 = n5 / this.slotHeight;
        int n7;
        if (n < this.getScrollBarX() && n >= n3 && n <= n4 && n6 >= 0 && n5 >= 0 && n6 < this.getSize()) {
            n7 = n6;
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            n7 = -" ".length();
        }
        return n7;
    }
    
    public int getSlotHeight() {
        return this.slotHeight;
    }
    
    public void setShowSelectionBox(final boolean showSelectionBox) {
        this.showSelectionBox = showSelectionBox;
    }
    
    protected int getScrollBarX() {
        return this.width / "  ".length() + (0x48 ^ 0x34);
    }
    
    public void handleMouseInput() {
        if (this.isMouseYWithinSlotBounds(this.mouseY)) {
            if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
                final int n = (this.width - this.getListWidth()) / "  ".length();
                final int n2 = (this.width + this.getListWidth()) / "  ".length();
                final int n3 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - (0x4C ^ 0x48);
                final int selectedElement = n3 / this.slotHeight;
                if (selectedElement < this.getSize() && this.mouseX >= n && this.mouseX <= n2 && selectedElement >= 0 && n3 >= 0) {
                    this.elementClicked(selectedElement, "".length() != 0, this.mouseX, this.mouseY);
                    this.selectedElement = selectedElement;
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                else if (this.mouseX >= n && this.mouseX <= n2 && n3 < 0) {
                    this.func_148132_a(this.mouseX - n, this.mouseY - this.top + (int)this.amountScrolled - (0x49 ^ 0x4D));
                }
            }
            if (Mouse.isButtonDown("".length()) && this.getEnabled()) {
                if (this.initialClickY == -" ".length()) {
                    int n4 = " ".length();
                    if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
                        final int n5 = (this.width - this.getListWidth()) / "  ".length();
                        final int n6 = (this.width + this.getListWidth()) / "  ".length();
                        final int n7 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - (0x9A ^ 0x9E);
                        final int selectedElement2 = n7 / this.slotHeight;
                        if (selectedElement2 < this.getSize() && this.mouseX >= n5 && this.mouseX <= n6 && selectedElement2 >= 0 && n7 >= 0) {
                            int n8;
                            if (selectedElement2 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L) {
                                n8 = " ".length();
                                "".length();
                                if (2 <= 1) {
                                    throw null;
                                }
                            }
                            else {
                                n8 = "".length();
                            }
                            this.elementClicked(selectedElement2, n8 != 0, this.mouseX, this.mouseY);
                            this.selectedElement = selectedElement2;
                            this.lastClicked = Minecraft.getSystemTime();
                            "".length();
                            if (0 >= 3) {
                                throw null;
                            }
                        }
                        else if (this.mouseX >= n5 && this.mouseX <= n6 && n7 < 0) {
                            this.func_148132_a(this.mouseX - n5, this.mouseY - this.top + (int)this.amountScrolled - (0xA ^ 0xE));
                            n4 = "".length();
                        }
                        final int scrollBarX = this.getScrollBarX();
                        final int n9 = scrollBarX + (0xBD ^ 0xBB);
                        if (this.mouseX >= scrollBarX && this.mouseX <= n9) {
                            this.scrollMultiplier = -1.0f;
                            int n10 = this.func_148135_f();
                            if (n10 < " ".length()) {
                                n10 = " ".length();
                            }
                            this.scrollMultiplier /= (this.bottom - this.top - MathHelper.clamp_int((this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight(), 0xB7 ^ 0x97, this.bottom - this.top - (0xCF ^ 0xC7))) / n10;
                            "".length();
                            if (0 == 4) {
                                throw null;
                            }
                        }
                        else {
                            this.scrollMultiplier = 1.0f;
                        }
                        if (n4 != 0) {
                            this.initialClickY = this.mouseY;
                            "".length();
                            if (4 <= 0) {
                                throw null;
                            }
                        }
                        else {
                            this.initialClickY = -"  ".length();
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                        }
                    }
                    else {
                        this.initialClickY = -"  ".length();
                        "".length();
                        if (1 == 4) {
                            throw null;
                        }
                    }
                }
                else if (this.initialClickY >= 0) {
                    this.amountScrolled -= (this.mouseY - this.initialClickY) * this.scrollMultiplier;
                    this.initialClickY = this.mouseY;
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
            }
            else {
                this.initialClickY = -" ".length();
            }
            int n11 = Mouse.getEventDWheel();
            if (n11 != 0) {
                if (n11 > 0) {
                    n11 = -" ".length();
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
                else if (n11 < 0) {
                    n11 = " ".length();
                }
                this.amountScrolled += n11 * this.slotHeight / "  ".length();
            }
        }
    }
    
    public void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.scrollUpButtonID) {
                this.amountScrolled -= this.slotHeight * "  ".length() / "   ".length();
                this.initialClickY = -"  ".length();
                this.bindAmountScrolled();
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            else if (guiButton.id == this.scrollDownButtonID) {
                this.amountScrolled += this.slotHeight * "  ".length() / "   ".length();
                this.initialClickY = -"  ".length();
                this.bindAmountScrolled();
            }
        }
    }
    
    public int func_148135_f() {
        return Math.max("".length(), this.getContentHeight() - (this.bottom - this.top - (0xAB ^ 0xAF)));
    }
    
    protected void overlayBackground(final int n, final int n2, final int n3, final int n4) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        worldRenderer.begin(0x42 ^ 0x45, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(this.left, n2, 0.0).tex(0.0, n2 / 32.0f).color(0x4 ^ 0x44, 0x7D ^ 0x3D, 0x60 ^ 0x20, n4).endVertex();
        worldRenderer.pos(this.left + this.width, n2, 0.0).tex(this.width / 32.0f, n2 / 32.0f).color(0x4B ^ 0xB, 0x4F ^ 0xF, 0x5B ^ 0x1B, n4).endVertex();
        worldRenderer.pos(this.left + this.width, n, 0.0).tex(this.width / 32.0f, n / 32.0f).color(0x53 ^ 0x13, 0xE4 ^ 0xA4, 0xF5 ^ 0xB5, n3).endVertex();
        worldRenderer.pos(this.left, n, 0.0).tex(0.0, n / 32.0f).color(0x53 ^ 0x13, 0xE7 ^ 0xA7, 0xE8 ^ 0xA8, n3).endVertex();
        instance.draw();
    }
    
    protected abstract void elementClicked(final int p0, final boolean p1, final int p2, final int p3);
    
    public void scrollBy(final int n) {
        this.amountScrolled += n;
        this.bindAmountScrolled();
        this.initialClickY = -"  ".length();
    }
    
    public void setDimensions(final int n, final int height, final int top, final int bottom) {
        this.width = n;
        this.height = height;
        this.top = top;
        this.bottom = bottom;
        this.left = "".length();
        this.right = n;
    }
    
    protected void setHasListHeader(final boolean hasListHeader, final int headerPadding) {
        this.hasListHeader = hasListHeader;
        this.headerPadding = headerPadding;
        if (!hasListHeader) {
            this.headerPadding = "".length();
        }
    }
    
    protected void func_178040_a(final int n, final int n2, final int n3) {
    }
    
    protected void bindAmountScrolled() {
        this.amountScrolled = MathHelper.clamp_float(this.amountScrolled, 0.0f, this.func_148135_f());
    }
    
    public boolean isMouseYWithinSlotBounds(final int n) {
        if (n >= this.top && n <= this.bottom && this.mouseX >= this.left && this.mouseX <= this.right) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int getAmountScrolled() {
        return (int)this.amountScrolled;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected abstract void drawSlot(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    protected abstract void drawBackground();
    
    protected void func_148142_b(final int n, final int n2) {
    }
    
    public void registerScrollButtons(final int scrollUpButtonID, final int scrollDownButtonID) {
        this.scrollUpButtonID = scrollUpButtonID;
        this.scrollDownButtonID = scrollDownButtonID;
    }
    
    protected abstract boolean isSelected(final int p0);
    
    public void setSlotXBoundsFromLeft(final int left) {
        this.left = left;
        this.right = left + this.width;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float n) {
        if (this.field_178041_q) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.drawBackground();
            final int scrollBarX = this.getScrollBarX();
            final int n2 = scrollBarX + (0x52 ^ 0x54);
            this.bindAmountScrolled();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final float n3 = 32.0f;
            worldRenderer.begin(0xB2 ^ 0xB5, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldRenderer.pos(this.left, this.bottom, 0.0).tex(this.left / n3, (this.bottom + (int)this.amountScrolled) / n3).color(0xA2 ^ 0x82, 0x4C ^ 0x6C, 0x26 ^ 0x6, 24 + 10 + 40 + 181).endVertex();
            worldRenderer.pos(this.right, this.bottom, 0.0).tex(this.right / n3, (this.bottom + (int)this.amountScrolled) / n3).color(0x9B ^ 0xBB, 0x9F ^ 0xBF, 0x30 ^ 0x10, 24 + 218 - 7 + 20).endVertex();
            worldRenderer.pos(this.right, this.top, 0.0).tex(this.right / n3, (this.top + (int)this.amountScrolled) / n3).color(0xC ^ 0x2C, 0x88 ^ 0xA8, 0x7D ^ 0x5D, 49 + 89 - 20 + 137).endVertex();
            worldRenderer.pos(this.left, this.top, 0.0).tex(this.left / n3, (this.top + (int)this.amountScrolled) / n3).color(0x29 ^ 0x9, 0x4E ^ 0x6E, 0x86 ^ 0xA6, 215 + 34 - 175 + 181).endVertex();
            instance.draw();
            final int n4 = this.left + this.width / "  ".length() - this.getListWidth() / "  ".length() + "  ".length();
            final int n5 = this.top + (0x8 ^ 0xC) - (int)this.amountScrolled;
            if (this.hasListHeader) {
                this.drawListHeader(n4, n5, instance);
            }
            this.drawSelectionBox(n4, n5, mouseX, mouseY);
            GlStateManager.disableDepth();
            final int n6 = 0x17 ^ 0x13;
            this.overlayBackground("".length(), this.top, 197 + 113 - 128 + 73, 103 + 102 - 10 + 60);
            this.overlayBackground(this.bottom, this.height, 139 + 237 - 272 + 151, 204 + 24 - 14 + 41);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(370 + 102 - 360 + 658, 332 + 566 - 641 + 514, "".length(), " ".length());
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(576 + 4098 - 3628 + 6379);
            GlStateManager.disableTexture2D();
            worldRenderer.begin(0x7 ^ 0x0, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldRenderer.pos(this.left, this.top + n6, 0.0).tex(0.0, 1.0).color("".length(), "".length(), "".length(), "".length()).endVertex();
            worldRenderer.pos(this.right, this.top + n6, 0.0).tex(1.0, 1.0).color("".length(), "".length(), "".length(), "".length()).endVertex();
            worldRenderer.pos(this.right, this.top, 0.0).tex(1.0, 0.0).color("".length(), "".length(), "".length(), 117 + 185 - 236 + 189).endVertex();
            worldRenderer.pos(this.left, this.top, 0.0).tex(0.0, 0.0).color("".length(), "".length(), "".length(), 12 + 80 + 103 + 60).endVertex();
            instance.draw();
            worldRenderer.begin(0x1E ^ 0x19, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldRenderer.pos(this.left, this.bottom, 0.0).tex(0.0, 1.0).color("".length(), "".length(), "".length(), 86 + 92 - 35 + 112).endVertex();
            worldRenderer.pos(this.right, this.bottom, 0.0).tex(1.0, 1.0).color("".length(), "".length(), "".length(), 57 + 79 - 112 + 231).endVertex();
            worldRenderer.pos(this.right, this.bottom - n6, 0.0).tex(1.0, 0.0).color("".length(), "".length(), "".length(), "".length()).endVertex();
            worldRenderer.pos(this.left, this.bottom - n6, 0.0).tex(0.0, 0.0).color("".length(), "".length(), "".length(), "".length()).endVertex();
            instance.draw();
            final int func_148135_f = this.func_148135_f();
            if (func_148135_f > 0) {
                final int clamp_int = MathHelper.clamp_int((this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight(), 0x24 ^ 0x4, this.bottom - this.top - (0xB ^ 0x3));
                int top = (int)this.amountScrolled * (this.bottom - this.top - clamp_int) / func_148135_f + this.top;
                if (top < this.top) {
                    top = this.top;
                }
                worldRenderer.begin(0x99 ^ 0x9E, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(scrollBarX, this.bottom, 0.0).tex(0.0, 1.0).color("".length(), "".length(), "".length(), 172 + 13 + 60 + 10).endVertex();
                worldRenderer.pos(n2, this.bottom, 0.0).tex(1.0, 1.0).color("".length(), "".length(), "".length(), 126 + 124 - 196 + 201).endVertex();
                worldRenderer.pos(n2, this.top, 0.0).tex(1.0, 0.0).color("".length(), "".length(), "".length(), 218 + 55 - 240 + 222).endVertex();
                worldRenderer.pos(scrollBarX, this.top, 0.0).tex(0.0, 0.0).color("".length(), "".length(), "".length(), 175 + 109 - 95 + 66).endVertex();
                instance.draw();
                worldRenderer.begin(0x6D ^ 0x6A, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(scrollBarX, top + clamp_int, 0.0).tex(0.0, 1.0).color(43 + 108 - 50 + 27, 100 + 112 - 131 + 47, 13 + 86 - 81 + 110, 185 + 143 - 299 + 226).endVertex();
                worldRenderer.pos(n2, top + clamp_int, 0.0).tex(1.0, 1.0).color(102 + 15 - 58 + 69, 107 + 17 - 74 + 78, 94 + 29 - 21 + 26, 126 + 244 - 145 + 30).endVertex();
                worldRenderer.pos(n2, top, 0.0).tex(1.0, 0.0).color(121 + 98 - 111 + 20, 8 + 112 - 58 + 66, 18 + 72 - 38 + 76, 93 + 72 - 118 + 208).endVertex();
                worldRenderer.pos(scrollBarX, top, 0.0).tex(0.0, 0.0).color(19 + 81 - 74 + 102, 106 + 87 - 81 + 16, 38 + 83 - 106 + 113, 48 + 172 - 130 + 165).endVertex();
                instance.draw();
                worldRenderer.begin(0x9B ^ 0x9C, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(scrollBarX, top + clamp_int - " ".length(), 0.0).tex(0.0, 1.0).color(62 + 85 + 7 + 38, 76 + 23 - 42 + 135, 89 + 88 - 112 + 127, 6 + 107 + 56 + 86).endVertex();
                worldRenderer.pos(n2 - " ".length(), top + clamp_int - " ".length(), 0.0).tex(1.0, 1.0).color(122 + 131 - 163 + 102, 170 + 158 - 139 + 3, 31 + 11 + 84 + 66, 82 + 198 - 249 + 224).endVertex();
                worldRenderer.pos(n2 - " ".length(), top, 0.0).tex(1.0, 0.0).color(85 + 148 - 166 + 125, 9 + 74 + 24 + 85, 153 + 48 - 122 + 113, 229 + 243 - 268 + 51).endVertex();
                worldRenderer.pos(scrollBarX, top, 0.0).tex(0.0, 0.0).color(33 + 90 + 64 + 5, 45 + 82 + 38 + 27, 39 + 176 - 30 + 7, 177 + 82 - 146 + 142).endVertex();
                instance.draw();
            }
            this.func_148142_b(mouseX, mouseY);
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(1512 + 3054 - 3878 + 6736);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }
    
    protected void func_148132_a(final int n, final int n2) {
    }
    
    public int getListWidth() {
        return 39 + 46 + 14 + 121;
    }
}
