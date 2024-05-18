// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click.theme.dark;

import com.krazzzzymonkey.catalyst.gui.click.base.ComponentType;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;
import com.krazzzzymonkey.catalyst.module.modules.gui.ClickGui;
import com.krazzzzymonkey.catalyst.gui.click.elements.Dropdown;
import com.krazzzzymonkey.catalyst.gui.click.base.Component;
import com.krazzzzymonkey.catalyst.gui.click.base.ComponentRenderer;

public class DarkDropDown extends ComponentRenderer
{
    private static final /* synthetic */ int[] lIIlIlI;
    
    @Override
    public void drawComponent(final Component llIllllIllIIIII, final int llIllllIlIlllll, final int llIllllIlIllllI) {
        final Dropdown llIllllIlIlllIl = (Dropdown)llIllllIllIIIII;
        final String llIllllIlIlllII = llIllllIlIlllIl.getText();
        this.theme.fontRenderer.drawString(llIllllIlIlllII, llIllllIlIlllIl.getX() + DarkDropDown.lIIlIlI[0], llIllllIlIlllIl.getY() + (llIllllIlIlllIl.getDropdownHeight() / DarkDropDown.lIIlIlI[1] - this.theme.fontRenderer.FONT_HEIGHT / DarkDropDown.lIIlIlI[2]), ClickGui.getColor());
        "".length();
        if (llllIlll(llIllllIlIlllIl.isMaximized() ? 1 : 0)) {
            llIllllIlIlllIl.renderChildren(llIllllIlIlllll, llIllllIlIllllI);
        }
    }
    
    private static boolean llllIlll(final int llIllllIlIlIIII) {
        return llIllllIlIlIIII != 0;
    }
    
    static {
        llllIllI();
    }
    
    public DarkDropDown(final Theme llIllllIllIlIII) {
        super(ComponentType.DROPDOWN, llIllllIllIlIII);
    }
    
    @Override
    public void doInteractions(final Component llIllllIlIlIlII, final int llIllllIlIlIIll, final int llIllllIlIlIIlI) {
    }
    
    private static void llllIllI() {
        (lIIlIlI = new int[3])[0] = (66 + 123 - 57 + 33 ^ 105 + 45 - 101 + 111);
        DarkDropDown.lIIlIlI[1] = "  ".length();
        DarkDropDown.lIIlIlI[2] = (0x54 ^ 0x50);
    }
}
