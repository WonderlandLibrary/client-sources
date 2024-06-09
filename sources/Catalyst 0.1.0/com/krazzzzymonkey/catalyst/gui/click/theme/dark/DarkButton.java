// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.theme.dark;

import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import com.krazzzzymonkey.catalyst.utils.visual.GLUtils;
import com.krazzzzymonkey.catalyst.utils.visual.ColorUtils;
import com.krazzzzymonkey.catalyst.gui.click.elements.Button;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentRenderer;

public class DarkButton extends ComponentRenderer
{
    private static final /* synthetic */ int[] lllllll;
    
    public DarkButton(final Theme llIlIIIlIlIIIIl) {
        super(ComponentType.BUTTON, llIlIIIlIlIIIIl);
    }
    
    private static void llIIllIIl() {
        (lllllll = new int[8])[0] = (0x2A ^ 0x18);
        DarkButton.lllllll[1] = (94 + 105 + 31 + 0 ^ 4 + 124 - 94 + 96);
        DarkButton.lllllll[2] = 186 + 27 - 20 + 62;
        DarkButton.lllllll[3] = (0xFF ^ 0xBD ^ (0x65 ^ 0x61));
        DarkButton.lllllll[4] = " ".length();
        DarkButton.lllllll[5] = (0x2D ^ 0x28);
        DarkButton.lllllll[6] = "  ".length();
        DarkButton.lllllll[7] = ("   ".length() ^ (0x9F ^ 0x98));
    }
    
    private static boolean llIIllIlI(final int llIlIIIlIIIIIll) {
        return llIlIIIlIIIIIll != 0;
    }
    
    static {
        llIIllIIl();
    }
    
    @Override
    public void doInteractions(final Component llIlIIIlIIIIlll, final int llIlIIIlIIIIllI, final int llIlIIIlIIIIlIl) {
    }
    
    @Override
    public void drawComponent(final Component llIlIIIlIIlIlll, final int llIlIIIlIIIlllI, final int llIlIIIlIIlIlIl) {
        final Button llIlIIIlIIlIlII = (Button)llIlIIIlIIlIlll;
        final String llIlIIIlIIlIIll = llIlIIIlIIlIlII.getText();
        int llIlIIIlIIlIIlI = ColorUtils.color(DarkButton.lllllll[0], DarkButton.lllllll[0], DarkButton.lllllll[0], DarkButton.lllllll[1]);
        final int llIlIIIlIIlIIIl = ColorUtils.color(DarkButton.lllllll[2], DarkButton.lllllll[2], DarkButton.lllllll[2], DarkButton.lllllll[2]);
        if (llIIllIlI(GLUtils.isHovered(llIlIIIlIIlIlII.getX(), llIlIIIlIIlIlII.getY(), llIlIIIlIIlIlII.getDimension().width, llIlIIIlIIlIlII.getDimension().height, llIlIIIlIIIlllI, llIlIIIlIIlIlIl) ? 1 : 0)) {
            llIlIIIlIIlIIlI = ColorUtils.color(DarkButton.lllllll[3], DarkButton.lllllll[3], DarkButton.lllllll[3], DarkButton.lllllll[2]);
        }
        if (llIIllIlI(llIlIIIlIIlIlII.isEnabled() ? 1 : 0)) {
            RenderUtils.drawRect((float)llIlIIIlIIlIlII.getX(), (float)llIlIIIlIIlIlII.getY(), (float)(llIlIIIlIIlIlII.getX() + llIlIIIlIIlIlII.getDimension().width - DarkButton.lllllll[4]), (float)(llIlIIIlIIlIlII.getY() + llIlIIIlIIlIlII.getDimension().height), llIlIIIlIIlIIIl);
            "".length();
            if ("   ".length() < 0) {
                return;
            }
        }
        else {
            RenderUtils.drawRect((float)llIlIIIlIIlIlII.getX(), (float)llIlIIIlIIlIlII.getY(), (float)(llIlIIIlIIlIlII.getX() + llIlIIIlIIlIlII.getDimension().width - DarkButton.lllllll[4]), (float)(llIlIIIlIIlIlII.getY() + llIlIIIlIIlIlII.getDimension().height), llIlIIIlIIlIIlI);
        }
        this.theme.fontRenderer.drawString(llIlIIIlIIlIIll, llIlIIIlIIlIlII.getX() + DarkButton.lllllll[5], llIlIIIlIIlIlII.getY() + (llIlIIIlIIlIlII.getDimension().height / DarkButton.lllllll[6] - this.theme.fontRenderer.FONT_HEIGHT / DarkButton.lllllll[7]), ColorUtils.color(DarkButton.lllllll[2], DarkButton.lllllll[2], DarkButton.lllllll[2], DarkButton.lllllll[2]));
        "".length();
    }
}
