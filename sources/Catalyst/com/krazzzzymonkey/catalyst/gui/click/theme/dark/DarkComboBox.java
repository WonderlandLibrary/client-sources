// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.theme.dark;

import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;
import org.lwjgl.input.Mouse;
import java.awt.Point;
import com.krazzzzymonkey.catalyst.utils.visual.GLUtils;
import java.awt.Color;
import java.awt.Dimension;
import org.lwjgl.opengl.GL11;
import com.krazzzzymonkey.catalyst.gui.click.elements.ComboBox;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentRenderer;

public class DarkComboBox extends ComponentRenderer
{
    private static final /* synthetic */ int[] lIIIlIll;
    
    private static void llIllllIl() {
        (lIIIlIll = new int[11])[0] = (-(0xFFFFE75D & 0x38AF) & (0xFFFFAFFE & 0x7BEF));
        DarkComboBox.lIIIlIll[1] = (0xFFFFBFC6 & 0x4B7D);
        DarkComboBox.lIIIlIll[2] = (0xFFFFAFF9 & 0x5DE7);
        DarkComboBox.lIIIlIll[3] = " ".length();
        DarkComboBox.lIIIlIll[4] = ((0x59 ^ 0x5F) & ~(0xB0 ^ 0xB6));
        DarkComboBox.lIIIlIll[5] = "  ".length();
        DarkComboBox.lIIIlIll[6] = (0x43 ^ 0x4B);
        DarkComboBox.lIIIlIll[7] = (0x74 ^ 0x5C);
        DarkComboBox.lIIIlIll[8] = (0x51 ^ 0x56);
        DarkComboBox.lIIIlIll[9] = (0x46 ^ 0x42);
        DarkComboBox.lIIIlIll[10] = -" ".length();
    }
    
    private static boolean lllIIIIII(final int llIIllIIIIllIIl, final int llIIllIIIIllIII) {
        return llIIllIIIIllIIl >= llIIllIIIIllIII;
    }
    
    private static boolean llIllllll(final int llIIllIIIIIlllI) {
        return llIIllIIIIIlllI != 0;
    }
    
    private static boolean lllIIIIlI(final int llIIllIIIIlllIl, final int llIIllIIIIlllII) {
        return llIIllIIIIlllIl == llIIllIIIIlllII;
    }
    
