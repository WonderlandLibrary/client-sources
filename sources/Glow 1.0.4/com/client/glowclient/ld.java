package com.client.glowclient;

import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;

public class Ld extends GuiSlot
{
    private final String d;
    private final String L;
    public int A;
    private final Minecraft B;
    private final rA b;
    
    public boolean isSelected(final int n) {
        return n == this.A;
    }
    
    public void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final float n7) {
        final Bb bb = this.b.b.get(n);
        final ItemStack b = bb.B;
        final String d = bb.D();
        final String m = bb.M();
        final String i = bb.M(this.L, this.d);
        WC.M(this.B.renderEngine, b, n2, n3);
        this.b.drawString(this.B.fontRenderer, d, n2 + 24, n3 + 6, 16777215);
        final rA b2 = this.b;
        final FontRenderer fontRenderer = this.B.fontRenderer;
        final int n8 = n2 + 215;
        final FontRenderer fontRenderer2 = this.B.fontRenderer;
        final String s = m;
        b2.drawString(fontRenderer, s, n8 - fontRenderer2.getStringWidth(s), n3 + 1, 16777215);
        final rA b3 = this.b;
        final FontRenderer fontRenderer3 = this.B.fontRenderer;
        final int n9 = n2 + 215;
        final FontRenderer fontRenderer4 = this.B.fontRenderer;
        final String s2 = i;
        b3.drawString(fontRenderer3, s2, n9 - fontRenderer4.getStringWidth(s2), n3 + 11, 16777215);
        if (n5 > n2 && n6 > n3 && n5 <= n2 + 18 && n6 <= n3 + 18) {
            this.b.renderToolTip(b, n5, n6);
            GlStateManager.disableLighting();
        }
    }
    
    public void drawContainerBackground(final Tessellator tessellator) {
    }
    
    public int getSize() {
        return this.b.b.size();
    }
    
    public int getScrollBarX() {
        return this.width / 2 + this.getListWidth() / 2 + 2;
    }
    
    public void elementClicked(final int a, final boolean b, final int n, final int n2) {
        this.A = a;
    }
    
    public Ld(final rA b) {
        final String s = "schematica.gui.materialavailable";
        super(Minecraft.getMinecraft(), b.width, b.height, 16, b.height - 34, 24);
        this.B = Minecraft.getMinecraft();
        this.L = I18n.format(s, (Object[])new Object[0]);
        this.d = I18n.format("schematica.gui.materialmissing", (Object[])new Object[0]);
        final int a = -1;
        this.A = -1;
        this.b = b;
        this.A = a;
    }
    
    public void drawBackground() {
    }
}
