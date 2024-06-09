// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.theme.dark;

import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;
import java.awt.Color;
import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import com.krazzzzymonkey.catalyst.utils.visual.ColorUtils;
import com.krazzzzymonkey.catalyst.module.modules.gui.ClickGui;
import com.krazzzzymonkey.catalyst.gui.click.elements.ExpandingButton;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentRenderer;

public class DarkExpandingButton extends ComponentRenderer
{
    private static final /* synthetic */ int[] llIIIlII;
    
    private static void lIIlIIlIll() {
        (llIIIlII = new int[10])[0] = 66 + 26 + 101 + 62;
        DarkExpandingButton.llIIIlII[1] = ((0x62 ^ 0x6F ^ (0x12 ^ 0x34)) & (20 + 54 + 100 + 12 ^ 82 + 94 - 133 + 102 ^ -" ".length()));
        DarkExpandingButton.llIIIlII[2] = " ".length();
        DarkExpandingButton.llIIIlII[3] = (0x61 ^ 0x6F);
        DarkExpandingButton.llIIIlII[4] = (0xB ^ 0x58 ^ (0xA6 ^ 0xAA));
        DarkExpandingButton.llIIIlII[5] = "  ".length();
        DarkExpandingButton.llIIIlII[6] = (0x83 ^ 0x87);
        DarkExpandingButton.llIIIlII[7] = (0x33 ^ 0x41 ^ (0xC5 ^ 0xB8));
        DarkExpandingButton.llIIIlII[8] = "   ".length();
        DarkExpandingButton.llIIIlII[9] = (0xAC ^ 0xA1);
    }
    
    @Override
    public void doInteractions(final Component lIlllllIIIIlllI, final int lIlllllIIIIllIl, final int lIlllllIIIIllII) {
    }
    
    static {
        lIIlIIlIll();
    }
    