    @Override
    public void drawComponent(final Component llIIllIIIllIlIl, final int llIIllIIIllIlII, final int llIIllIIIlllllI) {
        final ComboBox llIIllIIIllllIl = (ComboBox)llIIllIIIllIlIl;
        final Dimension llIIllIIIllllII = llIIllIIIllllIl.getDimension();
        GL11.glEnable(DarkComboBox.lIIIlIll[0]);
        GL11.glDisable(DarkComboBox.lIIIlIll[1]);
        GL11.glDisable(DarkComboBox.lIIIlIll[2]);
        GL11.glTranslated((double)(DarkComboBox.lIIIlIll[3] * llIIllIIIllllIl.getX()), (double)(DarkComboBox.lIIIlIll[3] * llIIllIIIllllIl.getY()), 0.0);
        int llIIllIIIlllIll = DarkComboBox.lIIIlIll[4];
        final int llIIllIIIlIllll = (Object)llIIllIIIllllIl.getElements();
        final short llIIllIIIlIlllI = (short)llIIllIIIlIllll.length;
        int llIIllIIIIlIlIl = DarkComboBox.lIIIlIll[4];
        while (llIlllllI(llIIllIIIIlIlIl, llIIllIIIlIlllI)) {
            final String llIIllIIlIIlIll = llIIllIIIlIllll[llIIllIIIIlIlIl];
            llIIllIIIlllIll = Math.max(llIIllIIIlllIll, this.theme.getFontRenderer().getStringWidth(llIIllIIlIIlIll));
            ++llIIllIIIIlIlIl;
            "".length();
            if ("  ".length() == -" ".length()) {
                return;
            }
        }
        int llIIllIIIlllIlI = DarkComboBox.lIIIlIll[4];
        if (llIllllll(llIIllIIIllllIl.isSelected() ? 1 : 0)) {
            final String[] llIIllIIlIIlIIl = llIIllIIIllllIl.getElements();
            int llIIllIIlIIlIlI = DarkComboBox.lIIIlIll[4];
            while (llIlllllI(llIIllIIlIIlIlI, llIIllIIlIIlIIl.length - DarkComboBox.lIIIlIll[3])) {
                llIIllIIIlllIlI += this.theme.getFontRenderer().FONT_HEIGHT + DarkComboBox.lIIIlIll[5];
                ++llIIllIIlIIlIlI;
                "".length();
                if (-" ".length() == "   ".length()) {
                    return;
                }
            }
            llIIllIIIlllIlI += 2;
        }
        llIIllIIIllllIl.setDimension(new Dimension(llIIllIIIlllIll + DarkComboBox.lIIIlIll[6] + this.theme.getFontRenderer().FONT_HEIGHT, this.theme.getFontRenderer().FONT_HEIGHT));
        GLUtils.glColor(new Color(DarkComboBox.lIIIlIll[5], DarkComboBox.lIIIlIll[5], DarkComboBox.lIIIlIll[5], DarkComboBox.lIIIlIll[7]));
        GL11.glBegin(DarkComboBox.lIIIlIll[8]);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glVertex2d((double)llIIllIIIllllII.width, 0.0);
        GL11.glVertex2d((double)llIIllIIIllllII.width, (double)(llIIllIIIllllII.height + llIIllIIIlllIlI));
        GL11.glVertex2d(0.0, (double)(llIIllIIIllllII.height + llIIllIIIlllIlI));
        GL11.glEnd();
        final Point llIIllIIIlllIIl = new Point(llIIllIIIllIlII, llIIllIIIlllllI);
        final float n = 0.0f;
        final float n2 = 0.0f;
        final float n3 = 0.0f;
        float n4;
        if (llIllllll(Mouse.isButtonDown(DarkComboBox.lIIIlIll[4]) ? 1 : 0)) {
            n4 = 0.5f;
            "".length();
            if (((0xBD ^ 0xA2) & ~(0x89 ^ 0x96)) != 0x0) {
                return;
            }
        }
        else {
            n4 = 0.3f;
        }
        GL11.glColor4f(n, n2, n3, n4);
        if (llIllllll(GLUtils.isHovered(llIIllIIIllllIl.getX(), llIIllIIIllllIl.getY(), llIIllIIIllllII.width, llIIllIIIllllII.height, llIIllIIIllIlII, llIIllIIIlllllI) ? 1 : 0)) {
            GL11.glBegin(DarkComboBox.lIIIlIll[8]);
            GL11.glVertex2d(0.0, 0.0);
            GL11.glVertex2d((double)llIIllIIIllllII.width, 0.0);
            GL11.glVertex2d((double)llIIllIIIllllII.width, (double)llIIllIIIllllII.height);
            GL11.glVertex2d(0.0, (double)llIIllIIIllllII.height);
            GL11.glEnd();
            "".length();
            if (-" ".length() >= ((0x1F ^ 0x29 ^ (0x87 ^ 0xAC)) & (161 + 6 - 58 + 60 ^ 152 + 0 - 111 + 139 ^ -" ".length()))) {
                return;
            }
        }
        else if (llIllllll(llIIllIIIllllIl.isSelected() ? 1 : 0) && lllIIIIII(llIIllIIIlllIIl.x, llIIllIIIllllIl.getX()) && lllIIIIIl(llIIllIIIlllIIl.x, llIIllIIIllllIl.getX() + llIIllIIIllllII.width)) {
            int llIIllIIlIIIllI = llIIllIIIllllII.height;
            final String[] llIIllIIlIIIlIl = llIIllIIIllllIl.getElements();
            int llIIllIIlIIIlll = DarkComboBox.lIIIlIll[4];
            while (llIlllllI(llIIllIIlIIIlll, llIIllIIlIIIlIl.length)) {
                if (lllIIIIlI(llIIllIIlIIIlll, llIIllIIIllllIl.getSelectedIndex())) {
                    "".length();
                    if (-"  ".length() > 0) {
                        return;
                    }
                }
                else {
                    int llIIllIIlIIlIII = this.theme.getFontRenderer().FONT_HEIGHT + DarkComboBox.lIIIlIll[5];
                    Label_0849: {
                        Label_0846: {
                            if (lllIIIIll(llIIllIIIllllIl.getSelectedIndex())) {
                                if (lllIIIIlI(llIIllIIlIIIlll, DarkComboBox.lIIIlIll[3])) {
                                    "".length();
                                    if (-(123 + 1 + 20 + 46 ^ 109 + 96 - 172 + 153) > 0) {
                                        return;
                                    }
                                    break Label_0846;
                                }
                            }
                            else if (!llIllllll(llIIllIIlIIIlll)) {
                                break Label_0846;
                            }
                            if (lllIIIIlI(llIIllIIIllllIl.getSelectedIndex(), llIIllIIlIIIlIl.length - DarkComboBox.lIIIlIll[3])) {
                                if (!lllIIIIlI(llIIllIIlIIIlll, llIIllIIlIIIlIl.length - DarkComboBox.lIIIlIll[5])) {
                                    break Label_0849;
                                }
                                "".length();
                                if (null != null) {
                                    return;
                                }
                            }
                            else if (!lllIIIIlI(llIIllIIlIIIlll, llIIllIIlIIIlIl.length - DarkComboBox.lIIIlIll[3])) {
                                break Label_0849;
                            }
                        }
                        ++llIIllIIlIIlIII;
                    }
                    if (lllIIIIII(llIIllIIIlllIIl.y, llIIllIIIllllIl.getY() + llIIllIIlIIIllI) && lllIIIIIl(llIIllIIIlllIIl.y, llIIllIIIllllIl.getY() + llIIllIIlIIIllI + llIIllIIlIIlIII)) {
                        GL11.glBegin(DarkComboBox.lIIIlIll[8]);
                        GL11.glVertex2d(0.0, (double)llIIllIIlIIIllI);
                        GL11.glVertex2d(0.0, (double)(llIIllIIlIIIllI + llIIllIIlIIlIII));
                        GL11.glVertex2d((double)llIIllIIIllllII.width, (double)(llIIllIIlIIIllI + llIIllIIlIIlIII));
                        GL11.glVertex2d((double)llIIllIIIllllII.width, (double)llIIllIIlIIIllI);
                        GL11.glEnd();
                        "".length();
                        if ("   ".length() == -" ".length()) {
                            return;
                        }
                        break;
                    }
                    else {
                        llIIllIIlIIIllI += llIIllIIlIIlIII;
                    }
                }
                ++llIIllIIlIIIlll;
                "".length();
                if (-" ".length() >= " ".length()) {
                    return;
                }
            }
        }
        final int llIIllIIIlllIII = this.theme.getFontRenderer().FONT_HEIGHT + DarkComboBox.lIIIlIll[9];
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.3f);
        GL11.glBegin(DarkComboBox.lIIIlIll[9]);
        if (llIllllll(llIIllIIIllllIl.isSelected() ? 1 : 0)) {
            GL11.glVertex2d(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9] + llIIllIIIlllIII / 2.0, llIIllIIIlllIII / 3.0);
            GL11.glVertex2d(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9] + llIIllIIIlllIII / 3.0, 2.0 * llIIllIIIlllIII / 3.0);
            GL11.glVertex2d(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9] + 2.0 * llIIllIIIlllIII / 3.0, 2.0 * llIIllIIIlllIII / 3.0);
            "".length();
            if (((0x63 ^ 0x77 ^ (0x6B ^ 0x78)) & (0x19 ^ 0x1E ^ ((0xB ^ 0x1B) & ~(0x9A ^ 0x8A)) ^ -" ".length())) < 0) {
                return;
            }
        }
        else {
            GL11.glVertex2d(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9] + llIIllIIIlllIII / 3.0, llIIllIIIlllIII / 3.0);
            GL11.glVertex2d(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9] + 2.0 * llIIllIIIlllIII / 3.0, llIIllIIIlllIII / 3.0);
            GL11.glVertex2d(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9] + llIIllIIIlllIII / 2.0, 2.0 * llIIllIIIlllIII / 3.0);
        }
        GL11.glEnd();
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        if (llIllllll(llIIllIIIllllIl.isSelected() ? 1 : 0)) {
            GL11.glBegin(DarkComboBox.lIIIlIll[3]);
            GL11.glVertex2d(2.0, (double)llIIllIIIllllII.height);
            GL11.glVertex2d((double)(llIIllIIIllllII.width - DarkComboBox.lIIIlIll[5]), (double)llIIllIIIllllII.height);
            GL11.glEnd();
        }
        GL11.glBegin(DarkComboBox.lIIIlIll[3]);
        GL11.glVertex2d((double)(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9]), 2.0);
        GL11.glVertex2d((double)(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9]), (double)(llIIllIIIllllII.height - DarkComboBox.lIIIlIll[5]));
        GL11.glEnd();
        GL11.glBegin(DarkComboBox.lIIIlIll[5]);
        if (llIllllll(llIIllIIIllllIl.isSelected() ? 1 : 0)) {
            GL11.glVertex2d(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9] + llIIllIIIlllIII / 2.0, llIIllIIIlllIII / 3.0);
            GL11.glVertex2d(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9] + llIIllIIIlllIII / 3.0, 2.0 * llIIllIIIlllIII / 3.0);
            GL11.glVertex2d(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9] + 2.0 * llIIllIIIlllIII / 3.0, 2.0 * llIIllIIIlllIII / 3.0);
            "".length();
            if (" ".length() < " ".length()) {
                return;
            }
        }
        else {
            GL11.glVertex2d(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9] + llIIllIIIlllIII / 3.0, llIIllIIIlllIII / 3.0);
            GL11.glVertex2d(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9] + 2.0 * llIIllIIIlllIII / 3.0, llIIllIIIlllIII / 3.0);
            GL11.glVertex2d(llIIllIIIlllIll + DarkComboBox.lIIIlIll[9] + llIIllIIIlllIII / 2.0, 2.0 * llIIllIIIlllIII / 3.0);
        }
        GL11.glEnd();
        GL11.glEnable(DarkComboBox.lIIIlIll[2]);
        final String llIIllIIIllIlll = llIIllIIIllllIl.getSelectedElement();
        this.theme.getFontRenderer().drawString(llIIllIIIllIlll, DarkComboBox.lIIIlIll[5], llIIllIIIllllII.height / DarkComboBox.lIIIlIll[5] - this.theme.getFontRenderer().FONT_HEIGHT / DarkComboBox.lIIIlIll[9], DarkComboBox.lIIIlIll[10]);
        "".length();
        if (llIllllll(llIIllIIIllllIl.isSelected() ? 1 : 0)) {
            int llIIllIIlIIIIll = llIIllIIIllllII.height + DarkComboBox.lIIIlIll[5];
            final String[] llIIllIIlIIIIlI = llIIllIIIllllIl.getElements();
            int llIIllIIlIIIlII = DarkComboBox.lIIIlIll[4];
            while (llIlllllI(llIIllIIlIIIlII, llIIllIIlIIIIlI.length)) {
                if (lllIIIIlI(llIIllIIlIIIlII, llIIllIIIllllIl.getSelectedIndex())) {
                    "".length();
                    if (" ".length() != " ".length()) {
                        return;
                    }
                }
                else {
                    this.theme.getFontRenderer().drawString(llIIllIIlIIIIlI[llIIllIIlIIIlII], DarkComboBox.lIIIlIll[5], llIIllIIlIIIIll, DarkComboBox.lIIIlIll[10]);
                    "".length();
                    llIIllIIlIIIIll += this.theme.getFontRenderer().FONT_HEIGHT + DarkComboBox.lIIIlIll[5];
                }
                ++llIIllIIlIIIlII;
                "".length();
                if (((0x58 ^ 0xD) & ~(0xF7 ^ 0xA2)) > (0x11 ^ 0x15)) {
                    return;
                }
            }
        }
        GL11.glEnable(DarkComboBox.lIIIlIll[1]);
        GL11.glDisable(DarkComboBox.lIIIlIll[0]);
        GL11.glTranslated((double)(DarkComboBox.lIIIlIll[10] * llIIllIIIllllIl.getX()), (double)(DarkComboBox.lIIIlIll[10] * llIIllIIIllllIl.getY()), 0.0);
    }
    
    private static boolean lllIIIIll(final int llIIllIIIIIllII) {
        return llIIllIIIIIllII == 0;
    }
    
    private static boolean llIlllllI(final int llIIllIIIIlIlIl, final int llIIllIIIIlIlII) {
        return llIIllIIIIlIlIl < llIIllIIIIlIlII;
    }
    
    static {
        llIllllIl();
    }
    
    @Override
    public void doInteractions(final Component llIIllIIIlIIIIl, final int llIIllIIIlIIlII, final int llIIllIIIlIIIll) {
        final ComboBox llIIllIIIlIIIlI = (ComboBox)llIIllIIIlIIIIl;
    }
    
    private static boolean lllIIIIIl(final int llIIllIIIIlIIIl, final int llIIllIIIIlIIII) {
        return llIIllIIIIlIIIl <= llIIllIIIIlIIII;
    }
    
    public DarkComboBox(final Theme llIIllIIlIlllII) {
        super(ComponentType.COMBO_BOX, llIIllIIlIlllII);
    }
}
