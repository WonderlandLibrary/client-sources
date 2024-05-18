// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.base;

import org.lwjgl.opengl.GL11;
import java.awt.Point;
import com.krazzzzymonkey.catalyst.utils.visual.GLUtils;
import java.awt.Color;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;

public abstract class ComponentRenderer
{
    private /* synthetic */ ComponentType type;
    public /* synthetic */ Theme theme;
    protected static final /* synthetic */ Color tooltipColor;
    private static final /* synthetic */ int[] lllllI;
    
    public void drawExpanded(final int lllIIlIIIIlllIl, final int lllIIlIIIIlllII, final int lllIIlIIIIllIll, final boolean lllIIlIIIIllIlI, final int lllIIlIIIIllIII) {
        GLUtils.glColor(lllIIlIIIIllIII);
    }
    
    public void drawArrow(final int lllIIlIIIIIllIl, final int lllIIlIIIIIllII, final int lllIIlIIIIIlIll, final boolean lllIIlIIIIIlIlI, final int lllIIlIIIIIlIIl) {
        GLUtils.glColor(lllIIlIIIIIlIIl);
    }
    
    private static void llIlIlII() {
        (lllllI = new int[14])[0] = -" ".length();
        ComponentRenderer.lllllI[1] = (-(0xFFFF82FF & 0x7FCD) & (0xFFFFAFDF & 0x5EFD));
        ComponentRenderer.lllllI[2] = (0x94 ^ 0x9C);
        ComponentRenderer.lllllI[3] = " ".length();
        ComponentRenderer.lllllI[4] = (0x61 ^ 0x64);
        ComponentRenderer.lllllI[5] = "   ".length();
        ComponentRenderer.lllllI[6] = (0x17 ^ 0x13);
        ComponentRenderer.lllllI[7] = "  ".length();
        ComponentRenderer.lllllI[8] = (-1 & 0xFFFFFF);
        ComponentRenderer.lllllI[9] = (-(0xFFFFE475 & 0x7F9F) & (0xFFFFEFF6 & 0x7FFF));
        ComponentRenderer.lllllI[10] = (0xFFFFA7B6 & 0x5B4B);
        ComponentRenderer.lllllI[11] = (0xFFFFEFA3 & 0x135F);
        ComponentRenderer.lllllI[12] = (0xFFFF9DF9 & 0x6FE7);
        ComponentRenderer.lllllI[13] = (0xA0 ^ 0x91 ^ (0x10 ^ 0x26));
    }
    
    public abstract void doInteractions(final Component p0, final int p1, final int p2);
    
    public void renderToolTip(final Component lllIIIlllllIIlI, final String lllIIIllllIllII, final Point lllIIIlllllIIII) {
        GL11.glPushMatrix();
        GL11.glDisable(ComponentRenderer.lllllI[1]);
        final int lllIIIllllIllll = ComponentRenderer.lllllI[2];
        final int lllIIIllllIlllI = this.theme.getFontRenderer().getStringWidth(lllIIIllllIllII);
        GLUtils.glColor(ComponentRenderer.tooltipColor.brighter());
        this.drawRect((float)(lllIIIlllllIIII.x - ComponentRenderer.lllllI[3]), (float)(lllIIIlllllIIII.y - lllIIIllllIllll - ComponentRenderer.lllllI[3]), (float)(lllIIIlllllIIII.x + lllIIIllllIlllI + ComponentRenderer.lllllI[4]), (float)(lllIIIlllllIIII.y + this.theme.getFontRenderer().FONT_HEIGHT - lllIIIllllIllll + ComponentRenderer.lllllI[5]), 1.0f);
        GLUtils.glColor(ComponentRenderer.tooltipColor);
        this.drawFilledRect((float)lllIIIlllllIIII.x, (float)(lllIIIlllllIIII.y - lllIIIllllIllll), (float)(lllIIIlllllIIII.x + lllIIIllllIlllI + ComponentRenderer.lllllI[6]), (float)(lllIIIlllllIIII.y + this.theme.getFontRenderer().FONT_HEIGHT - lllIIIllllIllll + ComponentRenderer.lllllI[7]));
        this.theme.getFontRenderer().drawStringWithShadow(lllIIIllllIllII, (float)(lllIIIlllllIIII.x + ComponentRenderer.lllllI[7]), (float)(lllIIIlllllIIII.y - lllIIIllllIllll + ComponentRenderer.lllllI[7]), ComponentRenderer.lllllI[8]);
        "".length();
        GL11.glEnable(ComponentRenderer.lllllI[1]);
        GL11.glPopMatrix();
    }
    
