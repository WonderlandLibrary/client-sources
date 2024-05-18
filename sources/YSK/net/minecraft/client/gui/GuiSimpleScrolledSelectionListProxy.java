package net.minecraft.client.gui;

import net.minecraft.realms.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;

public class GuiSimpleScrolledSelectionListProxy extends GuiSlot
{
    private final RealmsSimpleScrolledSelectionList field_178050_u;
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
    }
    
    @Override
    protected void drawBackground() {
        this.field_178050_u.renderBackground();
    }
    
    public int getMouseX() {
        return super.mouseX;
    }
    
    @Override
    protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        this.field_178050_u.selectItem(n, b, n2, n3);
    }
    
    @Override
    protected int getScrollBarX() {
        return this.field_178050_u.getScrollbarPosition();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float n) {
        if (this.field_178041_q) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.drawBackground();
            final int scrollBarX = this.getScrollBarX();
            final int n2 = scrollBarX + (0x5F ^ 0x59);
            this.bindAmountScrolled();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final int n3 = this.left + this.width / "  ".length() - this.getListWidth() / "  ".length() + "  ".length();
            final int n4 = this.top + (0xA0 ^ 0xA4) - (int)this.amountScrolled;
            if (this.hasListHeader) {
                this.drawListHeader(n3, n4, instance);
            }
            this.drawSelectionBox(n3, n4, mouseX, mouseY);
            GlStateManager.disableDepth();
            this.overlayBackground("".length(), this.top, 220 + 183 - 173 + 25, 247 + 190 - 259 + 77);
            this.overlayBackground(this.bottom, this.height, 181 + 160 - 269 + 183, 224 + 11 - 138 + 158);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(733 + 187 - 667 + 517, 345 + 686 - 544 + 284, "".length(), " ".length());
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(2627 + 7343 - 7589 + 5044);
            GlStateManager.disableTexture2D();
            final int func_148135_f = this.func_148135_f();
            if (func_148135_f > 0) {
                final int clamp_int = MathHelper.clamp_int((this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight(), 0x21 ^ 0x1, this.bottom - this.top - (0xA0 ^ 0xA8));
                int top = (int)this.amountScrolled * (this.bottom - this.top - clamp_int) / func_148135_f + this.top;
                if (top < this.top) {
                    top = this.top;
                }
                worldRenderer.begin(0x6D ^ 0x6A, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(scrollBarX, this.bottom, 0.0).tex(0.0, 1.0).color("".length(), "".length(), "".length(), 69 + 95 - 42 + 133).endVertex();
                worldRenderer.pos(n2, this.bottom, 0.0).tex(1.0, 1.0).color("".length(), "".length(), "".length(), 209 + 170 - 173 + 49).endVertex();
                worldRenderer.pos(n2, this.top, 0.0).tex(1.0, 0.0).color("".length(), "".length(), "".length(), 161 + 174 - 248 + 168).endVertex();
                worldRenderer.pos(scrollBarX, this.top, 0.0).tex(0.0, 0.0).color("".length(), "".length(), "".length(), 217 + 240 - 427 + 225).endVertex();
                instance.draw();
                worldRenderer.begin(0x8 ^ 0xF, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(scrollBarX, top + clamp_int, 0.0).tex(0.0, 1.0).color(121 + 106 - 113 + 14, 64 + 33 - 6 + 37, 24 + 77 + 26 + 1, 237 + 12 - 140 + 146).endVertex();
                worldRenderer.pos(n2, top + clamp_int, 0.0).tex(1.0, 1.0).color(112 + 33 - 116 + 99, 45 + 50 + 22 + 11, 113 + 65 - 144 + 94, 180 + 101 - 240 + 214).endVertex();
                worldRenderer.pos(n2, top, 0.0).tex(1.0, 0.0).color(98 + 17 - 72 + 85, 1 + 94 - 68 + 101, 70 + 103 - 171 + 126, 167 + 239 - 246 + 95).endVertex();
                worldRenderer.pos(scrollBarX, top, 0.0).tex(0.0, 0.0).color(100 + 92 - 79 + 15, 68 + 124 - 79 + 15, 95 + 21 - 15 + 27, 55 + 84 - 133 + 249).endVertex();
                instance.draw();
                worldRenderer.begin(0xA5 ^ 0xA2, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(scrollBarX, top + clamp_int - " ".length(), 0.0).tex(0.0, 1.0).color(86 + 6 - 10 + 110, 83 + 80 - 151 + 180, 27 + 172 - 103 + 96, 22 + 70 + 120 + 43).endVertex();
                worldRenderer.pos(n2 - " ".length(), top + clamp_int - " ".length(), 0.0).tex(1.0, 1.0).color(50 + 176 - 173 + 139, 179 + 181 - 182 + 14, 129 + 93 - 157 + 127, 199 + 101 - 248 + 203).endVertex();
                worldRenderer.pos(n2 - " ".length(), top, 0.0).tex(1.0, 0.0).color(189 + 62 - 240 + 181, 50 + 149 - 100 + 93, 3 + 69 + 30 + 90, 104 + 79 - 62 + 134).endVertex();
                worldRenderer.pos(scrollBarX, top, 0.0).tex(0.0, 0.0).color(43 + 86 - 128 + 191, 165 + 41 - 110 + 96, 175 + 138 - 139 + 18, 78 + 13 - 51 + 215).endVertex();
                instance.draw();
            }
            this.func_148142_b(mouseX, mouseY);
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(4479 + 928 + 1000 + 1017);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }
    
    @Override
    protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.field_178050_u.renderItem(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return this.field_178050_u.isSelectedItem(n);
    }
    
    public GuiSimpleScrolledSelectionListProxy(final RealmsSimpleScrolledSelectionList field_178050_u, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(Minecraft.getMinecraft(), n, n2, n3, n4, n5);
        this.field_178050_u = field_178050_u;
    }
    
    public int getWidth() {
        return super.width;
    }
    
    @Override
    protected int getSize() {
        return this.field_178050_u.getItemCount();
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getMouseY() {
        return super.mouseY;
    }
    
    @Override
    protected int getContentHeight() {
        return this.field_178050_u.getMaxPosition();
    }
}