    @Override
    public void drawComponent(final Component lIlllllIIIllllI, final int lIlllllIIIlllIl, final int lIlllllIIIlllII) {
        final ExpandingButton lIlllllIIIllIll = (ExpandingButton)lIlllllIIIllllI;
        final String lIlllllIIIllIlI = lIlllllIIIllIll.getText();
        int n;
        if (lIIlIIllII(ClickGui.isLight ? 1 : 0)) {
            n = ColorUtils.color(DarkExpandingButton.llIIIlII[0], DarkExpandingButton.llIIIlII[0], DarkExpandingButton.llIIIlII[0], DarkExpandingButton.llIIIlII[0]);
            "".length();
            if (((0x55 ^ 0x76) & ~(0xA5 ^ 0x86)) == "   ".length()) {
                return;
            }
        }
        else {
            n = ColorUtils.color(DarkExpandingButton.llIIIlII[1], DarkExpandingButton.llIIIlII[1], DarkExpandingButton.llIIIlII[1], DarkExpandingButton.llIIIlII[0]);
        }
        final int lIlllllIIIllIIl = n;
        int n2;
        if (lIIlIIllII(ClickGui.isLight ? 1 : 0)) {
            n2 = ColorUtils.color(DarkExpandingButton.llIIIlII[1], DarkExpandingButton.llIIIlII[1], DarkExpandingButton.llIIIlII[1], DarkExpandingButton.llIIIlII[0]);
            "".length();
            if ((0x79 ^ 0x5A ^ (0x1D ^ 0x3A)) <= 0) {
                return;
            }
        }
        else {
            n2 = ColorUtils.color(DarkExpandingButton.llIIIlII[0], DarkExpandingButton.llIIIlII[0], DarkExpandingButton.llIIIlII[0], DarkExpandingButton.llIIIlII[0]);
        }
        final int lIlllllIIIllIII = n2;
        if (lIIlIIllII(lIlllllIIIllIll.isEnabled() ? 1 : 0)) {
            RenderUtils.drawRect((float)lIlllllIIIllIll.getX(), (float)lIlllllIIIllIll.getY(), (float)(lIlllllIIIllIll.getX() + lIlllllIIIllIll.getDimension().width - DarkExpandingButton.llIIIlII[2]), (float)(lIlllllIIIllIll.getY() + DarkExpandingButton.llIIIlII[3]), lIlllllIIIllIIl);
            RenderUtils.drawRect((float)lIlllllIIIllIll.getX(), (float)lIlllllIIIllIll.getY(), (float)(lIlllllIIIllIll.getX() + lIlllllIIIllIll.getDimension().width - DarkExpandingButton.llIIIlII[4]), (float)(lIlllllIIIllIll.getY() + DarkExpandingButton.llIIIlII[3]), ClickGui.getColor());
            this.theme.fontRenderer.drawString(lIlllllIIIllIlI, lIlllllIIIllIll.getX() + lIlllllIIIllIll.getDimension().width / DarkExpandingButton.llIIIlII[5] - this.theme.fontRenderer.getStringWidth(lIlllllIIIllIlI) / DarkExpandingButton.llIIIlII[5], lIlllllIIIllIll.getY() + (lIlllllIIIllIll.getButtonHeight() / DarkExpandingButton.llIIIlII[5] - this.theme.fontRenderer.FONT_HEIGHT / DarkExpandingButton.llIIIlII[6]) - DarkExpandingButton.llIIIlII[2], ClickGui.getColor());
            "".length();
            "".length();
            if ((0x6F ^ 0x6A) == 0x0) {
                return;
            }
        }
        else {
            RenderUtils.drawRect((float)lIlllllIIIllIll.getX(), (float)lIlllllIIIllIll.getY(), (float)(lIlllllIIIllIll.getX() + lIlllllIIIllIll.getDimension().width - DarkExpandingButton.llIIIlII[2]), (float)(lIlllllIIIllIll.getY() + DarkExpandingButton.llIIIlII[3]), lIlllllIIIllIIl);
            this.theme.fontRenderer.drawString(lIlllllIIIllIlI, lIlllllIIIllIll.getX() + lIlllllIIIllIll.getDimension().width / DarkExpandingButton.llIIIlII[5] - this.theme.fontRenderer.getStringWidth(lIlllllIIIllIlI) / DarkExpandingButton.llIIIlII[5], lIlllllIIIllIll.getY() + (lIlllllIIIllIll.getButtonHeight() / DarkExpandingButton.llIIIlII[5] - this.theme.fontRenderer.FONT_HEIGHT / DarkExpandingButton.llIIIlII[6]) - DarkExpandingButton.llIIIlII[2], lIlllllIIIllIII);
            "".length();
        }
        if (lIIlIIllII(lIlllllIIIllIll.isMaximized() ? 1 : 0)) {
            RenderUtils.drawRect((float)lIlllllIIIllIll.getX(), (float)(lIlllllIIIllIll.getY() + lIlllllIIIllIll.getButtonHeight() - DarkExpandingButton.llIIIlII[2]), (float)(lIlllllIIIllIll.getX() + lIlllllIIIllIll.getDimension().width), (float)(lIlllllIIIllIll.getY() + lIlllllIIIllIll.getButtonHeight()), ClickGui.getColor());
            RenderUtils.drawRect((float)lIlllllIIIllIll.getX(), (float)(lIlllllIIIllIll.getY() + lIlllllIIIllIll.getDimension().height - DarkExpandingButton.llIIIlII[2]), (float)(lIlllllIIIllIll.getX() + lIlllllIIIllIll.getDimension().width), (float)(lIlllllIIIllIll.getY() + lIlllllIIIllIll.getDimension().height), ClickGui.getColor());
        }
        if (lIIlIIlllI(lIlllllIIIllIll.isMaximized() ? 1 : 0)) {
            this.drawExpanded(lIlllllIIIllIll.getX() + lIlllllIIIllIll.getDimension().width - DarkExpandingButton.llIIIlII[7], lIlllllIIIllIll.getY() + DarkExpandingButton.llIIIlII[8], DarkExpandingButton.llIIIlII[9], (boolean)(DarkExpandingButton.llIIIlII[1] != 0), new Color(DarkExpandingButton.llIIIlII[0], DarkExpandingButton.llIIIlII[0], DarkExpandingButton.llIIIlII[0], DarkExpandingButton.llIIIlII[0]).hashCode());
            "".length();
            if (-"   ".length() >= 0) {
                return;
            }
        }
        else {
            this.drawExpanded(lIlllllIIIllIll.getX() + lIlllllIIIllIll.getDimension().width - DarkExpandingButton.llIIIlII[7], lIlllllIIIllIll.getY() + DarkExpandingButton.llIIIlII[8], DarkExpandingButton.llIIIlII[9], (boolean)(DarkExpandingButton.llIIIlII[2] != 0), new Color(DarkExpandingButton.llIIIlII[0], DarkExpandingButton.llIIIlII[0], DarkExpandingButton.llIIIlII[0], DarkExpandingButton.llIIIlII[0]).hashCode());
        }
        if (lIIlIIllII(lIlllllIIIllIll.isMaximized() ? 1 : 0)) {
            lIlllllIIIllIll.renderChildren(lIlllllIIIlllIl, lIlllllIIIlllII);
        }
    }
    
    public DarkExpandingButton(final Theme lIlllllIIlIlIlI) {
        super(ComponentType.EXPANDING_BUTTON, lIlllllIIlIlIlI);
    }
    
    private static boolean lIIlIIllII(final int lIlllllIIIIlIlI) {
        return lIlllllIIIIlIlI != 0;
    }
    
    private static boolean lIIlIIlllI(final int lIlllllIIIIlIII) {
        return lIlllllIIIIlIII == 0;
    }
}