    public abstract void drawComponent(final Component p0, final int p1, final int p2);
    
    public void drawFilledRect(final float lllIIIlllIlllll, final float lllIIIlllIllllI, final float lllIIIlllIlllIl, final float lllIIIlllIlllII) {
        GL11.glEnable(ComponentRenderer.lllllI[9]);
        GL11.glBlendFunc(ComponentRenderer.lllllI[10], ComponentRenderer.lllllI[11]);
        GL11.glDisable(ComponentRenderer.lllllI[12]);
        GL11.glBegin(ComponentRenderer.lllllI[13]);
        GL11.glVertex3f(lllIIIlllIlllll, lllIIIlllIlllII, 1.0f);
        GL11.glVertex3f(lllIIIlllIlllIl, lllIIIlllIlllII, 1.0f);
        GL11.glVertex3f(lllIIIlllIlllIl, lllIIIlllIllllI, 1.0f);
        GL11.glVertex3f(lllIIIlllIlllll, lllIIIlllIllllI, 1.0f);
        GL11.glEnd();
        GL11.glEnable(ComponentRenderer.lllllI[12]);
    }
    
    public void drawArrow(final int lllIIIlllllllII, final int lllIIIllllllIll, final int lllIIIllllllIlI, final boolean lllIIIllllllIIl) {
        this.drawArrow(lllIIIlllllllII, lllIIIllllllIll, lllIIIllllllIlI, lllIIIllllllIIl, ComponentRenderer.lllllI[0]);
    }
    
    public void drawPin(final int lllIIlIIIIlIlIl, final int lllIIlIIIIlIlII, final int lllIIlIIIIlIIll, final boolean lllIIlIIIIlIIlI, final int lllIIlIIIIlIIIl) {
        GLUtils.glColor(lllIIlIIIIlIIIl);
    }
    
    public void drawRect(final float lllIIIlllIlIlII, final float lllIIIlllIlIIll, final float lllIIIlllIIllII, final float lllIIIlllIIlIll, final float lllIIIlllIlIIII) {
        this.drawFilledRect(lllIIIlllIlIlII + lllIIIlllIlIIII, lllIIIlllIlIIll, lllIIIlllIIllII - lllIIIlllIlIIII, lllIIIlllIlIIll + lllIIIlllIlIIII);
        this.drawFilledRect(lllIIIlllIlIlII, lllIIIlllIlIIll, lllIIIlllIlIlII + lllIIIlllIlIIII, lllIIIlllIIlIll);
        this.drawFilledRect(lllIIIlllIIllII - lllIIIlllIlIIII, lllIIIlllIlIIll, lllIIIlllIIllII, lllIIIlllIIlIll);
        this.drawFilledRect(lllIIIlllIlIlII + lllIIIlllIlIIII, lllIIIlllIIlIll - lllIIIlllIlIIII, lllIIIlllIIllII - lllIIIlllIlIIII, lllIIIlllIIlIll);
    }
    
    public ComponentRenderer(final ComponentType lllIIlIIIlIIlII, final Theme lllIIlIIIlIIIII) {
        this.type = lllIIlIIIlIIlII;
        this.theme = lllIIlIIIlIIIII;
    }
    
    static {
        llIlIlII();
        tooltipColor = new Color(0.0f, 0.5f, 1.0f, 0.75f);
    }
}
