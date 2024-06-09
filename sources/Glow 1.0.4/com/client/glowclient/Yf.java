package com.client.glowclient;

import net.minecraft.client.renderer.vertex.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.other.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class Yf extends qe
{
    public Yf(final int n, final int n2, final String s, final boolean b, final boolean b2) {
        super(n, n2, s, b, b2);
    }
    
    public static void M(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, float n7, float n8) {
        n7 = 1.0f / n7;
        n8 = 1.0f / n8;
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer;
        final BufferBuilder bufferBuilder2;
        final BufferBuilder bufferBuilder = bufferBuilder2 = (buffer = instance.getBuffer());
        bufferBuilder2.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder2.pos((double)n, (double)(n2 + n6), 0.0).tex((double)(n3 * n7), (double)((n4 + n6) * n8)).endVertex();
        bufferBuilder.pos((double)(n + n5), (double)(n2 + n6), 0.0).tex((double)((n3 + n5) * n7), (double)((n4 + n6) * n8)).endVertex();
        buffer.pos((double)(n + n5), (double)n2, 0.0).tex((double)((n3 + n5) * n7), (double)(n4 * n8)).endVertex();
        buffer.pos((double)n, (double)n2, 0.0).tex((double)(n3 * n7), (double)(n4 * n8)).endVertex();
        instance.draw();
    }
    
    @Override
    public void M(int n, int n2, final boolean b) {
        this.D(n, n2);
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        final int m = HUD.red.M();
        final int i = HUD.green.M();
        final int j = HUD.blue.M();
        if (this.e) {
            final int n3 = n2;
            this.D(n - this.A);
            this.M(n3 - this.H);
        }
        this.M(this.E() - this.b * 8);
        if (this.E() + this.k() < 15) {
            this.M(-this.k() + 15);
        }
        if (this.E() > scaledResolution.getScaledHeight() - 15) {
            this.M(scaledResolution.getScaledHeight() - 13);
        }
        GL11.glPushMatrix();
        GL11.glPushAttrib(1284);
        this.d = this.E();
        if (b) {
            ba.M(this.D(), this.E(), this.e(), 13, ga.M(m, i, j, 225));
            Ia.M(HUD.F, this.M(), this.D() + 2, this.E() + 2, true, ga.G, 1.0);
            if (!this.B) {
                n = (this.K.M() ? ga.H : ga.M);
                final int n4 = this.D() + 89;
                final int n5 = this.E() + 2;
                final int n6 = 9;
                ba.M(n4, n5, n6, n6, n);
            }
        }
        if (this.I.M()) {
            final int d = this.D();
            final int n7 = this.E() + 13;
            final int e = this.e();
            final int n8 = this.A() + 4 - 13;
            final int n9 = 0;
            ba.M(d, n7, e, n8, ga.M(n9, n9, n9, 150));
        }
        if (this.I.M()) {
            final ResourceLocation resourceLocation = new ResourceLocation("textures/gui/container/shulker_box.png");
            final float n10 = 1.0f;
            GL11.glColor4f(n10, n10, n10, n10);
            GlStateManager.enableAlpha();
            Wrapper.mc.getTextureManager().bindTexture(resourceLocation);
            final ea ea = null;
            ba.M(this.D() - 40, this.E() + 87, 0, 160, 176, 6, 0);
            final int n11 = this.D() - 40;
            final int n12 = this.E() + 13;
            final float n13 = 0.0f;
            final int n14 = 176;
            final int n15 = 76;
            final float n16 = 256.0f;
            Gui.drawModalRectWithCustomSizedTexture(n11, n12, n13, n13, n14, n15, n16, n16);
            Ia.M(ea, "Inventory", this.D() - 33, this.E() + 18, false, ga.k, 1.0);
            final NonNullList inventory;
            n2 = ((List)(inventory = Wrapper.mc.player.inventoryContainer.getInventory())).size() / 9 + ((((List)inventory).size() % 9 != 0) ? 1 : 0);
            final int size = ((List)inventory).size();
            int n17;
            int k = n17 = 0;
            while (k < size) {
                final int n18 = this.D() + 8 - 40;
                final int n19 = this.E() - 114;
                GlStateManager.clear(256);
                if (n17 != 5 && n17 != 6 && n17 != 7 && n17 != 8 && n17 != 36 && n17 != 37 && n17 != 38 && n17 != 39 && n17 != 40 && n17 != 41 && n17 != 42 && n17 != 43 && n17 != 44 && n17 != 45) {
                    this.M(Wrapper.mc.getRenderItem(), ((List<ItemStack>)inventory).get(n17), n17 % 9 * 18 + n18, n2 * 18 + (n17 / 9 + 1) * 18 + n19 + 1);
                }
                GlStateManager.disableLighting();
                k = ++n17;
            }
        }
        final float n20 = 0.0f;
        GL11.glColor3f(n20, n20, n20);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    private void M(final RenderItem renderItem, final ItemStack itemStack, final int n, final int n2) {
        FontRenderer fontRenderer;
        if ((fontRenderer = itemStack.getItem().getFontRenderer(itemStack)) == null) {
            fontRenderer = Wrapper.mc.fontRenderer;
        }
        GlStateManager.enableDepth();
        renderItem.zLevel = 120.0f;
        RenderHelper.enableGUIStandardItemLighting();
        renderItem.renderItemAndEffectIntoGUI(itemStack, n, n2);
        final String s = (itemStack.getCount() == 1) ? "" : String.valueOf(itemStack.getCount());
        final float zLevel = 0.0f;
        renderItem.renderItemOverlayIntoGUI(fontRenderer, itemStack, n, n2, s);
        renderItem.zLevel = zLevel;
    }
}
