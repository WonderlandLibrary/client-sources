package com.client.glowclient.gui;

import net.minecraft.client.gui.inventory.*;
import com.client.glowclient.modules.render.*;
import com.client.glowclient.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.inventory.*;

public class GuiPeek extends GuiContainer implements Comparable<Ce>
{
    private final int d;
    public final PeekMod L;
    private final ItemStack A;
    public int B;
    public int b;
    
    public void drawScreen(final int n, final int n2, final float n3) {
        final int b = this.b;
        final int b2 = this.B;
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        final float n4 = 1.0f;
        GlStateManager.color(n4, n4, n4, PeekMod.M(this.L) ? 1.0f : 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager$SourceFactor.SRC_ALPHA, GlStateManager$DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager$SourceFactor.ONE, GlStateManager$DestFactor.ZERO);
        PeekMod.M().getTextureManager().bindTexture(PeekMod.I);
        final int n5 = b;
        final int n6 = b2;
        final int n7 = 0;
        ba.M(n5, n6, n7, n7, 176, 16, 500);
        ba.M(b, b2 + 16, 0, 16, 176, 54, 500);
        ba.M(b, b2 + 16 + 54, 0, 160, 176, 6, 500);
        GlStateManager.disableDepth();
        Ia.M(null, this.A.getDisplayName(), b + 8, b2 + 6, false, ga.k, 1.0);
        GlStateManager.enableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        GlStateManager.enableLighting();
        Slot slot = null;
        final int n8 = b + 8;
        final int n9 = b2 - 1;
        for (final Slot slot2 : this.inventorySlots.inventorySlots) {
            final int n10 = n8 + slot2.xPos;
            final int n11 = n9 + slot2.yPos;
            if (slot2.getHasStack()) {
                PeekMod.M().getRenderItem().zLevel = 501.0f;
                PeekMod.M().getRenderItem().renderItemAndEffectIntoGUI(slot2.getStack(), n10, n11);
                PeekMod.M().getRenderItem().renderItemOverlayIntoGUI(PeekMod.M().fontRenderer, slot2.getStack(), n10, n11, (String)null);
                PeekMod.M().getRenderItem().zLevel = 0.0f;
            }
            final int n12 = n10;
            final int n13 = n11;
            final int n14 = 16;
            if (this.isPointInRegion(n12, n13, n14, n14, n, n2)) {
                slot = slot2;
            }
        }
        GlStateManager.disableLighting();
        if (slot != null) {
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final boolean b3 = true;
            GlStateManager.colorMask(b3, b3, b3, false);
            final Slot slot3 = slot;
            final boolean b4 = true;
            final int n15 = n8 + slot.xPos;
            final int n16 = n9 + slot.yPos;
            final int n17 = n8 + slot.xPos + 16;
            final int n18 = n9 + slot.yPos + 16;
            final int n19 = -2130706433;
            this.drawGradientRect(n15, n16, n17, n18, n19, n19);
            final boolean b5 = true;
            GlStateManager.colorMask(b4, b5, b5, b5);
            final float n20 = 1.0f;
            GlStateManager.color(n20, n20, n20, n20);
            GlStateManager.pushMatrix();
            PeekMod.D(this.L, true);
            if (!(slot3.getStack().getItem() instanceof ItemAir)) {
                this.renderToolTip(slot.getStack(), n, n2);
            }
            PeekMod.D(this.L, false);
            GlStateManager.popMatrix();
            GlStateManager.enableDepth();
        }
        if (this.isPointInRegion(this.b, this.B, this.getWidth(), this.getHeight(), n, n2)) {
            PeekMod.M(this.L, true);
        }
        GlStateManager.disableBlend();
        final float n21 = 1.0f;
        GlStateManager.color(n21, n21, n21, n21);
    }
    
    public int compareTo(final GuiPeek guiPeek) {
        return Integer.compare(this.d, guiPeek.d);
    }
    
    public int getWidth() {
        return this.xSize;
    }
    
    public int getHeight() {
        return this.ySize;
    }
    
    public int getPosX() {
        return this.b;
    }
    
    public int getPosY() {
        return this.B;
    }
    
    public GuiPeek(final PeekMod l, final Container container, final ItemStack a, final int d) {
        final int ySize = 76;
        final int xSize = 176;
        final int b = 0;
        final int b2 = 0;
        this.L = l;
        super(container);
        this.b = b2;
        this.B = b;
        this.A = a;
        this.d = d;
        this.mc = PeekMod.M();
        this.fontRenderer = PeekMod.M().fontRenderer;
        this.width = PeekMod.M().displayWidth;
        this.height = PeekMod.M().displayHeight;
        this.xSize = xSize;
        this.ySize = ySize;
    }
    
    public void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
    }
    
    public ItemStack getParentShulker() {
        return this.A;
    }
    
    public int compareTo(final Object o) {
        return this.compareTo((GuiPeek)o);
    }
}
