// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.theme.dark;

import com.krazzzzymonkey.catalyst.gui.click.elements.Text;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentRenderer;

public class DarkText extends ComponentRenderer
{
    private static final /* synthetic */ int[] llIlI;
    
    public DarkText(final Theme lllIlllIllIlllI) {
        super(ComponentType.TEXT, lllIlllIllIlllI);
    }
    
    private static boolean llIlllI(final int lllIlllIlIIlIll, final int lllIlllIlIIlIlI) {
        return lllIlllIlIIlIll < lllIlllIlIIlIlI;
    }
    
    @Override
    public void drawComponent(final Component lllIlllIllIIIII, final int lllIlllIlIlllll, final int lllIlllIlIllllI) {
        final Text lllIlllIlIlllIl = (Text)lllIlllIllIIIII;
        final String[] lllIlllIlIlllII = lllIlllIlIlllIl.getMessage();
        int lllIlllIlIllIll = lllIlllIlIlllIl.getY();
        final char lllIlllIlIlIlIl = (Object)lllIlllIlIlllII;
        final short lllIlllIlIlIlII = (short)lllIlllIlIlIlIl.length;
        float lllIlllIlIlIIll = DarkText.llIlI[0];
        while (llIlllI((int)lllIlllIlIlIIll, lllIlllIlIlIlII)) {
            final String lllIlllIllIIIlI = lllIlllIlIlIlIl[lllIlllIlIlIIll];
            this.theme.fontRenderer.drawString(lllIlllIllIIIlI, lllIlllIlIlllIl.getX() - DarkText.llIlI[1], lllIlllIlIllIll - DarkText.llIlI[1], DarkText.llIlI[2]);
            "".length();
            lllIlllIlIllIll += 10;
            ++lllIlllIlIlIIll;
            "".length();
            if ((0x84 ^ 0x80) != (0x33 ^ 0x37)) {
                return;
            }
        }
    }
    
    @Override
    public void doInteractions(final Component lllIlllIlIlIIII, final int lllIlllIlIIllll, final int lllIlllIlIIlllI) {
    }
    
    static {
        llIllIl();
    }
    
    private static void llIllIl() {
        (llIlI = new int[3])[0] = ((174 + 37 - 103 + 122 ^ 86 + 29 - 49 + 119) & (0x46 ^ 0xB ^ (0x1A ^ 0x8) ^ -" ".length()));
        DarkText.llIlI[1] = (0x9F ^ 0x9B);
        DarkText.llIlI[2] = -" ".length();
    }
}
